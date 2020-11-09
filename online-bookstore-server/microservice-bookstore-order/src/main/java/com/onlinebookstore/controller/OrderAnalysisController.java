package com.onlinebookstore.controller;

import com.onlinebookstore.common.CommonplaceResult;
import com.onlinebookstore.service.OrderAnalysisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 特别用于订单数据分析的controller
 * @author rkc
 * @date 2020/11/2 9:22
 * @version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/order-analysis")
public class OrderAnalysisController {
    @Resource
    private OrderAnalysisService orderAnalysisService;

    /**
     * 得到已经交易成功的订单的图书名称和销售数量
     */
    @GetMapping("pri/getMonthlyDealCount")
    public CommonplaceResult getMonthlyDealCount() {
        return orderAnalysisService.getMonthlyDealCount();
    }

    /**
     * 得到已经交易成功的订单的图书名称和改图书的总销售额
     */
    @GetMapping("pri/getMonthlyDealPrice")
    public CommonplaceResult getMonthlyDealPrice() {
        return orderAnalysisService.getMonthlyDealPrice();
    }
}
