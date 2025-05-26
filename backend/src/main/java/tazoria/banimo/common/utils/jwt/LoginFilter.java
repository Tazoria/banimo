package tazoria.banimo.common.utils.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import java.io.IOException;
import tazoria.banimo.common.constants.ResultMessage;
import tazoria.banimo.user.service.CustomUserDetails;

@RequiredArgsConstructor
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);

        // AuthenticationManager를 통해 인증 수행
        return authenticationManager.authenticate(authRequest);
    }

    /**
     * 로그인 성공 시 JWT 토큰 발급
     * @param req
     * @param res
     * @param chain
     * @param auth the object returned from the <tt>attemptAuthentication</tt>
     * method.
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication auth) {
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        String username = userDetails.getUsername();
        String role = userDetails.getAuthorities().iterator().next().getAuthority();
        String token = jwtUtil.createToken(username, role, 60 * 60 * 1000L); // 1시간 유효한 토큰 생성
        res.addHeader("Authorization", "Bearer " + token);
    }

    /**
     * 로그인 실패 시 401 응답 반환
     * @param req
     * @param res
     * @param failed
     * @throws IOException
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest req, HttpServletResponse res, AuthenticationException failed) throws IOException {
        res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        res.setContentType("application/json;charset=UTF-8");
        res.getWriter().write(ResultMessage.JOIN_FAILED + ": " + failed.getMessage());
    }
}
