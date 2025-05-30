package tazoria.banimo.diary.dto;

import lombok.*;
import tazoria.banimo.diary.entity.DiaryEntity;

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
        this.createdAt = entity.getCreatedAt().toString(); // LocalDateTime 등
        this.updatedAt = entity.getUpdatedAt().toString();
    }
}
