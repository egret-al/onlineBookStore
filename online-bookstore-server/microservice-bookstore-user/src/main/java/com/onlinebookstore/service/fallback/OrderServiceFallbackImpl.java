package com.onlinebookstore.service.fallback;

import com.onlinebookstore.common.CommonplaceResult;
import com.onlinebookstore.entity.orderserver.Order;
import com.onlinebookstore.service.OrderService;
import org.springframework.stereotype.Service;

/**
 * 订单微服务接口调用失败后的处理类
 * @author rkc
 * @version 1.0
 * @date 2020/12/14 17:35
 */
@Service
public class OrderServiceFallbackImpl implements OrderService {

    @Override
    public CommonplaceResult selectOrderBySerialNumber(String serialNumber) {
        return CommonplaceResult.buildError(serialNumber, "订单号：" + serialNumber + "服务器超时！");
    }

    @Override
    public CommonplaceResult insertOrder(Order order) {
        return CommonplaceResult.buildError(order, "订单添加出错，请稍后再试！");
    }

    @Override
    public CommonplaceResult updateOrder(Order order) {
        return CommonplaceResult.buildError(order, "调用出错");
    }
}
