package tazoria.banimo.diary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tazoria.banimo.diary.dto.DiaryDto;
import tazoria.banimo.diary.entity.DiaryEntity;

import java.util.List;

public interface DiaryRepository extends JpaRepository<DiaryEntity, Long> {
    /* 로그인한 사용자의 일기 목록 조회 */
    List<DiaryEntity> findAllByAuthor(String username);
}
