package com.onlinebookstore.service.impl;

import com.onlinebookstore.common.CommonplaceResult;
import com.onlinebookstore.entity.orderserver.Order;
import com.onlinebookstore.mapper.OrderMapper;
import com.onlinebookstore.service.OrderService;
import com.onlinebookstore.util.orderutil.OrderOperationStatusEnum;
import com.onlinebookstore.util.rocketmq.RocketMQConstantPool;
import com.onlinebookstore.util.rocketmq.RocketMQMessageSendUtils;
import io.seata.core.context.RootContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
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
    @Resource
    private RocketMQMessageSendUtils rocketMQMessageSendUtils;

    public static final int WAITING = 0;
    public static final int SENT = 1;
    public static final int ACKNOWLEDGE = 2;

    /**
     * 发货，本质上是修改标志位；因为没有真实的物流，
     * 因此我们认为发货后即为到货，需要发送一个延时消息：如果用户2分钟没有签收，则进行自动签收
     * @param order 处理的订单
     * @return CommonplaceResult
     */
    @Override
    public CommonplaceResult sendBook(Order order) {
        //发货
        order.setSendStatus(SENT);
        order.setDeliveryTime(new Date());
        int row = orderMapper.modifySendStatus(order);
        if (row > 0) {
            //发送延时消息
            rocketMQMessageSendUtils.sendDelayMessageAsync(RocketMQConstantPool.Topic.R_ORDER_IMPORT, OrderOperationStatusEnum.SENT.status,
                    order, new SendCallback() {
                        @Override
                        public void onSuccess(SendResult sendResult) {
                            log.info("自动签收消息发送成功！");
                        }

                        @Override
                        public void onException(Throwable e) {
                            e.printStackTrace();
                            log.error("自动签收消息发送失败！");
                        }
                    }, 6);
        }
        return row > 0 ? CommonplaceResult.buildSuccessNoData("发货成功") : CommonplaceResult.buildErrorNoData("发货失败");
    }

    /**
     * 手动签收订单
     * @param order 被处理订单
     * @return CommonplaceResult
     */
    @Override
    public CommonplaceResult acknowledge(Order order) {
        order.setSendStatus(ACKNOWLEDGE);
        order.setEndTime(new Date());
        return orderMapper.tryAutomaticallyAcknowledge(order) > 0 ? CommonplaceResult.buildSuccessNoData("签收成功") :
                CommonplaceResult.buildErrorNoData("已经被签收！");
    }

    /**
     * 根据订单号和账号删除订单
     * @param serialNumber 订单号
     * @param username 账号
     * @return 影响行数
     */
    @Override
    public CommonplaceResult deleteOrder(String serialNumber, String username) {
        return orderMapper.deleteOrder(serialNumber, username) > 0 ? CommonplaceResult.buildSuccessNoData("删除成功") : CommonplaceResult.buildErrorNoData("删除失败");
    }

    /**
     * 根据账号查询所有订单，并且按照订单创建时间倒序排列
     * @param username 账号
     * @return 订单列表
     */
    @Override
    public CommonplaceResult selectOrderByUsername(String username) {
        List<Order> orders = orderMapper.selectOrderByUsername(username);
        log.info(orders.toString());
        if (orders.size() == 0) {
            return CommonplaceResult.buildErrorNoData("没有订单哦！");
        }
        return CommonplaceResult.buildSuccessNoMessage(orders);
    }

    /**
     * 根据订单号取消订单
     * @param serial 订单号
     * @param username 账号
     */
    @Override
    public CommonplaceResult tryCancelOrder(String serial, String username) {
        int row = orderMapper.tryCancelOrder(serial, username);
        return row > 0 ? CommonplaceResult.buildSuccessNoData("取消成功！") : CommonplaceResult.buildErrorNoData("取消失败，该订单已经完成或取消！");
    }

    /**
     * 根据订单号查询订单
     * @param serialNumber 订单号
     * @return 订单
     */
    @Override
    public CommonplaceResult selectOrderBySerialNumber(String serialNumber) {
        Order order = orderMapper.selectOrderBySerialNumber(serialNumber);
        return CommonplaceResult.buildSuccessNoMessage(order);
    }

    /**
     * 查询订单是否过期
     * @param serialNumber 订单号
     * @return 是否过期
     */
    @Override
    public boolean isExpire(String serialNumber) {
        return orderMapper.isExpire(serialNumber) != -1;
    }

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
    @Transactional
    public CommonplaceResult updateOrder(Order order) {
        log.info("order-server，xid：" + RootContext.getXID());
        log.info("订单微服务准备修改订单状态为完成");
        //模拟异常
//        int i = 1 / 0;
        return orderMapper.updateOrder(order) > 1 ? CommonplaceResult.buildSuccess(true, "更新成功！") :
                CommonplaceResult.buildError(false, "更新失败！");
    }
}
