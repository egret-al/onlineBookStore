package com.onlinebookstore.service.impl;

import com.onlinebookstore.common.CommonplaceResult;
import com.onlinebookstore.entity.bookserver.Book;
import com.onlinebookstore.entity.bookserver.BookResource;
import com.onlinebookstore.mapper.BookMapper;
import com.onlinebookstore.mapper.BookResourceMapper;
import com.onlinebookstore.mapper.BookStorageMapper;
import com.onlinebookstore.service.BookService;
import com.onlinebookstore.util.RandomUtils;
import com.onlinebookstore.util.RedisUtils;
import com.onlinebookstore.util.bookutil.BookConstantPool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author rkc
 * @date 2020/9/18 16:29
 * @version 1.0
 */
@Slf4j
@Service
public class BookServiceImpl implements BookService {

    @Resource
    private BookMapper bookMapper;

    @Resource
    private BookResourceMapper bookResourceMapper;

    @Resource
    private BookStorageMapper bookStorageMapper;

    @Resource
    private RandomUtils randomUtils;

    @Resource
    private RedisUtils redisUtils;

    /**
     * 添加图书、资源和库存
     * @param book book
     * @return CommonplaceResult
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public CommonplaceResult addCompleteBook(Book book) {
        book.setCreateTime(new Date());
        try {
            log.info(String.valueOf(book));
            //添加图书
            bookMapper.addBook(book);
            for (BookResource bookResource : book.getBookResources()) {
                bookResource.setBookId(book.getId());
                bookResource.setCreateTime(new Date());
            }
            //添加资源
            bookResourceMapper.addBookResources(book.getBookResources());
            //添加库存
            book.getBookStorage().setLastAddTime(new Date());
            bookStorageMapper.addBookStorage(book.getBookStorage());
            return CommonplaceResult.buildSuccessNoData("添加成功！");
        } catch (Exception e) {
            log.error(e.getMessage());
            //抛出异常，让切面能够感知，进行事务回滚
            throw e;
        }
    }

    /**
     * 查询所有图书信息（缓存时间：60s）
     * @return 图书信息
     */
    @Override
    public CommonplaceResult selectAllBookAlone() {
        Object o = redisUtils.get(BookConstantPool.SELECT_ALL_BOOK_ALONE);
        if (!ObjectUtils.isEmpty(o)) {
            log.info("通过缓存读取的数据");
            return CommonplaceResult.buildSuccessNoMessage(o);
        }
        //缓存中不存在数据，从数据库中读取并且加入缓存
        List<Book> books = bookMapper.selectAllBookAlone();
        if (books.size() == 0) {
            return CommonplaceResult.buildErrorNoData("数据异常！");
        }
        log.info("通过数据库读取的数据");
        //加入redis缓存
        redisUtils.set(BookConstantPool.SELECT_ALL_BOOK_ALONE, books, BookConstantPool.CACHE_TIME[1]);
        return CommonplaceResult.buildSuccessNoMessage(books);
    }

    /**
     * 查询所有图书信息（缓存时间：[60 + random.nextInt(100)] s）
     * @return 图书信息+库存信息+资源信息
     */
    @Override
    public CommonplaceResult selectAllBookInfo() {
        Object o = redisUtils.get(BookConstantPool.SELECT_ALL_BOOK_INFO);
        if (!ObjectUtils.isEmpty(o)) {
            //不为空说明缓存有数据，直接把redis中取出的数据返回
            log.info("通过缓存读取的数据");
            return CommonplaceResult.buildSuccessNoMessage(o);
        }
        List<Book> books = bookMapper.selectAllBookInfo();
        if (ObjectUtils.isEmpty(books) || books.size() == 0) {
            return CommonplaceResult.buildErrorNoData("数据异常！");
        }
        log.info("通过数据库读取的数据");
        //加入redis缓存，时间1分钟+随机时间（秒）
        long cacheTime = BookConstantPool.CACHE_TIME[1];
        redisUtils.set(BookConstantPool.SELECT_ALL_BOOK_INFO, books, cacheTime + randomUtils.getInt(100));
        return CommonplaceResult.buildSuccessNoMessage(books);
    }

    /**
     * 查询所有图书信息（缓存时间：60s）
     * @return 图书信息+资源信息
     */
    @Override
    public CommonplaceResult selectAllBookWithResource() {
        Object o = redisUtils.get(BookConstantPool.SELECT_ALL_BOOK_WITH_RESOURCE);
        if (!ObjectUtils.isEmpty(o)) {
            return CommonplaceResult.buildSuccessNoMessage(o);
        }
        //查询数据库加入缓存
        List<Book> books = bookMapper.selectAllBookWithResource();
        if (!ObjectUtils.isEmpty(books) && books.size() > 0) {
            redisUtils.set(BookConstantPool.SELECT_ALL_BOOK_WITH_RESOURCE, books, BookConstantPool.CACHE_TIME[1]);
            return CommonplaceResult.buildSuccessNoMessage(books);
        }
        return CommonplaceResult.buildErrorNoData("数据异常！");
    }

    /**
     * 查询所有图书信息
     * @return 图书信息+库存信息
     */
    @Override
    public CommonplaceResult selectAllBookWithStorage() {
        List<Book> books = bookMapper.selectAllBookWithStorage();
        return books.size() == 0 ? CommonplaceResult.buildErrorNoData("数据异常！") : CommonplaceResult.buildSuccessNoMessage(books);
    }

    /**
     * 查询图书所有信息（缓存时间：60s）
     * @param bookId id
     * @return 图书信息
     */
    @Override
    public CommonplaceResult selectAllBookAloneById(Integer bookId) {
        //查缓存
        Object o = redisUtils.get(BookConstantPool.SELECT_ALL_BOOK_ALONE_BY_ID + bookId);
        //缓存不存在数据就进行查询数据库并且加入到缓存中
        if (ObjectUtils.isEmpty(o)) {
            Book book = bookMapper.selectAllBookAloneById(bookId);
            //数据不存在
            if (ObjectUtils.isEmpty(book)) {
                return CommonplaceResult.buildErrorNoData("没有该数据！");
            }
            //加入缓存
            redisUtils.set(BookConstantPool.SELECT_ALL_BOOK_ALONE_BY_ID + bookId, book, BookConstantPool.CACHE_TIME[1]);
            return CommonplaceResult.buildSuccessNoMessage(book);
        }
        //缓存有数据，直接返回
        return CommonplaceResult.buildSuccessNoMessage(o);
    }

    /**
     * 根据图书id查询图书的所有信息（缓存时间：60s）
     * @param bookId id
     * @return 图书信息+库存信息+资源信息
     */
    @Override
    public CommonplaceResult selectAllBookInfoByBookId(Integer bookId) {
        Object o = redisUtils.get(BookConstantPool.SELECT_ALL_BOOK_INFO_BY_BOOK_ID + bookId);
        if (ObjectUtils.isEmpty(o)) {
            Book book = bookMapper.selectAllBookInfoByBookId(bookId);
            if (!ObjectUtils.isEmpty(book)) {
                redisUtils.set(BookConstantPool.SELECT_ALL_BOOK_INFO_BY_BOOK_ID + bookId, o, BookConstantPool.CACHE_TIME[1]);
                return CommonplaceResult.buildSuccessNoMessage(book);
            }
        }
        return CommonplaceResult.buildErrorNoData("没有该数据！");
    }

    /**
     * 查询图书信息+资源信息（缓存时间：60s）
     * @param bookId 图书id
     * @return 图书信息+资源信息
     */
    @Override
    public CommonplaceResult selectAllBookWithResourceByBookId(Integer bookId) {
        Object o = redisUtils.get(BookConstantPool.SELECT_ALL_BOOK_WITH_RESOURCE_BY_BOOK_ID + bookId);
        if (!ObjectUtils.isEmpty(o)) {
            return CommonplaceResult.buildSuccessNoMessage(o);
        }
        Book book = bookMapper.selectAllBookWithResourceByBookId(bookId);
        if (ObjectUtils.isEmpty(book)) {
            return CommonplaceResult.buildErrorNoData("没有该数据");
        }
        redisUtils.set(BookConstantPool.SELECT_ALL_BOOK_WITH_RESOURCE_BY_BOOK_ID + bookId, book, BookConstantPool.CACHE_TIME[1]);
        return CommonplaceResult.buildSuccessNoMessage(book);
    }

    /**
     * 查询图书信息+库存信息，缓存时间：1h
     * @param bookId 图书id
     * @return 图书信息+库存信息
     */
    @Override
    public CommonplaceResult selectAllBookWithStorageByBookId(Integer bookId) {
        Object o = redisUtils.get(BookConstantPool.SELECT_ALL_BOOK_WITH_STORAGE_BY_BOOK_ID + bookId);
        if (ObjectUtils.isEmpty(o)) {
            Book book = bookMapper.selectAllBookWithStorageByBookId(bookId);
            if (ObjectUtils.isEmpty(book)) {
                return CommonplaceResult.buildErrorNoData("查询失败，没有该数据！");
            }
            redisUtils.set(BookConstantPool.SELECT_ALL_BOOK_WITH_STORAGE_BY_BOOK_ID + bookId, book, BookConstantPool.CACHE_TIME[5]);
            return CommonplaceResult.buildSuccessNoMessage(book);
        }
        return CommonplaceResult.buildSuccessNoMessage(o);
    }

    /**
     * 更新图书信息
     * @param book book
     * @return 影响行数
     */
    @Override
    public CommonplaceResult updateBook(Book book) {
        int row = bookMapper.updateBook(book);
        return row == 0 ? CommonplaceResult.buildErrorNoData("更新失败！") : CommonplaceResult.buildSuccessNoData("更新成功！");
    }

    /**
     * 根据id删除图书（会级联删除到库存和资源，谨慎调用）
     * @param bookId 图书id
     * @return 影响行数
     */
    @Override
    public CommonplaceResult deleteBookById(Integer bookId) {
        int row = bookMapper.deleteBookById(bookId);
        return row == 0 ? CommonplaceResult.buildErrorNoData("删除失败！") : CommonplaceResult.buildSuccessNoData("删除成功！");
    }
}
