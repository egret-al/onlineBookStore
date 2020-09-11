package com.onlinebookstore.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
public class Comment {

    /*
    主键自增长字段
     */
    private Integer id;

    /*
    用户信息表的外键字段，这里也就是发起评论的人
     */
    private Integer userId;

    /*
    来自bookstore_books库的图书id，这里也就是被评论的物品
     */
    private Integer bookId;

    /*
    评论的内容
     */
    private String content;

    /*
    评论的时间
     */
    private Date createTime;

}
