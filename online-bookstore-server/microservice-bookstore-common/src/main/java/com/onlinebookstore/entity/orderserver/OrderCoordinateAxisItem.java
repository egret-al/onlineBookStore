package com.onlinebookstore.entity.orderserver;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 坐标每项的实体类，由x轴和y轴的数据组成，一个图标通常由多个CoordinateAxis组成
 * @author rkc
 * @date 2020/11/2 9:30
 * @version 1.0
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrderCoordinateAxisItem<X, Y> {

    @JsonProperty("x_axis_data")
    private X xAxisData;

    @JsonProperty("y_axis_data")
    private Y yAxisData;
}
