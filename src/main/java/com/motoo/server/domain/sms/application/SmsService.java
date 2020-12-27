package com.motoo.server.domain.sms.application;

import com.motoo.server.domain.sms.domain.SmsAuthInfo;
import com.motoo.server.domain.sms.domain.SmsAuthNumber;
import com.motoo.server.domain.sms.dto.SmsSendRequest;
import com.motoo.server.domain.sms.exception.SmsAuthNoNotMatchException;
import com.motoo.server.domain.sms.repository.SmsAuthCountRepository;
import com.motoo.server.domain.sms.repository.SmsAuthNumberRepository;
import com.motoo.server.infra.sms.GeneratedRandomValues;
import com.motoo.server.infra.sms.SmsSender;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class SmsService {
    private final SmsSender smsSender;
    private final SmsAuthNumberRepository authNumberRepository;
    private final SmsAuthCountRepository smsAuthCountRepository;
    private final GeneratedRandomValues generatedRandomValues;


    //인증 SMS 발송
    public String sendAuthSMS(SmsSendRequest smsSendRequest) {
        String authNo = generatedRandomValues.randomNumber();
        String message = "인증번호는 " + authNo + " 입니다.";

        SmsAuthInfo smsAuthInfo = getSmsAuthCount(smsSendRequest);

        //인증횟수 초과 확인
        smsAuthInfo.checkAuthSmsSendCount();

        authNoSave(smsSendRequest.getPhoneNo(), authNo);

        smsSender.sendSms(smsSendRequest, message);

        smsAuthInfo.addCount();
        return authNo;
    }


    //SMS 인증번호 redis 에 저장되어있는지 확인
    public void checkSmsAuthNumber(String phoneNo, String authNo) {
        authNumberRepository.findByPhoneNoAndAuthNo(phoneNo, authNo)
                .orElseThrow(() -> new SmsAuthNoNotMatchException());
    }

    //SMS 인증횟수 객체 불러오기
    private SmsAuthInfo getSmsAuthCount(SmsSendRequest smsSendRequest) {
        return smsAuthCountRepository.findById(smsSendRequest.getPhoneNo())
                .orElseGet(() -> SmsAuthInfo.builder()
                        .phoneNo(smsSendRequest.getPhoneNo())
                        .authCount(0)
                        .build());
    }


    //SMS 인증번호 저장
    private void authNoSave(String phoneNo, String authNo) {
        authNumberRepository.save(SmsAuthNumber.builder()
                .phoneNo(phoneNo)
                .authNo(authNo)
                .build());
    }

}