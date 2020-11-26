package com.onlinebookstore.controller;

import com.onlinebookstore.common.CommonplaceResult;
import com.onlinebookstore.entity.userserver.Account;
import com.onlinebookstore.entity.userserver.ShoppingTrolley;
import com.onlinebookstore.service.ShoppingTrolleyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author rkc
 * @version 1.0
 * @date 2020/11/25 20:24
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/shopping/pri")
public class ShoppingTrolleyController {
    @Resource
    private ShoppingTrolleyService shoppingTrolleyService;

    /**
     * 根据账号查询购物车的物品，不同于selectProductByAccount的是，该接口的service层会调用book微服务，
     * 会将查询到的book填充到属性中并返回
     * @param account 账号
     * @return 购物车的商品集合
     */
    @PostMapping("selectCompleteProductByAccount")
    public CommonplaceResult selectCompleteProductByAccount(@RequestBody Account account) {
        if (StringUtils.isEmpty(account.getUsername())) {
            return CommonplaceResult.buildErrorNoData("非法请求！");
        }
        return shoppingTrolleyService.selectCompleteProductByAccount(account.getUsername());
    }

    /**
     * 根据账号查询购物车的物品
     * @param account 账号
     * @return 购物车的商品集合
     */
    @Deprecated
    @PostMapping("selectProductByAccount")
    public CommonplaceResult selectProductByAccount(@RequestBody Account account) {
        if (StringUtils.isEmpty(account.getUsername())) {
            return CommonplaceResult.buildErrorNoData("非法请求！");
        }
        return shoppingTrolleyService.selectProductByAccount(account.getUsername());
    }

    /**
     * 根据id查询购物车
     * @param shoppingTrolley 有效属性：id
     * @return ShoppingTrolley
     */
    @PostMapping("getTrolleyById")
    public CommonplaceResult getTrolleyById(@RequestBody ShoppingTrolley shoppingTrolley) {
        return shoppingTrolleyService.getTrolleyById(shoppingTrolley.getId());
    }

    /**
     * 根据id删除
     * @param shoppingTrolley 有效属性：id
     * @return 影响行数
     */
    @PostMapping("deleteTrolleyById")
    public CommonplaceResult deleteTrolleyById(@RequestBody ShoppingTrolley shoppingTrolley) {
        return shoppingTrolleyService.deleteTrolleyById(shoppingTrolley.getId());
    }

    /**
     * 添加商品到购物车
     * @param shoppingTrolley 商品购物车
     * @return 影响行数
     */
    @PostMapping("insertShoppingTrolley")
    public CommonplaceResult insertShoppingTrolley(@RequestBody ShoppingTrolley shoppingTrolley) {
        shoppingTrolley.setCreateTime(new Date());
        return shoppingTrolleyService.insertShoppingTrolley(shoppingTrolley);
    }

    /**
     * 改变收藏数量
     * @param shoppingTrolley 有效属性：id，collectCount用来代替变化数量
     * @return 影响行数
     */
    @PostMapping("modifyCollectCount")
    public CommonplaceResult modifyCollectCount(@RequestBody ShoppingTrolley shoppingTrolley) {
        int modify = shoppingTrolley.getCollectCount();
        return shoppingTrolleyService.modifyCollectCount(shoppingTrolley.getId(), modify);
    }
}
