package com.onlinebookstore.controller;

import com.onlinebookstore.common.CommonplaceResult;
import com.onlinebookstore.entity.userserver.User;
import com.onlinebookstore.service.UserService;
import com.onlinebookstore.util.userutil.UserConstantPool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

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
        return userService.selectAllUser();
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

    /**
     * 根据账号查询用户信息
     * 数据格式
     * {
     *     'username': '1234567890'
     * }
     * @param usernameMap 账号
     * @return json数据集
     */
    @PostMapping("pri/getUserInfo")
    public CommonplaceResult getUserInfoByUsername(@RequestBody Map<String, String> usernameMap) {
        String username = usernameMap.get(UserConstantPool.USERNAME);
        if (StringUtils.isEmpty(username)) {
            return CommonplaceResult.buildErrorNoData("非法数据！");
        }
        return userService.selectUserByUsername(username);
    }

    /**
     * 添加用户
     * 数据格式
     * {
     *     'account_username': 'xxx',
     *     'nick': 'xxx',
     *     'birthday': 'xxxx',
     *     'sex': 'xx',
     *     'phone': 'xxxxxxxxxx'
     * }
     * @param user 用户信息
     * @return json
     */
    @PostMapping("pub/addUser")
    public CommonplaceResult addUser(@RequestBody User user) {
        return userService.addUser(user);
    }
}
