package com.gradu.infou.Config;

import com.gradu.infou.Auth.Utils.JwtFilter;
import com.gradu.infou.Domain.Entity.Enum.Role;
import com.gradu.infou.Service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig{

    private static final String[] WHITE_LIST_URL = {
            "/",
            "/test",
            "/api/v1/auth/**",
            "/v2/api-docs",
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui/**",
            "/webjars/**",
            "/swagger-ui.html"
            ,"/h2-console/**"
            ,"/error/**"
            ,"/login/oauth2/code/kakao"
    };
    private final JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.httpBasic(HttpBasicConfigurer::disable)
                .csrf(CsrfConfigurer::disable)
                .cors(cors->cors.configurationSource(corsConfiguration()))
                .sessionManagement(config->config
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) //jwt 사용하는 경우 씀
                );

        //권한에 따라 다르게
        http.authorizeRequests()
                .requestMatchers(WHITE_LIST_URL).permitAll()
                .requestMatchers("/api/v1/user/**").hasRole(Role.USER.name())
                .requestMatchers("/api/v1/admin/**").hasRole(Role.ADMIN.name())
                .anyRequest().permitAll(); //나머지 uri은 인증만

        //filter 적용
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

//                http.oauth2Login((loginConfigure) ->
//                        loginConfigure.loginPage("/login")
//                                .userInfoEndpoint((userInfo) ->
//                                        userInfo.userService(loginService)) //로그인 완료 후 회원 정보 받기
//                                .defaultSuccessUrl("/") //OAuth 구글 로그인이 성공하면 이동할 uri 설정
//                )
//                .logout((logoutConfigurer) ->
//                        logoutConfigurer.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
//                                .logoutSuccessUrl("/login")
//                )
//                .build();

        return http.build();
    }

    @Bean
    protected CorsConfigurationSource corsConfiguration(){
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.addAllowedHeader("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);

        return source;
    }

}
