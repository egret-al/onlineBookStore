package com.onlinebookstore.service;

import com.onlinebookstore.common.CommonplaceResult;
import com.onlinebookstore.entity.bookserver.BookStorage;

/**
 * @author rkc
 * @date 2020/9/20 8:00
 * @version 1.0
 */
public interface BookStorageService {

    /**
     * 根据bookId得到库存信息
     * @param bookId 图书id
     * @return CommonplaceResult
     */
    CommonplaceResult selectStorageByBookId(Integer bookId);

    /**
     * 根据id得到库存信息
     * @param id 库存id
     * @return CommonplaceResult
     */
    CommonplaceResult selectStorageById(Integer id);

    /**
     * 根据库存id增加库存数量
     * @param id 库存id
     * @param count 增加的数量
     * @return CommonplaceResult
     */
    CommonplaceResult addStorageById(Integer id, Integer count);

    /**
     * 根据库存id减少库存数量
     * @param id 库存id
     * @param count 扣除的数量
     * @return CommonplaceResult
     */
    CommonplaceResult subtractStorageById(Integer id, Integer count);

    /**
     * 新增库存记录
     * @param bookStorage 库存对象
     * @return CommonplaceResult
     */
    CommonplaceResult insertStorage(BookStorage bookStorage);

    /**
     * 更新库存信息
     * @param bookStorage 新的库存信息
     * @return CommonplaceResult
     */
    CommonplaceResult updateStorage(BookStorage bookStorage);

    /**
     * 根据库存id删除库存信息
     * @param id 库存id
     * @return CommonplaceResult
     */
    CommonplaceResult deleteStorageById(Integer id);
}
