package com.gradu.infou.Service;


import com.gradu.infou.Domain.Entity.RewardDocument;
import com.gradu.infou.Domain.Entity.User;
import com.gradu.infou.Repository.RewardRepository;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class RewardService {
    private final UserService userService;
    private final RewardRepository rewardRepository;

    @Transactional
    public void increaseReward(HttpServletRequest request){ //강의평 creat controller(custom annotation)->강의평 creat service(User repository)->리워드 증가 함수(User) => 하나의 transaction
        User user = userService.findUserByRequest(request);
        user.setReward(user.getReward()+50L);
        RewardDocument rewardDocument = RewardDocument.toRewardDocument(user.getId().toString(), "강의평 작성", 50L, user.getReward());
        rewardRepository.save(rewardDocument);
    }

    public Page<RewardDocument> rewardList(HttpServletRequest request, Pageable pageable){
        User user = userService.findUserByRequest(request);
        Page<RewardDocument> rewardDocuments = rewardRepository.findAllByUserId(user.getId().toString(), pageable);
        return rewardDocuments;
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
