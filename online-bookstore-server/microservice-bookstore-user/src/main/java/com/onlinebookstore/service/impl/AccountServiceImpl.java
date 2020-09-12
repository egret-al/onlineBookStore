package com.onlinebookstore.service.impl;

import com.onlinebookstore.common.CommonplaceResult;
import com.onlinebookstore.entity.Account;
import com.onlinebookstore.entity.User;
import com.onlinebookstore.mapper.AccountMapper;
import com.onlinebookstore.mapper.UserMapper;
import com.onlinebookstore.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author rkc
 * @version 1.0
 * @date 2020/9/11 15:43
 */
@Slf4j
@Service
public class AccountServiceImpl implements AccountService {

    @Resource
    private AccountMapper accountMapper;

    @Resource
    private UserMapper userMapper;

    /**
     * 添加账户，首先查询该账户是否已经添加，如果已经添加则返回，
     * 否则继续进行，中途有任何的异常都会结束该事务操作
     * @param account 账户实体类
     * @return 影响行数
     */
    @Override
    @Transactional
    public CommonplaceResult addAccount(Account account, User User) {
        //如果查询是空，说明该账号没有被注册，可以进行注册操作，否则返回
        if (ObjectUtils.isEmpty(accountMapper.selectOneByUsername(account.getUsername()))) {
            //账号注册
            accountMapper.addAccount(account);
            //用户信息注册
            userMapper.addUser(User);
            return CommonplaceResult.buildSuccessNoData("注册成功！");
        }
        return CommonplaceResult.buildErrorNoData("注册失败！禁止重复注册");
    }

    /**
     * 根据账号和密码查询账户，通常用于登录业务
     * @param username 账号
     * @param password 密码
     * @return 账户实体类
     */
    @Override
    public CommonplaceResult selectAccountByUsernameAndPassword(String username, String password) {
        Account account = accountMapper.selectAccountByUsernameAndPassword(username, password);
        return account == null ? CommonplaceResult.buildErrorNoData("账号或者密码错误！") :
                CommonplaceResult.buildSuccess(account, "登录成功！");
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
        Account account = accountMapper.selectAccountByUsernameAndPassword(username, oldPassword);
        if (account == null) {
            //密码错误，匹配失败
            log.warn(username + "密码错误，修改失败！");
            return CommonplaceResult.buildErrorNoData("修改失败，密码错误！");
        }
        //匹配成功，可以进行修改操作
        int row = accountMapper.modifyPasswordByUsername(username, password);
        return row > 0 ? CommonplaceResult.buildSuccessNoData("修改成功！") : CommonplaceResult.buildErrorNoData("密码不能和旧密码相同！");
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

    /**
     * 需要进行逻辑处理，如果查询的账号的积分为0且modifyNumber为负数，
     * 又或者积分数不够扣除，均为扣除失败
     * @param username 账号
     * @param modifyNumber 修改的积分
     * @return 是否修改成功
     */
    @Override
    @Transactional
    public CommonplaceResult modifyScore(String username, Integer modifyNumber) {
        //查询积分信息
        Account account = accountMapper.selectOneByUsername(username);
        if (account == null) return CommonplaceResult.buildErrorNoData("账号异常！");
        int score = account.getScore();
        //积分为0，不能进行扣除，非法操作
        if (score == 0 && modifyNumber < 0)  return CommonplaceResult.buildErrorNoData("非法操作！");
        //积分不足扣除
        if (score < Math.abs(modifyNumber) && modifyNumber < 0) return CommonplaceResult.buildErrorNoData("积分不足，扣除失败！");
        //正常扣除或者添加
        int row = accountMapper.modifyScoreByUsername(username, modifyNumber);
        return row > 0 ? CommonplaceResult.buildSuccessNoData("修改成功！") :
                CommonplaceResult.buildErrorNoData("服务器异常");
    }
}
