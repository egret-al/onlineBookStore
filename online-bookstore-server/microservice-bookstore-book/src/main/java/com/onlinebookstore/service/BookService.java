package com.onlinebookstore.service;

import com.onlinebookstore.common.CommonplaceResult;
import com.onlinebookstore.entity.bookserver.Book;

import java.util.List;

/**
 * @author rkc
 * @date 2020/9/18 16:28
 * @version 1.0
 */
public interface BookService {

    /**
     * 查询全部图书+库存+类型
     * @return CommonplaceResult
     */
    CommonplaceResult selectBookAndType();

    /**
     * 根据类型查询图书
     * @param typeId 类型id
     * @return CommonplaceResult
     */
    CommonplaceResult selectAllBookWithResourceByType(int typeId);

    /**
     * 根据类型查询（分页）
     * @param typeId 类型id
     * @return CommonplaceResult
     */
    CommonplaceResult selectAllBookInfoByTypePage(int typeId, int currentPage, int pageSize);

    /**
     * 根据类型查询
     * @param typeId 类型id
     * @return CommonplaceResult
     */
    CommonplaceResult selectAllBookInfoByType(int typeId);

    /**
     * 模糊查询
     * @param str 查询字符串
     * @return 图书信息+库存信息+资源信息
     */
    CommonplaceResult selectAllBookInfoLike(String str);

    /**
     * 模糊查询
     * @param str 查询字符串
     * @return 图书信息+资源信息
     */
    CommonplaceResult selectAllBookWithResourceLike(String str);

    /**
     * 根据id列表查询Book集合
     * @param ids id列表
     * @return Book集合
     */
    CommonplaceResult selectBookByIds(List<Integer> ids);

    /**
     * 添加图书
     * @param book book
     * @return CommonplaceResult
     */
    CommonplaceResult addCompleteBook(Book book);

    /**
     * 查询所有图书信息
     * @return 图书信息
     */
    CommonplaceResult selectAllBookAlone();

    CommonplaceResult selectAllBookInfoPage(int currentPage, int pageSize);

    /**
     * 查询所有图书信息
     * @return 图书信息+库存信息+资源信息
     */
    CommonplaceResult selectAllBookInfo();

    /**
     * 查询所有图书信息
     * @return 图书信息+资源信息
     */
    CommonplaceResult selectAllBookWithResource();

    /**
     * 查询所有图书信息
     * @return 图书信息+库存信息
     */
    CommonplaceResult selectAllBookWithStorage();

    /**
     * 查询所有图书信息
     * @param bookId id
     * @return 图书信息
     */
    CommonplaceResult selectAllBookAloneById(Integer bookId);

    /**
     * 根据图书id查询图书的所有信息
     * @param bookId id
     * @return 图书信息+库存信息+资源信息
     */
    CommonplaceResult selectAllBookInfoByBookId(Integer bookId);

    /**
     * 查询图书信息+资源信息
     * @param bookId 图书id
     * @return 图书信息+资源信息
     */
    CommonplaceResult selectAllBookWithResourceByBookId(Integer bookId);

    /**
     * 查询图书信息+库存信息
     * @param bookId 图书id
     * @return 图书信息+库存信息
     */
    CommonplaceResult selectAllBookWithStorageByBookId(Integer bookId);

    /**
     * 更新图书信息
     * @param book book
     * @return 影响行数
     */
    CommonplaceResult updateBook(Book book);

    /**
     * 根据id删除图书（会级联删除到库存和资源，谨慎调用）
     * @param bookId 图书id
     * @return 影响行数
     */
    CommonplaceResult deleteBookById(Integer bookId);
}
