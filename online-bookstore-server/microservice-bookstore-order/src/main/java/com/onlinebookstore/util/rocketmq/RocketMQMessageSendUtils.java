package com.onlinebookstore.util.rocketmq;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;

/**
 * @author rkc
 * @version 1.0
 * @date 2020/12/2 9:36
 */
@Slf4j
@Component
public class RocketMQMessageSendUtils {

    @Autowired
    private DefaultMQProducer rocketMqProducer;

    /**
     * 发送普通消息
     * @param topic topic
     * @param tag tag
     * @param body 消息体
     * @return 是否成功
     */
    public boolean sendNormalMessage(String topic, String tag, Object body) {
        Message message = new Message(topic, tag, JSON.toJSONBytes(body));
        try {
            SendResult sendResult = rocketMqProducer.send(message);
            if (!ObjectUtils.isEmpty(sendResult)) {
                log.info("普通消息发送成功");
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            log.error("普通消息发送失败：" + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 发送普通延时消息
     * @param topic topic
     * @param tag tag
     * @param body 消息体
     * @param level 延时等级，从1开始：1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
     * @return 是否发送成功
     */
    public boolean sendNormalDelayMessage(String topic, String tag, Object body, int level) {
        Message message = new Message(topic, tag, JSON.toJSONBytes(body));
        //设置延时      1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
        message.setDelayTimeLevel(level);
        try {
            SendResult sendResult = rocketMqProducer.send(message);
            if (!ObjectUtils.isEmpty(sendResult)) {
                log.info("延时消息发送成功");
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            log.error("延时消息发送失败：" + e.getMessage());
            return false;
        }
    }

    /**
     * 异步发送消息
     * @param topic topic
     * @param tag tag
     * @param body 消息体
     * @param sendCallback 发送消息失败或者成功后的回调接口，业务逻辑需要实现这个接口，并且作为参数传入进来
     */
    public void sendMessageAsync(String topic, String tag, Object body, SendCallback sendCallback) {
        Message message = new Message(topic, tag, JSON.toJSONBytes(body));
        try {
            rocketMqProducer.send(message, sendCallback);
        } catch (Exception e) {
            log.error("异步消息发送失败：" + e.getMessage());
        }
    }

    /**
     * 异步发送延迟消息
     * @param topic topic
     * @param tag tag
     * @param body 消息体
     * @param sendCallback 发送消息失败或者成功后的回调接口，业务逻辑需要实现这个接口，并且作为参数传入进来
     * @param level 延时等级，从1开始：1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
     */
    public void sendDelayMessageAsync(String topic, String tag, Object body, SendCallback sendCallback, int level) {
        Message message = new Message(topic, tag, JSON.toJSONBytes(body));
        message.setDelayTimeLevel(level);
        try {
            rocketMqProducer.send(message, sendCallback);
        } catch (Exception e) {
            log.error("异步消息发送失败：" + e.getMessage());
        }
    }

    /**
     * sendOneWay：无需要等待响应，TPS最快，但是没有结果反馈且消息可能丢失
     * @param topic topic
     * @param tag tag
     * @param body 消息体
     */
    public void sendOneWay(String topic, String tag, Object body) {
        Message message = new Message(topic, tag, JSON.toJSONBytes(body));
        try {
            rocketMqProducer.sendOneway(message);
        } catch (Exception e) {
            log.error("OneWay");
        }
    }
}
