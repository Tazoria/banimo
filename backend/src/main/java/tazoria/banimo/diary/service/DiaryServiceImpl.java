package tazoria.banimo.diary.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import tazoria.banimo.common.constants.ResultMessage;
import tazoria.banimo.common.dto.ApiResponseDTO;
import tazoria.banimo.diary.dto.DiaryDto;
import tazoria.banimo.diary.entity.DiaryEntity;
import tazoria.banimo.diary.repository.DiaryRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class DiaryServiceImpl implements DiaryService {
    public final DiaryRepository diaryRepository;

    public ResponseEntity<ApiResponseDTO<List<DiaryDto>>> getPostList(String username) {
        // 로그인한 사용자의 일기 목록 조회
        List<DiaryEntity> postEntityList = diaryRepository.findAllByAuthor(username);

        List<DiaryDto> postList = postEntityList.stream()
                .map(DiaryDto::new)
                .collect(Collectors.toList());

        // 빈 목록도 응답은 성공으로 처리
        return ResponseEntity.ok(ApiResponseDTO.success(postList));
    }

    public ResponseEntity<ApiResponseDTO<String>> writePost(DiaryDto diaryDto) {
        // 일기 작성
        DiaryEntity diaryEntity = DiaryEntity.builder()
                .author(diaryDto.getAuthor())
                .title(diaryDto.getTitle())
                .content(diaryDto.getContent())
                .favorites(diaryDto.isFavorites())
                .build();

        try {
            diaryRepository.save(diaryEntity);
            return ResponseEntity.ok(ApiResponseDTO.success(ResultMessage.DIARY_WRITE_SUCCESS.getMessage()));
        } catch (Exception e) {
            log.error(ResultMessage.DIARY_WRITE_FAILED.getMessage() + ": " + e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponseDTO.error(ResultMessage.DIARY_WRITE_FAILED.getMessage()));
        }
    }
}
