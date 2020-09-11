package com.onlinebookstore.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
public class User {

    /*
    主键自增长字段
     */
    private Integer id;

    /*
    账号表的外键字段
     */
    private String accountUsername;

    /*
    昵称
     */
    private String nickname;

    /*
    用户生日
     */
    private Date birthday;

    /*
    性别
     */
    private String sex;

    /*
    手机号
     */
    private String phone;

    /*
    上一次登录时间
     */
    private Date lastLoginTime;

    /**
     * 账号信息
     */
    private Account account;
}
