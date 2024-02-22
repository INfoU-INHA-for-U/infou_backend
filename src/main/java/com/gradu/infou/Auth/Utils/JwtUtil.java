package com.gradu.infou.Auth.Utils;

import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.security.SignatureException;
import java.util.Date;

@Slf4j
@Component
public class JwtUtil {
    @Value("${jwt.secret}")
    private String secretKey;

    public String getId(String token){
        return Jwts.parser().setSigningKey(secretKey).build().parseClaimsJws(token).getBody().get("id", String.class);
    }


    public boolean validToken(String token){
        try{
            Jwts.parser().setSigningKey(secretKey).build().parseClaimsJws(token).getBody().getExpiration().before(new Date());
            return true;

        } catch (ExpiredJwtException e){
            log.error("Expired JWT Token");
        } catch (MalformedJwtException e){
            log.error("Invalid JWT Token");
        } catch (UnsupportedJwtException e){
            log.error("Unsupported JWT Token");
        } catch (IllegalArgumentException e){
            log.error("JWT claims string is empty");
        }
        return false;
    }

    public String createJwt(Long id, Long expiredMs){
        return Jwts.builder()
                .claim("id", id.toString())
                .issuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+expiredMs))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String resolveToken(HttpServletRequest request){

        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);

        // token 안 보내면 Block
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            return null;
        }

        //Token 꺼내기
        return authorization.split(" ")[1];
    }
}
