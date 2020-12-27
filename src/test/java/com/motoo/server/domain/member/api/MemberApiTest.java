package com.motoo.server.domain.member.api;

import com.motoo.server.domain.member.domain.Member;
import com.motoo.server.domain.member.dto.Gender;
import com.motoo.server.domain.member.dto.MemberExistenceType;
import com.motoo.server.domain.member.dto.SignUpRequest;
import com.motoo.server.domain.model.SnsProvider;
import com.motoo.server.test.IntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import static com.motoo.server.config.ApiDocumentUtils.getDocumentRequest;
import static com.motoo.server.config.ApiDocumentUtils.getDocumentResponse;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class MemberApiTest extends IntegrationTest {

    private String email;
    private String pwd;
    private String confirmPwd;
    private String name;
    private String countryNo;
    private String phoneNo;
    private String nickname;
    private String gender;
    private String birthday;
    private String recommender;
    private String snsId;
    private SnsProvider snsProvider;
    private String snsProfileImagePath;


    @BeforeEach
    void beforeEach() {
        email = "robotwar12@naver.com";
        pwd = "test1234";
        confirmPwd = "test1234";
        name = "김정민";
        countryNo = "82";
        phoneNo = "01097950034";
        nickname = "배트맨";
        gender = Gender.MALE.name();
        birthday = "19840501";
        recommender = "";
        snsId = "123323223";
        snsProvider = SnsProvider.KAKAO;
        snsProfileImagePath = "";

    }

    @Test
    @DisplayName("이몌일 중복 테스트")
    public void email_nickname_existence() throws Exception {
        //given
        final MemberExistenceType type = MemberExistenceType.EMAIL;
        final Member member = memberSetup.save();
        final String email = member.getEmail();

        //when
        final ResultActions resultActions = mvc.perform(get("/member/existence")
                .contentType(MediaType.APPLICATION_JSON)
                .param("type", type.name())
                .param("value", email))
                .andDo(print());

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("existence").value(true))
                .andDo(document("existenceEmail",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestParameters(
                                parameterWithName("type").description("중복검사 항목 : email, nickname "),
                                parameterWithName("value").description("중복검사 값 (test@naver.com , 닉네임) ")
                        ),
                        responseFields(
                                fieldWithPath("existence").type(JsonFieldType.BOOLEAN).description("존재여부(true:존재함,false:없음_사용가능")
                        )
                ));
    }


    @Test
    @DisplayName("회원가입 테스트")
    void signUp() throws Exception {
        //given
        MockMultipartFile profileImage
                = new MockMultipartFile(
                "profileImage",
                "hello.jpg",
                MediaType.TEXT_PLAIN_VALUE,
                "Hello, World!".getBytes()
        );


        SignUpRequest request = SignUpRequest.builder()
                .email(email)
                .pwd(pwd)
                .confirmPwd(confirmPwd)
                .name(name)
                .countryNo(countryNo)
                .phoneNo(phoneNo)
                .profileImage(profileImage)
                .nickname(nickname)
                .gender(gender)
                .birthday(birthday)
                .recommender(recommender)
                .snsId(snsId)
                .snsProfileImagePath(snsProfileImagePath)
                .snsProvider(snsProvider)
                .build();
        //when
        ResultActions resultActions = mvc.perform(multipart("/member/signup")
                .file(profileImage)
                .param("email", request.getEmail())
                .param("pwd", request.getPwd())
                .param("confirmPwd", request.getConfirmPwd())
                .param("name", request.getName())
                .param("countryNo", request.getCountryNo())
                .param("phoneNo", request.getPhoneNo())
                .param("nickname", request.getNickname())
                .param("gender", request.getGender())
                .param("birthday", request.getBirthday())
                .param("recommender", request.getRecommender())
                .param("snsId", request.getSnsId())
                .param("snsProvider", request.getSnsProvider().name())
                .param("snsProfileImagePath", request.getSnsProfileImagePath()))
                .andDo(print());
        //then
        resultActions.andExpect(status().isOk())
                .andDo(document("signUp",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestParameters(
                                parameterWithName("email").optional().description("이메일 주소 (sns 통해 가입시 없음)"),
                                parameterWithName("pwd").optional().description("비밀번호(sns 통해 가입시 없음)"),
                                parameterWithName("confirmPwd").description("비밀번호 확인(sns 통해 가입시 없음)"),
                                parameterWithName("name").description("이름"),
                                parameterWithName("countryNo").description("국가코드"),
                                parameterWithName("phoneNo").description("휴대폰번호"),
                                parameterWithName("profileImage").optional().description("The file to upload"),
                                parameterWithName("nickname").description("닉네임"),
                                parameterWithName("gender").optional().description("성별(MALE,FEMALE,ETC"),
                                parameterWithName("birthday").optional().description("생년월일"),
                                parameterWithName("recommender").optional().description("추천인 닉네임"),
                                parameterWithName("snsId").optional().description("sns 유니크 아이디"),
                                parameterWithName("snsProvider").optional().description("sns 업체(카카오,페이스북)"),
                                parameterWithName("snsProfileImagePath").optional().description("sns 프로필 이미지경로")
                        )
                ));
    }
}