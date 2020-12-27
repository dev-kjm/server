package com.motoo.server.domain.board.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BoardListResponse {

    private Long boardNo;

    private String title;

    private Integer views;

    private LocalDateTime registerDate;

    private LocalDateTime modifyDate;

    @QueryProjection
    public BoardListResponse(Long boardNo, String title, Integer views, LocalDateTime registerDate, LocalDateTime modifyDate) {
        this.boardNo = boardNo;
        this.title = title;
        this.views = views;
        this.registerDate = registerDate;
        this.modifyDate = modifyDate;
    }
}
