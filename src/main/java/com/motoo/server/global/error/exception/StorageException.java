package com.motoo.server.global.error.exception;

public class StorageException extends BusinessException{
    public StorageException(String message) {
        super(message, ErrorCode.FILE_ERROR);
    }
}
