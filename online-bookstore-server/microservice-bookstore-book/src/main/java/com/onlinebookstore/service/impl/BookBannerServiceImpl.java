package com.onlinebookstore.service.impl;

import com.onlinebookstore.common.CommonplaceResult;
import com.onlinebookstore.entity.bookserver.BookBanner;
import com.onlinebookstore.mapper.BookBannerMapper;
import com.onlinebookstore.service.BookBannerService;
import com.onlinebookstore.util.RedisUtils;
import com.onlinebookstore.util.bookutil.BookConstantPool;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author rkc
 * @date 2020/9/24 11:16
 * @version 1.0
 */
@Service
public class BookBannerServiceImpl implements BookBannerService {

    @Resource
    private BookBannerMapper bookBannerMapper;

    @Resource
    private RedisUtils redisUtils;

    /**
     * 查询所有Banner（缓存时间：10分钟）
     */
    @Override
    public CommonplaceResult selectAll() {
        Object o = redisUtils.get(BookConstantPool.SELECT_ALL);
        if (!ObjectUtils.isEmpty(o)) {
            //缓存有数据，直接读取缓存并返回数据
            return CommonplaceResult.buildSuccessNoMessage(o);
        }
        //从数据库读取数据并且加入redis缓存
        List<BookBanner> bookBanners = bookBannerMapper.selectAll();
        if (ObjectUtils.isEmpty(bookBanners) || bookBanners.size() == 0) {
            return CommonplaceResult.buildErrorNoData("没有数据！");
        }
        //加入缓存
        redisUtils.set(BookConstantPool.SELECT_ALL, bookBanners, BookConstantPool.CACHE_TIME[3]);
        return CommonplaceResult.buildSuccess(bookBanners, "数据获取成功！");
    }

    /**
     * 查询最新的count条记录（缓存时间：10分钟）
     * @param count 数量
     */
    @Override
    public CommonplaceResult selectCount(Integer count) {
        Object o = redisUtils.get(BookConstantPool.SELECT_COUNT);
        if (!ObjectUtils.isEmpty(o)) {
            return CommonplaceResult.buildSuccessNoMessage(o);
        }
        List<BookBanner> bookBanners = bookBannerMapper.selectCount(count);
        if (ObjectUtils.isEmpty(bookBanners) || bookBanners.size() == 0) {
            return CommonplaceResult.buildErrorNoData("没有数据！");
        }
        redisUtils.set(BookConstantPool.SELECT_COUNT, bookBanners, BookConstantPool.CACHE_TIME[3]);
        return CommonplaceResult.buildSuccess(bookBanners, "数据获取成功！");
    }

    /**
     * 新增数据
     * @param bookBanner 实体类
     */
    @Override
    public CommonplaceResult insertBookBanner(BookBanner bookBanner) {
        return bookBannerMapper.insertBookBanner(bookBanner) > 0 ? CommonplaceResult.buildSuccessNoData("添加成功")
                : CommonplaceResult.buildErrorNoData("添加失败！");
    }

    /**
     * 根据id删除BookBanner
     * @param url url
     */
    @Override
    public CommonplaceResult deleteBookBannerByUrl(String url) {
        if (bookBannerMapper.deleteBookBannerByUrl(url) > 0) {
            //这里只有管理员操作，因此不涉及到数据库和redis的一致性
            this.refreshCache();
            return CommonplaceResult.buildSuccessNoData("删除成功");
        }
        return CommonplaceResult.buildErrorNoData("删除失败！");
    }

    /**
     * 刷新缓存
     */
    @Override
    public CommonplaceResult refreshCache() {
        redisUtils.del(BookConstantPool.SELECT_ALL);
        return CommonplaceResult.buildSuccessNoData("刷新成功");
    }
}
