package com.onlinebookstore.controller;

import com.onlinebookstore.common.CommonplaceResult;
import com.onlinebookstore.entity.BookStorage;
import com.onlinebookstore.service.BookService;
import com.onlinebookstore.service.BookStorageService;
import com.onlinebookstore.service.impl.BookServiceImpl;
import com.onlinebookstore.util.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 图书微服务的接口，主要给用户微服务和前端直接申请资源
 * 当其他微服务调用该接口的时候，调用方和被调用方的参数都需要保持一致，
 * 两方的请求必须保持一致
 * 例：1、被调用方是 @RequestMapping(value = "xxx", method = RequestMethod.POST)
 *     则调用方的方法也得 @RequestMapping(value = "xxx", method = RequestMethod.POST)保持一致
 *     2、被调用方是：@RequestMapping(value = "pub/selectBookAndStorageByBookId/{bookId}", method = RequestMethod.POST)
 *                   public CommonplaceResult selectBookAndStorageByBookId(@PathVariable("bookId") Integer bookId) { }
 *        调用方也需要保持一致：@RequestMapping(value = "/api/v1/book/pub/selectBookAndStorageByBookId/{bookId}")
 *                            CommonplaceResult selectBookAndStorageByBookId(@PathVariable("bookId") Integer bookId);
 *        微服务调用直接传参：bookService.selectBookAndStorageByBookId(bookId)
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

    @Resource
    private BookStorageService bookStorageService;

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
    @GetMapping(value = "pub/selectBookAndStorageByBookId/{bookId}")
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
     * 根据图书id查询信息
     * @param bookId 图书id
     * @return 只包含图书的基本信息
     */
    @GetMapping(value = "pub/selectAllBookAloneById/{bookId}")
    public CommonplaceResult selectAllBookAloneById(@PathVariable("bookId") Integer bookId) {
        return bookService.selectAllBookAloneById(bookId);
    }

    /**
     * 根据图书id查询图书信息+资源信息+库存信息
     * @param bookId 图书id
     */
    @GetMapping("pub/selectBookContainAllInfoById/{bookId}")
    public CommonplaceResult selectBookContainAllInfoById(@PathVariable("bookId") Integer bookId) {
        return bookService.selectAllBookInfoByBookId(bookId);
    }

    /**
     * 根据图书id得到库存信息
     * @param bookId 图书id
     */
    @GetMapping("pub/selectStorageByBookId/{bookId}")
    public CommonplaceResult selectStorageByBookId(@PathVariable("bookId") Integer bookId) {
        return bookStorageService.selectStorageByBookId(bookId);
    }

    /**
     * 根据id查询库存
     * @param id id
     */
    @GetMapping("pub/selectStorageById/{id}")
    public CommonplaceResult selectStorageById(@PathVariable("id") Integer id) {
        return bookStorageService.selectStorageById(id);
    }

    /**
     * 根据id增加库存
     * 数据格式：
     * {
     *     'id': 'xx',
     *     'count': 'xxx'
     * }
     * @param pojo 包含id和count键值
     */
    @PostMapping("pri/addStorageById")
    public CommonplaceResult addStorageById(@RequestBody Map<String, Integer> pojo) {
        Integer id = pojo.get("id");
        Integer count = pojo.get("count");
        return bookStorageService.addStorageById(id, count);
    }

    /**
     * 根据id减少库存
     * 数据格式：
     * {
     *     'id': 'xx',
     *     'count': 'xxx'
     * }
     * @param pojo 包含id和count键值
     */
    @PostMapping("pri/subtractStorageById")
    public CommonplaceResult subtractStorageById(@RequestBody Map<String, Integer> pojo) {
        Integer id = pojo.get("id");
        Integer count = pojo.get("count");
        return bookStorageService.subtractStorageById(id, count);
    }

    /**
     * 更新库存信息
     * @param bookStorage 新的库存信息
     */
    @PostMapping(value = "pri/updateStorage")
    public CommonplaceResult updateStorage(@RequestBody BookStorage bookStorage) {
        return bookStorageService.updateStorage(bookStorage);
    }

}
