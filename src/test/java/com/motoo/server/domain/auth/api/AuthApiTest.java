package com.motoo.server.domain.auth.api;

import com.motoo.server.domain.member.domain.Member;
import com.motoo.server.global.config.JwtTokenProvider;
import com.motoo.server.test.IntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletException;

import static com.motoo.server.config.ApiDocumentUtils.getDocumentRequest;
import static com.motoo.server.config.ApiDocumentUtils.getDocumentResponse;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class AuthApiTest extends IntegrationTest {
    @Autowired
    WebApplicationContext context;

    private Member member;

    private String email;
    private String password;

    @Autowired
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @BeforeEach
    void before(RestDocumentationContextProvider restDocumentation) throws ServletException {
        email = "robotwar12@naver.com";
        password = "test1234";
        member = memberSetup.withEmailAndPassword(email, passwordEncoder().encode(password));

    }

    @Test
    @DisplayName("로그인 테스트")
    void login_test() throws Exception {
        //given

        //when
        ResultActions resultActions = login();

        //then
        resultActions.andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(cookie().exists(JwtTokenProvider.ACCESS_TOKEN_COOKIE_NAME))
                .andExpect(cookie().exists(JwtTokenProvider.REFRESH_TOKEN_COOKIE_NAME))
                .andExpect(cookie().httpOnly(JwtTokenProvider.ACCESS_TOKEN_COOKIE_NAME, true))
                .andDo(document("oauth-login",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestParameters(
                                parameterWithName("email").description("이메일"),
                                parameterWithName("pwd").description("비밀번호")
                        ),
                        responseFields(
                                fieldWithPath("memberNo").description("회원번호"),
                                fieldWithPath("email").description("이메일"),
                                fieldWithPath("name").description("이름"),
                                fieldWithPath("nickname").description("닉네임")
                        )


                ));
    }

    private ResultActions login() throws Exception {
        return mvc.perform(post("/oauth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .param("email", email)
                .param("pwd", password)
                .characterEncoding("UTF-8"));
    }
}