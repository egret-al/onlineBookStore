package com.onlinebookstore.service.impl;

import com.onlinebookstore.common.CommonplaceResult;
import com.onlinebookstore.mapper.SexRatioMapper;
import com.onlinebookstore.service.SexRatioService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author rkc
 * @version 1.0
 * @date 2020/11/30 10:38
 */
@Service
public class SexRatioServiceImpl implements SexRatioService {
    @Resource
    private SexRatioMapper sexRatioMapper;

    /**
     * 得到男女性别比例
     * @return CommonplaceResult
     */
    @Override
    public CommonplaceResult getSexRatio() {
        return CommonplaceResult.buildSuccessNoMessage(sexRatioMapper.getSexRatio());
    }
}
