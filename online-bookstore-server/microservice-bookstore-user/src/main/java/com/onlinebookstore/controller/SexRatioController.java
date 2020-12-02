package com.onlinebookstore.controller;

import com.onlinebookstore.common.CommonplaceResult;
import com.onlinebookstore.service.SexRatioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author rkc
 * @version 1.0
 * @date 2020/11/30 10:42
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/sex-ratio/pri")
public class SexRatioController {
    @Resource
    private SexRatioService sexRatioService;

    /**
     * 得到男女性别比例
     * @return CommonplaceResult
     */
    @PostMapping("getSexRatio")
    public CommonplaceResult getSexRatio() {
        return sexRatioService.getSexRatio();
    }
}
