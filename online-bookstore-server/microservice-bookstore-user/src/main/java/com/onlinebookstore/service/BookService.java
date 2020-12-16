package com.onlinebookstore.service;

import com.onlinebookstore.common.CommonplaceResult;
import com.onlinebookstore.service.fallback.BookServiceFallbackImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 图书微服务的接口
 * @author rkc
 * @date 2020/9/19 15:15
 * @version 2.0
 */
@FeignClient(value = "book-server", fallback = BookServiceFallbackImpl.class)
public interface BookService {

    /**
     * 根据id列表查询Book集合
     * @param ids id列表
     * @return Book集合
     */
    @PostMapping(value = "/api/v1/book/pri/selectBookByIds")
    CommonplaceResult selectBookByIds(@RequestBody List<Integer> ids);

    /**
     * 根据图书id得到图书信息和库存信息
     * @param bookId 图书id
     */
    @GetMapping(value = "/api/v1/book/pub/selectBookAndStorageByBookId/{bookId}")
    CommonplaceResult selectBookAndStorageByBookId(@PathVariable("bookId") Integer bookId);

    /**
     * 根据id减少库存
     * @param pojo 包含id和count键值
     */
    @PostMapping("/api/v1/book/pri/subtractStorageById")
    CommonplaceResult subtractStorageById(@RequestBody Map<String, Integer> pojo);
}
