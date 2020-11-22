package com.onlinebookstore.controller;

import com.onlinebookstore.annotation.JsonObject;
import com.onlinebookstore.common.CommonplaceResult;
import com.onlinebookstore.entity.orderserver.Order;
import com.onlinebookstore.entity.userserver.Account;
import com.onlinebookstore.entity.userserver.User;
import com.onlinebookstore.service.AccountService;
import com.onlinebookstore.util.userutil.UserConstantPool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author rkc
 * @version 1.0
 * @date 2020/9/11 9:18
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    /**
     * 余额充值接口
     * @param map 充值参数
     * @return CommonplaceResult
     */
    @PostMapping("pri/topUpResidue")
    public CommonplaceResult topUpResidue(@RequestBody Map<String, String> map) {
        String username = map.get(UserConstantPool.USERNAME);
        String count = map.get(UserConstantPool.COUNT);
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(count)) return CommonplaceResult.buildErrorNoData("数据不全！");
        return accountService.topUpResidue(username, Integer.parseInt(count));
    }

    /**
     * 购买图书接口
     */
    @GetMapping("pri/purchaseBook/{serialNumber}")
    public CommonplaceResult purchaseBook(@PathVariable("serialNumber") String serialNumber) {
        if (ObjectUtils.isEmpty(serialNumber)) {
            return CommonplaceResult.buildErrorNoData("非法数据");
        }
        return accountService.purchaseBook(serialNumber);
    }

    /**
     * 创建订单，并且设置订单30分钟内未支付则自动过期
     * 数据格式：
     * {
     *     'book_id': 'xx',
     *     'username_id': 'xx',
     *     'order_content': 'xxx',
     *     'product_count': 'xxx'
     * }
     * @param order 订单
     * @return 创建成功
     */
    @PostMapping("pri/createOrder")
    public CommonplaceResult createOrder(@RequestBody Order order) {
        return accountService.createOrder(order);
    }

    /**
     * 注册账号接口
     * 数据格式
     * {
     *     'account': {
     *         'username': 'xxx',
     *         'password': 'xxx'
     *     },
     *     'user': {
     *         'nickname': 'xx',
     *         'birthday': 'xxxxxx',
     *         'sex': 'xx',
     *         'phone': 'xxxxxxxxxx'
     *     }
     * }
     * @return json
     */
    @PostMapping("pub/registry")
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
        List<Account> accountList = accountService.selectAllAccount();
        return CommonplaceResult.buildSuccess(accountList, "获取成功！");
    }

    /**
     * 根据账号查询账号信息+用户信息
     * @param username 账号
     * @return 数据集
     */
    @GetMapping("pub/getAccountWithUser/{username}")
    public CommonplaceResult getAccountContainUserByUsername(@PathVariable(UserConstantPool.USERNAME) String username) {
        return accountService.getAccountContainUserByUsername(username);
    }

    /**
     * 登录接口
     * 数据格式：
     * {
     *     'username': '1234567890',
     *     'password': 'xxxxxxx'
     * }
     * @param loginInfo 登录信息
     * @return 是否登录成功
     */
    @PostMapping("pub/login")
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
     * 数据格式：
     * {
     *     'username': '1234567890',
     *     'modifyScoreCount': 10
     * }
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
     * 数据格式：
     * {
     *     'username': 'xxx',
     *     'count': 'xxx',
     *     'use_score': true
     * }
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
