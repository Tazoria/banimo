package tazoria.banimo.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tazoria.banimo.user.entity.UserEntity;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
