package com.onlinebookstore.service;

import com.onlinebookstore.common.CommonplaceResult;
import com.onlinebookstore.entity.userserver.AdminAccount;

import java.util.List;

/**
 * @author rkc
 * @date 2020/11/1 10:21
 * @version 1.0
 */
public interface AdminAccountService {

    CommonplaceResult updatePassword(AdminAccount adminAccount, String newPassword);

    CommonplaceResult getAdminByUsername(String username);

    /**
     * 根据账号密码查询管理员账号
     * @param username 账号
     * @param password 密码
     * @return 包含账号实体的json结果
     */
    CommonplaceResult selectAdminByUsernameAndPassword(String username, String password);

    /**
     * 添加管理员账号
     * @param adminAccount 账号实体
     * @return 影响行数
     */
    CommonplaceResult addAdminAccount(AdminAccount adminAccount);

    /**
     * 查询所有管理员账号
     * @return 包含账号实体的账号实体集合
     */
    CommonplaceResult selectAllAdminAccount();
}
