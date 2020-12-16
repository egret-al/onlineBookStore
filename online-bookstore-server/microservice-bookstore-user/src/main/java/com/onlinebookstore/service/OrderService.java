package com.onlinebookstore.service;

import com.onlinebookstore.common.CommonplaceResult;
import com.onlinebookstore.entity.orderserver.Order;
import com.onlinebookstore.service.fallback.OrderServiceFallbackImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @author rkc
 * @date 2020/9/21 9:39
 * @version 2.0
 */
@FeignClient(value = "order-server", fallback = OrderServiceFallbackImpl.class)
public interface OrderService {

    /**
     * 根据订单号查询订单
     * @param serialNumber 订单号
     * @return 包含订单的对象
     */
    @GetMapping("/api/v1/order/pri/selectOrderBySerialNumber/{serialNumber}")
    CommonplaceResult selectOrderBySerialNumber(@PathVariable("serialNumber") String serialNumber);

    /**
     * 新建订单
     * @param order 订单对象
     * @return 影响行数
     */
    @PostMapping("/api/v1/order/pri/insertOrder")
    CommonplaceResult insertOrder(@RequestBody Order order);

    /**
     * 更新订单
     * @param order 订单对象
     */
    @PostMapping("/api/v1/order/pri/updateOrder")
    CommonplaceResult updateOrder(@RequestBody Order order);
}
