package com.onlinebookstore.service;

import com.onlinebookstore.common.CommonplaceResult;
import com.onlinebookstore.entity.bookserver.BookStorage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 图书微服务的接口
 * @author rkc
 * @date 2020/9/19 15:15
 * @version 1.0
 */
@FeignClient("book-server")
public interface BookService {

    /**
     * 根据id列表查询Book集合
     * @param ids id列表
     * @return Book集合
     */
    @PostMapping(value = "/api/v1/book/pri/selectBookByIds")
    CommonplaceResult selectBookByIds(@RequestBody List<Integer> ids);

    /**
     * 得到所有图书的所有信息，包括库存和资源信息
     */
    @GetMapping(value = "/api/v1/book/pub/selectAllInfo")
    CommonplaceResult selectAllInfo();

    /**
     * 得到所有图书的信息，包括图书信息和库存信息
     */
    @GetMapping(value = "/api/v1/book/pub/selectAllBookAndStorage")
    CommonplaceResult selectAllBookAndStorage();

    /**
     * 得到所有的图书信息，包括图书信息和资源信息
     */
    @GetMapping(value = "/api/v1/book/pub/selectBookAndResource")
    CommonplaceResult selectBookAndResource();

    /**
     * 根据图书id得到图书信息和库存信息
     * @param bookId 图书id
     */
    @GetMapping(value = "/api/v1/book/pub/selectBookAndStorageByBookId/{bookId}")
    CommonplaceResult selectBookAndStorageByBookId(@PathVariable("bookId") Integer bookId);

    /**
     * 根据图书id得到图书信息和资源信息
     * @param bookId 图书id
     */
    @GetMapping(value = "/api/v1/book/pub/selectBookAndResourceByBookId/{bookId}")
    CommonplaceResult selectBookAndResourceByBookId(@PathVariable("bookId") Integer bookId);

    /**
     * 根据图书id查询信息
     * @param bookId 图书id
     * @return 只包含图书的基本信息
     */
    @GetMapping(value = "/api/v1/book/pub/selectBookContainAllInfoById/{bookId}")
    CommonplaceResult selectBookContainAllInfoById(@PathVariable("bookId") Integer bookId);

    /**
     * 根据图书id查询图书信息+资源信息+库存信息
     * @param bookId 图书id
     */
    @GetMapping(value = "/api/v1/book/pub/selectStorageByBookId/{bookId}")
    CommonplaceResult selectStorageByBookId(@PathVariable("bookId") Integer bookId);

    /**
     * 根据id查询库存
     * @param id id
     */
    @GetMapping("/api/v1/book/pub/selectStorageById/{id}")
    CommonplaceResult selectStorageById(@PathVariable("id") Integer id);

    /**
     * 根据id增加库存
     * {
     *     'id': 'xx',
     *     'count': 'xxx'
     * }
     * @param pojo 包含id和count键值
     */
    @PostMapping("/api/v1/book/pri/addStorageById")
    CommonplaceResult addStorageById(@RequestBody Map<String, Integer> pojo);

    /**
     * 根据id减少库存
     * 数据格式：
     * {
     *     'id': 'xx',
     *     'count': 'xxx'
     * }
     * @param pojo 包含id和count键值
     */
    @PostMapping("/api/v1/book/pri/subtractStorageById")
    CommonplaceResult subtractStorageById(@RequestBody Map<String, Integer> pojo);

    /**
     * 更新库存信息
     * @param bookStorage 新的库存信息
     */
    @PostMapping(value = "/api/v1/book/pri/updateStorage")
    CommonplaceResult updateStorage(@RequestBody BookStorage bookStorage);
}
