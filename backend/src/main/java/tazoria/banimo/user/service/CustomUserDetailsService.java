package tazoria.banimo.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tazoria.banimo.user.entity.UserEntity;
import tazoria.banimo.user.repository.UserRepository;
import tazoria.banimo.common.constants.ResultMessage;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> userOptional = userRepository.findByUsername(username);

        // 사용자가 졵대하지 않는 경우
        UserEntity user = userOptional.orElseThrow(() -> {
            log.warn(ResultMessage.USER_NOT_FOUND.getMessage());
            return new UsernameNotFoundException(ResultMessage.USER_NOT_FOUND.getMessage());
        });
        // 사용자 정보를 찾았을 때
        return new CustomUserDetails(user);
    }
}
