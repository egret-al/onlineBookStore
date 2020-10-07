package com.onlinebookstore.service;

import com.onlinebookstore.common.CommonplaceResult;
import com.onlinebookstore.entity.userserver.User;

/**
 * @author rkc
 * @version 1.0
 * @date 2020/9/11 15:44
 */
public interface UserService {

    /**
     * 添加用户，通常在创建账户时在同一个事务中进行操作
     * @param user 用户信息类
     * @return 影响行数
     */
    CommonplaceResult addUser(User user);

    /**
     * 根据登录账号查询用户信息
     * @param accountUsername 登录账号
     * @return 用户信息类
     */
    CommonplaceResult selectUserByUsername(String accountUsername);

    /**
     * 查询所有用户信息
     * @return 用户信息集合
     */
    CommonplaceResult selectAllUser();

    /**
     * 根据id修改用户信息
     * @param user id
     * @return 影响行数
     */
    CommonplaceResult modifyUserById(User user);

    /**
     * 根据id查询用户全部信息，包括级联映射的账户信息
     * @param id id
     * @return 用户信息+账号信息
     */
    CommonplaceResult getUserContainAccountById(Integer id);
}
