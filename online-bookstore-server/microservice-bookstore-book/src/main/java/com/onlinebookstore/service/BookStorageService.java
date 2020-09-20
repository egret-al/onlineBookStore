package com.onlinebookstore.service;

import com.onlinebookstore.common.CommonplaceResult;
import com.onlinebookstore.entity.BookStorage;

/**
 * @author rkc
 * @date 2020/9/20 8:00
 * @version 1.0
 */
public interface BookStorageService {

    /**
     * 根据bookId得到库存信息
     * @param bookId 图书id
     */
    CommonplaceResult selectStorageByBookId(Integer bookId);

    /**
     * 根据id得到库存信息
     * @param id 库存id
     */
    CommonplaceResult selectStorageById(Integer id);

    /**
     * 根据库存id增加库存数量
     * @param id 库存id
     * @param count 增加的数量
     */
    CommonplaceResult addStorageById(Integer id, Integer count);

    /**
     * 根据库存id减少库存数量
     * @param id 库存id
     * @param count 扣除的数量
     */
    CommonplaceResult subtractStorageById(Integer id, Integer count);

    /**
     * 新增库存记录
     * @param bookStorage 库存对象
     */
    CommonplaceResult insertStorage(BookStorage bookStorage);

    /**
     * 更新库存信息
     * @param bookStorage 新的库存信息
     */
    CommonplaceResult updateStorage(BookStorage bookStorage);

    /**
     * 根据库存id删除库存信息
     * @param id 库存id
     */
    CommonplaceResult deleteStorageById(Integer id);
}
