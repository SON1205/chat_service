package com.yeahpeu.backend.common.exception;

public class AlreadyExistsException extends BaseException {
    public AlreadyExistsException(String code, String message) {
        super(code, message);
    }

    public AlreadyExistsException(ExceptionCode exceptionCode) {
        super(exceptionCode);
    }

    public AlreadyExistsException(ExceptionCode exceptionCode, String message) {
        super(exceptionCode, message);
    }
}
