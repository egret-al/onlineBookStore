package com.onlinebookstore.exception;

/**
 * @author rkc
 * @date 2020/9/23 9:14
 * @version 1.0
 */
public class RemainInsufficientException extends RuntimeException {

    private Integer code;
    private String message;

    public RemainInsufficientException() {
        this(0, "余额不足！");
    }

    public RemainInsufficientException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public RemainInsufficientException(String message) {
        this(0, message);
    }
}
