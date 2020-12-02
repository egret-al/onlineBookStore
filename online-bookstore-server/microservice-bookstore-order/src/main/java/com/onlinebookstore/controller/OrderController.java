package com.onlinebookstore.controller;

import com.onlinebookstore.common.CommonplaceResult;
import com.onlinebookstore.entity.orderserver.Order;
import com.onlinebookstore.exception.StatusCodeException;
import com.onlinebookstore.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;


import javax.annotation.Resource;
import java.util.Map;

/**
 * @author rkc
 * @date 2020/9/21 8:29
 * @version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/order")
public class OrderController {

    @Resource
    private OrderService orderService;

    /**
     * 发货，本质上是修改标志位
     * @return CommonplaceResult
     */
    @PostMapping("pri/sendBook")
    public CommonplaceResult sendBook(@RequestBody Order order) {
        return orderService.sendBook(order);
    }

    /**
     * 手动签收
     * @param order 签收订单
     * @return CommonplaceResult
     */
    @PostMapping("pri/acknowledge")
    public CommonplaceResult acknowledge(@RequestBody Order order) {
        return orderService.acknowledge(order);
    }

    /**
     * 根据订单号和账号删除订单
     * @param serialNumber 订单号
     * @param username 账号
     * @return 影响行数
     */
    @GetMapping("pri/deleteOrder/{serialNumber}/{username}")
    public CommonplaceResult deleteOrder(@PathVariable("serialNumber") String serialNumber, @PathVariable("username") String username) {
        if (StringUtils.isEmpty(serialNumber) || StringUtils.isEmpty(username)) {
            return CommonplaceResult.buildErrorNoData("非法数据！");
        }
        return orderService.deleteOrder(serialNumber, username);
    }

    /**
     * 根据账号查询所有订单，并且按照订单创建时间倒序排列
     * @param username 账号
     * @return 订单列表
     */
    @GetMapping("pri/selectOrderByUsername/{username}")
    public CommonplaceResult selectOrderByUsername(@PathVariable("username") String username) {
        if (StringUtils.isEmpty(username)) {
            return CommonplaceResult.buildErrorNoData("数据异常！");
        }
        return orderService.selectOrderByUsername(username);
    }

    /**
     * 根据订单号取消订单
     * @param serial 订单号
     * @param username 账号
     */
    @GetMapping("pri/cancelOrder/{serial}/{username}")
    public CommonplaceResult tryCancelOrder(@PathVariable("serial") String serial, @PathVariable("username") String username) {
        if (ObjectUtils.isEmpty(serial)) {
            return CommonplaceResult.buildErrorNoData("数据异常！");
        }
        return orderService.tryCancelOrder(serial, username);
    }

    /**
     * 根据订单号查询订单
     * @param serialNumber 订单号
     * @return 包含订单的对象
     */
    @GetMapping("pri/selectOrderBySerialNumber/{serialNumber}")
    public CommonplaceResult selectOrderBySerialNumber(@PathVariable("serialNumber") String serialNumber) {
        return orderService.selectOrderBySerialNumber(serialNumber);
    }

    /**
     * 根据订单号查询订单是否过期
     * @param serialNumber 订单号
     * @return 是否过期
     */
    @GetMapping("pri/isExpire/{serialNumber}")
    public boolean isExpire(@PathVariable("serialNumber") String serialNumber) {
        return orderService.isExpire(serialNumber);
    }

    /**
     * 查询所有订单
     */
    @PostMapping("pri/selectAll")
    public CommonplaceResult selectAll() {
        return orderService.selectAll();
    }

    /**
     * 根据账号查询所有订单
     * @param username 账号
     */
    @GetMapping("pri/selectAllByUsername/{username}")
    public CommonplaceResult selectAllByUsername(@PathVariable(value = "username") String username) {
        return orderService.selectAllByUsername(username);
    }

    /**
     * 根据图书id查询该书本的所有订单
     * @param bookId 图书id
     */
    @GetMapping("pri/selectAllByBookId/{bookId}")
    public CommonplaceResult selectAllByBookId(@PathVariable("bookId") Integer bookId) {
        return orderService.selectAllByBookId(bookId);
    }

    /**
     * 新建订单
     * @param order 订单对象
     * @return 影响行数
     */
    @PostMapping("pri/insertOrder")
    public CommonplaceResult insertOrder(@RequestBody Order order) {
        return orderService.insertOrder(order);
    }

    /**
     * 更新订单状态
     * 数据格式：
     * @param pojo 包含状态码和订单号
     */
    @PostMapping("pri/updateOrderStatus")
    public CommonplaceResult updateOrderStatus(@RequestBody Map<String, String> pojo) {
        int code = Integer.parseInt(pojo.get("code"));
        String serialNumber = pojo.get("serial_number");
        if (code < -1 || code > 1) {
            throw new StatusCodeException(code, "状态码非法！");
        }
        return orderService.updateOrderStatus(code, serialNumber);
    }

    /**
     * 更新订单
     * @param order 订单对象
     */
    @PostMapping("pri/updateOrder")
    public CommonplaceResult updateOrder(@RequestBody Order order) {
        return orderService.updateOrder(order);
    }
}
