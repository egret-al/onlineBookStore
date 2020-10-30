package com.onlinebookstore.service;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;


/**
 * 消息消费者抽象类，对应的具体业务类只需要继承该类，并且重写registerMessageListener方法即可
 * @author rkc
 * @date 2020/10/8 16:52
 * @version 1.0
 */
public abstract class AbstractRocketMQService {

    protected DefaultMQPushConsumer mqPushConsumer;

    public AbstractRocketMQService(String nameSrv, String group, String topic, String expression) throws MQClientException {
        this.initPushConsumer(nameSrv, group, topic, expression);
    }

    /**
     * 指定信息进行初始化
     * @param nameSrv nameSrv地址
     * @param group 消费组
     * @param topic topic
     * @param expression tag过滤表达式
     * @return DefaultMQPushConsumer
     * @throws MQClientException 订阅失败
     */
    protected void initPushConsumer(String nameSrv, String group, String topic, String expression) throws MQClientException {
        DefaultMQPushConsumer mqPushConsumer = new DefaultMQPushConsumer(group);
        mqPushConsumer.setNamesrvAddr(nameSrv);
        mqPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        //订阅topic，并且设置不过滤
        mqPushConsumer.subscribe(topic, expression);
        //不能设置广播模式。否则投递到broker的消息就会被每个consumer进行消费，造成消费多次
        mqPushConsumer.setMessageModel(MessageModel.CLUSTERING);
        this.mqPushConsumer = mqPushConsumer;
        //注册监听器并且开启监听
        start();
    }

    /**
     * 钩子函数，注册消息监听器，逻辑由子类编写
     */
    protected abstract void registerMessageListener();

    /**
     * 开启消息监听
     * @throws MQClientException 开启失败
     */
    private void start() throws MQClientException {
        registerMessageListener();
        mqPushConsumer.start();
    }
}
