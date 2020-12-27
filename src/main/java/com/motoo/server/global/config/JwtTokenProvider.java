package com.motoo.server.global.config;


import com.motoo.server.domain.auth.dto.JwtTokenData;
import com.motoo.server.domain.member.domain.Member;
import com.motoo.server.domain.model.MemberRoles;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RequiredArgsConstructor
@Component
@Slf4j
public class JwtTokenProvider {
//tODO  refresh token 만기 처리

    public static final String ACCESS_TOKEN_COOKIE_NAME = "accessToken";
    public static final String REFRESH_TOKEN_COOKIE_NAME = "refreshToken";

    @Value("spring.jwt.access_secret")
    private String access_secretKey;
    @Value("spring.jwt.refresh_secret")
    private String refresh_secretKey;

    @PostConstruct
    public void init() {

        access_secretKey = Base64.encodeBase64URLSafeString(access_secretKey.getBytes());
        refresh_secretKey = Base64.encodeBase64URLSafeString(refresh_secretKey.getBytes());
    }

    public String createAccessToken(JwtTokenData jwtToken) {
        Claims claims = Jwts.claims().setSubject(String.valueOf(jwtToken.getMemberNo()));
        claims.put("email", jwtToken.getEmail());
        claims.put("role", jwtToken.getRole());

        Date now = new Date();
        //1시간
        long tokenValidMilliSecond = 1000L * 60 * 60;
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenValidMilliSecond))
                .signWith(SignatureAlgorithm.HS256, access_secretKey)
                .compact();
    }

//     refresh token 생성
    public String createRefreshToken(JwtTokenData jwtToken) {
        Claims claims = Jwts.claims().setSubject(String.valueOf(jwtToken.getMemberNo()));
        claims.put("email", jwtToken.getEmail());
        claims.put("role", jwtToken.getRole());

        Date now = new Date();

        //10일
        long tokenValidMilliSecond = 1000L * 60 * 60 * 24 * 10;
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenValidMilliSecond))
                .signWith(SignatureAlgorithm.HS256, refresh_secretKey)
                .compact();

    }


    //토큰에서 회원정보 추출
    public JwtTokenData getJwtMemberInfo(String token) {

        Claims parseInfo = Jwts.parser().setSigningKey(access_secretKey).parseClaimsJws(token).getBody();
        JwtTokenData jwtToken = JwtTokenData.builder()
                .memberNo(Long.parseLong(parseInfo.getSubject()))
                .email(String.valueOf(parseInfo.get("email")))
                .role(MemberRoles.valueOf(String.valueOf(parseInfo.get("role"))))
                .build();

        return jwtToken;
    }

    /**
     * 토큰으로 인증정보 조회
     *
     * @param token
     * @return
     */
    public Authentication getAuthentication(String token) {
        JwtTokenData jwtTokenData = getJwtMemberInfo(token);
        String email = String.valueOf(jwtTokenData.getEmail());

        MemberRoles role = jwtTokenData.getRole();

        log.info("roles :: " + role.name());
        UserDetails member = Member.builder()
                .memberNo(jwtTokenData.getMemberNo())
                .email(email)
                .memberRole(MemberRoles.ROLE_USER).build();

        return new UsernamePasswordAuthenticationToken(member, "", member.getAuthorities());
    }

    /**
     * Header 에서 토큰 파싱
     *
     * @param request
     * @return
     */
//    public String resolveToken(HttpServletRequest request) {
//        log.info("X-XSRF-TOKEN :: " +request.getHeader("X-XSRF-TOKEN"));
//        log.info("X-XSRF-TOKEN :: "+request.getHeader("-XSRF-TOKEN"));
//
//        return request.getHeader("bearer");
//    }

    /**
     * 쿠키에서 accessToken 파싱
     *
     * @param request
     * @return
     */
    public String resolveAccessToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

        String accessToken = "";
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(ACCESS_TOKEN_COOKIE_NAME)) {
                    accessToken = cookie.getValue();

                }
            }
        }

        return accessToken;
    }

    /**
     * 쿠키에서 refreshToken 파싱
     *
     * @param request
     * @return
     */
    public String resolveRfreshToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

        String refreshToken = "";
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(REFRESH_TOKEN_COOKIE_NAME)) {
                    refreshToken = cookie.getValue();
                }
            }
        }

        return refreshToken;
    }

    /**
     * 액세스토큰 검증
     *
     * @param token
     * @return
     */
    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(access_secretKey).parseClaimsJws(token);

            return !claims.getBody().getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
//            throw new ServiceException(ServiceErrorCode.INVALID_JWT_TOKEN);
            return false;
        } catch (Exception e) {
            return false;
        }
    }
    /**
     * 리프레시 토큰검증
     */
}
