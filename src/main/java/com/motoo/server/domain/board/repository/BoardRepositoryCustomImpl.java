package com.motoo.server.domain.board.repository;

import com.motoo.server.domain.board.domain.Board;
import com.motoo.server.domain.board.domain.BoardType;
import com.motoo.server.domain.board.domain.QBoard;
import com.motoo.server.domain.board.dto.BoardCondition;
import com.motoo.server.domain.board.dto.BoardListResponse;
import com.motoo.server.domain.board.dto.QBoardListResponse;
import com.motoo.server.domain.member.domain.Member;
import com.motoo.server.global.common.UseYn;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import java.util.List;

import static com.motoo.server.domain.board.domain.QBoard.board;
import static org.springframework.util.StringUtils.isEmpty;

public class BoardRepositoryCustomImpl implements BoardRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public BoardRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<BoardListResponse> search(BoardCondition condition, Pageable pageable) {
        QueryResults<BoardListResponse> results = queryFactory
                .select(new QBoardListResponse(
                        board.boardNo,
                        board.title,
                        board.views,
                        board.registerDate,
                        board.modifyDate
                ))
                .from(board)
                .where(postTypeEq(condition.getBoardType()),
                        MemberEq(condition.getMember()),
                        board.useYn.eq(UseYn.Y))
                .orderBy(board.registerDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<BoardListResponse> content = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);
    }


    @Override
    public Board detailBoard(Member member, BoardType boardType, Long boardNo) {
        return queryFactory
                .selectFrom(QBoard.board)
                .where(postTypeEq(boardType),
                        MemberEq(member),
                        QBoard.board.useYn.eq(UseYn.Y),
                        QBoard.board.boardNo.eq(boardNo))
                .fetchOne();
    }

    private BooleanExpression postTypeEq(BoardType boardType) {
        return isEmpty(boardType) ? null : board.boardType.eq(boardType);
    }

    private BooleanExpression MemberEq(Member member) {
        return isEmpty(member) ? null : board.member.eq(member);
    }
}
