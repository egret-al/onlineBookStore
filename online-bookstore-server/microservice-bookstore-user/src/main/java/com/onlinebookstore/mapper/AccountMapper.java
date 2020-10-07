package com.onlinebookstore.mapper;

import com.onlinebookstore.entity.userserver.Account;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author rkc
 * @version 1.0
 * @date 2020/9/11 9:51
 */
@Mapper
public interface AccountMapper {

    /**
     * 添加账户
     * @param account 账户实体类
     * @return 影响行数
     */
    int addAccount(Account account);

    /**
     * 根据账号和密码查询账户，通常用于登录业务
     * @param username 账号
     * @param password 密码
     * @return 账户+用户
     */
    Account selectAccountByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

    /**
     * 根据账号查询账号信息
     * @param username 账号
     * @return 账户实体类
     */
    Account selectOneByUsername(String username);

    /**
     * 查询所有的账户，通常由管理员调用
     * @return 账户集合列表
     */
    List<Account> selectAllAccount();

    /**
     * 根据账号修改密码
     * @param username 账号
     * @return 影响行数
     */
    int modifyPasswordByUsername(@Param("username") String username, @Param("password") String password);

    /**
     * 根据账号修改积分
     * @param username 被增加的账号
     * @param additionalScore 将要增加的积分，如果为负数，则为扣除积分
     * @return 影响行数
     */
    int modifyScoreByUsername(@Param("username") String username, @Param("additional") Integer additionalScore);

    /**
     * 根据账号查询全部信息，包括用户信息的关联查询
     * @param username 账号
     * @return 账号信息+用户信息
     */
    Account getAccountContainUserByUsername(String username);

    /**
     * 修改金额
     * @param username 账号
     * @param count 数量
     * @return 影响行数
     */
    int modifyBalance(@Param("username") String username, @Param("count") Integer count);

    /**
     * 抵扣操作，扣除积分和余额
     * @param username 操作账号
     * @param score 扣除的积分
     * @param count 扣除的金额
     * @return 影响行数
     */
    int subtractScoreAndBalance(@Param("username") String username, @Param("score") Integer score, @Param("count") Integer count);
}
