package com.onlinebookstore.service;

import com.onlinebookstore.common.CommonplaceResult;

/**
 * @author rkc
 * @version 1.0
 * @date 2020/11/29 10:15
 */
public interface OrderGraphService {

    /**
     * 得到销售量最好的6个的最新半年的销售额
     * @return CommonplaceResult
     */
    CommonplaceResult getPriceOverviewHalfYearTop6();

    /**
     * 得到销售量最好的6个的最新半年的销售量
     * @return CommonplaceResult
     */
    CommonplaceResult getCountOverviewHalfYearTop6();
}
