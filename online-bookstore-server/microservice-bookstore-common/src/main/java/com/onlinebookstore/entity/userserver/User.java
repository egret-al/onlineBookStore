package com.onlinebookstore.entity.userserver;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @author rkc
 * @version 1.0
 * @date 2020/9/11 9:31
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

    private static final long serialVersionUID = -6686177318399301644L;
    /**
     * 主键自增长字段
     */
    private Integer id;

    /**
     * 账号表的外键字段
     */
    @JsonProperty("account_username")
    private String accountUsername;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 用户生日
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date birthday;

    /**
     * 性别
     */
    private String sex;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 上一次登录时间
     */
    @JsonProperty("last_login_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date lastLoginTime;

    /**
     * 账号信息
     */
    private Account account;
}
