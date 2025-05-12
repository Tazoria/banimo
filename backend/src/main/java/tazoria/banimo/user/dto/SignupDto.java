package tazoria.banimo.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tazoria.banimo.user.entity.UserEntity;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignupDto {
    private String username;
    private String password;
    private String email;

    // toEntity: 빌더 패턴을 사용해 DTO 엔티티를 만들어주는 메서드
    public UserEntity toEntity() {
        return UserEntity.builder()
                .username(username)
                .password(password)
                .email(email)
                .build();
    }
}
