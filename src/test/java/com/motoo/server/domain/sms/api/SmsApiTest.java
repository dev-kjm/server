package com.motoo.server.domain.sms.api;


import com.motoo.server.domain.sms.dto.SmsSendRequest;
import com.motoo.server.infra.sms.GeneratedRandomValues;
import com.motoo.server.infra.sms.SmsSender;
import com.motoo.server.test.IntegrationTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import redis.embedded.RedisServer;

import static com.motoo.server.config.ApiDocumentUtils.getDocumentRequest;
import static com.motoo.server.config.ApiDocumentUtils.getDocumentResponse;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
class SmsApiTest extends IntegrationTest {
    @MockBean
    GeneratedRandomValues generatedValue;

    @MockBean
    SmsSender smsSender;

    private SmsSendRequest request;
    private String phoneNo;

    private static RedisServer redisServer;

    @BeforeAll
    static void beforeALL() {
        log.info("before All");
        redisServer = new RedisServer(6379);
        redisServer.start();
    }

    @AfterAll
    static void afterAll() {
        log.info("after All");
        redisServer.stop();
    }

    @BeforeEach
    void before() {
        String countryCode = "82";
        String name = "김정민";
        phoneNo = "01097950034";

        request = SmsSendRequest.builder().
                countryCode(countryCode).
                name(name).
                phoneNo(phoneNo).
                build();
    }


    @Test
    @DisplayName("SMS발송 테스트")
    void send() throws Exception {
        //when
        ResultActions resultActions = sendSms(request);
        //then
        resultActions.andExpect(status().isOk())
                .andDo(document("sendSms",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestParameters(
                                parameterWithName("countryCode").description("국가코드"),
                                parameterWithName("name").description("이름"),
                                parameterWithName("phoneNo").description("휴대폰번호 ")
                        ))
                );


    }

    @Test
    @DisplayName("SMS 인증번호 검증")
    void verification() throws Exception {
        //given
        String authNo = "102938";
        given(generatedValue.randomNumber()).willReturn(authNo);

        sendSms(request);

        //when
        ResultActions resultActions = mvc.perform(post("/sms/verification")
                .contentType(MediaType.APPLICATION_JSON)
                .param("phoneNo", phoneNo)
                .param("smsAuthNo", authNo))
                .andDo(print());
        //then
        resultActions.andExpect(status().isOk())
                .andDo(document("sendSmsVerification",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestParameters(
                                parameterWithName("phoneNo").description("휴대폰번호"),
                                parameterWithName("smsAuthNo").description("인증번호 6자리")
                        ))
                );


    }

    //sms 발송
    private ResultActions sendSms(SmsSendRequest request) throws Exception {
        return mvc.perform(post("/sms/send")
                .contentType(MediaType.APPLICATION_JSON)
                .param("name", request.getName())
                .param("countryCode", request.getCountryCode())
                .param("phoneNo", request.getPhoneNo()))
                .andDo(print());
    }
}