package com.onlinebookstore.entity;

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
 * @date 2020/9/17 22:02
 * @version 1.0
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BookStorage implements Serializable {

    /*
    自增长id
     */
    private Integer id;

    /*
    书本的id
     */
    @JsonProperty("book_id")
    private Integer bookId;

    /*
    最后一次添加时间
     */
    @JsonProperty("last_add_time")
    @JsonFormat(pattern = BookConstantPool.TIME_FORMAT, timezone = BookConstantPool.TIMEZONE)
    private Date lastAddTime;

    /*
    剩余数量
     */
    @JsonProperty("residue_count")
    private Integer residueCount;
}
