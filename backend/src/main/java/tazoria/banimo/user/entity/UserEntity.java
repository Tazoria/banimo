package tazoria.banimo.user.entity;

import jakarta.persistence.*;
import lombok.*;
import tazoria.banimo.common.constants.UserRole;
import tazoria.banimo.common.entity.BaseEntity;

@Entity
@Table(name = "user_Info")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "username", unique = true, updatable = false, nullable = false, length = 50)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "isEnabled")
    private boolean isEnabled;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole role; // (USER, ADMIN)

    // 빌더 패턴으로 객체 생성
    @Builder
    public UserEntity(String username, String password, String email, boolean isEnabled, UserRole role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.isEnabled = isEnabled;
        this.role = role == null ? UserRole.USER : role; // 기본값은 USER
    }
}
