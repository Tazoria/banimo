package tazoria.banimo.diary.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import tazoria.banimo.common.entity.BaseEntity;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "diary_comment")
public class CommentEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "commenter", nullable = false)
    private String commenter;

    @Column(name = "content", length = 500)
    private String content;

    @Column(name = "isDeleted")
    private boolean isDeleted;

    @Column(name="parentCommentId")
    private int parentCommentId;

    @Column(name="commentDepth", nullable = false)
    private int commentDepth;

    @Column(name="diaryId", nullable = false)
    private int diaryId;

    @Builder
    public CommentEntity(String commenter, String content, boolean isDeleted, int parentCommentId, int commentDepth, int diaryId) {
        this.commenter = commenter;
        this.content = content;
        this.isDeleted = isDeleted;
        this.parentCommentId = parentCommentId;
        this.commentDepth = commentDepth;
        this.diaryId = diaryId;
    }
}
