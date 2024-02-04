package com.gradu.infou.Auth.Utils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {

            //Token 꺼내기
            String token = resolveToken(request);

            //Token validation 여부
            if (token==null||!jwtUtil.validToken(token)) {
                filterChain.doFilter(request, response);
                return;
            }

            //UserName Token에서 꺼내기
            String userName = jwtUtil.getClientId(token);

            // 토큰이 유효하고 만료되지 않았다면 SecurityContext에 인증 정보를 저장
            // 토큰이 만료되지 않았는지는 JwtService에서 확인
            if (SecurityContextHolder.getContext().getAuthentication() == null) {

                //user에 맞는 권한 부여
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(userName);

                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }

        } catch (Exception exception) {
            log.error("Exception [Err_Msg]: {}", exception.getMessage());
            //exception.printStackTrace();
        }

        filterChain.doFilter(request, response);
    }

    private String resolveToken(HttpServletRequest request){

        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);

        // token 안 보내면 Block
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            return null;
        }

        //Token 꺼내기
        return authorization.split(" ")[1];
    }
}
