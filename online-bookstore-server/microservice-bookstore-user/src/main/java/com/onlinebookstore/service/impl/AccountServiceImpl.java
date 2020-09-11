package com.onlinebookstore.service.impl;

import com.onlinebookstore.common.CommonplaceResult;
import com.onlinebookstore.entity.Account;
import com.onlinebookstore.mapper.AccountMapper;
import com.onlinebookstore.service.AccountService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author rkc
 * @version 1.0
 * @date 2020/9/11 15:43
 */
@Service
public class AccountServiceImpl implements AccountService {

    @Resource
    private AccountMapper accountMapper;

    /**
     * 添加账户
     * @param account 账户实体类
     * @return 影响行数
     */
    @Override
    public int addAccount(Account account) {
        return accountMapper.addAccount(account);
    }

    /**
     * 根据账号和密码查询账户，通常用于登录业务
     * @param username 账号
     * @param password 密码
     * @return 账户实体类
     */
    @Override
    public Account selectAccountByUsernameAndPassword(String username, String password) {
        return accountMapper.selectAccountByUsernameAndPassword(username, password);
    }

    /**
     * 查询所有的账户，通常由管理员调用
     * @return 账户集合列表
     */
    @Override
    public List<Account> selectAllAccount() {
        return accountMapper.selectAllAccount();
    }

    /**
     * 根据账号修改密码
     * @param username 账号
     * @param oldPassword 旧密码
     * @param password 新密码
     * @return 影响行数
     */
    @Override
    public CommonplaceResult modifyPasswordByUsername(String username, String oldPassword, String password) {
        //验证账号密码是否正确，如果正确则直接进行修改操作，否则修改失败
        Account account = accountMapper.selectAccountByUsernameAndPassword(username, password);
        if (account == null) {
            //密码错误，匹配失败
            return CommonplaceResult.buildErrorNoData("修改失败，密码错误！");
        }
        //匹配成功，可以进行修改操作
        int row = accountMapper.modifyPasswordByUsername(username, password);
        return row > 0 ? CommonplaceResult.buildSuccessNoData("修改成功！") : CommonplaceResult.buildErrorNoData("密码不能和旧密码一直！");
    }

    /**
     * 根据账号增加积分
     * @param username 被增加的账号
     * @param additionalScore 将要增加的积分
     * @return 影响行数
     */
    @Override
    public int addScoreByUsername(String username, Integer additionalScore) {
        return accountMapper.addScoreByUsername(username, additionalScore);
    }

    /**
     * 根据账号查询全部信息，包括用户信息的关联查询
     * @param username 账号
     * @return 账号信息+用户信息
     */
    @Override
    public CommonplaceResult getAccountContainUserByUsername(String username) {
        Account accountWithUser = accountMapper.getAccountContainUserByUsername(username);
        return accountWithUser == null ? CommonplaceResult.buildErrorNoData("获取失败，不存在该用户！") :
                CommonplaceResult.buildSuccess(accountWithUser, "获取成功！");
    }
}
