package com.gradu.infou.Service;

import com.gradu.infou.Auth.Utils.JwtUtil;
import com.gradu.infou.Config.exception.BaseException;
import com.gradu.infou.Domain.Entity.User;
import com.gradu.infou.Repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static com.gradu.infou.Config.BaseResponseStatus.INVALID_JWT;
import static com.gradu.infou.Config.BaseResponseStatus.USERS_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class UserService {
    @Value("${jwt.secret.access}")
    private String secretAccessKey;
    private final UserRepository userRepository;
    public User findUserByRequest(HttpServletRequest request){
        String token = JwtUtil.resolveToken(request);
        if(token==null||!JwtUtil.validToken(token, secretAccessKey)){
            throw new BaseException(INVALID_JWT);
        }
        String userId = JwtUtil.getId(token, secretAccessKey);
        User user = userRepository.findById(Long.parseLong(userId)).orElseThrow(() -> new BaseException(USERS_NOT_FOUND));
        return user;
    }
}
