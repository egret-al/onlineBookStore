package com.onlinebookstore.service;

import com.onlinebookstore.common.CommonplaceResult;
import com.onlinebookstore.entity.bookserver.BookType;

/**
 * @author rkc
 * @date 2020/9/30 16:16
 * @version 1.0
 */
public interface BookTypeService {

    /**
     * 查询所有类型和对应的图书
     * @return CommonplaceResult
     */
    CommonplaceResult selectAllWithBook();

    /**
     * 查询所有类型
     * @return 图书类型集合
     */
    CommonplaceResult selectAllType();

    /**
     * 根据id查询类型
     * @param id id
     * @return 类型
     */
    CommonplaceResult selectTypeById(Integer id);

    /**
     * 根据类型
     * @param bookType 实体类
     * @return 影响行数
     */
    CommonplaceResult updateType(BookType bookType);

    /**
     * 添加类型
     * @param bookType 类型实体类
     * @return 影响行数
     */
    CommonplaceResult insertType(BookType bookType);
}
