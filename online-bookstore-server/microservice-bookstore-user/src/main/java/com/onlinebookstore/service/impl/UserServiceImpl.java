package com.onlinebookstore.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.onlinebookstore.common.CommonplaceResult;
import com.onlinebookstore.entity.userserver.User;
import com.onlinebookstore.mapper.UserMapper;
import com.onlinebookstore.service.UserService;
import com.onlinebookstore.util.AliyunSmsUtil;
import com.onlinebookstore.util.RedisUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

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
    private static final String resetPhoneBlacklist = "resetPhone:blacklist";
    private static final String resetPhoneSentCode = "resetPhone:sentCode";

    @Resource
    private RedisUtils redisUtils;

    /**
     * 发送验证码
     * @param user 用户
     * @return CommonplaceResult
     */
    @Override
    public CommonplaceResult modifyPhoneSendCode(User user) {
        String phone = user.getPhone();
        if (ObjectUtils.isEmpty(redisUtils.get(resetPhoneBlacklist + user.getAccountUsername()))) {
            String code = AliyunSmsUtil.getCode();
            JSONObject jsonObject = AliyunSmsUtil.sendSms(phone, code);
            if (jsonObject.getInteger("code") == AliyunSmsUtil.SEND_SUCCESS) {
                //验证码存入redis，设置3分钟时间
                redisUtils.set(resetPhoneSentCode + user.getAccountUsername(), code, 180);
                //加入黑名单，2分钟内不能再次发送
                redisUtils.set(resetPhoneBlacklist + user.getAccountUsername(), true, 120);
                return CommonplaceResult.buildSuccess(jsonObject, "发送成功！");
            }
            return CommonplaceResult.buildError(jsonObject, "发送失败！");
        }
        return CommonplaceResult.buildErrorNoData("发送次数过多，发送失败！");
    }

    /**
     * 修改手机
     * @param user 用户
     * @param code 验证码
     * @return CommonplaceResult
     */
    @Override
    public CommonplaceResult modifyPhone(User user, String code) {
        String correctCode = String.valueOf(redisUtils.get(resetPhoneSentCode + user.getAccountUsername()));
        if (StringUtils.isEmpty(correctCode)) return CommonplaceResult.buildErrorNoData("验证码已过期或不存在！");
        if (code.equals(correctCode)) {
            redisUtils.del(resetPhoneSentCode + user.getAccountUsername());
            return userMapper.modifyPhone(user.getPhone(), user.getAccountUsername()) > 0 ? CommonplaceResult.buildSuccessNoData("修改成功")
                    : CommonplaceResult.buildErrorNoData("修改失败");
        }
        return CommonplaceResult.buildErrorNoData("验证码输入错误！");
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
