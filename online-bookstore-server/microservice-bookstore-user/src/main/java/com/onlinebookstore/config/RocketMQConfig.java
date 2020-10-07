package com.onlinebookstore.config;

import com.alibaba.fastjson.JSON;
import com.onlinebookstore.entity.userserver.Account;
import com.onlinebookstore.util.rocketmq.RocketMQConstantPool;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author rkc
 * @date 2020/10/6 21:39
 * @version 1.0
 */
@Component
public class RocketMQConfig {

    @Autowired
    private RocketMQProperties rocketMQProperties;

    /**
     * 初始化生产者
     * @throws Exception
     */
    @Bean
    public DefaultMQProducer defaultMQProducer() throws Exception {
        //实例化消息生产者
        DefaultMQProducer defaultMQProducer = new DefaultMQProducer(RocketMQConstantPool.ProducerGroup.R_PRODUCER_ORDER_GROUP);
        //生产者投递消息重试次数，默认为2
        defaultMQProducer.setRetryTimesWhenSendFailed(3);
        //设置namesrv
        defaultMQProducer.setNamesrvAddr(rocketMQProperties.getNamesrvAddr());
        //消息发送超时时间
        defaultMQProducer.setSendMsgTimeout(rocketMQProperties.getSendMsgTimeoutMillis());
        //启动Producer实例
        defaultMQProducer.start();


        Account account = new Account();
        account.setUsername("测试名称");
        account.setCreateTime(new Date());
        account.setPassword("12312312");
        account.setScore(12);
        Message message = new Message("test-topic", "test-tag", JSON.toJSONBytes(account));
        defaultMQProducer.send(message);
        return defaultMQProducer;
    }
}
