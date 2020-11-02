package com.onlinebookstore.entity.orderserver;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * 图表每项的集合，由多个CoordinateAxisItem组成，能够构成一个基本的图表数据
 * @author rkc
 * @date 2020/11/2 9:33
 * @version 1.0
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrderCoordinateAxis<X, Y> {

    private List<OrderCoordinateAxisItem<X, Y>> orderCoordinateAxisItemList;
}
