package com.onlinebookstore.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author rkc
 * @version 1.0
 * @date 2020/12/5 11:11
 */
@Data
@Component
@ConfigurationProperties(prefix = "linux")
public class SftpProperties {
    private String username;
    private String password;
    private String ip;
    private String port;
    private Integer id;
    private String uploadPath;
    private String downPath;
}
