package com.onlinebookstore.controller;

import com.onlinebookstore.common.CommonplaceResult;
import com.onlinebookstore.service.OrderGraphService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author rkc
 * @version 1.0
 * @date 2020/11/29 16:40
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/order-graph")
public class OrderGraphController {
    @Resource
    private OrderGraphService orderGraphService;

    /**
     * 得到最好的6个的最新半年的销售额
     * @return CommonplaceResult
     */
    @PostMapping("pri/getPriceOverviewHalfYearTop6")
    public CommonplaceResult getPriceOverviewHalfYearTop6() {
        return orderGraphService.getPriceOverviewHalfYearTop6();
    }

    /**
     * 得到最好的6个的最新半年的销售量
     * @return CommonplaceResult
     */
    @PostMapping("pri/getCountOverviewHalfYearTop6")
    public CommonplaceResult getCountOverviewHalfYearTop6() {
        return orderGraphService.getCountOverviewHalfYearTop6();
    }
}
