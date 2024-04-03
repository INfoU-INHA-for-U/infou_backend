package com.gradu.infou.Service;

import com.gradu.infou.Auth.Utils.JwtUtil;
import com.gradu.infou.Config.exception.BaseException;
import com.gradu.infou.Domain.Dto.Controller.UserPutReqDto;
import com.gradu.infou.Domain.Dto.Service.UserDetailResDto;
import com.gradu.infou.Domain.Entity.RewardDocument;
import com.gradu.infou.Domain.Entity.User;
import com.gradu.infou.Repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.gradu.infou.Config.BaseResponseStatus.*;

@Service
@RequiredArgsConstructor
public class UserService {
    @Value("${jwt.secret.access}")
    private String secretAccessKey;
    private final UserRepository userRepository;

    public UserDetailResDto detailUser(HttpServletRequest request){
        User user = findUserByRequest(request);
        UserDetailResDto userDetailResDto = UserDetailResDto.toUserDetailResDto(user);

        return userDetailResDto;
    }

    public User findUserByRequest(HttpServletRequest request){
        String token = JwtUtil.resolveToken(request);
        if(token==null||!JwtUtil.validToken(token, secretAccessKey)){
            throw new BaseException(INVALID_JWT);
        }
        String userId = JwtUtil.getId(token, secretAccessKey);
        User user = userRepository.findById(Long.parseLong(userId)).orElseThrow(() -> new BaseException(USERS_NOT_FOUND));
        return user;
    }

    @Transactional
    public void userUpdate(HttpServletRequest request, UserPutReqDto userPutReqDto){
        User user = findUserByRequest(request);
        user.setName(userPutReqDto.getName());
        user.setMajor(userPutReqDto.getMajor());
        user.setGrade(userPutReqDto.getGrade());
        user.setSelectNotice(userPutReqDto.getSelectNotice());
    }



    public String getNick(HttpServletRequest request){
        User user = findUserByRequest(request);
        return user.getName();
    }

//    public Page<RewardDocument> userRewardList(HttpServletRequest request){
//        User user = findUserByRequest(request);
//        Page<RewardDocument> rewardDocuments = rewardService.rewardList(user);
//        return rewardDocuments;
//    }


}
