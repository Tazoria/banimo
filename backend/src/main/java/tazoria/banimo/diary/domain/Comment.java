package tazoria.banimo.diary.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "diary_comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "content", length = 1000)
    private String content;

    @Column(name = "isDeleted")
    @ColumnDefault("'N'")
    private char isDeleted;

    @Column(name="parentId")
    private int parentId;

    @Column(name="diaryId", nullable = false)
    private int diaryId;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Builder
    public Comment(String username, String content, char isDeleted, int parentId, int diaryId) {
        this.username = username;
        this.content = content;
        this.isDeleted = isDeleted;
        this.parentId = parentId;
        this.diaryId = diaryId;
    }
}
