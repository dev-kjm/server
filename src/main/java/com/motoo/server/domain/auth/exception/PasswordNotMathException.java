package com.motoo.server.domain.auth.exception;

import com.motoo.server.global.error.exception.BusinessException;
import com.motoo.server.global.error.exception.ErrorCode;

public class PasswordNotMathException extends BusinessException {
    public PasswordNotMathException() {
        super(ErrorCode.PASSWORD_NOT_MATCHED_ERROR);
    }
}
