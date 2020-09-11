package com.onlinebookstore.controller;

import com.onlinebookstore.common.CommonplaceResult;
import com.onlinebookstore.entity.User;
import com.onlinebookstore.mapper.UserMapper;
import com.onlinebookstore.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author rkc
 * @version 1.0
 * @date 2020/9/11 15:57
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 查询全部用户，不包含账户信息
     * @return 数据集
     */
    @GetMapping("pub/selectAll")
    public CommonplaceResult selectAllUser() {
        List<User> users = userService.selectAllUser();
        return CommonplaceResult.buildSuccess(users, "获取成功！");
    }

    /**
     * 根据id查询用户信息+账户信息
     * @param id id
     * @return 数据集
     */
    @GetMapping("pub/getUserWithAccount/{id}")
    public CommonplaceResult getUserContainAccountById(@PathVariable("id") Integer id) {
        return userService.getUserContainAccountById(id);
    }
}
