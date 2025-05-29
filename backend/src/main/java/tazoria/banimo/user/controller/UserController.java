package tazoria.banimo.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import tazoria.banimo.common.dto.ApiResponseDTO;
import tazoria.banimo.user.dto.SignupDto;

import tazoria.banimo.user.dto.TokenResponseDto;
import tazoria.banimo.user.dto.LoginDto;
import tazoria.banimo.user.dto.UserInfoDto;
import tazoria.banimo.user.service.UserService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponseDTO<TokenResponseDto>> login(@RequestBody LoginDto loginDto) {
        return userService.login(loginDto);
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResponseDTO<String>> signup(@RequestBody SignupDto signupDto) {
        return userService.signup(signupDto);
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponseDTO<UserInfoDto>> getCurrentUser(Authentication authentication) {
        return userService.getUserInfoByUsername(authentication.getName());
    }

}
