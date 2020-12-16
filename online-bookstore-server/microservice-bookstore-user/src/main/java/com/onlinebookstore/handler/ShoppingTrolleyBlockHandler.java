package com.onlinebookstore.handler;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.onlinebookstore.common.CommonplaceResult;
import com.onlinebookstore.entity.userserver.Account;
import com.onlinebookstore.entity.userserver.ShoppingTrolley;
import lombok.extern.slf4j.Slf4j;

/**
 * @author rkc
 * @version 1.0
 * @date 2020/12/16 11:13
 */
@Slf4j
public class ShoppingTrolleyBlockHandler {

    public static CommonplaceResult handleGetBookCountByUsername(String username, BlockException blockException) {
        log.error("{}获取购物车数量失败：{}", username, blockException.getMessage());
        return CommonplaceResult.buildError(0, "查询失败，请稍后再试！");
    }

    public static CommonplaceResult handleSelectCompleteProductByAccount(Account account, BlockException blockException) {
        log.error("{}查询购物车失败：{}", account.getUsername(), blockException.getMessage());
        return CommonplaceResult.buildErrorNoData("服务器繁忙，请稍后再试！");
    }

    public static CommonplaceResult handleModifyCollectCount(ShoppingTrolley shoppingTrolley, BlockException blockException) {
        log.error(blockException.getMessage());
        return CommonplaceResult.buildErrorNoData("操作失败！");
    }
}
