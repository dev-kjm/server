package com.motoo.server.setup;

import com.motoo.server.config.TestProfile;
import com.motoo.server.domain.auth.dto.JwtTokenData;
import com.motoo.server.domain.member.domain.Member;
import com.motoo.server.global.config.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;

@Profile(TestProfile.TEST)
@RequiredArgsConstructor
@Component
public class JwtTokenCookieSetup {
    private final JwtTokenProvider jwtTokenProvider;

    public Cookie makeCookie(Member findMember) {
        JwtTokenData jwtTokenData = JwtTokenData.builder()
                .memberNo(findMember.getMemberNo())
                .email(findMember.getEmail())
                .role( findMember.getMemberRole())
                .build();

        String accessToken = jwtTokenProvider.createAccessToken(jwtTokenData);

        //쿠키생성
        //todo 쿠키 시간 설정을 수정해아 하나?
        Cookie accessTokenCookie = new Cookie("accessToken", accessToken);
        accessTokenCookie.setHttpOnly(true);
        accessTokenCookie.setMaxAge(0); //브라우저 닫히면 쿠키삭제
        accessTokenCookie.setPath("/");


        return  accessTokenCookie;
    }
}
