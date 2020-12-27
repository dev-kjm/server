package com.motoo.server.domain.board.api;

import com.motoo.server.domain.board.application.BoardService;
import com.motoo.server.domain.board.domain.BoardType;
import com.motoo.server.domain.board.dto.BoardWriteRequest;
import com.motoo.server.domain.member.domain.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
@Slf4j
public class BoardCommandApi {

    private final BoardService boardService;

    @PostMapping("/question")
    public void questionWrite(@AuthenticationPrincipal Member member, @ModelAttribute @Valid BoardWriteRequest request) {
        boardService.boardWrite(member, BoardType.QUESTION, request);
    }


    @PostMapping("/suggest")
    public void suggestWrite(@AuthenticationPrincipal Member member, @ModelAttribute @Valid BoardWriteRequest request) {
        boardService.boardWrite(member, BoardType.SUGGEST, request);
    }


    @PostMapping("/notice")
    public void noticeWrite(@AuthenticationPrincipal Member member, @ModelAttribute @Valid BoardWriteRequest request) {
        boardService.boardWrite(member, BoardType.NOTICE, request);
    }



    @PostMapping("/faq")
    public void faqWrite(@AuthenticationPrincipal Member member, @ModelAttribute @Valid BoardWriteRequest request) {
        boardService.boardWrite(member, BoardType.FAQ, request);
    }
}
