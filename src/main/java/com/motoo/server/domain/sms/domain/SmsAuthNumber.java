package com.motoo.server.domain.sms.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.io.Serializable;

/**
 * SMS 인증번호 Redis에 저장할 객체
 * timeToLive : 저장되는 시간
 */
@Getter
@Builder
@ToString
@RedisHash(value = "smsAuthNumber", timeToLive = 180L)
public class SmsAuthNumber implements Serializable {
    @Id
    @Indexed
    private String phoneNo;

    @Indexed
    private String authNo;
}
