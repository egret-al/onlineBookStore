package com.onlinebookstore.service;

import com.onlinebookstore.common.CommonplaceResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 图书微服务的接口
 * @author rkc
 * @date 2020/9/19 15:15
 * @version 1.0
 */
@FeignClient("book-server")
public interface BookService {

    @GetMapping("/api/v1/book/pub/selectAllInfo")
    CommonplaceResult selectAllInfo();
}
