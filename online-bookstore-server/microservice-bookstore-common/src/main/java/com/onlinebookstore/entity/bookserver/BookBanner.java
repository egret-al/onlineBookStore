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

/**
 * @author rkc
 * @date 2020/9/24 10:56
 * @version 1.0
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BookBanner implements Serializable {

    private static final long serialVersionUID = -271571376380759367L;
    /**
     * 自增长id
     */
    private Integer id;

    /**
     * 图片路径
     */
    @JsonProperty("resource_url")
    private String resourceUrl;

    /**
     * 修改时间
     */
    @JsonProperty("modify_time")
    @JsonFormat(pattern = BookConstantPool.TIME_FORMAT, timezone = BookConstantPool.TIMEZONE)
    private Date modifyTime;

    /**
     * bookId
     */
    @JsonProperty("b_id")
    private Integer bookId;
}
