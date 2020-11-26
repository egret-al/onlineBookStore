package com.onlinebookstore.controller;

import com.onlinebookstore.annotation.JsonObject;
import com.onlinebookstore.common.CommonplaceResult;
import com.onlinebookstore.entity.bookserver.*;
import com.onlinebookstore.service.*;
import com.onlinebookstore.service.impl.BookServiceImpl;
import com.onlinebookstore.util.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
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

    @Resource
    private BookBannerService bookBannerService;

    @Resource
    private BookResourceService bookResourceService;

    @Resource
    private BookTypeService bookTypeService;

    /**
     * 根据id列表查询Book集合
     * @param ids id列表
     * @return Book集合
     */
    @PostMapping("pri/selectBookByIds")
    public CommonplaceResult selectBookByIds(@RequestBody List<Integer> ids) {
        return bookService.selectBookByIds(ids);
    }

    /**
     * 添加图书和资源（图片）
     * @param book book
     * @return CommonplaceResult
     */
    @PostMapping("pri/addBookAndResource")
    public CommonplaceResult addCompleteBook(@RequestBody Book book) {
        if (ObjectUtils.isEmpty(book) || ObjectUtils.isEmpty(book.getBookResources()) || ObjectUtils.isEmpty(book.getBookStorage())) {
            return CommonplaceResult.buildErrorNoData("数据不全！添加失败");
        }
        log.info(book.toString());
        return bookService.addCompleteBook(book);
    }

    /**
     * 查询所有类型和对应的图书
     * @return CommonplaceResult
     */
    @GetMapping("pub/selectAllTypeWithBook")
    public CommonplaceResult selectAllTypeWithBook() {
        return bookTypeService.selectAllWithBook();
    }

    /**
     * 新加图书类型
     * @param bookType 图书类型
     * @return 影响行数
     */
    @PostMapping("pri/insertType")
    public CommonplaceResult insertType(@RequestBody BookType bookType) {
        return bookTypeService.insertType(bookType);
    }

    /**
     * 更新类型
     * 数据格式：
     * {
     *     'type': 'xxx',
     *     'supplement': 'xxx',
     *     'img': 'xxx',
     *     'id': 'xx'
     * }
     * @param bookType 类型
     * @return 影响行数
     */
    @PostMapping("pri/updateType")
    public CommonplaceResult updateType(@RequestBody BookType bookType) {
        return bookTypeService.updateType(bookType);
    }

    /**
     * 根据类型id查询类型
     * @param id id
     * @return 类型信息
     */
    @GetMapping("pub/selectTypeById/{id}")
    public CommonplaceResult selectTypeById(@PathVariable("id") Integer id) {
        return bookTypeService.selectTypeById(id);
    }

    /**
     * 查询所有图书类型
     * @return 图书类型
     */
    @GetMapping("pub/selectAllType")
    public CommonplaceResult selectAllType() {
        return bookTypeService.selectAllType();
    }

    /**
     * 查询所有资源信息
     * @return 资源信息集合
     */
    @GetMapping("pub/selectAllResourceAlone")
    public CommonplaceResult selectAllResourceAlone() {
        return bookResourceService.selectAllResourceAlone();
    }

    /**
     * 根据图书的id查询其所有的资源对象
     * @param bookId 图书id
     * @return 指定图书下的所有资源
     */
    @GetMapping("pub/selectAllResourceAloneByBookId/{bookId}")
    public CommonplaceResult selectAllResourceAloneByBookId(@PathVariable("bookId") Integer bookId) {
        return bookResourceService.selectAllResourceAloneByBookId(bookId);
    }

    /**
     * 根据资源id查询资源对象
     * @param resourceId 资源id
     * @return 资源对象
     */
    @GetMapping("pub/selectResourceById/{id}")
    public CommonplaceResult selectResourceById(@PathVariable("id") Integer resourceId) {
        return bookResourceService.selectResourceById(resourceId);
    }

    /**
     * 更新资源信息，通常由管理员操作
     * 数据格式：
     * {
     *     'id': 'xx',
     *     'resourceUrl': 'xxx',
     *     'symbol': 'xxx',
     *     'supplement': 'xxx'
     * }
     * @param bookResource 资源对象
     * @return 影响行数
     */
    @PostMapping("pri/updateResource")
    public CommonplaceResult updateResource(@RequestBody BookResource bookResource) {
        return bookResourceService.updateResource(bookResource);
    }

    /**
     * 根据id删除资源
     * @param resourceId 资源id
     * @return 影响行数
     */
    @GetMapping("pri/deleteResourceById/{id}")
    public CommonplaceResult deleteResourceById(@PathVariable("id") Integer resourceId) {
        return bookResourceService.deleteResourceById(resourceId);
    }

    /**
     * 获取所有Banner
     */
    @GetMapping("pub/selectAllBanner")
    public CommonplaceResult selectAllBanner() {
        return bookBannerService.selectAll();
    }

    /**
     * 查询最新的count条记录
     * @param count 数量
     */
    @GetMapping("pub/selectCountBanner/{count}")
    public CommonplaceResult selectCountBanner(@PathVariable("count") Integer count) {
        return bookBannerService.selectCount(count);
    }

    /**
     * 修改BookBanner
     * @param bookBanner 实体类
     */
    @PostMapping("pri/updateBookBanner")
    public CommonplaceResult updateBookBanner(@RequestBody BookBanner bookBanner) {
        return bookBannerService.updateBookBanner(bookBanner);
    }

    /**
     * 添加Banner
     * @param bookBanner banner数据对象
     */
    @PostMapping("pri/insertBookBanner")
    public CommonplaceResult insertBookBanner(@RequestBody BookBanner bookBanner) {
        return bookBannerService.insertBookBanner(bookBanner);
    }

    /**
     * 根据id删除banner
     * 数据格式：
     * {
     *     'id': 'xxx'
     * }
     * @param pojo 存放id的map
     */
    @PostMapping("pri/deleteBookBannerById")
    public CommonplaceResult deleteBookBannerById(@RequestBody Map<String, Integer> pojo) {
        Integer id = pojo.get("id");
        if (ObjectUtils.isEmpty(id)) {
            return CommonplaceResult.buildErrorNoData("数据错误！");
        }
        return bookBannerService.deleteBookBannerById(id);
    }

    /**
     * 得到所有图书的所有信息，包括库存和资源信息
     */
    @GetMapping("pub/selectAllInfo")
    public CommonplaceResult selectAllInfo() {
        return bookService.selectAllBookInfo();
    }

    /**
     * 模糊查询：得到所有图书的所有信息，包括库存和资源信息
     */
    @PostMapping("pub/selectAllInfoLike")
    public CommonplaceResult selectAllInfoLike(@RequestBody Book book) {
        if (book.getBookName().length() == 0) {
            return bookService.selectAllBookInfo();
        }
        return bookService.selectAllBookInfoLike(book.getBookName());
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
     * 模糊查询：得到所有的图书信息，包括图书信息和资源信息
     */
    @PostMapping("pub/selectBookAndResourceLike")
    public CommonplaceResult selectBookAndResourceLike(@RequestBody Book book) {
        if (book.getBookName().length() == 0) {
            return bookService.selectAllBookWithResource();
        }
        return bookService.selectAllBookWithResourceLike(book.getBookName());
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
