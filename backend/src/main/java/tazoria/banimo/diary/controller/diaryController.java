package tazoria.banimo.diary.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tazoria.banimo.common.dto.ApiResponseDTO;
import tazoria.banimo.diary.dto.DiaryDto;
import tazoria.banimo.diary.service.DiaryService;

import java.util.List;

@RestController()
@RequestMapping("/diary")
@AllArgsConstructor
public class diaryController {

    private final DiaryService diaryService;

    /**
     * 로그인한 사용자의 일기 목록 조회
     * @param username 조회할 사용자의 이름
     * @return 해당 사용자의 일기 목록
     */
    @GetMapping("/list")
    public ResponseEntity<ApiResponseDTO<List<DiaryDto>>> getPostList(String username) {
        return diaryService.getPostList(username);
    }

    /**
     * 일기 작성
     * @param diaryDto 작성할 일기의 내용 및 작성자 정보
     * @return 작성 결과 메시지
     */
    @PostMapping("/write")
    public ResponseEntity<ApiResponseDTO<String>> writePost(DiaryDto diaryDto) {
        return diaryService.writePost(diaryDto);
    }
}
