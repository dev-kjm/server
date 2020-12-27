package com.motoo.server.domain.member.exception;

import com.motoo.server.global.error.exception.ErrorCode;
import com.motoo.server.global.error.exception.InvalidValueException;

public class EmailDuplicateException extends InvalidValueException {
    public EmailDuplicateException(final String email) {
        super(email, ErrorCode.EMAIL_DUPLICATION);
    }
}
