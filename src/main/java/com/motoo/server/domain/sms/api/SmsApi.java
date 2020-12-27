package com.motoo.server.domain.sms.api;

import com.motoo.server.domain.sms.application.SmsService;
import com.motoo.server.domain.sms.dto.SmsSendRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/sms")
public class SmsApi {
    private final SmsService smsService;


    @PostMapping(value = "/send")
    public ResponseEntity send(@ModelAttribute @Valid SmsSendRequest sendRequest) {
        smsService.sendAuthSMS(sendRequest);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/verification")
    public ResponseEntity verification(@RequestParam String phoneNo, @RequestParam String smsAuthNo) {
        smsService.checkSmsAuthNumber(phoneNo, smsAuthNo);
        return ResponseEntity.ok().build();
    }
}
