package com.motoo.server.infra.sms;

import com.motoo.server.domain.sms.dto.SmsSendRequest;
import org.springframework.stereotype.Component;

@Component
public interface SmsSender {
    void sendSms(SmsSendRequest request, String message);
}
