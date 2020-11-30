package com.onlinebookstore.mapper;

import com.onlinebookstore.entity.orderserver.OrderAnalysisItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author rkc
 * @version 1.0
 * @date 2020/11/29 11:26
 */
@Mapper
public interface OrderGraphMapper {

    /**
     * 得到销售量前top的图书名称
     * @param top 前top
     * @return book_id集合
     */
    List<Integer> getTopCountBookName(int top);

    /**
     * 得到销售额前top的图书名称
     * @param top 前top
     * @return book_id集合
     */
    List<Integer> getTopPriceBookName(int top);

    /**
     * 根据bookId获取需要分析的订单
     * @param bookId bookId
     * @param month 指定的月份前，如指定为6，则返回的是前5月+当前的情况
     */
    List<OrderAnalysisItem> selectOrderItemByBookId(@Param("bookId") int bookId, @Param("month") int month);
}
