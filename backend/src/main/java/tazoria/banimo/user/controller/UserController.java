package tazoria.banimo.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tazoria.banimo.common.dto.ApiResponseDTO;
import tazoria.banimo.user.dto.SignupDto;

import tazoria.banimo.user.service.UserService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponseDTO<String>> signup(@RequestBody SignupDto signupDto) {
        return userService.signup(signupDto);
    }

}
