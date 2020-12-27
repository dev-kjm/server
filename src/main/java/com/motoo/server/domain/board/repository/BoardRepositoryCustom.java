package com.motoo.server.domain.board.repository;

import com.motoo.server.domain.board.domain.Board;
import com.motoo.server.domain.board.domain.BoardType;
import com.motoo.server.domain.board.dto.BoardCondition;
import com.motoo.server.domain.board.dto.BoardListResponse;
import com.motoo.server.domain.member.domain.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardRepositoryCustom {

    //게시판 리스트
    Page<BoardListResponse> search(BoardCondition boardCondition, Pageable pageable);

    //게시판 상세조회
    Board detailBoard(Member member, BoardType boardType, Long boardNo);
}
