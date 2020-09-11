package com.onlinebookstore.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * @author rkc
 * @version 1.0
 * @date 2020/9/11 9:01
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    /*
    登录账号
     */
    private String username;

    /*
    登录密码
     */
    private String password;

    /*
    账号积分
     */
    private Integer score;

    /*
    账号创建时间
     */
    private Date createTime;

    /*
    用户信息
     */
    private User user;
}
