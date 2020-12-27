package com.motoo.server.domain.member.exception;

import com.motoo.server.global.error.exception.ErrorCode;
import com.motoo.server.global.error.exception.InvalidValueException;

public class NicknameDuplicateException extends InvalidValueException {

    public NicknameDuplicateException(final String nickname) {
        super(nickname, ErrorCode.NICKNAME_DUPLICATION);
    }
}
