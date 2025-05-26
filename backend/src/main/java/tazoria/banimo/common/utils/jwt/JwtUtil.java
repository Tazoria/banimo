package tazoria.banimo.common.utils.jwt;

import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {

    private final SecretKey secretKey;
    private final long expiration;

    public JwtUtil(@Value("${jwt.secret}") String secret,
                   @Value("${jwt.token.accessExpiration}") long expirationTime) {
        secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
        expiration = expirationTime;
    }

    /**
     * JWT 토큰에서 사용자 이름 추출
     * @param token JWT 토큰
     * @return 사용자 이름
     */
    public String getUsername(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("username", String.class);
    }

    /**
     * JWT 토큰에서 사용자 권한 추출
     * @param token JWT 토큰
     * @return 사용자 권한
     */
    public String getRole(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseClaimsJws(token)
                .getPayload()
                .get("role", String.class);
    }

    /**
     * JWT 만료 여부 확인
     * @param token JWT 토큰
     * @return 사용자 이름
     */
    public boolean isTokenExpired(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration()
                .before(new Date());
    }

    /**
     * JWT 토큰 생성
     * @param username 사용자 이름
     * @param role 사용자 역할
     * @param expiration 토큰 만료 시간 (밀리초 단위)
     * @return 생성된 JWT 토큰
     */
    public String createToken(String username, String role, long expiration) {
        return Jwts.builder()
                .claim("username", username)
                .claim("role", role)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(secretKey)
                .compact();
    }
}
