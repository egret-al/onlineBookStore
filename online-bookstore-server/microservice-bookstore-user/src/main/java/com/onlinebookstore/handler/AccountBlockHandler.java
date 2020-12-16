package com.onlinebookstore.handler;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.onlinebookstore.common.CommonplaceResult;
import com.onlinebookstore.entity.userserver.Account;
import com.onlinebookstore.entity.userserver.User;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * AccountController的处理类，对对应的接口调用进行保护
 * @author rkc
 * @version 1.0
 * @date 2020/12/16 11:02
 */
@Slf4j
public class AccountBlockHandler {

    public static CommonplaceResult handleRegistry(Account account, User user, BlockException blockException) {
        log.error(blockException.getMessage());
        return CommonplaceResult.buildErrorNoData("当前注册人数过多，请稍后再试！");
    }

    public static CommonplaceResult handleLogin(Map<String, String> loginInfo, BlockException blockException) {
        log.error(blockException.getMessage());
        return CommonplaceResult.buildErrorNoData("登录超时，请稍后再试！");
    }

    public static CommonplaceResult handleTopUpResidue(Map<String, String> map, BlockException blockException) {
        log.error(blockException.getMessage());
        return CommonplaceResult.buildErrorNoData("充值失败！请稍后再试！");
    }
}
