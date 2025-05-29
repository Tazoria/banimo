package tazoria.banimo.common.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResultMessage {

    SUCCESS("요청이 성공적으로 처리되었습니다."),
    ERROR("요청 처리 중 오류가 발생했습니다."),
    NOT_FOUND("요청한 자원을 찾을 수 없습니다."),

    LOGIN_SUCCESS("로그인이 성공적으로 완료되었습니다."),
    LOGIN_FAILED("로그인에 실패했습니다. 아이디 또는 비밀번호를 확인해주세요."),
    JOIN_SUCCESS("회원가입이 완료되었습니다."),
    JOIN_FAILED("회원가입에 실패했습니다."),
    DUPLICATED_USERNAME("이미 가입된 회원입니다."),
    DUPLICATED_EMAIL("이미 가입된 이메일 주소입니다."),
    USER_NOT_FOUND("사용자를 찾을 수 없습니다.");

    private final String message;

}
