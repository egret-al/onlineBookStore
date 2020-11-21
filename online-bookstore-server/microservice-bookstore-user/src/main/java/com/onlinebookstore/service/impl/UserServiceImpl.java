package com.onlinebookstore.service.impl;

import com.onlinebookstore.common.CommonplaceResult;
import com.onlinebookstore.entity.userserver.User;
import com.onlinebookstore.mapper.UserMapper;
import com.onlinebookstore.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author rkc
 * @version 1.0
 * @date 2020/9/11 15:44
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    /**
     * 修改手机
     * @param user 用户
     * @return CommonplaceResult
     */
    @Override
    public CommonplaceResult modifyPhone(User user) {
        return userMapper.modifyPhone(user.getPhone(), user.getAccountUsername()) > 0 ? CommonplaceResult.buildSuccessNoData("修改成功")
                : CommonplaceResult.buildErrorNoData("修改失败");
    }

    /**
     * 修改昵称
     * @param user 用户
     * @return CommonplaceResult
     */
    @Override
    public CommonplaceResult modifyNickname(User user) {
        return userMapper.modifyNickname(user.getNickname(), user.getAccountUsername()) > 0 ? CommonplaceResult.buildSuccessNoData("修改成功")
                : CommonplaceResult.buildErrorNoData("修改失败");
    }

    /**
     * 修改性别
     * @param user 用户
     * @return 是否成功
     */
    @Override
    public CommonplaceResult modifySex(User user) {
        return userMapper.modifySex(user.getAccountUsername(), user.getSex()) > 1 ? CommonplaceResult.buildSuccessNoData("修改成功")
                : CommonplaceResult.buildErrorNoData("修改失败");
    }

    /**
     * 添加用户，通常在创建账户时在同一个事务中进行操作
     * @param user 用户信息类
     * @return 影响行数
     */
    @Override
    public CommonplaceResult addUser(User user) {
        return userMapper.addUser(user) > 0 ? CommonplaceResult.buildSuccessNoData("添加成功") :
                CommonplaceResult.buildErrorNoData("发生异常");
    }

    /**
     * 根据登录账号查询用户信息
     * @param accountUsername 登录账号
     * @return 用户信息类
     */
    @Override
    public CommonplaceResult selectUserByUsername(String accountUsername) {
        User user = userMapper.selectUserByUsername(accountUsername);
        if (ObjectUtils.isEmpty(user)) {
            return CommonplaceResult.buildErrorNoData("没有该信息");
        }
        return CommonplaceResult.buildSuccess(user, "查询成功");
    }

    /**
     * 查询所有用户信息
     * @return 用户信息集合
     */
    @Override
    public CommonplaceResult selectAllUser() {
        List<User> users = userMapper.selectAllUser();
        return users.size() > 0 ? CommonplaceResult.buildSuccess(users, "查询成功") :
                CommonplaceResult.buildError(users, "没有数据");
    }

    /**
     * 根据id修改用户信息
     * @param user id
     * @return 影响行数
     */
    @Override
    public CommonplaceResult modifyUserById(User user) {
        return userMapper.modifyUserById(user) > 0 ? CommonplaceResult.buildSuccessNoData("修改成功") :
                CommonplaceResult.buildErrorNoData("修改失败");
    }

    /**
     * 根据id查询用户全部信息，包括级联映射的账户信息
     * @param id id
     * @return 用户信息+账号信息
     */
    @Override
    public CommonplaceResult getUserContainAccountById(Integer id) {
        User userWithAccount = userMapper.getUserContainAccountById(id);
        return ObjectUtils.isEmpty(userWithAccount) ? CommonplaceResult.buildErrorNoData("查询失败！") :
                CommonplaceResult.buildSuccess(userWithAccount, "查询成功");
    }
}
