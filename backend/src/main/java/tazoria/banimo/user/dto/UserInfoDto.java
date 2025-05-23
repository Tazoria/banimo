package tazoria.banimo.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserInfoDto {
    private String username;
    private String password;

    public UserInfoDto(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
