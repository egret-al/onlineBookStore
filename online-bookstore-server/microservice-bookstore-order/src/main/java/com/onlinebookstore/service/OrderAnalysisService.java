package com.onlinebookstore.service;

import com.onlinebookstore.common.CommonplaceResult;

/**
 * @author rkc
 * @date 2020/11/2 11:21
 * @version 1.0
 */
public interface OrderAnalysisService {

    /**
     * 得到每月销售量
     * @return CommonplaceResult
     */
    CommonplaceResult getMonthlyDealCount();

    /**
     * 得到每月销售额
     * @return CommonplaceResult
     */
    CommonplaceResult getMonthlyDealPrice();

    /**
     * 得到已经交易成功的订单的图书名称和销售数量，封装到CommonplaceResult
     * @param day day天之内的数据，传入-1则为全部数据
     * @param count 销售数量在前count条的数据
     * @return CommonplaceResult
     */
    CommonplaceResult getBookNameAndCountWithDealtOrder(int day, int count);

    /**
     * 得到已经交易成功的订单的图书名称和改图书的总销售额
     * @param day day天之内的数据，传入-1则为全部数据
     * @param count 销售数量在前count条的数据
     * @return CommonplaceResult
     */
    CommonplaceResult getBookNameAndWholePriceWithDealtOrder(int day, int count);
}
