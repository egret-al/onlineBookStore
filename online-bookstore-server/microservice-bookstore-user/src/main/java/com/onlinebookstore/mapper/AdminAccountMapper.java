package com.onlinebookstore.mapper;

import com.onlinebookstore.entity.userserver.AdminAccount;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author rkc
 * @date 2020/11/1 10:01
 * @version 1.0
 */
@Mapper
public interface AdminAccountMapper {

    int updatePassword(@Param("username") String username, @Param("newPassword") String newPassword);

    AdminAccount getAdminByUsername(String username);

    /**
     * 得到加密的密文
     * @param username username
     * @return 密文
     */
    String getPasswordByUsername(String username);

    /**
     * 根据账号密码查询管理员账号
     * @param username 账号
     * @param password 密码
     * @return 账号实体
     */
    AdminAccount selectAdminByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

    /**
     * 添加管理员账号
     * @param adminAccount 账号实体
     * @return 影响行数
     */
    int addAdminAccount(AdminAccount adminAccount);

    /**
     * 查询所有管理员账号
     * @return 账号实体集合
     */
    List<AdminAccount> selectAllAdminAccount();
}
