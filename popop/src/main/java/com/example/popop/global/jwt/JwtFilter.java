package com.example.popop.global.jwt;

import com.example.popop.domain.user.entity.User;
import com.example.popop.domain.user.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private static final String BEARER_PREFIX = "Bearer ";

    @Value("${jwt.secret}")
    private final String secretKey;
    private final JwtUtil jwtUtil;
    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        logger.info("authorization : " + authorization);

        // token 안
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            logger.info("authorization을 잘못 보냈습니다.");
            filterChain.doFilter(request, response);
            return;
        }

        // token 꺼내기
//        String token = authorization.split(" ")[1];
        String token = authorization.substring("Bearer ".length());
        logger.info("token : " + token);


        // token Expired되었는지
        if (jwtUtil.isExpired(token)) {
            logger.error("Token이 만료 되었습니다.");
            filterChain.doFilter(request, response);
            return;
        }


        String loginId = jwtUtil.getLoginId(token);
        logger.info("loginId : " + loginId);

        User loginUser = userService.getLoginId(loginId);

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginUser.getLoginId(), null
                        , List.of(new SimpleGrantedAuthority(loginUser.getRole().name())));
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        // 권한 부여
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);
    }
}
