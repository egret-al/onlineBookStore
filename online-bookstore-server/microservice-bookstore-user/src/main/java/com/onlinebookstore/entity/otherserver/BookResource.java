package com.onlinebookstore.entity.otherserver;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.onlinebookstore.util.BookConstantPool;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @author rkc
 * @date 2020/9/17 15:57
 * @version 1.0
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BookResource implements Serializable {

    /*
    自增长id
     */
    private Integer id;

    /*
    图书外键字段
     */
    @JsonProperty("book_id")
    private Integer bookId;

    /*
    资源路径
     */
    @JsonProperty("resource_url")
    private String resourceUrl;

    /*
    标识，用于区别该资源的作用
     */
    private String symbol;

    /*
    补充说明
     */
    private String supplement;

    @JsonProperty("create_time")
    @JsonFormat(pattern = BookConstantPool.TIME_FORMAT, timezone = BookConstantPool.TIMEZONE)
    private Date createTime;
}
