package tazoria.banimo.diary.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class diaryController {
    @GetMapping("/")
    public String test() {
        return "test";
    }
}
