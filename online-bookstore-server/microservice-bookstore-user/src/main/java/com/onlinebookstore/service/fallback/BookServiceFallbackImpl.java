package com.onlinebookstore.service.fallback;

import com.onlinebookstore.common.CommonplaceResult;
import com.onlinebookstore.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 图书微服务接口调用失败后的处理类
 * @author rkc
 * @version 1.0
 * @date 2020/12/14 17:32
 */
@Slf4j
@Service
public class BookServiceFallbackImpl implements BookService {

    @Override
    public CommonplaceResult selectBookByIds(List<Integer> ids) {
        log.info("bookId列表：{}", ids.toString());
        return CommonplaceResult.buildError(ids, "请稍后再试！");
    }

    @Override
    public CommonplaceResult selectBookAndStorageByBookId(Integer bookId) {
        log.info("bookId：{}", bookId);
        return CommonplaceResult.buildError(false, "服务器繁忙，请稍后再试！");
    }

    @Override
    public CommonplaceResult subtractStorageById(Map<String, Integer> pojo) {
        log.info("参数：{}", pojo.toString());
        return CommonplaceResult.buildError(false, "服务器繁忙，扣减失败！");
    }
}
