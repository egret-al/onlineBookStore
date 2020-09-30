package com.onlinebookstore.service.impl;

import com.onlinebookstore.common.CommonplaceResult;
import com.onlinebookstore.entity.Order;
import com.onlinebookstore.mapper.OrderMapper;
import com.onlinebookstore.service.OrderService;
import com.onlinebookstore.util.OrderStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author rkc
 * @date 2020/9/21 8:28
 * @version 1.0
 */
@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderMapper orderMapper;

    /**
     * 查询所有订单
     */
    @Override
    public CommonplaceResult selectAll() {
        List<Order> orders = orderMapper.selectAll();
        return orders.size() == 0 ? CommonplaceResult.buildErrorNoData("没有数据！") :
                CommonplaceResult.buildSuccessNoMessage(orders);
    }

    /**
     * 根据账号查询所有订单
     * @param username 账号
     */
    @Override
    public CommonplaceResult selectAllByUsername(String username) {
        List<Order> orders = orderMapper.selectAllByUsername(username);
        return orders.size() == 0 ? CommonplaceResult.buildErrorNoData("该用户没有订单！") :
                CommonplaceResult.buildSuccess(orders, "您有" + orders.size() + "条订单");
    }

    /**
     * 根据图书id查询该书本的所有订单
     * @param bookId 图书id
     */
    @Override
    public CommonplaceResult selectAllByBookId(Integer bookId) {
        List<Order> orders = orderMapper.selectAllByBookId(bookId);
        return orders.size() == 0 ? CommonplaceResult.buildErrorNoData("该图书没有订单！") :
                CommonplaceResult.buildSuccess(orders, "该图书有" + orders.size() + "条订单");
    }

    /**
     * 新建订单
     * @param order 订单对象
     * @return 影响行数
     */
    @Override
    public CommonplaceResult insertOrder(Order order) {
        return orderMapper.insertOrder(order) > 0 ? CommonplaceResult.buildSuccessNoData("新建成功") :
                CommonplaceResult.buildErrorNoData("插入失败，服务器异常！");
    }

    /**
     * 更新订单状态
     * @param code 订单状态
     * @param serialNumber 订单号
     * @return 影响行数
     */
    @Override
    public CommonplaceResult updateOrderStatus(Integer code, String serialNumber) {
        return orderMapper.updateOrderStatus(code, serialNumber) > 0 ?
                CommonplaceResult.buildSuccess(true, "修改成功！") :
                CommonplaceResult.buildError(false, "修改失败！");
    }

    /**
     * 根据订单编号删除订单
     * @param serialNumber 订单编号
     */
    @Override
    public CommonplaceResult deleteOrderBySerialNumber(String serialNumber) {
        return orderMapper.deleteOrderBySerialNumber(serialNumber) > 0 ? CommonplaceResult.buildSuccessNoData("删除成功") :
                CommonplaceResult.buildErrorNoData("删除失败！");
    }

    /**
     * 更新订单
     * @param order 订单对象
     */
    @Override
    public CommonplaceResult updateOrder(Order order) {
        return orderMapper.updateOrder(order) > 1 ? CommonplaceResult.buildSuccess(true, "更新成功！") :
                CommonplaceResult.buildError(false, "更新失败！");
    }
}
