package com.onlinebookstore.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.onlinebookstore.util.OrderConstantPool;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * @author rkc
 * @date 2020/9/21 8:19
 * @version 1.0
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    /*
    订单编号
     */
    @JsonProperty("serial_number")
    private String serialNumber;

    /*
    来自bookstore_books库的图书表的id
     */
    @JsonProperty("book_id")
    private Integer bookId;

    /*
    来自bookstore_user库的账号表登录账号
     */
    @JsonProperty("username_id")
    private String usernameId;

    /*
    订单内容
     */
    @JsonProperty("order_content")
    private String orderContent;

    /*
    购买商品数量，默认为1
     */
    @JsonProperty("product_count")
    private Integer productCount;

    /*
    订单总价
     */
    @JsonProperty("whole_price")
    private Integer wholePrice;

    /*
    获得的积分
     */
    @JsonProperty("obtain_score")
    private Integer obtainScore;

    /*
    订单创建时间
     */
    @JsonProperty("create_time")
    @JsonFormat(pattern = OrderConstantPool.TIME_FORMAT, timezone = OrderConstantPool.TIMEZONE)
    private Date createTime;

    /*
    订单付款时间
     */
    @JsonProperty("payment_time")
    @JsonFormat(pattern = OrderConstantPool.TIME_FORMAT, timezone = OrderConstantPool.TIMEZONE)
    private Date paymentTime;

    /*
    发货时间
     */
    @JsonProperty("delivery_time")
    @JsonFormat(pattern = OrderConstantPool.TIME_FORMAT, timezone = OrderConstantPool.TIMEZONE)
    private Date deliveryTime;

    /*
    订单结束时间
     */
    @JsonProperty("end_time")
    @JsonFormat(pattern = OrderConstantPool.TIME_FORMAT, timezone = OrderConstantPool.TIMEZONE)
    private Date endTime;

    /*
    订单状态，默认为0代表未支付，1为成功，-1失败
     */
    @JsonProperty("order_payment_status")
    private Integer orderPaymentStatus;
}
