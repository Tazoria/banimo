package tazoria.banimo.common.utils.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public JwtTokenFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String path = request.getRequestURI();
        if (path.startsWith("/user/login")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = resolveToken(request);

        try {
            if (token != null && jwtTokenProvider.validateToken(token) == JwtCode.ACCESS && SecurityContextHolder.getContext().getAuthentication() == null) {
                Authentication authentication = jwtTokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                log.info("Authentication 클래스: {}", authentication.getClass().getName());
                log.info("JWT 인증 완료");
            }
        }catch(Exception e) {
            log.warn("JWT 필터 예외: {}", e.getMessage());
        }

        // 다음 필터로 이동
        filterChain.doFilter(request, response);
    }

    // 헤더에서 토큰정보 반환
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        return (bearerToken != null && bearerToken.startsWith("Bearer "))
                ? bearerToken.substring(7)
                : null;
    }
}
