package tazoria.banimo.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tazoria.banimo.common.dto.ApiResponseDTO;
import tazoria.banimo.user.dto.LoginDto;
import tazoria.banimo.user.dto.SignupDto;
import tazoria.banimo.user.dto.LoginDto;
import tazoria.banimo.user.dto.TokenResponseDto;
import tazoria.banimo.user.entity.UserEntity;
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

}
