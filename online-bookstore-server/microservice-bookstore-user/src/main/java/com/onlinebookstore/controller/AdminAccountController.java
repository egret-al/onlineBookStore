package com.onlinebookstore.controller;

import com.onlinebookstore.annotation.JsonObject;
import com.onlinebookstore.common.CommonplaceResult;
import com.onlinebookstore.entity.userserver.AdminAccount;
import com.onlinebookstore.service.AdminAccountService;
import com.onlinebookstore.util.userutil.UserConstantPool;
import lombok.extern.slf4j.Slf4j;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

/**
 * @author rkc
 * @date 2020/11/1 10:38
 * @version 1.0
 */
@Slf4j
@RefreshScope
@RestController
@RequestMapping("/api/v1/adminAccount")
public class AdminAccountController {

    @Resource
    private AdminAccountService adminAccountService;

    /**
     * 修改密码
     * @param adminAccount 管理员账号对象，包含账号和旧密码
     * @param newPassword 新密码
     * @return 修改结果
     */
    @PostMapping("pri/updatePassword")
    public CommonplaceResult updatePassword(@JsonObject("adminAccount") AdminAccount adminAccount, @JsonObject("newPassword") String newPassword) {
        if (StringUtils.isEmpty(adminAccount.getUsername()) || StringUtils.isEmpty(adminAccount.getPassword())) return CommonplaceResult.buildErrorNoData("参数不全！");
        return adminAccountService.updatePassword(adminAccount, newPassword);
    }

    /**
     * 根据账号获取管理员账号信息
     * @param username 账号
     * @return 管理员账号
     */
    @PostMapping("pri/getAdminByUsername")
    public CommonplaceResult getAdminByUsername(@JsonObject("username") String username) {
        return adminAccountService.getAdminByUsername(username);
    }

    /**
     * 登录接口
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
        adminAccount.setCreateTime(new Date());
        return adminAccountService.addAdminAccount(adminAccount);
    }
}
