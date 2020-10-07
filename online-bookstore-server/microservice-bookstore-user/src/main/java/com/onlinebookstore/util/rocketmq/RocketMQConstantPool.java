package com.onlinebookstore.util.rocketmq;

/**
 * @author rkc
 * @date 2020/10/6 21:43
 * @version 1.0
 */
public class RocketMQConstantPool {

    /**
     * top
     */
    public static class Topic {
        public static final String R_ORDER_IMPORT = "R_ORDER_IMPORT";
    }

    /**
     * TAG
     */
    public static class Tag {
        public static final String R_ORDER_IMPORT_MODIFY_STATUS = "R_ORDER_IMPORT_MODIFY_STATUS";
    }

    /**
     * consumeGroup 生产者
     */
    public static class ProducerGroup {
        public static final String R_PRODUCER_ORDER_GROUP = "R_PRODUCER_ORDER_GROUP";
    }

    /**
     * consumeGroup 消费者
     */
    public static class ConsumerGroup {
        public static final String R_CONSUMER_ORDER_GROUP = "R_CONSUMER_ORDER_GROUP";
    }
}
