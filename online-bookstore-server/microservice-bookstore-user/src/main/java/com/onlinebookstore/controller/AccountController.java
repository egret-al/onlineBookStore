package com.onlinebookstore.controller;

import com.onlinebookstore.common.CommonplaceResult;
import com.onlinebookstore.entity.Account;
import com.onlinebookstore.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author rkc
 * @version 1.0
 * @date 2020/9/11 9:18
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/account")
public class AccountController {

    @Resource
    private AccountService accountService;

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
    public CommonplaceResult getAccountContainUserByUsername(@PathVariable("username") String username) {
        return accountService.getAccountContainUserByUsername(username);
    }

    /**
     * 修改账号密码
     * @param username 账号
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @return 数据集
     */
    @PostMapping("pri/modifyPassword")
    public CommonplaceResult modifyPasswordByUsername(String username, String oldPassword, String newPassword) {
        return accountService.modifyPasswordByUsername(username, oldPassword, newPassword);
    }
}
