package com.motoo.server.domain.member.exception;

import com.motoo.server.global.error.exception.ErrorCode;
import com.motoo.server.global.error.exception.InvalidValueException;

public class InputPasswordNotMatchException extends InvalidValueException {
    public InputPasswordNotMatchException(final String value) {
        super(value, ErrorCode.INPUT_PASSWORD_NOT_MATCH);
    }
}
