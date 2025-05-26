package tazoria.banimo.common.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserRole {
    USER("ROLE_USER", 1),
    ADMIN("ROLE_ADMIN", 9);

    private final String description;
    private final int level; // 권한 레벨, 숫자가 높을수록 높은 권한
}
