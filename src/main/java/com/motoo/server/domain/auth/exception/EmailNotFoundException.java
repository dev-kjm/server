package com.motoo.server.domain.auth.exception;

import com.motoo.server.global.error.exception.EntityNotFoundException;

public class EmailNotFoundException extends EntityNotFoundException {
    public EmailNotFoundException(String target) {
        super(target + " is not found");
    }
}
