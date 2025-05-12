package tazoria.banimo.user.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import tazoria.banimo.common.entity.BaseEntity;

@Entity
@Getter
@Setter
@Table(name = "user_Info")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "username", unique = true, updatable = false, nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "isActive")
    private Character isActive;

    // 빌더 패턴으로 객체 생성
    @Builder
    public UserEntity(String username, String password, String email, Character isActive) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.isActive = isActive == null ? 'Y' : isActive;
    }
}
