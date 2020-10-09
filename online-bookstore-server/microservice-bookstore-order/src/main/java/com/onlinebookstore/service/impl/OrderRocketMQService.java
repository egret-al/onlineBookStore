package com.onlinebookstore.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.onlinebookstore.config.RocketMQProperties;
import com.onlinebookstore.entity.orderserver.Order;
import com.onlinebookstore.exception.SerialNumberNullException;
import com.onlinebookstore.mapper.OrderMapper;
import com.onlinebookstore.service.AbstractRocketMQService;
import com.onlinebookstore.util.orderutil.OrderOperationStatusEnum;
import com.onlinebookstore.util.rocketmq.RocketMQConstantPool;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.util.Date;

/**
 * @author rkc
 * @date 2020/10/8 17:05
 * @version 1.0
 */
@Slf4j
@Service
public class OrderRocketMQService extends AbstractRocketMQService {

    @Resource
    private OrderMapper orderMapper;

    public OrderRocketMQService() throws MQClientException {
        super(RocketMQProperties.NAMESRV_ADDR, RocketMQConstantPool.ConsumerGroup.R_CONSUMER_ORDER_GROUP,
                RocketMQConstantPool.Topic.R_ORDER_IMPORT, "*");
    }

    /**
     * 接收消息进行业务逻辑处理
     */
    @Override
    protected void registerMessageListener() {
        mqPushConsumer.registerMessageListener((MessageListenerConcurrently) (messageList, context) -> {
            MessageExt message = messageList.get(0);
            return doMessage(message);
        });
        mqPushConsumer.shutdown();
    }

    /**
     * 处理消息，根据tag进行判断需要执行哪些步骤
     * @param message 消息
     * @return 消息处理状态
     */
    public ConsumeConcurrentlyStatus doMessage(MessageExt message) {
        int times = message.getReconsumeTimes();
        try {
            String tag = message.getTags();
            //根据tag进行判断是什么操作
            if (tag.equals(OrderOperationStatusEnum.CANCEL.status)) {
                JSONObject jsonObject = (JSONObject) JSON.parse(message.getBody());
                Order order = jsonObject.toJavaObject(Order.class);
                if (tryCancelOrder(order)) {
                    log.info("订单取消成功");
                } else {
                    log.error("订单已经完成");
                }
            } else {
                //TODO 其他操作
                log.info("其它操作");
            }
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        } catch (Exception e) {
            log.error("RocketMQ消费者测试报错：" + e.getMessage());
            if (times > RocketMQProperties.RECONSUME_TIMES) {
                log.error("重试次数已经达到，请工作人员进行手动处理！");
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
            //一会继续重试
            return ConsumeConcurrentlyStatus.RECONSUME_LATER;
        }
    }

    /**
     * 取消订单操作应该是延时消息，通过消息生产者在消息投递的时候指定延时时间，消费者接收
     * 到该消息就会去尝试修改“未支付”状态为“失败”状态，返回true，如果订单已经为完成，则
     * 修改失败，返回false
     * @param order 订单实体类
     */
    public boolean tryCancelOrder(Order order) {
        //TODO 调用Mapper尝试取消订
        if (StringUtils.isEmpty(order.getSerialNumber())) {
            throw new SerialNumberNullException(-1, "订单号不能为空！");
        }
        order.setEndTime(new Date());
        return orderMapper.tryCancelOrder(order.getSerialNumber()) > 0;
    }

    @PreDestroy
    public void shutdown() {
        mqPushConsumer.shutdown();
    }
}
