package com.onlinebookstore.service;

import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.common.message.MessageExt;

/**
 * @author rkc
 * @date 2020/10/10 9:18
 * @version 1.0
 */
public interface OrderHandler {

    /**
     * 处理消息
     * @param message 消息
     * @return 处理状态
     */
    ConsumeConcurrentlyStatus doMessage(MessageExt message);

    /**
     * 关闭
     */
    void shutdown();
}
