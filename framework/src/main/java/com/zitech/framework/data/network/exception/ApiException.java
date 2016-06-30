package com.zitech.framework.data.network.exception;


public class ApiException extends RuntimeException {

    public final int errorCode;

    public ApiException(int errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}

