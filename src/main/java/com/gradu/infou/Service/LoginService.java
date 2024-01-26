package com.gradu.infou.Service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class LoginService extends DefaultOAuth2UserService {
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException{
        OAuth2User oAuth2User = super.loadUser(userRequest);
        System.out.println("oAuth2User = "+oAuth2User.getAttributes());
        return super.loadUser(userRequest);
    }
    public void socialLogin(String code, String registrationId){
        log.warn("code: "+code);
        log.warn("registration: "+registrationId);
        System.out.println("code= "+code);
        System.out.println("registrationId= "+registrationId);
    }
}
