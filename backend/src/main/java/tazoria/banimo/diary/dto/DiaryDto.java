package tazoria.banimo.diary.dto;

import lombok.*;
import tazoria.banimo.diary.entity.DiaryEntity;

import java.time.format.DateTimeFormatter;

@Getter
@Setter
@NoArgsConstructor
public class DiaryDto {
    public int id;
    public String author;
    public String title;
    public String content;
    public boolean favorites;
    public String createdAt;
    public String updatedAt;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * 일기 저장시
     * @param author 작성자
     * @param title 제목
     * @param content 내용
     * @param favorites 즐겨찾기 여부
     */
    @Builder
    public DiaryDto(String author, String title, String content, boolean favorites) {
        this.author = author;
        this.title = title;
        this.content = content;
        this.favorites = favorites;
    }

    /**
     * 일기 조회시
     * @param entity DiaryEntity 객체
     */
    public DiaryDto(DiaryEntity entity) {
        this.id = entity.getId();
        this.author = entity.getAuthor(); // author가 String이라고 가정
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.favorites = entity.isFavorites(); // boolean getter
        // 날짜 포맷팅
        this.createdAt = entity.getCreatedAt() != null ? entity.getCreatedAt().format(FORMATTER) : null;
        this.updatedAt = entity.getUpdatedAt() != null ? entity.getCreatedAt().format(FORMATTER) : null;
    }
}
