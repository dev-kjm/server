package com.motoo.server.domain.board.dto;

import com.motoo.server.domain.board.domain.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponse {
    // 댓글 번호
    private Long commentNo;

    // 내용
    private String contents;

    public CommentResponse(Comment comment) {
        this.commentNo = comment.getCommentNo();
        this.contents  = comment.getContents();
    }
}
