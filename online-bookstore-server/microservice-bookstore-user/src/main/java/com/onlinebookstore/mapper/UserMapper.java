package com.onlinebookstore.mapper;

import com.onlinebookstore.entity.userserver.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author rkc
 * @version 1.0
 * @date 2020/9/11 11:02
 */
@Mapper
public interface UserMapper {

    /**
     * 修改手机号
     * @param phone 手机号
     * @param username 账号
     * @return 影响行数
     */
    int modifyPhone(@Param("phone") String phone, @Param("username") String username);

    /**
     * 修改昵称
     * @param nickname 昵称
     * @param username 账号
     * @return 影响行数
     */
    int modifyNickname(@Param("nickname") String nickname, @Param("username") String username);

    /**
     * 更改性别
     * @param username 账号
     * @param sex 性别
     * @return 影响行数
     */
    int modifySex(@Param("username") String username, @Param("sex") String sex);

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
