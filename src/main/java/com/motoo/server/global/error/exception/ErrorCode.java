package com.motoo.server.global.error.exception;

public enum ErrorCode {

    // Common
    INVALID_INPUT_VALUE(400, "C001", " Invalid Input Value"),
    ENTITY_NOT_FOUND(400, "C002", " Entity Not Found"),
    METHOD_NOT_ALLOWED(405, "C002", " Invalid Input Value"),
    INTERNAL_SERVER_ERROR(500, "C004", "Server Error"),
    INVALID_TYPE_VALUE(400, "C005", " Invalid Type Value"),
    HANDLE_ACCESS_DENIED(403, "C006", "Access is Denied"),

    //OAuth
    PASSWORD_NOT_MATCHED_ERROR(400, "auth001","Invalid password"),

    // Member
    EMAIL_DUPLICATION(400, "M001", "Email is Duplication"),
    NICKNAME_DUPLICATION(400, "M001", "Nickname is Duplication"),
    IMAGE_FILE_INVALID(400, "M002", "Image file is invalid"),
    MEMBER_NOT_FOUND(400, "M003", "member is invalid"),
    INPUT_PASSWORD_NOT_MATCH(400, "M004", "The password you entered is different"),


    // SMS
    SMS_AUTH_COUNT_ERROR(400, "SMS001", "SMS verification count exceeded"),
    SMS_AUTH_NO_NOT_MATCH_ERROR(400, "SMS002", "Invalid Input authNumber"),

    // Social
    SOCIAL_AUTH_PROCESSING_ERROR(400, "SNS001", "sns processing error"),

    //File storage
    FILE_ERROR(400, "FILE001", "FileStorage error");

    private final String code;
    private final String message;
    private int status;

    ErrorCode(final int status, final String code, final String message) {
        this.status = status;
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return this.message;
    }

    public String getCode() {
        return code;
    }

    public int getStatus() {
        return status;
    }
}
