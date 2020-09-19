package com.onlinebookstore.controller;

import com.onlinebookstore.common.CommonplaceResult;
import com.onlinebookstore.service.BookService;
import com.onlinebookstore.util.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 图书微服务的接口
 * @author rkc
 * @date 2020/9/18 16:30
 * @version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/book")
public class BookController {

    @Resource
    private BookService bookService;

    /**
     * 得到所有图书的所有信息，包括库存和资源信息
     */
    @GetMapping("pub/selectAllInfo")
    public CommonplaceResult selectAllInfo() {
        return bookService.selectAllBookInfo();
    }

    /**
     * 得到所有图书的信息，包括图书信息和库存信息
     */
    @GetMapping("pub/selectAllBookAndStorage")
    public CommonplaceResult selectAllBookAndStorage() {
        return bookService.selectAllBookWithStorage();
    }

    /**
     * 得到所有的图书信息，包括图书信息和资源信息
     */
    @GetMapping("pub/selectBookAndResource")
    public CommonplaceResult selectBookAndResource() {
        return bookService.selectAllBookWithResource();
    }

    /**
     * 根据图书id得到图书信息和库存信息
     * @param bookId 图书id
     */
    @GetMapping("pub/selectBookAndStorageByBookId/{bookId}")
    public CommonplaceResult selectBookAndStorageByBookId(@PathVariable("bookId") Integer bookId) {
        return bookService.selectAllBookWithStorageByBookId(bookId);
    }

    /**
     * 根据图书id得到图书信息和资源信息
     * @param bookId 图书id
     */
    @GetMapping("pub/selectBookAndResourceByBookId/{bookId}")
    public CommonplaceResult selectBookAndResourceByBookId(@PathVariable("bookId") Integer bookId) {
        return bookService.selectAllBookWithResourceByBookId(bookId);
    }

    /**
     * 根据图书id查询图书信息+资源信息+库存信息
     * @param bookId 图书id
     */
    @GetMapping("pub/selectBookContainAllInfoById/{bookId}")
    public CommonplaceResult selectBookContainAllInfoById(@PathVariable("bookId") Integer bookId) {
        return bookService.selectAllBookInfoByBookId(bookId);
    }
}
