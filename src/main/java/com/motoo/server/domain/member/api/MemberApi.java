package com.motoo.server.domain.member.api;

import com.motoo.server.domain.member.application.MemberSearchService;
import com.motoo.server.domain.member.application.MemberSignUpService;
import com.motoo.server.domain.member.domain.Member;
import com.motoo.server.domain.member.dto.FindMemberResponse;
import com.motoo.server.domain.member.dto.MemberExistenceType;
import com.motoo.server.domain.member.dto.SignUpRequest;
import com.motoo.server.global.common.response.Existence;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
@Slf4j
public class MemberApi {

    private final MemberSearchService memberSearchService;
    private final MemberSignUpService memberSignUpService;

    @PostMapping("/signup")
    public ResponseEntity create(@ModelAttribute @Valid SignUpRequest memberRequest) {

        Member member = memberSignUpService.doSignUp(memberRequest);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/deactivate")
    public ResponseEntity deactivate(@AuthenticationPrincipal Member member, HttpServletResponse response) {

        memberSignUpService.deactivate(member.getMemberNo(), response);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/me")
    public FindMemberResponse findMember(@AuthenticationPrincipal Member member) {
        return memberSearchService.findByMemberNo(member.getMemberNo());
    }

    @GetMapping("/existence")
    public Existence isExistTarget(
            @RequestParam("type") final MemberExistenceType type,
            @RequestParam(value = "value", required = false) final String value
    ) {
        return new Existence(memberSearchService.isExistTarget(type, value));
    }


}
