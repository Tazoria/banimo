package tazoria.banimo.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tazoria.banimo.common.constants.ResultMessage;
import tazoria.banimo.common.dto.ApiResponseDTO;
import tazoria.banimo.common.utils.jwt.JwtCode;
import tazoria.banimo.user.entity.UserEntity;
import tazoria.banimo.user.dto.SignupDto;
import tazoria.banimo.user.dto.SigninDto;
import tazoria.banimo.user.dto.TokenResponseDto;
import tazoria.banimo.user.repository.UserRepository;
import tazoria.banimo.common.utils.jwt.JwtTokenProvider;

@RequiredArgsConstructor    // final이 붙거나 @Notnull이 붙은 필드의 생성자 추가
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    @Override
    public ResponseEntity<ApiResponseDTO<TokenResponseDto>> signin(SigninDto signinDto) {
        try {
            // 사용자 인증
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            signinDto.getUsername(),
                            signinDto.getPassword()
                    )
            );

            // 토큰 발급
            String accessToken = jwtProvider.generatedAccessToken(authentication);
            String refreshToken = jwtProvider.generateRefreshToken(authentication);

            return ResponseEntity
                    .ok()
                    .body(ApiResponseDTO.success(new TokenResponseDto(accessToken, refreshToken)));
        }catch(BadCredentialsException e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponseDTO.error(e));  // todo 유연하게 추가 필요할듯
        }catch(Exception e) {
            log.info("Error during signin: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponseDTO.error(e));
        }

    }

    @Override
    public ResponseEntity<ApiResponseDTO<String>> signup(SignupDto signupDto) {
        // 중복 사용자 여부 확인 - username, email
        boolean isUsernameDuplicated = userRepository.existsByUsername(signupDto.getUsername());
        boolean isEmailDuplicated = userRepository.existsByEmail(signupDto.getEmail());

        if(isUsernameDuplicated) {
            return ResponseEntity
                    .badRequest()
                    .body(ApiResponseDTO.fail(ResultMessage.DUPLICATED_USERNAME.getMessage()));
        }else if(isEmailDuplicated) {
            return ResponseEntity
                    .badRequest()
                    .body(ApiResponseDTO.fail(ResultMessage.DUPLICATED_EMAIL.getMessage()));
        }

        // 비밀번호 암호화 및 사용자 저장
        UserEntity userEntity = signupDto.toEntity();
        userEntity.setPassword(passwordEncoder.encode(signupDto.getPassword()));

        userRepository.save(userEntity);

        return ResponseEntity
                .ok()
                .body(ApiResponseDTO.success(ResultMessage.JOIN_SUCCESS.getMessage()));
    }

    /**
     * 사용자 조회
     *
     * @param username
     * @return
     */
    public ResponseEntity<ApiResponseDTO<UserEntity>> getUser(String username) {
        return userRepository.findByUsername(username)
                .map(user -> ResponseEntity.ok(ApiResponseDTO.success(user)))
                .orElseGet(() -> ResponseEntity
                        .badRequest()
                        .body(ApiResponseDTO.fail(ResultMessage.NOT_FOUND.getMessage())));
    }

}
