package com.onlinebookstore.mapper;

import com.onlinebookstore.entity.BookBanner;
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
     * 根据id删除BookBanner
     * @param id id
     * @return 影响行数
     */
    int deleteBookBannerById(Integer id);

    /**
     * 修改BookBanner
     * @param bookBanner 实体类
     * @return 影响行数
     */
    int updateBookBanner(BookBanner bookBanner);
}
