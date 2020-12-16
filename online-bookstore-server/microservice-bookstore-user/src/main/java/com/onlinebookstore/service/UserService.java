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
     * 发送验证码
     * @param user 用户
     * @return CommonplaceResult
     */
    CommonplaceResult modifyPhoneSendCode(User user);

    /**
     * 修改手机
     * @param user 用户
     * @param code 验证码
     * @return CommonplaceResult
     */
    CommonplaceResult modifyPhone(User user, String code);

    /**
     * 修改昵称
     * @param user 用户
     * @return CommonplaceResult
     */
    CommonplaceResult modifyNickname(User user);

    /**
     * 更改性别
     * @param user 用户
     * @return CommonplaceResult
     */
    CommonplaceResult modifySex(User user);

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
     * 根据id查询用户全部信息，包括级联映射的账户信息
     * @param id id
     * @return 用户信息+账号信息
     */
    CommonplaceResult getUserContainAccountById(Integer id);
}
