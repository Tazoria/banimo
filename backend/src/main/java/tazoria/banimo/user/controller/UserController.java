package tazoria.banimo.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import tazoria.banimo.common.dto.ApiResponseDTO;
import tazoria.banimo.user.dto.UserInfoDto;
import tazoria.banimo.user.dto.SignupDto;
import tazoria.banimo.user.dto.TokenResponseDto;
import tazoria.banimo.user.entity.UserEntity;
import tazoria.banimo.user.service.UserService;

@RequiredArgsConstructor

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<ApiResponseDTO<UserInfoDto>> me(UserInfoDto userInfoDto) {
        return userService.me(userInfoDto);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponseDTO<TokenResponseDto>> login(@RequestBody UserInfoDto userInfoDto) {
        return userService.login(userInfoDto);
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResponseDTO<String>> signup(@RequestBody SignupDto signupDto) {
        return userService.signup(signupDto);
    }

}
