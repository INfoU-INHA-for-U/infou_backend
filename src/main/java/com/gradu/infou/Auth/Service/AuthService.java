package com.gradu.infou.Auth.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gradu.infou.Auth.Dto.*;
import com.gradu.infou.Auth.Utils.JwtUtil;
import com.gradu.infou.Config.BaseResponse;
import com.gradu.infou.Config.BaseResponseStatus;
import com.gradu.infou.Config.exception.BaseException;
import com.gradu.infou.Domain.Entity.User;
import com.gradu.infou.Repository.UserRepository;
import com.gradu.infou.Service.RedisService;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.OutputStream;
import java.time.Duration;
import java.util.Optional;

import static com.gradu.infou.Config.BaseResponseStatus.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final RedisService redisService;

    private final Long accessTokenExpiredAtMs=1000*60*60L; //1시간
    private final Long refreshTokenExpiredAtMs=1000*60*60*24*14L; //2주

    @Value("${jwt.secret.access}")
    private String secretAccessKey;
    @Value("${jwt.secret.refresh}")
    private String secretRefreshKey;

    public TokenResDto join(JoinReqDto joinReqDto){

        isPresentUser(joinReqDto.getAuthId());

        //인하대 메일인지 확인
        if(!validEmail(joinReqDto.getEmail())){
            throw new BaseException(INVALID_EMAIL);
        }

        User newUser = joinReqDto.toUserEntity();
        User saveUser = userRepository.save(newUser);

        String accessToken=jwtUtil.createJwt(saveUser.getId(), accessTokenExpiredAtMs, secretAccessKey);
        String refreshToken=jwtUtil.createJwt(saveUser.getId(), refreshTokenExpiredAtMs, secretRefreshKey);

        //redis에 저장
        redisService.setValues(saveUser.getId().toString(),refreshToken, Duration.ofMillis(refreshTokenExpiredAtMs));

        return TokenResDto.fromEntity(accessToken, refreshToken);
    }

    public TokenResDto login(LoginReqDto loginReqDto){

        User user = userRepository.findByAuthId(loginReqDto.getAuthId()).
                orElseThrow(() -> new BaseException(USERS_NOT_FOUND));

        String accessToken = jwtUtil.createJwt(user.getId(), accessTokenExpiredAtMs, secretAccessKey);
        String refreshToken = jwtUtil.createJwt(user.getId(), refreshTokenExpiredAtMs,secretRefreshKey);

        //redis에 저장
        redisService.setValues(user.getId().toString(),refreshToken, Duration.ofMillis(refreshTokenExpiredAtMs));

        return TokenResDto.fromEntity(accessToken, refreshToken);
    }

    public TokenResDto refreshToken(HttpServletRequest request, HttpServletResponse response){
        //refresh token이 유효한지 확인
        String token = jwtUtil.resolveToken(request);

        if(token==null||!jwtUtil.validToken(token, secretRefreshKey)){
            throw new BaseException(INVALID_JWT);
        }

        String id = jwtUtil.getId(token, secretRefreshKey);
        Long idL = Long.parseLong(id);

        //redis에 refresh token이 있는가?
        String redisRefresh = redisService.getValues(id);
        if(redisRefresh=="false"||!redisRefresh.equals(token)){ //없거나 refresh token 값이 일치하지 않는 경우
            //탈취됐다고 간주, redis에 user id와 관련된 토큰 삭제
            redisService.deleteKey(id);
            throw new BaseException(INVALID_REFRESH);
        }

        String accessToken = jwtUtil.createJwt(idL, accessTokenExpiredAtMs, secretAccessKey);
        String refreshToken = jwtUtil.createJwt(idL, refreshTokenExpiredAtMs, secretRefreshKey);

        //redis에 새로운 refresh token 갱신
        redisService.setValues(id, refreshToken, Duration.ofMillis(refreshTokenExpiredAtMs));

        return TokenResDto.fromEntity(accessToken, refreshToken);
    }

    @Transactional
    public void logout(HttpServletRequest request, HttpServletResponse response) {

        log.info("111");

        String token = jwtUtil.resolveToken(request);

        if(token==null||!jwtUtil.validToken(token, secretAccessKey)){
            outputStream(response, INVALID_JWT);
            return;
        }

        String id = jwtUtil.getId(token, secretAccessKey);

        //redis에 refresh token이 있는가?
        String redisRefresh = redisService.getValues(id);
        if(redisRefresh!="false"){ //refresh token이 저장되어 있는 경우, 삭제
            redisService.deleteKey(id);
        }

    }

    public static void outputStream(HttpServletResponse response, BaseResponseStatus status){
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

        try (OutputStream os = response.getOutputStream()) {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(os, new BaseResponse(status));
            os.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    private boolean isPresentUser(String clientId) {
        userRepository.findByAuthId(clientId).ifPresent(user -> {
            throw new BaseException(USER_ALREADY_EXIST);
        });
        return true;
    }

    private boolean validEmail(String email){
        String domain = email.substring(email.indexOf("@") + 1);
        return domain.equals("inha.edu") || domain.equals("inha.ac.kr");
    }

}
