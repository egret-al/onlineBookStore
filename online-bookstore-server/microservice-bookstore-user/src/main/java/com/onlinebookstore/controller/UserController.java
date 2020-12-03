package com.onlinebookstore.controller;

import com.onlinebookstore.common.CommonplaceResult;
import com.onlinebookstore.entity.userserver.Account;
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
     * 修改手机
     * @param user user
     * @return CommonplaceResult
     */
    @PostMapping("pri/modifyPhone")
    public CommonplaceResult modifyPhone(@RequestBody User user) {
        if (StringUtils.isEmpty(user.getAccountUsername()) || StringUtils.isEmpty(user.getPhone())) return CommonplaceResult.buildErrorNoData("数据不全");
        return userService.modifyPhone(user);
    }

    /**
     * 修改昵称
     * @param user user
     * @return CommonplaceResult
     */
    @PostMapping("pri/modifyNickname")
    public CommonplaceResult modifyNickname(@RequestBody User user) {
        if (StringUtils.isEmpty(user.getAccountUsername()) || StringUtils.isEmpty(user.getNickname())) return CommonplaceResult.buildErrorNoData("数据不全");
        return userService.modifyNickname(user);
    }

    /**
     * 修改性别
     * @param user user
     * @return CommonplaceResult
     */
    @PostMapping("pri/modifySex")
    public CommonplaceResult modifySex(@RequestBody User user) {
        if (StringUtils.isEmpty(user.getAccountUsername()) || StringUtils.isEmpty(user.getSex())) return CommonplaceResult.buildErrorNoData("数据不全");
        return userService.modifySex(user);
    }

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
