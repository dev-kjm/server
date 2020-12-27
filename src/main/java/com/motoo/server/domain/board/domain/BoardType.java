package com.motoo.server.domain.board.domain;

public enum BoardType {
    QUESTION("문의"), FAQ("FAQ"), NOTICE("공지사항"), SUGGEST("건의및제안");

    private String description;

    BoardType(String description) {
        this.description = description;
    }

}
