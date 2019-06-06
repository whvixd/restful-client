package com.github.restful.client.exception;

import com.github.restful.client.exception.base.ExceptionCode;

/**
 * 参数校验异常
 * Created by wangzhx on 2018/8/12.
 */
public class ArgValidationException extends RuntimeException {
    private int errorCode = ExceptionCode.ARG_VALIDATE_ERROR.getErrorCode();

    public ArgValidationException() {
        super(ExceptionCode.ARG_VALIDATE_ERROR.getErrorMessage());
    }

    public ArgValidationException(String errorMessage) {
        super(errorMessage);
    }

    public ArgValidationException(Throwable cause) {
        super(cause);
    }

    public ArgValidationException(ExceptionCode exceptionCode) {
        this(exceptionCode.getErrorCode(), exceptionCode.getErrorMessage());
    }

    public ArgValidationException(String errorMessage, Throwable cause) {
        super(errorMessage, cause);
    }

    public ArgValidationException(int errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
    }

    public ArgValidationException(int errorCode, String errorMessage, Throwable cause) {
        super(errorMessage, cause);
        this.errorCode = errorCode;
    }

}
