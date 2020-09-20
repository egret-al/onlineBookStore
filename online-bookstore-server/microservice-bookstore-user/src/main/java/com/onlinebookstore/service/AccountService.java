package com.onlinebookstore.service;

import com.onlinebookstore.common.CommonplaceResult;
import com.onlinebookstore.entity.Account;
import com.onlinebookstore.entity.User;

import java.util.List;

/**
 * @author rkc
 * @version 1.0
 * @date 2020/9/11 15:43
 */
public interface AccountService {

    /**
     * 添加账户
     * @param account 账户实体类
     * @return 影响行数
     */
    CommonplaceResult addAccount(Account account, User user);

    /**
     * 根据账号和密码查询账户，通常用于登录业务
     * @param username 账号
     * @param password 密码
     * @return 账户实体类
     */
    CommonplaceResult selectAccountByUsernameAndPassword(String username, String password);

    /**
     * 查询所有的账户，通常由管理员调用
     * @return 账户集合列表
     */
    List<Account> selectAllAccount();

    /**
     * 根据账号修改密码
     * @param username 账号
     * @param oldPassword 旧密码
     * @param password 新密码
     * @return 影响行数
     */
    CommonplaceResult modifyPasswordByUsername(String username, String oldPassword, String password);


    /**
     * 根据账号查询全部信息，包括用户信息的关联查询
     * @param username 账号
     * @return 账号信息+用户信息
     */
    CommonplaceResult getAccountContainUserByUsername(String username);

    /**
     * 根据账号修改积分
     * @param username 账号
     * @param modifyNumber 修改的积分
     * @return 是否修改成功
     */
    CommonplaceResult modifyScore(String username, Integer modifyNumber);

    /**
     * 修改余额
     * @param username 账号
     * @param count 修改的数量，如果为负数则为扣除，否则为充值
     * @param useScore 是否使用抵扣，只有在count为负数时才有效，count为正数时为null
     * @return 操作是否成功
     */
    CommonplaceResult modifyBalance(String username, int count, boolean useScore);

    /**
     * 购买图书
     * @param bookId 图书id
     * @param count 数量
     * @param username 购买账号
     * @param useScore 是否使用积分
     */
    CommonplaceResult purchaseBook(Integer bookId, Integer count, String username, Boolean useScore);
}
