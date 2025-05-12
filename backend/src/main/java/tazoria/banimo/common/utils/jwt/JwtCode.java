package tazoria.banimo.common.utils.jwt;

public enum JwtCode {
    ACCESS,             // 유효한 토큰
    EXPIRED,            // 만료된 토큰
    INVALID_SIGNATURE,  // 서명 위조
    MALFORMED,          // 구조가 이상한 토큰
    WRONG_INPUT,        // 아이디 비밀번호 불일치
    UNKNOWN             // 기타 예외
}
