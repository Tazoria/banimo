    package tazoria.banimo.common.utils.jwt;
    
    import io.jsonwebtoken.*;
    import io.jsonwebtoken.security.Keys;
    import io.jsonwebtoken.security.SignatureException;
    import jakarta.annotation.PostConstruct;
    import jakarta.transaction.Transactional;
    import lombok.RequiredArgsConstructor;
    import lombok.extern.slf4j.Slf4j;
    
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.beans.factory.annotation.Value;
    import org.springframework.core.env.Environment;
    import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
    import org.springframework.security.core.Authentication;
    import org.springframework.security.core.GrantedAuthority;
    import org.springframework.security.core.userdetails.User;
    import org.springframework.security.core.authority.SimpleGrantedAuthority;
    import org.springframework.stereotype.Component;
    
    import java.nio.charset.StandardCharsets;
    import java.security.Key;
    import java.util.Arrays;
    import java.util.Collection;
    import java.util.Date;
    import java.util.stream.Collectors;
    
    @RequiredArgsConstructor
    @Component
    @Slf4j
    public class JwtTokenProvider {
    
        private final RefreshTokenRepository refreshTokenRepository;
        private final Environment env;

        private Key key;
    
        @Value("${jwt.token.accessExpiration}")
        private long accessTokenValidTime;   // Access Token 유효시간
    
        @Value("${jwt.token.refreshExpiration}")
        private long refreshTokenValidTime;   // Refresh Token 유효시간
    
        @Value("${jwt.secret}")
        private String secretKeyString;  // 시크릿 키를 가져오는 변수
    
        @PostConstruct
        protected void init() {
            key = Keys.hmacShaKeyFor(secretKeyString.getBytes(StandardCharsets.UTF_8)); // 시크릿 키를 사용하여 Key 객체 생성
        }
    
        /**
         * 공통 토큰 생성 로직(Access/Refresh 모두 사용)* @param authentication
         * @param accessTokenValidTime
         * @return
         */
        public String generateToken(Authentication authentication, Long accessTokenValidTime) {
            String authorities = authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.joining(","));  // 권한을 콤마로 연결

            Date now = new Date();
            Date expiration = new Date(now.getTime() + accessTokenValidTime);

            return Jwts.builder()
                    .claims()
                    .subject(authentication.getName())   // 사용자 ID
                    .add("auth", authorities)    // 권한 정보
                    .issuedAt(now)   // 발행 시간
                    .expiration(expiration)  // 만료 시간
                    .and()
                    .signWith(key)     // 서명 키 및 알고리즘
                    .compact();
        }
    
        /**
         * Access Token 생성
         * @param authentication
         * @return
         */
        public String generateAccessToken(Authentication authentication) {
//            return generateToken(authentication, accessTokenValidTime);
            String token  = generateToken(authentication, accessTokenValidTime);
            log.info("===== access token ===== " + token);
            return token;
        }
    
        /**
         * Refresh Token 생성 및 DB 저장
         * @param authentication
         * @return
         */
        public String generateRefreshToken(Authentication authentication) {
            String refreshToken = generateToken(authentication, refreshTokenValidTime); // 토큰 생성
            Long userId;
            try {
                userId = Long.parseLong(authentication.getName()); // 사용자 ID (문자열 → Long)
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("유효하지 않은 사용자 ID 형식입니다.");
            }
    
            // 기존 토큰이 존재하면 업데이트, 없으면 저장
            refreshTokenRepository.findByUserId(userId).ifPresentOrElse(
                    existingRefreshToken -> {
                        existingRefreshToken.setRefreshToken(refreshToken);  // 기존 토큰 갱신
                        refreshTokenRepository.save(existingRefreshToken);  // 저장
                    },
                    () -> {
                        RefreshToken newRefreshToken = RefreshToken.builder()
                                .userId(userId)
                                .refreshToken(refreshToken)
                                .build();
                        refreshTokenRepository.save(newRefreshToken);
                    }
            );
    
            return refreshToken;
        }
    
        /**
         * 토큰에서 인증 정보 추출
         * 0.12.x 버전대에 buildParser() 관련해 이슈가 있는 듯 함. parser()가 buildParser()를 리턴하므로 build() 필요
         * @param token
         * @return
         */
        public Authentication getAuthentication(String token) {
            Claims claims = Jwts.parser()
                    .setSigningKey(key)
                    .build()
                    .parseSignedClaims(token)
                    .getBody();
    
            Collection<? extends GrantedAuthority> authorities =
                    Arrays.stream(claims.get("auth").toString().split(","))
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList());
    
            User principal = new User(claims.getSubject(), "", authorities);    // 패스워드는 사용하지 않음
            return new UsernamePasswordAuthenticationToken(principal, token, authorities);
        }
    
        /**
         * 토큰 유효성 검사
         * @param token
         * @return
         */
        public JwtCode validateToken(String token) {
            try {
                Jwts.parser()
                        .setSigningKey(key)  // 서명 검증을 위한 키 설정
                        .build()
                        .parseClaimsJws(token); // JWT 검증
    
                // 파싱 성공(유효 토큰)
                return JwtCode.ACCESS;
            } catch (ExpiredJwtException e) { // 토큰 만료
                log.error("[JWT] 만료됨: {}", e.getMessage());
                return JwtCode.EXPIRED;
            } catch (SignatureException e) {    // 서명 조작 의심
                log.error("[JWT] 서명위조: {}", e.getMessage());
                return JwtCode.INVALID_SIGNATURE;
            } catch (JwtException e) {  // 기타 JWT 예외 처리
                log.error("[JWT] 구조 오류: {}", e.getMessage());
                return JwtCode.MALFORMED;
            } catch (Exception e) { // 그 외 모든 예외
                log.error("[JWT] 알 수 없는 오류: {}", e.getMessage());
                return JwtCode.UNKNOWN;
            }
        }
    
        /**
         * userId로 Refresh Token 조회
         * @param userId
         * @return
         */
        @Transactional
        public String getRefreshToken(Long userId) {
            RefreshToken refreshToken = refreshTokenRepository.findByUserId(userId)
                    .orElseThrow(() -> new IllegalArgumentException("refresh token이 존재하지 않습니다."));
            return refreshToken.getRefreshToken();
        }
    }