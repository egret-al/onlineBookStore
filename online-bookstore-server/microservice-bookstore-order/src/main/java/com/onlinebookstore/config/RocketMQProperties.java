package com.onlinebookstore.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author rkc
 * @date 2020/10/6 21:34
 * @version 1.0
 */
@Data
@Component
@ConfigurationProperties(prefix = "rocketmq")
public class RocketMQProperties {

    /**
     * namesrv地址
     */
    private String namesrvAddr;

    /**
     * 超时时间
     */
    private Integer sendMsgTimeoutMillis;

    /**
     * 失败重试次数
     */
    private Integer reconsumeTimes;
}
