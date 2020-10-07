package com.onlinebookstore.service;

import com.onlinebookstore.common.CommonplaceResult;
import com.onlinebookstore.entity.orderserver.Order;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author rkc
 * @date 2020/9/21 9:39
 * @version 1.0
 */
@FeignClient("order-server")
public interface OrderService {

    /**
     * 查询所有订单
     */
    @GetMapping("/api/v1/order/pri/selectAll")
    CommonplaceResult selectAll();

    /**
     * 根据账号查询所有订单
     * @param username 账号
     */
    @GetMapping("/api/v1/order/pri/selectAllByUsername/{username}")
    CommonplaceResult selectAllByUsername(@PathVariable(value = "username") String username);

    /**
     * 根据图书id查询该书本的所有订单
     * @param bookId 图书id
     */
    @GetMapping("/api/v1/order/pri/selectAllByBookId/{bookId}")
    CommonplaceResult selectAllByBookId(@PathVariable("bookId") Integer bookId);

    /**
     * 新建订单
     * @param order 订单对象
     * @return 影响行数
     */
    @PostMapping("/api/v1/order/pri/insertOrder")
    CommonplaceResult insertOrder(@RequestBody Order order);

    /**
     * 更新订单状态
     * 数据格式：
     * {
     *     'code': 'xx',
     *     'serial_number': 'xxx'
     * }
     * @param pojo 包含状态码和订单号
     */
    @PostMapping("/api/v1/order/pri/updateOrderStatus")
    CommonplaceResult updateOrderStatus(@RequestBody Map<String, String> pojo);

    /**
     * 更新订单
     * @param order 订单对象
     */
    @PostMapping("/api/v1/order/pri/updateOrder")
    CommonplaceResult updateOrder(@RequestBody Order order);
}
