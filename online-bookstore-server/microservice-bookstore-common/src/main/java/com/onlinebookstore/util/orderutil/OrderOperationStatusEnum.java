package com.onlinebookstore.util.orderutil;

/**
 * 订单操作状态枚举
 * @author rkc
 * @date 2020/9/21 8:55
 * @version 1.0
 */
public enum OrderOperationStatusEnum {

    /**
     * DELETE：删除状态
     * INSERT：新增订单状态
     * CANCEL：取消状态
     * MODIFY：修改状态
     * UN_PAYMENT：未支付状态
     * FINISH：订单完成状态
     * ERROR：发生错误
     */
    DELETE("delete"), INSERT("insert"), CANCEL("cancel"), MODIFY("modify"), UN_PAYMENT("unPayment"),
    FINISH("finish"), ERROR("error");

    public String status;

    OrderOperationStatusEnum(String status) {
        this.status = status;
    }
}
