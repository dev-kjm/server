package com.motoo.server.domain.board.dto;

import com.motoo.server.domain.board.domain.BoardType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardDetailResponse {

    private Long boardNo;

    private BoardType boardType;

    private String title;

    private String contents;

    private Integer views;

    @Builder.Default
    private List<CommentResponse> comments = new ArrayList<>();

    @Builder.Default
    private List<FileInfoResponse> attachFiles = new ArrayList<>();

    private LocalDateTime registerDate;

    private LocalDateTime modifyDate;

}
