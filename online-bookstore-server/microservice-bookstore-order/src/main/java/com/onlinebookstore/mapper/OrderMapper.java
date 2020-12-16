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
     * 尝试自动签收
     * @param order 待签收订单
     * @return 是否签收成功
     */
    int tryAutomaticallyAcknowledge(Order order);

    /**
     * 修改发货状态
     * @param order order
     * @return 影响行数
     */
    int modifySendStatus(Order order);

    /**
     * 根据订单号和账号删除订单
     * @param serialNumber 订单号
     * @param username 账号
     * @return 影响行数
     */
    int deleteOrder(@Param("serialNumber") String serialNumber, @Param("username") String username);

    /**
     * 根据账号查询所有订单，并且按照订单创建时间倒序排列
     * @param username 账号
     * @return 订单列表
     */
    List<Order> selectOrderByUsername(String username);

    /**
     * 根据订单号查询订单
     * @param serialNumber 订单号
     * @return 订单对象
     */
    Order selectOrderBySerialNumber(String serialNumber);

    /**
     * 根据订单号查询订单是否过期
     * @param serialNumber 订单号
     * @return 返回订单状态
     */
    int isExpire(String serialNumber);

    /**
     * 消费者将未支付的订单取消
     * @param serialNumber 订单号
     * @return 返回0则为取消失败，订单已经被处理，返回1则取消成功，用户没有在规定时间内进行支付
     */
    int mqTryCancelOrder(@Param("serialNumber") String serialNumber);

    /**
     * 将未支付的订单取消
     * @param serialNumber 订单号
     * @return 返回0则为取消失败，订单已经被处理，返回1则取消成功，用户没有在规定时间内进行支付
     */
    int tryCancelOrder(@Param("serialNumber") String serialNumber, @Param("username") String username);

    /**
     * 查询所有订单
     */
    List<Order> selectAll();

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
