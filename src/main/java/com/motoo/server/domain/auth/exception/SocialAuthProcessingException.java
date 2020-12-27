package com.motoo.server.domain.auth.exception;

import com.motoo.server.global.error.exception.BusinessException;
import com.motoo.server.global.error.exception.ErrorCode;

public class SocialAuthProcessingException extends BusinessException {
    public SocialAuthProcessingException() {
        super(ErrorCode.SOCIAL_AUTH_PROCESSING_ERROR);
    }

}
