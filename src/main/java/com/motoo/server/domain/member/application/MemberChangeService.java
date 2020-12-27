package com.motoo.server.domain.member.application;

import com.motoo.server.domain.auth.exception.PasswordNotMathException;
import com.motoo.server.domain.member.domain.Member;
import com.motoo.server.domain.member.dto.BankCode;
import com.motoo.server.domain.member.dto.FindMemberResponse;
import com.motoo.server.domain.member.exception.InputPasswordNotMatchException;
import com.motoo.server.domain.member.exception.MemberNotFoundException;
import com.motoo.server.domain.member.repository.MemberRepository;
import com.motoo.server.global.util.FileUploadUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberChangeService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final FileUploadUtil fileUploadUtil;

    public FindMemberResponse changeProfileImage(Long memberNo, MultipartFile imageFile) {
        Member findMember = memberRepository.findById(memberNo).orElseThrow(MemberNotFoundException::new);


        String uploadPath = fileUploadUtil.upload(imageFile);
        findMember.changeProfileImagePath(uploadPath);

        return new FindMemberResponse(findMember);
    }

    public FindMemberResponse changePassword(Long memberNo, String oldPwd, String newPwd, String confirmPwd) {
        if (!newPwd.equals(confirmPwd)) {
            throw new InputPasswordNotMatchException("password");
        }
        Member findMember = memberRepository.findById(memberNo).orElseThrow(MemberNotFoundException::new);

        if (!passwordEncoder.matches(oldPwd, findMember.getPassword())) {
            throw new PasswordNotMathException();
        }
        findMember.changePassword(passwordEncoder.encode(newPwd));

        return new FindMemberResponse(findMember);
    }

    public FindMemberResponse changeNickname(Long memberNo, String nickname) {
        Member findMember = memberRepository.findById(memberNo).orElseThrow(MemberNotFoundException::new);
        findMember.changeNickname(nickname);

        return new FindMemberResponse(findMember);
    }

    public FindMemberResponse changePhoneNo(Long memberNo, String countryNo, String phoneNo) {
        Member findMember = memberRepository.findById(memberNo).orElseThrow(MemberNotFoundException::new);
        findMember.changePhoneNumber(countryNo, phoneNo);

        return new FindMemberResponse(findMember);
    }

    public FindMemberResponse changeBankAccount(Long memberNo, BankCode bankCode, String accountNumber) {
        Member findMember = memberRepository.findById(memberNo).orElseThrow(MemberNotFoundException::new);
        findMember.changeBankAccount(bankCode, accountNumber);

        return new FindMemberResponse(findMember);
    }

}
