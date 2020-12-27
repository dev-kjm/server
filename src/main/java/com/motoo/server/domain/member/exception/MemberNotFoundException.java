package com.motoo.server.domain.member.exception;

import com.motoo.server.global.error.exception.BusinessException;
import com.motoo.server.global.error.exception.ErrorCode;

public class MemberNotFoundException extends BusinessException {
    public MemberNotFoundException() {
        super( ErrorCode.MEMBER_NOT_FOUND);
    }
}
