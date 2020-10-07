package com.onlinebookstore.mapper;

import com.onlinebookstore.entity.orderserver.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author rkc
 * @date 2020/9/21 8:17
 * @version 1.0
 */
@Mapper
public interface OrderMapper {

    /**
     * 查询所有订单
     */
    List<Order> selectAll();

    /**
     * 根据账号查询所有订单
     * @param username 账号
     */
    List<Order> selectAllByUsername(String username);

    /**
     * 根据图书id查询订单
     * @param bookId 图书id
     */
    List<Order> selectAllByBookId(Integer bookId);

    /**
     * 插入订单
     * @param order 订单对象
     * @return 影响行数
     */
    int insertOrder(Order order);

    /**
     * 根据订单号更新订单状态
     * @param code 订单状态码
     * @param serialNumber 订单号
     * @return 影响行数
     */
    int updateOrderStatus(@Param("code") Integer code, @Param("serialNumber") String serialNumber);

    /**
     * 更新订单
     * @param order 订单对象
     */
    int updateOrder(Order order);

    /**
     * 根据订单编号删除订单
     * @param serialNumber 订单编号
     * @return 影响行数
     */
    int deleteOrderBySerialNumber(String serialNumber);
}
