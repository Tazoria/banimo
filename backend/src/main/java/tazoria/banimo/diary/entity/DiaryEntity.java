package tazoria.banimo.diary.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tazoria.banimo.common.entity.BaseEntity;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "diary_post")
public class DiaryEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "writer", nullable = false)
    private String author;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", length = 1000)
    private String content;

    @Column(name = "isEnabled")
    private boolean isEnabled;

    @Column(name = "favorites", nullable = false)
    private boolean favorites;

    @Builder
    public DiaryEntity(String author, String title, String content, boolean isEnabled, boolean favorites) {
        this.author = author;
        this.title = title;
        this.content = content;
        this.isEnabled = isEnabled;
        this.favorites = favorites;
    }
}
