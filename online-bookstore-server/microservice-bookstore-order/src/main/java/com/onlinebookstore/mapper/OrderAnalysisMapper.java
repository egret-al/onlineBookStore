package com.onlinebookstore.mapper;

import com.onlinebookstore.entity.orderserver.Order;
import com.onlinebookstore.entity.orderserver.OrderCoordinateAxis;
import com.onlinebookstore.entity.orderserver.OrderCoordinateAxisItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author rkc
 * @date 2020/11/2 9:27
 * @version 1.0
 */
@Mapper
public interface OrderAnalysisMapper {

    /**
     * 得到成功销售的日期
     * @return 日期字符串集合
     */
    List<String> getOrderSuccessDate();

    /**
     * 获取指定月的订单
     * @param yearAndMonth YYYY-mm
     * @return 指定月份的成交订单集合
     */
    List<Order> getOrdersByMonthly(String yearAndMonth);

    /**
     * 得到已经交易成功的订单的图书名称和销售数量，封装到OrderCoordinateAxis
     * @param day day天之内的数据，传入-1则为全部数据
     * @param count 销售数量在前count条的数据
     * @return OrderCoordinateAxis
     */
    List<OrderCoordinateAxisItem<String, Integer>> getBookNameAndCountWithDealtOrder(@Param("day") int day, @Param("count") int count);

    /**
     * 得到已经交易成功的订单的图书名称和改图书的总销售额
     * @param day day天之内的数据，传入-1则为全部数据
     * @param count 销售数量在前count条的数据
     * @return OrderCoordinateAxis
     */
    List<OrderCoordinateAxisItem<String, Integer>> getBookNameAndWholePriceWithDealtOrder(@Param("day") int day, @Param("count") int count);
}
