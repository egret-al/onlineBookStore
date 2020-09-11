package com.onlinebookstore.service.impl;

import com.onlinebookstore.common.CommonplaceResult;
import com.onlinebookstore.entity.User;
import com.onlinebookstore.mapper.UserMapper;
import com.onlinebookstore.service.UserService;
import org.springframework.stereotype.Service;

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
     * 添加用户，通常在创建账户时在同一个事务中进行操作
     * @param user 用户信息类
     * @return 影响行数
     */
    @Override
    public int addUser(User user) {
        return userMapper.addUser(user);
    }

    /**
     * 根据登录账号查询用户信息
     * @param accountUsername 登录账号
     * @return 用户信息类
     */
    @Override
    public User selectUserByUsername(String accountUsername) {
        return userMapper.selectUserByUsername(accountUsername);
    }

    /**
     * 查询所有用户信息
     * @return 用户信息集合
     */
    @Override
    public List<User> selectAllUser() {
        return userMapper.selectAllUser();
    }

    /**
     * 根据id修改用户信息
     * @param user id
     * @return 影响行数
     */
    @Override
    public int modifyUserById(User user) {
        return userMapper.modifyUserById(user);
    }

    /**
     * 根据id查询用户全部信息，包括级联映射的账户信息
     * @param id id
     * @return 用户信息+账号信息
     */
    @Override
    public CommonplaceResult getUserContainAccountById(Integer id) {
        User userWithAccount = userMapper.getUserContainAccountById(id);
        return userWithAccount == null ? CommonplaceResult.buildErrorNoData("查询失败！") :
                CommonplaceResult.buildSuccess(userWithAccount, "查询成功");
    }
}
