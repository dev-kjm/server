package com.motoo.server.domain.sms.exception;


import com.motoo.server.global.error.exception.BusinessException;
import com.motoo.server.global.error.exception.ErrorCode;

public class SmsAuthCountExcessException extends BusinessException {
    public SmsAuthCountExcessException() {
        super(ErrorCode.SMS_AUTH_COUNT_ERROR);
    }
}
