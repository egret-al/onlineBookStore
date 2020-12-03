package com.onlinebookstore.controller;

import com.onlinebookstore.common.CommonplaceResult;
import com.onlinebookstore.entity.userserver.AdminAccount;
import com.onlinebookstore.service.AdminAccountService;
import com.onlinebookstore.util.userutil.UserConstantPool;
import lombok.extern.slf4j.Slf4j;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author rkc
 * @date 2020/11/1 10:38
 * @version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/adminAccount")
public class AdminAccountController {

    @Resource
    private AdminAccountService adminAccountService;

    /**
     * 登录接口
     * 数据格式：
     * @param loginInfo 登录信息
     * @return 是否登录成功
     */
    @PostMapping("pub/login")
    public CommonplaceResult login(@RequestBody Map<String, String> loginInfo) {
        String username = loginInfo.get(UserConstantPool.USERNAME);
        String password = loginInfo.get(UserConstantPool.PASSWORD);
        if (!StringUtils.isEmpty(username) && !StringUtils.isEmpty(password)) {
            return adminAccountService.selectAdminByUsernameAndPassword(username, password);
        }
        return CommonplaceResult.buildErrorNoData("账号或密码不能为空！");
    }

    /**
     * 查询所有管理员账号
     * @return 管理员账号
     */
    @GetMapping("pri/selectAll")
    public CommonplaceResult selectAll() {
        return adminAccountService.selectAllAdminAccount();
    }

    /**
     * 添加账号
     * @param adminAccount 账号实体类
     * @return 是否添加成功
     */
    @PostMapping("pri/add")
    public CommonplaceResult addAdminAccount(@RequestBody AdminAccount adminAccount) {
        return adminAccountService.addAdminAccount(adminAccount);
    }
}
