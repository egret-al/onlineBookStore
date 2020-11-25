package com.onlinebookstore.entity.userserver;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.onlinebookstore.entity.bookserver.Book;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @author rkc
 * @version 1.0
 * @date 2020/11/25 19:07
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingTrolley implements Serializable {
    private static final long serialVersionUID = -7467178365897119543L;

    /**
     * 主键
     */
    private Integer id;

    /**
     * 图书表的冗余字段
     */
    @JsonProperty("book_id")
    private Integer bookId;

    /**
     * 账号表账号
     */
    @JsonProperty("account_username")
    private String accountUsername;

    /**
     * 收藏数量
     */
    @JsonProperty("collect_count")
    private Integer collectCount;

    /**
     * 首次创建时间
     */
    @JsonProperty("create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    private Account account;

    private Book book;
}
