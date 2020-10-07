package com.onlinebookstore.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.onlinebookstore.entity.userserver.Account;
import com.onlinebookstore.util.rocketmq.RocketMQConstantPool;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.*;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;


/**
 * @author rkc
 * @date 2020/10/6 21:39
 * @version 1.0
 */
@Slf4j
@Component
public class RocketMQConfig {

    @Autowired
    private RocketMQProperties rocketMQProperties;

    @Bean
    public DefaultMQPushConsumer defaultMQPushConsumer() throws Exception {
        DefaultMQPushConsumer defaultMQPushConsumer = new DefaultMQPushConsumer(RocketMQConstantPool.ConsumerGroup.R_CONSUMER_ORDER_GROUP);
        defaultMQPushConsumer.setNamesrvAddr(rocketMQProperties.getNamesrvAddr());
        defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);

        //订阅topic，并且设置不过滤
        defaultMQPushConsumer.subscribe(RocketMQConstantPool.Topic.R_ORDER_IMPORT, "*");
        //不能设置广播模式。否则投递到broker的消息就会被每个consumer进行消费，造成消费多次
        defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);


        defaultMQPushConsumer.registerMessageListener((MessageListenerConcurrently) (msgs, context) -> {
            MessageExt message = msgs.get(0);
            //得到重试次数
            int times = message.getReconsumeTimes();
            try {
                String topic = message.getTopic();
                String tag = message.getTags();
                log.info("topic: " + topic);
                log.info("tag: " + tag);
                JSONObject jsonObject = (JSONObject) JSON.parse(message.getBody());
                Account account = jsonObject.toJavaObject(Account.class);
                log.info(String.valueOf(account));

                //消费成功
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            } catch (Exception e) {
                log.error("RocketMQ消费者测试报错：" + e.getMessage());
                if (times > 3) {
                    log.error("重试次数已经达到，请工作人员进行手动处理！");
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
                //一会继续重试
                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
            }
        });
        defaultMQPushConsumer.start();

        return defaultMQPushConsumer;
    }
}
