package com.motoo.server.domain.sms.exception;


import com.motoo.server.global.error.exception.BusinessException;
import com.motoo.server.global.error.exception.ErrorCode;

public class SmsAuthNoNotMatchException extends BusinessException {
    public SmsAuthNoNotMatchException() {
        super(ErrorCode.SMS_AUTH_NO_NOT_MATCH_ERROR);
    }
}
