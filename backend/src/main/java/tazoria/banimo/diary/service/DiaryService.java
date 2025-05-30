package tazoria.banimo.diary.service;

import org.springframework.http.ResponseEntity;
import tazoria.banimo.common.dto.ApiResponseDTO;
import tazoria.banimo.diary.dto.DiaryDto;

import java.util.List;

public interface DiaryService {
    /* 로그인한 사용자의 일기 목록 조회 */
    ResponseEntity<ApiResponseDTO<List<DiaryDto>>> getPostList(String username);
    /* 일기 작성 */
    ResponseEntity<ApiResponseDTO<String>> writePost(DiaryDto diaryDto);

}
