package com.motoo.server.infra.sms;

import com.motoo.server.domain.sms.dto.SmsSendRequest;
import lombok.extern.slf4j.Slf4j;
import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Slf4j
@Component
public class CoolSmsSender implements SmsSender {
    @Value("${cool_sms.api_key}")
    private String API_KEY;

    @Value("${cool_sms.api_secret}")
    private String API_SECRET;

    @Value("${cool_sms.sender_phone_no}")
    private String SENDER_PHONE_NO;

    @Override
    public void sendSms(SmsSendRequest request, String message) {

        Message coolsms = new Message(API_KEY, API_SECRET);

        // 4 params(to, from, type, text) are mandatory. must be filled
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("from", SENDER_PHONE_NO);
        params.put("to", request.getPhoneNo());
        params.put("country", request.getCountryCode());
        params.put("type", "SMS");
        params.put("text", message);
        params.put("app_version", "test app 1.2"); // application name and version

        try {
            JSONObject obj = (JSONObject) coolsms.send(params);
            System.out.println(obj.toString());
        } catch (CoolsmsException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            System.out.println(e.getCode());
        }
    }

}
