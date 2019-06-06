package com.github.restful.client.exception;

import com.github.restful.client.exception.base.ExceptionCode;

/**
 * 参数校验异常
 * Created by wangzhx on 2018/8/12.
 */
public class ServerException extends RuntimeException {
    private int errorCode = ExceptionCode.Server_ERROR.getErrorCode();

    public ServerException() {
        super(ExceptionCode.Server_ERROR.getErrorMessage());

    }

    public ServerException(String errorMessage) {
        super(errorMessage);
    }

    public ServerException(Throwable cause) {
        super(cause);
    }

    public ServerException(ExceptionCode exceptionCode) {
        this(exceptionCode.getErrorCode(), exceptionCode.getErrorMessage());
    }

    public ServerException(String errorMessage, Throwable cause) {
        super(errorMessage, cause);
    }

    public ServerException(int errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
    }

    public ServerException(int errorCode, String errorMessage, Throwable cause) {
        super(errorMessage, cause);
        this.errorCode = errorCode;
    }

}
