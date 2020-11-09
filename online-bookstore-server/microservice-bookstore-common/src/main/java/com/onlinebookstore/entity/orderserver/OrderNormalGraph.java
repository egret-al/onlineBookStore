package com.onlinebookstore.entity.orderserver;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * @author rkc
 * @date 2020/11/7 10:33
 * @version 1.0
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrderNormalGraph {
    /**
     * 横坐标数据名称
     */
    private List<String> xData;

    /**
     * 一条记录是一个SeriesItem，series代表了多条多个seriesItem的集合
     */
    private List<OrderSeriesItem> series;
}
