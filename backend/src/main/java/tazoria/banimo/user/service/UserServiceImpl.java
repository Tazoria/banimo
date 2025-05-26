package tazoria.banimo.user.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tazoria.banimo.common.constants.ResultMessage;
import tazoria.banimo.common.dto.ApiResponseDTO;
import tazoria.banimo.user.dto.SignupDto;
import tazoria.banimo.user.entity.UserEntity;
import tazoria.banimo.user.repository.UserRepository;

@Slf4j
@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 회원가입
     * @param signupDto
     * @return
     */
    @Transactional
    public ResponseEntity<ApiResponseDTO<String>> signup(SignupDto signupDto) {
        boolean isDuplicatedUser = validateDuplicateUser(signupDto.getUsername(), signupDto.getEmail());
        if (isDuplicatedUser) {
            return ResponseEntity
                    .badRequest()
                    .body(ApiResponseDTO.error(ResultMessage.DUPLICATED_USERNAME.getMessage()));
        }

        UserEntity user = createUserEntity(signupDto);
        userRepository.save(user);
        return ResponseEntity
                .ok()
                .body(ApiResponseDTO.success(ResultMessage.JOIN_SUCCESS.getMessage()));
    }

    /**
     * 회원가입 시 중복된 사용자명 또는 이메일이 있는지 확인
     * @param username
     * @param email
     * @return true: 중복된 사용자명 또는 이메일이 있음 혹은 에러, false: 중복되지 않음
     */
    private boolean validateDuplicateUser(String username, String email) {
        try {
            if (userRepository.existsByUsername(username)) {
                log.warn(ResultMessage.DUPLICATED_USERNAME.getMessage());
                return true;
            } else if (userRepository.existsByEmail(email)) {
                log.warn(ResultMessage.DUPLICATED_EMAIL.getMessage());
                return true;
            }
            return false;
        } catch (Exception e) {
            log.warn(ResultMessage.ERROR.getMessage());
            return true;
        }
    }

    /**
     * 사용자 추가
     * @param signupDto
     * @return
     */
    private UserEntity createUserEntity(SignupDto signupDto) {
        return UserEntity.builder()
                .username(signupDto.getUsername())
                .password(passwordEncoder.encode(signupDto.getPassword()))
                .email(signupDto.getEmail())
                .isEnabled('Y')  // null을 넘기면 'Y'로 처리됨
                .role(signupDto.getRole())      // null을 넘기면 USER로 처리됨
                .build();
    }
}
