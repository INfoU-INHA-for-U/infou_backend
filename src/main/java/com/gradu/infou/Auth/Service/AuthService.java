package com.gradu.infou.Auth.Service;

import com.gradu.infou.Auth.Dto.JoinReqDto;
import com.gradu.infou.Auth.Dto.JoinResDto;
import com.gradu.infou.Auth.Dto.LoginReqDto;
import com.gradu.infou.Auth.Dto.LoginResDto;
import com.gradu.infou.Auth.Utils.JwtUtil;
import com.gradu.infou.Config.exception.BaseException;
import com.gradu.infou.Domain.Entity.User;
import com.gradu.infou.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.gradu.infou.Config.BaseResponseStatus.USERS_NOT_FOUND;
import static com.gradu.infou.Config.BaseResponseStatus.USER_ALREADY_EXIST;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    //1000*60*60L;
    private final Long expiredAtMs=1000*60*60L;


    public String createToken(String userName){
        return jwtUtil.createJwt(userName, expiredAtMs);
    }


    public boolean isPresentUser(String clientId) {
        userRepository.findByAuthId(clientId).ifPresent(user -> {
            throw new BaseException(USER_ALREADY_EXIST);
        });
        return true;
    }

    public JoinResDto join(JoinReqDto joinReqDto){

        isPresentUser(joinReqDto.getAuthId());

        User newUser = joinReqDto.toUserEntity();
        userRepository.save(newUser);

        String token=createToken(newUser.getAuthId());

        return JoinResDto.fromEntity(token);
    }

    public LoginResDto login(LoginReqDto loginReqDto){

        User user = userRepository.findByAuthId(loginReqDto.getAuthId()).
                orElseThrow(() -> new BaseException(USERS_NOT_FOUND));


        return LoginResDto.fromEntity(createToken(user.getAuthId()));
    }

}
