package com.onlinebookstore.util;

/**
 * @author rkc
 * @date 2020/9/21 8:55
 * @version 1.0
 */
public enum OrderStatusEnum {

    ORDER_SUCCESS(1), ORDER_WAITING(0), ORDER_FAILED(-1);

    private final Integer code;

    OrderStatusEnum(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
