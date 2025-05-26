package tazoria.banimo.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import tazoria.banimo.common.utils.jwt.JwtFilter;
import tazoria.banimo.common.utils.jwt.JwtUtil;
import tazoria.banimo.common.utils.jwt.LoginFilter;

import java.util.Collections;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;
    private final JwtUtil jwtUtil;


    /**
     * Spring Security의 AuthenticationManager를 빈으로 등록
     * - 로그인 시 사용자의 인증(Authentication)을 담당
     * @return
     * @throws Exception
     */
    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * 비밀번호 암호화를 위한 BCryptPasswordEncoder 빈 등록
     * - 회원가입 시 비밀번호를 안전하게 암호화하여 저장
     * @return
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * CORS 설정을 위한 Bean 등록
     * - 프론트엔드(React 등)에서 API 요청 시 CORS 문제 해결
     * @return
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        return request -> {
            CorsConfiguration configuration = new CorsConfiguration();
            configuration.setAllowedOrigins(Collections.singletonList("http://localhost:8080"));    // 프론트엔드 주소
            configuration.setAllowedMethods(Collections.singletonList("*")); // 모든 HTTP 메소드 허용
            configuration.setAllowCredentials(true); // 자격 증명 허용 (쿠키, 인증 헤더 등)
            configuration.setAllowedHeaders(Collections.singletonList("*")); // 모든 헤더 허용
            configuration.setExposedHeaders(Collections.singletonList("Authorization")); // 응답 헤더에 Authorization 노출
            configuration.setMaxAge(3600L); // CORS preflight 요청의 캐시 시간 설정 (1시간)
            return configuration;
        };
    }

    /**
     * CORS 필터를 빈으로 등록
     * - CORS 설정을 적용하기 위한 필터
     * @param http
     * @return
     * @throws Exception
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // CORS 설정 적용
                .csrf(csrf ->  csrf.disable()) // CSRF 보호 비활성화 (API 서버에서는 일반적으로 비활성화)
                .formLogin(form -> form.disable()) // 폼 로그인 비활성화 (API 서버에서는 일반적으로 비활성화)
                .httpBasic(httpBasic -> httpBasic.disable()) // HTTP Basic 인증 비활성화 (API 서버에서는 일반적으로 비활성화)

                // 엔드포인트별 접근 권한 설정
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/", "/signup", "/user/login", "/user/signup").permitAll() // 로그인 및 회원가입 API는 인증 없이 접근 허용
                        .requestMatchers("/admin").hasAuthority("ROLE_ADMIN") // 관리자 전용 API
                        .anyRequest().authenticated()) // 나머지 요청은 인증 필요

                // JWT 필터 추가 (기존 UsernamePasswordAuthenticationFilter 이전에 실행)
                .addFilterBefore(new JwtFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class)
                // 로그인 필터 추가 (JWTFilter 실행 후 JWT 발급 처리)
                .addFilterAfter(new LoginFilter(authenticationManager(), jwtUtil), JwtFilter.class)
                // 세션을 사용하지 않음 (JWT 기반 인증이므로 STATELESS 모드 설정)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }
}
