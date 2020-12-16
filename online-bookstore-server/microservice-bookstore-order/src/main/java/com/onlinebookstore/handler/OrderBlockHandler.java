package com.onlinebookstore.handler;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.onlinebookstore.common.CommonplaceResult;
import com.onlinebookstore.entity.orderserver.Order;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * @author rkc
 * @version 1.0
 * @date 2020/12/16 11:23
 */
@Slf4j
public class OrderBlockHandler {

    public static CommonplaceResult handleSelectOrderByUsername(String username, BlockException e) {
        e.printStackTrace();
        return CommonplaceResult.buildErrorNoData("请求人数过多，请稍后再试！");
    }

    public static CommonplaceResult handleInsertOrder(Order order, BlockException e) {
        log.error(e.getMessage());
        return CommonplaceResult.buildError(order , "创建订单失败，请稍后再试！");
    }

    public static CommonplaceResult handleUpdateOrderStatus(Map<String, String> pojo, BlockException e) {
        log.error(e.getMessage());
        return CommonplaceResult.buildErrorNoData("订单状态修改失败，请稍后再试！");
    }

    public static CommonplaceResult handleUpdateOrder(Order order, BlockException e) {
        log.error(e.getMessage());
        return CommonplaceResult.buildError(order, "订单更新失败！");
    }
}
