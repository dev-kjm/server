package com.motoo.server.domain.board.repository;

import com.motoo.server.domain.board.domain.Board;
import com.motoo.server.domain.board.domain.BoardType;
import com.motoo.server.domain.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long>, BoardRepositoryCustom {
    List<Board> findByMember(Member member);

    Optional<Board> findByMemberAndBoardNoAndBoardType(Member member, Long BoardNo, BoardType boardType);

//    Page<Board> findByBoardTypeAndUseYn(BoardType type, UseYn useYn);

//    Page<Board> findByBoardTypeAndMemberAndUseYn(Member member, BoardType boardType, UseYn y, Pageable pageable);
}
