package com.motoo.server.infra.sms;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class GeneratedRandomValues {

    public String randomNumber() {
        int bound = 1000000; //6자리 숫자 생성
        Random random = new Random();

        return String.valueOf(random.nextInt(bound));
    }
}
