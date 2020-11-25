package com.onlinebookstore.service;

import com.onlinebookstore.common.CommonplaceResult;
import com.onlinebookstore.entity.userserver.ShoppingTrolley;

/**
 * @author rkc
 * @version 1.0
 * @date 2020/11/25 20:17
 */
public interface ShoppingTrolleyService {

    /**
     * 根据账号查询购物车的物品，不同于selectProductByAccount的是，该接口的service层会调用book微服务，
     * 会将查询到的book填充到属性中并返回
     * @param username 账号
     * @return 购物车的商品集合
     */
    CommonplaceResult selectCompleteProductByAccount(String username);

    /**
     * 根据账号查询购物车的物品
     * @param username 账号
     * @return 购物车的商品集合
     */
    CommonplaceResult selectProductByAccount(String username);

    /**
     * 根据id查询购物车
     * @param id id
     * @return ShoppingTrolley
     */
    CommonplaceResult getTrolleyById(int id);

    /**
     * 根据id删除
     * @param id id
     * @return 影响行数
     */
    CommonplaceResult deleteTrolleyById(int id);

    /**
     * 添加商品到购物车
     * @param shoppingTrolley 商品购物车
     * @return 影响行数
     */
    CommonplaceResult insertShoppingTrolley(ShoppingTrolley shoppingTrolley);

    /**
     * 改变收藏数量
     * @param id id
     * @param count 数量
     * @return 影响行数
     */
    CommonplaceResult modifyCollectCount(int id, int count);
}
