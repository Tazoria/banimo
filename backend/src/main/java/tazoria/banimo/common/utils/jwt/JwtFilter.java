package tazoria.banimo.common.utils.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import tazoria.banimo.common.constants.JwtCode;
import tazoria.banimo.common.constants.UserRole;
import tazoria.banimo.user.entity.UserEntity;
import tazoria.banimo.user.service.CustomUserDetails;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) throws ServletException, IOException {

        String uri = req.getRequestURI();

        // 인증 제외 URL
        if (uri.equals("/user/signup") || uri.equals("/user/login")) {
            filterChain.doFilter(req, res);  // 그냥 통과시킴
            return;
        }

        String authorizationHeader = req.getHeader("Authorization");

        if(authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            log.warn(JwtCode.UNAUTHORIZED_HEADER.getMessage());
            filterChain.doFilter(req, res);
            return;
        }

        // "Bearer " 이후의 토큰 값만 추출
        String token = authorizationHeader.substring(7);

        try {
            // JWT 토큰 만료 여부 확인
            if(jwtUtil.isTokenExpired(token)) {
                log.warn(JwtCode.EXPIRED.getMessage());
                res.sendError(HttpServletResponse.SC_UNAUTHORIZED, JwtCode.EXPIRED.getMessage());
                return;
            }

            // JWT에서 사용자 정보 추출
            String username = jwtUtil.getUsername(token);
            UserRole role = UserRole.valueOf(jwtUtil.getRole(token));

            // 인증 객체 생성
            UserEntity user = UserEntity.builder()
                    .username(username)
                    .password("N/A") // 비밀번호는 JWT 기반 인증이므로 사용하지 않음
                    .role(role)
                    .build();

            CustomUserDetails customUserDetails = new CustomUserDetails(user);
            Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());

            // SecurityContext에 인증 정보 저장
            if(SecurityContextHolder.getContext().getAuthentication() == null) {
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }catch (Exception e) {
            log.error(JwtCode.INVALID_TOKEN.getMessage());
            res.sendError(HttpServletResponse.SC_UNAUTHORIZED, JwtCode.INVALID_TOKEN.getMessage());
            return;
        }

        filterChain.doFilter(req, res);
    }
}
