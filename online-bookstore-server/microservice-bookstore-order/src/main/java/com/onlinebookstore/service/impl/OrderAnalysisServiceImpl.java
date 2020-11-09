package com.onlinebookstore.service.impl;

import com.onlinebookstore.common.CommonplaceResult;
import com.onlinebookstore.entity.orderserver.Order;
import com.onlinebookstore.entity.orderserver.OrderCoordinateAxisItem;
import com.onlinebookstore.entity.orderserver.OrderNormalGraph;
import com.onlinebookstore.mapper.OrderAnalysisMapper;
import com.onlinebookstore.service.OrderAnalysisService;
import com.onlinebookstore.util.orderutil.OrderConstantPool;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author rkc
 * @date 2020/11/2 11:22
 * @version 1.0
 */
@Service
public class OrderAnalysisServiceImpl implements OrderAnalysisService {

    @Resource
    private OrderAnalysisMapper orderAnalysisMapper;

    /**
     * 得到每月每本书的销售量
     * @return CommonplaceResult
     */
    @Override
    public CommonplaceResult getMonthlyDealCount() {
        List<String> date = orderAnalysisMapper.getOrderSuccessDate();
        Map<String, Map<String, Integer>> graph = new ConcurrentHashMap<>(10);

        for (String dateItem : date) {
            List<Order> orders = orderAnalysisMapper.getOrdersByMonthly(dateItem);
            Map<String, Integer> item = new ConcurrentHashMap<>(10);
            for (Order order : orders) {
                if (item.containsKey(order.getBookName())) {
                    item.put(order.getBookName(), item.get(order.getBookName()) + order.getProductCount());
                } else {
                    item.put(order.getBookName(), order.getProductCount());
                }
            }
            graph.put(dateItem, item);
        }

        return CommonplaceResult.buildSuccessNoMessage(graph);
    }

    /**
     * 得到每月销售额
     * @return CommonplaceResult
     */
    @Override
    public CommonplaceResult getMonthlyDealPrice() {
        List<String> date = orderAnalysisMapper.getOrderSuccessDate();
        Map<String, Map<String, Integer>> graph = new ConcurrentHashMap<>(10);

        for (String dateItem : date) {
            List<Order> orders = orderAnalysisMapper.getOrdersByMonthly(dateItem);
            Map<String, Integer> item = new ConcurrentHashMap<>(10);
            for (Order order : orders) {
                if (item.containsKey(order.getBookName())) {
                    item.put(order.getBookName(), item.get(order.getBookName()) + order.getWholePrice());
                } else {
                    item.put(order.getBookName(), order.getWholePrice());
                }
            }
            graph.put(dateItem, item);
        }
        return CommonplaceResult.buildSuccessNoMessage(graph);
    }

    /**
     * 得到已经交易成功的订单的图书名称和销售数量，封装到CommonplaceResult
     * @return CommonplaceResult
     */
    @Override
    public CommonplaceResult getBookNameAndCountWithDealtOrder(int day, int count) {
        if (day < 0) {
            day = OrderConstantPool.WEEK;
        }
        if (count < 0) {
            count = OrderConstantPool.TOP_FIVE;
        }
        return CommonplaceResult.buildSuccessNoMessage(orderAnalysisMapper.getBookNameAndCountWithDealtOrder(day, count));
    }

    /**
     * 得到已经交易成功的订单的图书名称和改图书的总销售额
     * @return CommonplaceResult
     */
    @Override
    public CommonplaceResult getBookNameAndWholePriceWithDealtOrder(int day, int count) {
        if (day < 0) {
            day = OrderConstantPool.WEEK;
        }
        if (count < 0) {
            count = OrderConstantPool.TOP_FIVE;
        }
        return CommonplaceResult.buildSuccessNoMessage(orderAnalysisMapper.getBookNameAndWholePriceWithDealtOrder(day, count));
    }
}
