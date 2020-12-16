package com.onlinebookstore.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.onlinebookstore.annotation.JsonObject;
import com.onlinebookstore.common.CommonplaceResult;
import com.onlinebookstore.entity.orderserver.Order;
import com.onlinebookstore.entity.userserver.Account;
import com.onlinebookstore.entity.userserver.User;
import com.onlinebookstore.handler.AccountBlockHandler;
import com.onlinebookstore.service.AccountService;
import com.onlinebookstore.util.userutil.UserConstantPool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author rkc
 * @version 1.0
 * @date 2020/9/11 9:18
 */
@Slf4j
@RefreshScope
@RestController
@RequestMapping("/api/v1/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    /**
     * 重置密码
     * @param account 账号
     * @return CommonplaceResult
     */
    @PostMapping("pub/resetPassword")
    public CommonplaceResult resetPassword(@JsonObject("account") Account account, @JsonObject("code") String code) {
        if (StringUtils.isEmpty(account.getUsername()) || StringUtils.isEmpty(account.getPassword())) return CommonplaceResult.buildErrorNoData("错误！");
        return accountService.resetPassword(account, code);
    }

    /**
     * 忘记密码
     * @param account 账号
     * @return CommonplaceResult
     */
    @PostMapping("pub/forgotPassword")
    public CommonplaceResult forgotPassword(@JsonObject("account") Account account, @JsonObject("phone") String phone) {
        if (StringUtils.isEmpty(account.getUsername())) return CommonplaceResult.buildErrorNoData("非法操作！");
        return accountService.forgotPassword(account, phone);
    }

    /**
     * 余额充值接口
     * @param map 充值参数
     * @return CommonplaceResult
     */
    @PostMapping("pri/topUpResidue")
    @SentinelResource(value = "topUpResidue", blockHandlerClass = AccountBlockHandler.class, blockHandler = "handleTopUpResidue")
    public CommonplaceResult topUpResidue(@RequestBody Map<String, String> map) {
        String username = map.get(UserConstantPool.USERNAME);
        String count = map.get(UserConstantPool.COUNT);
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(count)) return CommonplaceResult.buildErrorNoData("数据不全！");
        return accountService.topUpResidue(username, Integer.parseInt(count));
    }

    /**
     * 购买图书接口
     */
    @PostMapping("pri/purchaseBook")
    public CommonplaceResult purchaseBook(@JsonObject("serialNumber") String serialNumber) {
        if (ObjectUtils.isEmpty(serialNumber)) {
            return CommonplaceResult.buildErrorNoData("非法数据");
        }
        return accountService.purchaseBook(serialNumber);
    }

    /**
     * 创建订单，并且设置订单30分钟内未支付则自动过期
     * @param order 订单
     * @return 创建成功
     */
    @PostMapping("pri/createOrder")
    public CommonplaceResult createOrder(@RequestBody Order order) {
        return accountService.createOrder(order);
    }

    /**
     * 注册账号接口
     * @return json
     */
    @PostMapping("pub/registry")
    @SentinelResource(value = "registry", blockHandlerClass = AccountBlockHandler.class, blockHandler = "handleRegistry")
    public CommonplaceResult registry(@JsonObject("account") Account account, @JsonObject("user") User user) {
        account.setCreateTime(new Date());
        user.setLastLoginTime(new Date());
        user.setAccountUsername(account.getUsername());
        return accountService.addAccount(account, user);
    }

    /**
     * 查询全部账户，不包含用户信息
     * @return 数据集
     */
    @GetMapping("pub/selectAll")
    public CommonplaceResult selectAllAccount() {
        return accountService.selectAllAccount();
    }

    /**
     * 根据账号查询账号信息+用户信息
     * @param username 账号
     * @return 数据集
     */
    @PostMapping("pub/getAccountWithUser")
    public CommonplaceResult getAccountContainUserByUsername(@JsonObject(UserConstantPool.USERNAME) String username) {
        return accountService.getAccountContainUserByUsername(username);
    }

    /**
     * 登录接口
     * @param loginInfo 登录信息
     * @return 是否登录成功
     */
    @PostMapping("pub/login")
    @SentinelResource(value = "login", blockHandlerClass = AccountBlockHandler.class, blockHandler = "handleLogin")
    public CommonplaceResult login(@RequestBody Map<String, String> loginInfo) {
        String username = loginInfo.get(UserConstantPool.USERNAME);
        String password = loginInfo.get(UserConstantPool.PASSWORD);
        if (!StringUtils.isEmpty(username) && !StringUtils.isEmpty(password)) {
            return accountService.selectAccountByUsernameAndPassword(username, password);
        }
        return CommonplaceResult.buildErrorNoData("账号或密码不能为空！");
    }

    /**
     * 修改账号密码
     * @return 数据集
     */
    @PostMapping("pri/modifyPassword")
    public CommonplaceResult modifyPasswordByUsername(@RequestBody Map<String, String> map) {
        String username = map.get("username");
        String oldPassword = map.get("oldPassword");
        String newPassword = map.get("newPassword");
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(oldPassword) || StringUtils.isEmpty(newPassword)) {
            return CommonplaceResult.buildErrorNoData("数据不全！");
        }
        return accountService.modifyPasswordByUsername(username, oldPassword, newPassword);
    }

    /**
     * 增加用户积分的接口
     * @param modifyScorePojo 里面包含了账号和将要改变的积分
     * @return json数据信息
     */
    @PostMapping("pri/modifyScore")
    public CommonplaceResult modifyScore(@RequestBody Map<String, String> modifyScorePojo) {
        String username = modifyScorePojo.get(UserConstantPool.USERNAME);
        Integer score = Integer.valueOf(modifyScorePojo.get(UserConstantPool.MODIFY_SCORE_COUNT));
        return accountService.modifyScore(username, score);
    }

    /**
     * 操作余额接口
     * @param operateInfo 前端传递的信息
     */
    @Deprecated
    @PostMapping("pri/operateBalance")
    public CommonplaceResult operateBalance(@RequestBody Map<String, Object> operateInfo) {
        try {
            String username = (String) operateInfo.get(UserConstantPool.USERNAME);
            int count = (int) operateInfo.get(UserConstantPool.COUNT);
            boolean useScore = (boolean) operateInfo.get(UserConstantPool.USE_SCORE);
            return accountService.modifyBalance(username, count, useScore);
        } catch (Exception e) {
            return CommonplaceResult.buildErrorNoData(e.getMessage());
        }
    }
}
