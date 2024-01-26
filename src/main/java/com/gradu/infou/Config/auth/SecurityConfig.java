package com.gradu.infou.Config.auth;

import com.gradu.infou.Service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig{

    private final LoginService loginService;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http
                .authorizeRequests()
                .requestMatchers("/v1/**").authenticated() //private로 시작하는 uri는 로그인 필수
                .anyRequest().permitAll() //나머지 uri는 모든 접근 허용
                .and()
                .oauth2Login((loginConfigure)->
                                loginConfigure.loginPage("/login")
                                        .userInfoEndpoint((userInfo)->
                                                userInfo.userService(loginService)) //로그인 완료 후 회원 정보 받기
                                        .defaultSuccessUrl("/") //OAuth 구글 로그인이 성공하면 이동할 uri 설정
                        )
                .logout((logoutConfigurer)->
                        logoutConfigurer.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                .logoutSuccessUrl("/login")
                )
                .build();
    }

}
