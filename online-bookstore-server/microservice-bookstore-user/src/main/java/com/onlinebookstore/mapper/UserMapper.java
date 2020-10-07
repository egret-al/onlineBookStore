package com.onlinebookstore.mapper;

import com.onlinebookstore.entity.userserver.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author rkc
 * @version 1.0
 * @date 2020/9/11 11:02
 */
@Mapper
public interface UserMapper {

    /**
     * 添加用户，通常在创建账户时在同一个事务中进行操作
     * @param user 用户信息类
     * @return 影响行数
     */
    int addUser(User user);

    /**
     * 根据登录账号查询用户信息
     * @param accountUsername 登录账号
     * @return 用户信息类
     */
    User selectUserByUsername(String accountUsername);

    /**
     * 查询所有用户信息
     * @return 用户信息集合
     */
    List<User> selectAllUser();

    /**
     * 根据id修改用户信息
     * @param user id
     * @return 影响行数
     */
    int modifyUserById(User user);

    /**
     * 根据id查询用户全部信息，包括关联查询的账户信息
     * @param id id
     * @return 用户信息+账号信息
     */
    User getUserContainAccountById(Integer id);
}
