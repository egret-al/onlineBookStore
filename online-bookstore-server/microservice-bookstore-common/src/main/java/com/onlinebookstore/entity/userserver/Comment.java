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

    /**
     * 主键自增长字段
     */
    private Integer id;

    /**
     * 用户信息表的外键字段，这里也就是发起评论的人
     */
    @JsonProperty("user_id")
    private Integer userId;

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

}
