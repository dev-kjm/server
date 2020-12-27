package com.motoo.server.domain.board.api;

import com.motoo.server.domain.board.application.BoardService;
import com.motoo.server.domain.board.domain.BoardType;
import com.motoo.server.domain.board.dto.BoardDetailResponse;
import com.motoo.server.domain.board.dto.BoardListResponse;
import com.motoo.server.domain.member.domain.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
@Slf4j
public class BoardQueryApi {

    private final BoardService boardService;

    @GetMapping("/question")
    public Page<BoardListResponse> questions(@AuthenticationPrincipal Member member, final Pageable pageable) {
        return boardService.findByMemberBoards(member, BoardType.QUESTION, pageable);
    }

    @GetMapping("/suggest")
    public Page<BoardListResponse> suggestList(@AuthenticationPrincipal Member member, final Pageable pageable) {
        return boardService.findByMemberBoards(member, BoardType.SUGGEST, pageable);
    }

    @GetMapping("/notice")
    public Page<BoardListResponse> notices(@AuthenticationPrincipal Member member, final Pageable pageable) {
        return boardService.findByMemberBoards(null, BoardType.NOTICE, pageable);
    }

    @GetMapping("/faq")
    public Page<BoardListResponse> faqList(@AuthenticationPrincipal Member member, final Pageable pageable) {
        return boardService.findByMemberBoards(null, BoardType.FAQ, pageable);
    }


    @GetMapping("/question/{boardNo}")
    public BoardDetailResponse questionList(@AuthenticationPrincipal final Member member,
                                            @PathVariable(name = "boardNo") final Long boardNo) {
        return boardService.getBoardDetail(member, boardNo, BoardType.QUESTION);
    }

    @GetMapping("/suggest/{boardNo}")
    public BoardDetailResponse suggestDetail(@AuthenticationPrincipal Member member,
                                             @PathVariable(name = "boardNo") Long boardNo) {
        return boardService.getBoardDetail(member, boardNo, BoardType.SUGGEST);
    }

    @GetMapping("/notice/{boardNo}")
    public BoardDetailResponse noticeDetail(@AuthenticationPrincipal Member member,
                                            @PathVariable(name = "boardNo") Long boardNo) {
        return boardService.getBoardDetail(null, boardNo, BoardType.NOTICE);
    }


    @GetMapping("/faq/{boardNo}")
    public BoardDetailResponse faqDetail(@AuthenticationPrincipal Member member,
                                            @PathVariable(name = "boardNo") Long boardNo) {
        return boardService.getBoardDetail(null, boardNo, BoardType.FAQ);
    }
}
