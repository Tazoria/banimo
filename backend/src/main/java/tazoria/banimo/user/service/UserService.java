package tazoria.banimo.user.service;

import org.springframework.http.ResponseEntity;
import tazoria.banimo.common.dto.ApiResponseDTO;
import tazoria.banimo.user.dto.LoginDto;
import tazoria.banimo.user.dto.SignupDto;
import tazoria.banimo.user.dto.TokenResponseDto;
import tazoria.banimo.user.dto.UserInfoDto;

public interface UserService {
    ResponseEntity<ApiResponseDTO<TokenResponseDto>> login(LoginDto loginDto);
    ResponseEntity<ApiResponseDTO<String>> signup(SignupDto signupDto);
    ResponseEntity<ApiResponseDTO<UserInfoDto>> getUserInfoByUsername(String username);
}