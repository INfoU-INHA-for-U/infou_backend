package com.gradu.infou.Service;

import com.gradu.infou.Auth.Utils.JwtUtil;
import com.gradu.infou.Config.exception.BaseException;
import com.gradu.infou.Domain.Entity.User;
import com.gradu.infou.Repository.UserRepository;
import com.nimbusds.oauth2.sdk.GeneralException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Optional;

import static com.gradu.infou.Config.BaseResponseStatus.*;

@Service
@RequiredArgsConstructor
public class RewardService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;


    @Transactional
    public void increaseReward(User user){ //강의평 creat controller(custom annotation)->강의평 creat service(User repository)->리워드 증가 함수(User) => 하나의 transaction
//        String authId = getClient(request);
//
//        User user = userRepository.findByAuthId(authId)
//                .orElseThrow(()->new BaseException(USERS_NOT_FOUND));

        user.modifyReward(true, 10L);
    }

//    private String getClient(HttpServletRequest request){
//        String token = jwtUtil.resolveToken(request);
//        if(token==null) {
//            throw new BaseException(EMPTY_JWT);
//        }
//
//        if(jwtUtil.validToken(token)==false){
//            throw new BaseException(INVALID_JWT);
//        }
//
//        return jwtUtil.getClientId(token);
//    }
}
