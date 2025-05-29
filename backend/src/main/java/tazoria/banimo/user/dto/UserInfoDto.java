package tazoria.banimo.user.dto;

import lombok.*;
import tazoria.banimo.common.constants.UserRole;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDto {
    private String username;
    private String email;
    private UserRole role;
}
