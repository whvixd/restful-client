package com.github.restful.client.exception.base;

/**
 * 异常信息
 * Created by wangzhx on 2018/8/12.
 */
public enum ExceptionCode {
    ARG_VALIDATE_ERROR(5_01, "参数校验错误"),
    Server_ERROR(5_02, "服务异常");

    private int errorCode;
    private String errorMessage;

    ExceptionCode(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
