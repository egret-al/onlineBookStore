package com.onlinebookstore.service;

import com.onlinebookstore.common.CommonplaceResult;

/**
 * @author rkc
 * @version 1.0
 * @date 2020/11/30 10:38
 */
public interface SexRatioService {

    /**
     * 得到男女性别比例
     * @return CommonplaceResult
     */
    CommonplaceResult getSexRatio();
}
