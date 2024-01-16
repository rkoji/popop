package com.example.popop.global.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
@Slf4j
public class JwtUtil {

    private final Key key;

        public JwtUtil(@Value("${jwt.secret}") String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }


    public String getLoginId(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            return claims.get("loginId", String.class);
        } catch (SignatureException e) {
            // 토큰의 서명이 유효하지 않은 경우
            log.error("Invalid token signature: {}", e.getMessage());
            throw new RuntimeException("Invalid token signature");
        } catch (ExpiredJwtException e) {
            // 토큰이 만료된 경우
            log.error("Token has expired: {}", e.getMessage());
            throw new RuntimeException("Token has expired");
        } catch (JwtException e) {
            // 그 외의 JWT 예외 처리
            log.error("JWT exception: {}", e.getMessage());
            throw new RuntimeException("JWT exception");
        }
    }

    // 발급된 Token이 만료 시간이 지났는지 체크
    public boolean isExpired(String token) {
        Date expiredDate = extractClaims(token).getExpiration();
        // Token의 만료 날짜가 지금보다 이전인지 확인
        return expiredDate.before(new Date());
    }

    public String createJwt(String loginId, String role, Long expiredMs) {
        Claims claims = Jwts.claims();
        claims.put("loginId", loginId);
        claims.put("role", role);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiredMs))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // SecretKey를 사용해 Token Parsing
    private Claims extractClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

}
