package com.gradu.infou.Auth.Utils;

import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class JwtUtil {

    public String getId(String token, String secretKey){
        return Jwts.parser().setSigningKey(secretKey).build().parseClaimsJws(token).getBody().get("id", String.class);
    }


    public boolean validToken(String token, String secretKey){
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
        } catch (SignatureException e){
            log.error("Invalid Secret");
        }
        return false;
    }

    public String createJwt(Long id, Long expiredMs, String secretKey){
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
