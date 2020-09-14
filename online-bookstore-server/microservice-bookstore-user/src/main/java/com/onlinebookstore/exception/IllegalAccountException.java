package com.onlinebookstore.exception;

/**
 * @author rkc
 * @date 2020/9/14 8:15
 * @version 1.0
 */
public class IllegalAccountException extends RuntimeException {

    private Integer code;
    private String message;

    public IllegalAccountException() {
        this(0, "账号非法异常！");
    }

    public IllegalAccountException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public IllegalAccountException(String message) {
        this(0, message);
    }
}
