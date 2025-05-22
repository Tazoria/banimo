package tazoria.banimo.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfoDto {
    private String username;
    private String password;

    public UserInfoDto(String username, String password) {
        this.username = username;
        this.password = password;
    }
}

