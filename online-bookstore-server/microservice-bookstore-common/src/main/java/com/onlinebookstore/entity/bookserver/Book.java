package com.onlinebookstore.entity.bookserver;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.onlinebookstore.util.bookutil.BookConstantPool;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author rkc
 * @date 2020/9/17 15:50
 * @version 1.0
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Book implements Serializable {

    /**
     * 自增长id
     */
    private Integer id;

    /**
     * 图书名称
     */
    @JsonProperty("book_name")
    private String bookName;

    /**
     * isbn
     */
    private String isbn;

    /**
     * 作者
     */
    private String author;

    /**
     * 出版商
     */
    private String publisher;

    /**
     * 价格
     */
    private Integer price;

    /**
     * 上架时间
     */
    @JsonProperty("create_time")
    @JsonFormat(pattern = BookConstantPool.TIME_FORMAT, timezone = BookConstantPool.TIMEZONE)
    private Date createTime;

    /**
     * 类型id
     */
    @JsonProperty("t_id")
    private Integer typeId;

    /**
     * 主封面
     */
    @JsonProperty("main_cover")
    private String mainCover;

    /**
     * 描述信息
     */
    private String description;

    /**
     * 图书资源列表（图片）
     */
    private List<BookResource> bookResources;

    /**
     * 图书库存信息
     */
    private BookStorage bookStorage;
}
