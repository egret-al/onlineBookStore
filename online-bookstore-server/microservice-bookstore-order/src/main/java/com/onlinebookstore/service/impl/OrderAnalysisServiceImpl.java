package com.onlinebookstore.service.impl;

import com.onlinebookstore.common.CommonplaceResult;
import com.onlinebookstore.entity.orderserver.OrderCoordinateAxisItem;
import com.onlinebookstore.mapper.OrderAnalysisMapper;
import com.onlinebookstore.service.OrderAnalysisService;
import com.onlinebookstore.util.orderutil.OrderConstantPool;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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
