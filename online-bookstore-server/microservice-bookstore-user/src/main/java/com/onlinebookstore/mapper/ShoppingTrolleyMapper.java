package com.onlinebookstore.mapper;

import com.onlinebookstore.entity.userserver.ShoppingTrolley;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

/**
 * @author rkc
 * @version 1.0
 * @date 2020/11/25 19:11
 */
@Mapper
public interface ShoppingTrolleyMapper {

    /**
     * 根据账号查询购物车的物品
     * @param username 账号
     * @return 购物车的商品集合
     */
    List<ShoppingTrolley> selectProductByAccount(String username);

    /**
     * 根据id查询购物车
     * @param id id
     * @return ShoppingTrolley
     */
    ShoppingTrolley getTrolleyById(int id);

    /**
     * 根据id删除
     * @param id id
     * @return 影响行数
     */
    int deleteTrolleyById(int id);

    /**
     * 添加商品到购物车
     * @param shoppingTrolley 商品购物车
     * @return 影响行数
     */
    int insertShoppingTrolley(ShoppingTrolley shoppingTrolley) throws SQLIntegrityConstraintViolationException;

    /**
     * 改变收藏数量
     * @param id id
     * @param count 数量
     * @return 影响行数
     */
    int modifyCollectCount(@Param("id") int id, @Param("count") int count);
}
