package com.onlinebookstore.config;

import com.onlinebookstore.util.rocketmq.RocketMQConstantPool;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @author rkc
 * @date 2020/10/6 21:39
 * @version 1.0
 */
@Component
public class RocketMQConfig {

    public static final String namesrvAddr = "112.126.78.225:9876";
    public static final int sendMsgTimeoutMillis = 3000;
    public static final int reconsumeTimes = 3;

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
        mqProducer.setNamesrvAddr(namesrvAddr);
        //消息发送超时时间
        mqProducer.setSendMsgTimeout(sendMsgTimeoutMillis);
        //启动Producer实例
        mqProducer.start();
        return mqProducer;
    }
}
