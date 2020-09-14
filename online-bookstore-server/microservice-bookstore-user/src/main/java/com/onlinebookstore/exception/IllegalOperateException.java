package com.onlinebookstore.exception;

/**
 * @author rkc
 * @date 2020/9/14 8:09
 * @version 1.0
 */
public class IllegalOperateException extends RuntimeException {

    private Integer code;
    private String message;

    public IllegalOperateException() {
        this(0, "非法操作异常！");
    }

    public IllegalOperateException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public IllegalOperateException(String message) {
        this(0, message);
    }
}
