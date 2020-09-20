package com.onlinebookstore.service;

import com.onlinebookstore.common.CommonplaceResult;
import com.onlinebookstore.entity.otherserver.BookStorage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * 图书微服务的接口
 * @author rkc
 * @date 2020/9/19 15:15
 * @version 1.0
 */
@FeignClient("book-server")
public interface BookService {

    @GetMapping(value = "/api/v1/book/pub/selectAllInfo")
    CommonplaceResult selectAllInfo();

    @GetMapping(value = "/api/v1/book/pub/selectAllBookAndStorage")
    CommonplaceResult selectAllBookAndStorage();

    @GetMapping(value = "/api/v1/book/pub/selectBookAndResource")
    CommonplaceResult selectBookAndResource();

    @GetMapping(value = "/api/v1/book/pub/selectBookAndStorageByBookId/{bookId}")
    CommonplaceResult selectBookAndStorageByBookId(@PathVariable("bookId") Integer bookId);

    @GetMapping(value = "/api/v1/book/pub/selectBookAndResourceByBookId/{bookId}")
    CommonplaceResult selectBookAndResourceByBookId(@PathVariable("bookId") Integer bookId);

    @GetMapping(value = "/api/v1/book/pub/selectBookContainAllInfoById/{bookId}")
    CommonplaceResult selectBookContainAllInfoById(@PathVariable("bookId") Integer bookId);

    @GetMapping(value = "/api/v1/book/pub/selectStorageByBookId/{bookId}")
    CommonplaceResult selectStorageByBookId(@PathVariable("bookId") Integer bookId);

    @GetMapping(value = "/api/v1/book/pub/selectStorageById/{id}")
    CommonplaceResult selectStorageById(@PathVariable("id") Integer id);

    @PostMapping(value = "/api/v1/book/pub/addStorageById")
    CommonplaceResult addStorageById(@RequestParam("id") Integer id, @RequestParam("count") Integer count);

    @PostMapping(value = "/api/v1/book/pub/subtractStorageById")
    CommonplaceResult subtractStorageById(@RequestParam("id") Integer id, @RequestParam("count") Integer count);

    @PostMapping(value = "/api/v1/book/pub/updateStorage")
    CommonplaceResult updateStorage(@RequestBody BookStorage bookStorage);

    @GetMapping(value = "/api/v1/book/pub/selectAllBookAloneById")
    CommonplaceResult selectAllBookAloneById(@RequestParam("bookId") Integer bookId);
}
