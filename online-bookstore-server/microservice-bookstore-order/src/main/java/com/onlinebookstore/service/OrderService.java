package com.onlinebookstore.service;

import com.onlinebookstore.common.CommonplaceResult;
import com.onlinebookstore.entity.orderserver.Order;

/**
 * @author rkc
 * @date 2020/9/21 8:28
 * @version 1.0
 */
public interface OrderService {

    /**
     * 查询所有订单
     */
    CommonplaceResult selectAll();

    /**
     * 根据账号查询所有订单
     * @param username 账号
     */
    CommonplaceResult selectAllByUsername(String username);

    /**
     * 根据图书id查询订单
     * @param bookId 图书id
     */
    CommonplaceResult selectAllByBookId(Integer bookId);

    /**
     * 插入订单
     * @param order 订单对象
     * @return 影响行数
     */
    CommonplaceResult insertOrder(Order order);

    /**
     * 根据订单号更新订单状态
     * @param code 订单状态
     * @param serialNumber 订单号
     * @return 影响行数
     */
    CommonplaceResult updateOrderStatus(Integer code, String serialNumber);

    /**
     * 根据订单编号删除订单
     * @param serialNumber 订单编号
     * @return 影响行数
     */
    CommonplaceResult deleteOrderBySerialNumber(String serialNumber);

    /**
     * 更新订单
     * @param order 订单对象
     */
    CommonplaceResult updateOrder(Order order);
}
