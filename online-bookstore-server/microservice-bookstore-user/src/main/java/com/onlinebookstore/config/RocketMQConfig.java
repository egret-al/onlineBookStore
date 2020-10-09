package com.onlinebookstore.config;

import com.alibaba.fastjson.JSON;
import com.onlinebookstore.entity.userserver.Account;
import com.onlinebookstore.util.rocketmq.RocketMQConstantPool;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
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
    @Bean(destroyMethod = "shutdown")
    public DefaultMQProducer defaultMQProducer() throws Exception {
        //实例化消息生产者
        DefaultMQProducer mqProducer = new DefaultMQProducer(RocketMQConstantPool.ProducerGroup.R_PRODUCER_ORDER_GROUP);
        //生产者投递消息重试次数，默认为2
        mqProducer.setRetryTimesWhenSendFailed(3);
        //设置namesrv
        mqProducer.setNamesrvAddr(rocketMQProperties.getNamesrvAddr());
        //消息发送超时时间
        mqProducer.setSendMsgTimeout(rocketMQProperties.getSendMsgTimeoutMillis());
        //启动Producer实例
        mqProducer.start();
        return mqProducer;
    }
}
