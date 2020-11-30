package com.onlinebookstore.entity.orderserver;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author rkc
 * @version 1.0
 * @date 2020/11/29 16:12
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrderAnalysisItem implements Serializable {
    private static final long serialVersionUID = 5194803637231245291L;

    @JsonProperty("book_id")
    private Integer bookId;

    @JsonProperty("book_name")
    private String bookName;

    @JsonProperty("total_price")
    private Integer totalPrice;

    @JsonProperty("total_count")
    private Integer totalCount;

    @JsonProperty("data_date")
    private String dataDate;
}
