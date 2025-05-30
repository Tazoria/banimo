package tazoria.banimo.diary.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class CommentDto {
    public int id;
    public String commenter;
    public String content;
    public boolean isDeleted;
    public int parentCommentId;
    public int commentDepth;
    public int diaryId;
    public String createdAt;
    public String updatedAt;

    @Builder
    public CommentDto(String commenter, String content, boolean isDeleted, int parentCommentId, int commentDepth, int diaryId) {
        this.commenter = commenter;
        this.content = content;
        this.isDeleted = isDeleted;
        this.parentCommentId = parentCommentId;
        this.commentDepth = commentDepth;
        this.diaryId = diaryId;
    }
}
