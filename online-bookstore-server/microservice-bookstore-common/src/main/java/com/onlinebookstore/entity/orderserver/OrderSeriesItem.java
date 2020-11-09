package com.onlinebookstore.entity.orderserver;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * 数据集，该项记录的多条数据，如：《高等代数》在1,2,3月销量分别为100,200,300，则该对象数据应为data：100, 200, 300，name："高等代数"，
 * 而1,2,3月则为OrderNormalGraph的xData属性
 * @author rkc
 * @date 2020/11/7 10:36
 * @version 1.0
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrderSeriesItem {

    private List<Integer> data;
    private String name;
}
