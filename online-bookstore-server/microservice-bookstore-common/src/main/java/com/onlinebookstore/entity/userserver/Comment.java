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
 * @date 2020/9/11 9:37
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Comment implements Serializable {

    private static final long serialVersionUID = -5367392727783367938L;
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
     * 来自bookstore_books库的图书id，这里也就是被评论的物品
     */
    @JsonProperty("book_id")
    private Integer bookId;

    /**
     * 评论的内容
     */
    private String content;

    /**
     * 评论的时间
     */
    @JsonProperty("create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * 评论的用户
     */
    private Account account;
}
