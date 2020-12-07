package com.onlinebookstore.service.impl;

import com.onlinebookstore.common.CommonplaceResult;
import com.onlinebookstore.entity.bookserver.BookResource;
import com.onlinebookstore.mapper.BookResourceMapper;
import com.onlinebookstore.service.BookResourceService;
import com.onlinebookstore.util.RedisUtils;
import com.onlinebookstore.util.bookutil.BookConstantPool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author rkc
 * @date 2020/9/20 10:19
 * @version 1.0
 */
@Slf4j
@Service
public class BookResourceServiceImpl implements BookResourceService {

    @Resource
    private BookResourceMapper bookResourceMapper;

    @Resource
    private RedisUtils redisUtils;


    /**
     * 查询所有资源信息，缓存时间：30m
     * @return 资源信息集合
     */
    @Override
    public CommonplaceResult selectAllResourceAlone() {
        Object o = redisUtils.get(BookConstantPool.SELECT_ALL_RESOURCE_ALONE);
        if (!ObjectUtils.isEmpty(o)) {
            return CommonplaceResult.buildSuccessNoMessage(o);
        }
        List<BookResource> bookResources = bookResourceMapper.selectAllResourceAlone();
        if (!ObjectUtils.isEmpty(bookResources) && bookResources.size() > 0) {
            redisUtils.set(BookConstantPool.SELECT_ALL_RESOURCE_ALONE, bookResources, BookConstantPool.CACHE_TIME[4]);
            return CommonplaceResult.buildSuccessNoMessage(bookResources);
        }
        return CommonplaceResult.buildErrorNoData("没有数据！");
    }

    /**
     * 根据图书的id查询其所有的资源对象，缓存时间：30m
     * @param bookId 图书id
     * @return 指定图书下的所有资源
     */
    @Override
    public CommonplaceResult selectAllResourceAloneByBookId(Integer bookId) {
        Object o = redisUtils.get(BookConstantPool.SELECT_ALL_RESOURCE_ALONE_BY_BOOK_ID + bookId);
        if (ObjectUtils.isEmpty(o)) {
            List<BookResource> bookResources = bookResourceMapper.selectAllResourceAloneByBookId(bookId);
            if (!ObjectUtils.isEmpty(bookResources) && bookResources.size() > 0) {
                redisUtils.set(BookConstantPool.SELECT_ALL_RESOURCE_ALONE_BY_BOOK_ID + bookId, bookResources, BookConstantPool.CACHE_TIME[4]);
                return CommonplaceResult.buildSuccessNoMessage(bookResources);
            }
            return CommonplaceResult.buildErrorNoData("没有数据！");
        }
        return CommonplaceResult.buildSuccessNoMessage(o);
    }

    /**
     * 根据资源id查询资源对象，缓存时间：30m
     * @param resourceId 资源id
     * @return 资源对象
     */
    @Override
    public CommonplaceResult selectResourceById(Integer resourceId) {
        Object o = redisUtils.get(BookConstantPool.SELECT_RESOURCE_BY_ID + resourceId);
        if (ObjectUtils.isEmpty(o)) {
            BookResource bookResource = bookResourceMapper.selectResourceById(resourceId);
            if (!ObjectUtils.isEmpty(bookResource)) {
                redisUtils.set(BookConstantPool.SELECT_RESOURCE_BY_ID + resourceId, bookResource, BookConstantPool.CACHE_TIME[30]);
                return CommonplaceResult.buildSuccessNoMessage(bookResource);
            }
            return CommonplaceResult.buildErrorNoData("没有该数据！");
        }
        return CommonplaceResult.buildSuccessNoMessage(o);
    }

    /**
     * 更新资源信息
     * @param bookResource 资源对象
     * @return 影响行数
     */
    @Override
    public CommonplaceResult updateResource(BookResource bookResource) {
        bookResource.setCreateTime(new Date());
        //删
        int row = bookResourceMapper.updateResource(bookResource);
        if (row > 0) {
            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //删
            return CommonplaceResult.buildSuccessNoData("更新成功");
        }
        return CommonplaceResult.buildErrorNoData("更新失败");
    }

    /**
     * 根据id删除资源
     * @param resourceId 资源id
     * @return 影响行数
     */
    @Override
    public CommonplaceResult deleteResourceById(Integer resourceId) {
        return bookResourceMapper.deleteResourceById(resourceId) > 0 ? CommonplaceResult.buildSuccessNoData("删除成功") :
                CommonplaceResult.buildErrorNoData("删除失败");
    }
}
