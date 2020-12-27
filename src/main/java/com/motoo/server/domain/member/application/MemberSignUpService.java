package com.motoo.server.domain.member.application;

import com.motoo.server.domain.auth.domain.SnsInfo;
import com.motoo.server.domain.auth.repository.SnsInfoRepository;
import com.motoo.server.domain.member.domain.Member;
import com.motoo.server.domain.member.dto.Gender;
import com.motoo.server.domain.member.dto.SignUpRequest;
import com.motoo.server.domain.member.exception.EmailDuplicateException;
import com.motoo.server.domain.member.exception.MemberNotFoundException;
import com.motoo.server.domain.member.exception.NicknameDuplicateException;
import com.motoo.server.domain.member.repository.MemberRepository;
import com.motoo.server.domain.model.MemberRoles;
import com.motoo.server.domain.model.MemberState;
import com.motoo.server.global.config.JwtTokenProvider;
import com.motoo.server.global.util.FileUploadUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MemberSignUpService {
    private final MemberRepository memberRepository;
    private final SnsInfoRepository snsInfoRepository;
    private final PasswordEncoder passwordEncoder;
    private final FileUploadUtil fileUploadUtil;

    public Member doSignUp(SignUpRequest dto) {

        if (memberRepository.existsByEmail(dto.getEmail())) {
            throw new EmailDuplicateException(dto.getEmail());
        }

        if (memberRepository.existsByNickname(dto.getNickname())) {
            throw new NicknameDuplicateException(dto.getNickname());
        }

        if (dto.getSnsProvider() != null) {
            dto.setPwd("");
        }

        //이미지 업로드 처리
        if (dto.getProfileImage() != null) {
            dto.setSnsProfileImagePath(fileUploadUtil.upload(dto.getProfileImage()));
        }

        //비밀번호 암호화
        dto.setPwd(passwordEncoder.encode(dto.getPwd()));

        Member member = Member.builder()
                .email(dto.getEmail())
                .pwd(dto.getPwd())
                .name(dto.getName())
                .nickname(dto.getNickname())
                .phoneNo(dto.getPhoneNo())
                .gender(Enum.valueOf(Gender.class, dto.getGender()))
                .birthday(dto.getBirthday())
                .countryNo(dto.getCountryNo())
                .recommender(dto.getRecommender())
                .profileImagePath(dto.getSnsProfileImagePath())
                .memberState(MemberState.NORMAL)
                .memberRole(MemberRoles.ROLE_USER)
                .build();


        Member saveMember = memberRepository.save(member);

        if (dto.getSnsProvider() != null) {
            snsInfoRepository.save(SnsInfo.builder()
                    .snsId(dto.getSnsId())
                    .member(member)
                    .snsProvider(dto.getSnsProvider().name())
                    .userYn("Y")
                    .build());
        }

        return saveMember;
    }

    //회원탈퇴 처리
    public void deactivate(Long memberNo, HttpServletResponse response) {
        Member findMember = memberRepository.findById(memberNo).orElseThrow(MemberNotFoundException::new);
        findMember.changeMemberState(MemberState.DROP);


        Cookie accessTokenCookie = new Cookie(JwtTokenProvider.ACCESS_TOKEN_COOKIE_NAME, null);
        accessTokenCookie.setHttpOnly(true);
        accessTokenCookie.setMaxAge(0); //브라우저 닫히면 쿠키삭제
        accessTokenCookie.setPath("/");

        Cookie refreshTokenCookie = new Cookie(JwtTokenProvider.REFRESH_TOKEN_COOKIE_NAME, null);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setMaxAge(0); //브라우저 닫히면 쿠키삭제
        refreshTokenCookie.setPath("/");

        response.addCookie(accessTokenCookie);
        response.addCookie(refreshTokenCookie);
    }
}
