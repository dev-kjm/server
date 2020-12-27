package com.motoo.server.domain.member.api;

import com.motoo.server.domain.member.application.MemberChangeService;
import com.motoo.server.domain.member.domain.Member;
import com.motoo.server.domain.member.dto.BankCode;
import com.motoo.server.domain.member.dto.FindMemberResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
@Slf4j
public class MemberChangeApi {

    private final MemberChangeService memberChangeService;

    @PutMapping("/image")
    public FindMemberResponse updateMemberImage(@AuthenticationPrincipal Member member,
                                                @RequestParam("profileImage") MultipartFile imagefile) {
        return memberChangeService.changeProfileImage(member.getMemberNo(), imagefile);
    }

    @PutMapping("/password")
    public FindMemberResponse updatePassword(@AuthenticationPrincipal Member member,
                                             @RequestParam("oldPwd") String oldPwd,
                                             @RequestParam("newPwd") String newPwd,
                                             @RequestParam("confirmPwd") String confirmPwd) {
        return memberChangeService.changePassword(member.getMemberNo(), oldPwd, newPwd, confirmPwd);
    }

    @PutMapping("/nickname")
    public FindMemberResponse updateNickname(@AuthenticationPrincipal Member member,
                                             @RequestParam("nickname") String nickname) {
        return memberChangeService.changeNickname(member.getMemberNo(), nickname);
    }

    @PutMapping("/phone")
    public FindMemberResponse updatePhone(@AuthenticationPrincipal Member member,
                                          @RequestParam("countryNo") String countryNo,
                                          @RequestParam("phoneNo") String phoneNo) {
        return memberChangeService.changePhoneNo(member.getMemberNo(), countryNo, phoneNo);
    }

    @PutMapping("/bank")
    public FindMemberResponse updateBank(@AuthenticationPrincipal Member member,
                                         @RequestParam("bankCode") String bankCode,
                                         @RequestParam("accountNumber") String accountNumber) {
        return memberChangeService.changeBankAccount(member.getMemberNo(), BankCode.valueOf(bankCode), accountNumber);
    }
}
