package com.onlinebookstore.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author rkc
 * @version 1.0
 * @date 2020/9/11 9:35
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    /*
    主键自增长字段
     */
    private Integer id;

    /*
    账号表的外键字段
     */
    @JsonProperty("account_username")
    private String accountUsername;

    /*
    地址
     */
    private String address;

    /*
    账户对象
     */
    private Account account;
}
