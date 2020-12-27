package com.motoo.server.domain.board.repository;

import com.motoo.server.domain.board.domain.Board;
import com.motoo.server.domain.board.domain.Comment;
import com.motoo.server.global.common.UseYn;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {

    List<Comment> findByBoardAndUseYn(Board board, UseYn useYn);
}
