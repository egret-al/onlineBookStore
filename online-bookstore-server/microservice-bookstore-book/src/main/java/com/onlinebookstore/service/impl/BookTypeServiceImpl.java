package com.onlinebookstore.service.impl;

import com.onlinebookstore.common.CommonplaceResult;
import com.onlinebookstore.entity.bookserver.Book;
import com.onlinebookstore.entity.bookserver.BookType;
import com.onlinebookstore.mapper.BookTypeMapper;
import com.onlinebookstore.service.BookTypeService;
import com.onlinebookstore.util.RedisUtils;
import com.onlinebookstore.util.bookutil.BookConstantPool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author rkc
 * @date 2020/9/30 16:17
 * @version 1.0
 */
@Slf4j
@Service
public class BookTypeServiceImpl implements BookTypeService {

    @Resource
    private BookTypeMapper bookTypeMapper;

    @Resource
    private RedisUtils redisUtils;

    /**
     * 查询所有类型和对应的图书
     * @return CommonplaceResult
     */
    @Override
    public CommonplaceResult selectAllWithBook() {
        Object o = redisUtils.get(BookConstantPool.SELECT_ALL_TYPE_WITH_BOOK);
        if (ObjectUtils.isEmpty(o)) {
            List<BookType> bookTypes = bookTypeMapper.selectAllTypeWithBook(15);
            if (bookTypes.size() > 0) {
                try {
                    redisUtils.set(BookConstantPool.SELECT_ALL_TYPE_WITH_BOOK, bookTypes, BookConstantPool.CACHE_TIME[3]);
                } catch (Exception e) {
                    log.error(e.getMessage());
                    e.printStackTrace();
                }
                return CommonplaceResult.buildSuccessNoMessage(bookTypes);
            }
        }
        return CommonplaceResult.buildSuccessNoMessage(o);
    }

    /**
     * 查询所有类型，因为类型数据很少变化，因此缓存1h
     * @return 类型
     */
    @Override
    public CommonplaceResult selectAllType() {
        Object o = redisUtils.get(BookConstantPool.SELECT_ALL_TYPE);
        if (ObjectUtils.isEmpty(o)) {
            //查数据库，加入redis
            List<BookType> bookTypes = bookTypeMapper.selectAllType();
            if (bookTypes.size() > 0) {
                redisUtils.set(BookConstantPool.SELECT_ALL_TYPE, bookTypes, BookConstantPool.CACHE_TIME[5]);
                return CommonplaceResult.buildSuccessNoMessage(bookTypes);
            }
            return CommonplaceResult.buildErrorNoData("没有数据！");
        }
        //缓存有数据直接返回
        return CommonplaceResult.buildSuccessNoMessage(o);
    }

    /**
     * 根据id查询类型，缓存时间1h
     * @param id id
     * @return 类型
     */
    @Override
    public CommonplaceResult selectTypeById(Integer id) {
        Object o = redisUtils.get(BookConstantPool.SELECT_TYPE_BY_ID + id);
        if (ObjectUtils.isEmpty(o)) {
            BookType bookType = bookTypeMapper.selectTypeById(id);
            if (ObjectUtils.isEmpty(bookType)) {
                return CommonplaceResult.buildErrorNoData("没有该数据！");
            }
            //加入缓存
            redisUtils.set(BookConstantPool.SELECT_TYPE_BY_ID + id, bookType, BookConstantPool.CACHE_TIME[5]);
            return CommonplaceResult.buildSuccessNoMessage(bookType);
        }
        return CommonplaceResult.buildSuccessNoMessage(o);
    }

    @Override
    public CommonplaceResult updateType(BookType bookType) {
        return bookTypeMapper.updateType(bookType) > 0 ? CommonplaceResult.buildSuccessNoData("更新成功！")
                : CommonplaceResult.buildErrorNoData("更新失败！");
    }

    @Override
    public CommonplaceResult insertType(BookType bookType) {
        return bookTypeMapper.insertType(bookType) > 0 ? CommonplaceResult.buildSuccessNoData("添加成功！")
                : CommonplaceResult.buildErrorNoData("添加失败！");
    }
}
