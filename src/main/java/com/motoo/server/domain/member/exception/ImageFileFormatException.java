package com.motoo.server.domain.member.exception;

import com.motoo.server.global.error.exception.ErrorCode;
import com.motoo.server.global.error.exception.InvalidValueException;

public class ImageFileFormatException extends InvalidValueException {
    public ImageFileFormatException(final String fileFormat) {
        super(fileFormat, ErrorCode.IMAGE_FILE_INVALID);
    }
}
