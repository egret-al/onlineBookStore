package com.onlinebookstore.exception;

/**
 * @author rkc
 * @date 2020/9/21 9:26
 * @version 1.0
 */
public class SerialNumberNullException extends RuntimeException {

    private Integer code;
    private String message;

    public SerialNumberNullException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
