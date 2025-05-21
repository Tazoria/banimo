package tazoria.banimo.user.service;

import org.springframework.http.ResponseEntity;
import tazoria.banimo.common.dto.ApiResponseDTO;
import tazoria.banimo.user.entity.UserEntity;
import tazoria.banimo.user.dto.SignupDto;
import tazoria.banimo.user.dto.LoginDto;
import tazoria.banimo.user.dto.TokenResponseDto;

public interface UserService {
    ResponseEntity<ApiResponseDTO<TokenResponseDto>> login(LoginDto loginDto);
    ResponseEntity<ApiResponseDTO<String>> signup(SignupDto signinRequestDto);
    ResponseEntity<ApiResponseDTO<UserEntity>> getUser(String username);
}