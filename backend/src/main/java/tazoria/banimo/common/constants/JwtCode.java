package tazoria.banimo.common.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum JwtCode {
    UNAUTHORIZED_HEADER("Authorization 헤더가 없거나 잘못된 형식입니다."),
    ACCESS("유효한 토큰"),
    EXPIRED("만료된 토큰"),
    INVALID_TOKEN("유효하지 않은 토큰");

    private final String message;
}
