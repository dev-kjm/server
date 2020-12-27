package com.motoo.server.domain.auth.application;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.motoo.server.domain.auth.dto.JwtTokenData;
import com.motoo.server.domain.auth.dto.KakaoProfile;
import com.motoo.server.domain.auth.dto.SnsProfileInfo;
import com.motoo.server.domain.auth.exception.EmailNotFoundException;
import com.motoo.server.domain.auth.exception.PasswordNotMathException;
import com.motoo.server.domain.auth.exception.SocialAuthProcessingException;
import com.motoo.server.domain.auth.repository.SnsInfoRepository;
import com.motoo.server.domain.member.domain.Member;
import com.motoo.server.domain.member.repository.MemberRepository;
import com.motoo.server.domain.model.MemberState;
import com.motoo.server.domain.model.SnsProvider;
import com.motoo.server.global.config.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.SerializationUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Base64;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AuthService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final SnsInfoRepository snsInfoRepository;
    private final ObjectMapper mapper;

    private OAuth20Service oAuth20Service;

    @Value("{oauth2.kakao.client-id}")
    private String apiKey;
    @Value("{oauth2.kakao.client-secret}")
    private String apiSecret;
    @Value("{oauth2.kakao.redirect-url}")
    private String callback;

    public Member login(String email, String password, HttpServletResponse response) {

        Member findMember = memberRepository.findByEmailAndMemberState(email, MemberState.NORMAL).orElseThrow(
                () -> new EmailNotFoundException(email));

        if (!passwordEncoder.matches(password, findMember.getPassword())) {
            new PasswordNotMathException();
        }

        setJwtCookie(response, findMember);
        return findMember;
    }

    private void setJwtCookie(HttpServletResponse response, Member findMember) {
        JwtTokenData jwtTokenData = JwtTokenData.builder()
                .memberNo(findMember.getMemberNo())
                .email(findMember.getEmail())
                .role(findMember.getMemberRole())
                .build();

        String accessToken = jwtTokenProvider.createAccessToken(jwtTokenData);
        String refreshToken = jwtTokenProvider.createRefreshToken(jwtTokenData);

        findMember.changeRefreshToken(refreshToken);

        //쿠키생성
        //todo 쿠키 시간 설정을 수정해아 하나?
        Cookie accessTokenCookie = new Cookie(JwtTokenProvider.ACCESS_TOKEN_COOKIE_NAME, accessToken);
        accessTokenCookie.setHttpOnly(true);
        accessTokenCookie.setMaxAge(0); //브라우저 닫히면 쿠키삭제
        accessTokenCookie.setPath("/");

        Cookie refreshTokenCookie = new Cookie(JwtTokenProvider.REFRESH_TOKEN_COOKIE_NAME, refreshToken);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setMaxAge(0); //브라우저 닫히면 쿠키삭제
        refreshTokenCookie.setPath("/");

        response.addCookie(accessTokenCookie);
        response.addCookie(refreshTokenCookie);
    }

    /**
     * sns 인증 Url 호출
     *
     * @param provider
     * @return
     */
//    public String getAuthorizationUrl(String provider) {
//
//        oAuth20Service = new ServiceBuilder("5b17111e1695435893102ec7a6da7bec")
//                .apiSecret("Pv7CuYylaJBuZVbR8bn0WDSjCJjUYyPB")
//                .callback("http://localhost/oauth/callback/kakao")
//                .debug()
//                .build(KakaoApi.instance());
//
//        String authorizationUrl = oAuth20Service.getAuthorizationUrl();
//        log.info("authorizationUrl ::: " + authorizationUrl);
//        return authorizationUrl;
//    }


    //sns 콜백 통해 정보 추출
    public SnsProfileInfo getProfileInfo(String code) {

        KakaoProfile kakaoProfile = null;

        try {
            OAuth2AccessToken accessToken = oAuth20Service.getAccessToken(code);

            String PROTECTED_RESOURCE_URL = "https://kapi.kakao.com/v2/user/me";
            OAuthRequest oAuthRequest = new OAuthRequest(Verb.GET, PROTECTED_RESOURCE_URL);
            oAuth20Service.signRequest(accessToken, oAuthRequest);

            Response response = oAuth20Service.execute(oAuthRequest);

            if (response.getCode() != 200) {
                throw new SocialAuthProcessingException();
            }

            kakaoProfile = mapper.readValue(response.getBody(), KakaoProfile.class);
            log.info("kakaoProfile :: " + kakaoProfile.toString());
        } catch (Exception e) {
            throw new SocialAuthProcessingException();
        }

        String birthday = "";
        if (kakaoProfile.getKakao_account().getBirthyear() != null
                && kakaoProfile.getKakao_account().getBirthday() != null) {
            birthday = kakaoProfile.getKakao_account().getBirthyear()
                    + kakaoProfile.getKakao_account().getBirthday();
        }

        SnsProfileInfo profileInfo = SnsProfileInfo.builder()
                .snsId(String.valueOf(kakaoProfile.getId()))
                .gender(kakaoProfile.getKakao_account().getGender())
                .email(kakaoProfile.getKakao_account().getEmail())
                .birthday(birthday)
                .profileImagePath(kakaoProfile.getProperties().getProfile_image())
                .snsProvider(SnsProvider.KAKAO)
                .build();
        log.info("profileInfo :: " + profileInfo);
        return profileInfo;
    }

    public Cookie setSnsInfoCookie(SnsProfileInfo profileInfo) {
        Cookie snsInfoCookie = new Cookie("snsInfo", serialize(profileInfo));
        snsInfoCookie.setHttpOnly(true);
        snsInfoCookie.setMaxAge(0); //브라우저 닫히면 쿠키삭제
        snsInfoCookie.setPath("/");

        return snsInfoCookie;
    }

    //sns 가입여부 확인
    public boolean existsBySnsMember(SnsProvider snsProvider, String snsId) {
        return snsInfoRepository.existsBySnsProviderAndSnsId(snsProvider.name(), snsId);
    }

    //sns 통해서 로그인 처리
    public Member SnsLogin(SnsProvider snsProvider, String snsId, HttpServletResponse response) {
        Member findMember = snsInfoRepository.findBySnsProviderAndSnsId(snsProvider.name(), snsId)
                .orElseThrow()
                .getMember();

        setJwtCookie(response, findMember);

        return findMember;
    }


    private String serialize(SnsProfileInfo snsProfileInfo) {
        return Base64.getUrlEncoder().encodeToString(
                SerializationUtils.serialize(snsProfileInfo));
    }

    public void logout(HttpServletResponse response) {
        Cookie accessTokenCookie = new Cookie(JwtTokenProvider.ACCESS_TOKEN_COOKIE_NAME, null);
        accessTokenCookie.setHttpOnly(true);
        accessTokenCookie.setMaxAge(0); //브라우저 닫히면 쿠키삭제
        accessTokenCookie.setPath("/");

        response.addCookie(accessTokenCookie);
    }
}
