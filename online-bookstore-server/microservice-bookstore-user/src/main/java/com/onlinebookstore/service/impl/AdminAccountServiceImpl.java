package com.onlinebookstore.service.impl;

import com.onlinebookstore.common.CommonplaceResult;
import com.onlinebookstore.entity.userserver.Account;
import com.onlinebookstore.entity.userserver.AdminAccount;
import com.onlinebookstore.mapper.AdminAccountMapper;
import com.onlinebookstore.service.AdminAccountService;
import com.onlinebookstore.util.JwtUtil;
import com.onlinebookstore.util.userutil.UserConstantPool;
import lombok.extern.slf4j.Slf4j;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author rkc
 * @date 2020/11/1 10:25
 * @version 1.0
 */
@Slf4j
@Service
public class AdminAccountServiceImpl implements AdminAccountService {

    @Resource
    private AdminAccountMapper adminAccountMapper;

    @Autowired
    private StringEncryptor encryptor;

    @Override
    public CommonplaceResult updatePassword(AdminAccount adminAccount, String newPassword) {
        String ciphertext = adminAccountMapper.getPasswordByUsername(adminAccount.getUsername());
        log.info(encryptor.decrypt(ciphertext));
        if (encryptor.decrypt(ciphertext).equals(adminAccount.getPassword())) {
            //解密匹配，能够正常修改，进行加密处理
            newPassword = encryptor.encrypt(newPassword);
            int row = adminAccountMapper.updatePassword(adminAccount.getUsername(), newPassword);
            return row > 0 ? CommonplaceResult.buildSuccessNoData("修改成功！") : CommonplaceResult.buildErrorNoData("修改失败！");
        }
        return CommonplaceResult.buildErrorNoData("修改失败！密码错误！");
    }

    @Override
    public CommonplaceResult getAdminByUsername(String username) {
        AdminAccount admin = adminAccountMapper.getAdminByUsername(username);
        return ObjectUtils.isEmpty(admin) ? CommonplaceResult.buildErrorNoData("没有该账号信息") : CommonplaceResult.buildSuccessNoMessage(admin);
    }

    /**
     * 登录，逻辑和正常登录逻辑类似，都采用JWT进行鉴权
     * @param username 账号
     * @param password 密码
     */
    @Override
    public CommonplaceResult selectAdminByUsernameAndPassword(String username, String password) {
        //得到密文
        String ciphertext = adminAccountMapper.getPasswordByUsername(username);
        if (StringUtils.isEmpty(ciphertext)) return CommonplaceResult.buildErrorNoData("登录失败");
        if (encryptor.decrypt(ciphertext).equals(password)) {
            //解密后和明文能够匹配，使用当前密文进行再次查询
            password = ciphertext;
            AdminAccount adminAccount = adminAccountMapper.selectAdminByUsernameAndPassword(username, password);
            if (ObjectUtils.isEmpty(adminAccount)) {
                return CommonplaceResult.buildErrorNoData("账号或者密码错误！");
            }
            //登录成功，生成jwt令牌，返回到客户端
            Map<String, Object> info = new HashMap<>(2);
            //基于工具类生成jwt token
            String token = JwtUtil.createJWT(UUID.randomUUID().toString(), adminAccount.getUsername(), null);
            //将token携带回去，每次发起请求都需要在请求头中携带token，便于网关进行拦截验证
            info.put(UserConstantPool.TOKEN, token);
            //登录成功，将封装好的账户+用户对象返回，避免前端再次请求获取用户信息数据
            info.put(UserConstantPool.ACCOUNT, adminAccount);
            return CommonplaceResult.buildSuccess(info, "登录成功！");
        }
        return CommonplaceResult.buildErrorNoData("账号或者密码错误！");
    }

    /**
     * 添加账号
     * @param adminAccount 账号实体
     */
    @Override
    public CommonplaceResult addAdminAccount(AdminAccount adminAccount) {
        AdminAccount admin = adminAccountMapper.getAdminByUsername(adminAccount.getUsername());
        if (ObjectUtils.isEmpty(admin)) {
            //加密
            adminAccount.setPassword(encryptor.encrypt(adminAccount.getPassword()));
            return adminAccountMapper.addAdminAccount(adminAccount) > 0 ? CommonplaceResult.buildSuccessNoData("添加成功") : CommonplaceResult.buildErrorNoData("添加失败");
        }
        return CommonplaceResult.buildErrorNoData("该账号已存在！");
    }

    /**
     * 查询所有
     */
    @Override
    public CommonplaceResult selectAllAdminAccount() {
        List<AdminAccount> adminAccounts = adminAccountMapper.selectAllAdminAccount();
        return adminAccounts.size() == 0 ? CommonplaceResult.buildErrorNoData("没有信息") : CommonplaceResult.buildSuccessNoMessage(adminAccounts);
    }
}
