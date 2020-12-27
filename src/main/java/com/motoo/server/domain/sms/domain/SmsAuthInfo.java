package com.motoo.server.domain.sms.domain;

import com.motoo.server.domain.sms.exception.SmsAuthCountExcessException;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

/**
 * 휴대폰 번호당 하루에 받은 인증횟수 저장
 * timeToLive : 저장되는 시간
 */
@Getter
@Builder
@ToString
@RedisHash(value = "smsAuthCount", timeToLive = 1L * 60 * 60 * 24)
public class SmsAuthInfo {
    private final int MAX_SMS_SEND_COUNT = 3;

    @Id
    private String phoneNo;
    private int authCount;

    public void addCount() {
        this.authCount += 1;
    }

    public void checkAuthSmsSendCount() {
        if (authCount >= MAX_SMS_SEND_COUNT) {
            throw new SmsAuthCountExcessException();
        }
    }

}
