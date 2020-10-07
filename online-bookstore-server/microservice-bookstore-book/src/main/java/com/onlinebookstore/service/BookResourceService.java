package com.onlinebookstore.service;

import com.onlinebookstore.common.CommonplaceResult;
import com.onlinebookstore.entity.bookserver.BookResource;

import java.util.List;

/**
 * @author rkc
 * @date 2020/9/20 8:00
 * @version 1.0
 */
public interface BookResourceService {

    /**
     * 查询所有资源信息
     * @return 资源信息集合
     */
    CommonplaceResult selectAllResourceAlone();

    /**
     * 根据图书的id查询其所有的资源对象
     * @param bookId 图书id
     * @return 指定图书下的所有资源
     */
    CommonplaceResult selectAllResourceAloneByBookId(Integer bookId);

    /**
     * 根据资源id查询资源对象
     * @param resourceId 资源id
     * @return 资源对象
     */
    CommonplaceResult selectResourceById(Integer resourceId);

    /**
     * 更新资源信息
     * @param bookResource 资源对象
     * @return 影响行数
     */
    CommonplaceResult updateResource(BookResource bookResource);

    /**
     * 根据id删除资源
     * @param resourceId 资源id
     * @return 影响行数
     */
    CommonplaceResult deleteResourceById(Integer resourceId);
}
