package com.onlinebookstore.mapper;

import com.onlinebookstore.entity.bookserver.BookBanner;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author rkc
 * @date 2020/9/24 11:00
 * @version 1.0
 */
@Mapper
public interface BookBannerMapper {

    /**
     * 查询所有Banner
     */
    List<BookBanner> selectAll();

    /**
     * 查询最新的count条记录
     * @param count 数量
     */
    List<BookBanner> selectCount(Integer count);

    /**
     * 新增数据
     * @param bookBanner 实体类
     * @return 影响行数
     */
    int insertBookBanner(BookBanner bookBanner);

    /**
     * 根据url删除BookBanner
     * @param url url
     * @return 影响行数
     */
    int deleteBookBannerByUrl(String url);

    /**
     * 修改BookBanner
     * @param bookBanner 实体类
     * @return 影响行数
     */
    int updateBookBanner(BookBanner bookBanner);
}
