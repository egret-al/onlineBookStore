package com.onlinebookstore.service.impl;

import com.alibaba.nacos.common.util.UuidUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onlinebookstore.common.CommonplaceResult;
import com.onlinebookstore.entity.Account;
import com.onlinebookstore.entity.User;
import com.onlinebookstore.entity.otherserver.Book;
import com.onlinebookstore.entity.otherserver.BookStorage;
import com.onlinebookstore.entity.otherserver.Order;
import com.onlinebookstore.exception.IllegalOperateException;
import com.onlinebookstore.exception.RemainInsufficientException;
import com.onlinebookstore.mapper.AccountMapper;
import com.onlinebookstore.mapper.UserMapper;
import com.onlinebookstore.service.AccountService;
import com.onlinebookstore.service.BookService;
import com.onlinebookstore.service.OrderService;
import com.onlinebookstore.util.JsonUtil;
import com.onlinebookstore.util.JwtUtil;
import com.onlinebookstore.util.UserConstantPool;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author rkc
 * @version 1.0
 * @date 2020/9/11 15:43
 */
@Slf4j
@Service
public class AccountServiceImpl implements AccountService {

    @Resource
    private AccountMapper accountMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private BookService bookService;

    @Resource
    private OrderService orderService;

    @Resource
    private JsonUtil jsonUtil;

    /**
     * 添加账户，首先查询该账户是否已经添加，如果已经添加则返回，
     * 否则继续进行，中途有任何的异常都会结束该事务操作
     * @param account 账户实体类
     * @return 影响行数
     */
    @Override
    @Transactional
    public CommonplaceResult addAccount(Account account, User User) {
        //如果查询是空，说明该账号没有被注册，可以进行注册操作，否则返回
        if (ObjectUtils.isEmpty(accountMapper.selectOneByUsername(account.getUsername()))) {
            //账号注册
            accountMapper.addAccount(account);
            //用户信息注册
            userMapper.addUser(User);
            return CommonplaceResult.buildSuccessNoData("注册成功！");
        }
        return CommonplaceResult.buildErrorNoData("注册失败！禁止重复注册");
    }

    /**
     * JWT单点登录
     * @param username 账号
     * @param password 密码
     * @return 账户实体类
     */
    @Override
    public CommonplaceResult selectAccountByUsernameAndPassword(String username, String password) {
        Account account = accountMapper.selectAccountByUsernameAndPassword(username, password);
        if (ObjectUtils.isEmpty(account)) {
            return CommonplaceResult.buildErrorNoData("账号或者密码错误！");
        }
        //登录成功，生成jwt令牌，返回到客户端
        Map<String, Object> info = new HashMap<>();
        //基于工具类生成jwt token
        String token = JwtUtil.createJWT(UUID.randomUUID().toString(), account.getUsername(), null);
        //将token携带回去，每次发起请求都需要在请求头中携带token，便于网关进行拦截验证
        info.put(UserConstantPool.TOKEN, token);
        //登录成功，将封装好的账户+用户对象返回，避免前端再次请求获取用户信息数据
        info.put(UserConstantPool.ACCOUNT, account);
        return CommonplaceResult.buildSuccess(info, "登录成功！");
    }

    /**
     * 查询所有的账户，通常由管理员调用
     * @return 账户集合列表
     */
    @Override
    public List<Account> selectAllAccount() {
        CommonplaceResult commonplaceResult = bookService.selectAllInfo();
        System.out.println(commonplaceResult.getData());
        return accountMapper.selectAllAccount();
    }

    /**
     * 根据账号修改密码
     * @param username 账号
     * @param oldPassword 旧密码
     * @param password 新密码
     * @return 影响行数
     */
    @Override
    public CommonplaceResult modifyPasswordByUsername(String username, String oldPassword, String password) {
        //验证账号密码是否正确，如果正确则直接进行修改操作，否则修改失败
        Account account = accountMapper.selectAccountByUsernameAndPassword(username, oldPassword);
        if (account == null) {
            //密码错误，匹配失败
            log.warn(username + "密码错误，修改失败！");
            return CommonplaceResult.buildErrorNoData("修改失败，密码错误！");
        }
        //匹配成功，可以进行修改操作
        int row = accountMapper.modifyPasswordByUsername(username, password);
        return row > 0 ? CommonplaceResult.buildSuccessNoData("修改成功！") : CommonplaceResult.buildErrorNoData("密码不能和旧密码相同！");
    }

    /**
     * 根据账号查询全部信息，包括用户信息的关联查询
     * @param username 账号
     * @return 账号信息+用户信息
     */
    @Override
    public CommonplaceResult getAccountContainUserByUsername(String username) {
        Account accountWithUser = accountMapper.getAccountContainUserByUsername(username);
        return accountWithUser == null ? CommonplaceResult.buildErrorNoData("获取失败，不存在该用户！") :
                CommonplaceResult.buildSuccess(accountWithUser, "获取成功！");
    }

    /**
     * 需要进行逻辑处理，如果查询的账号的积分为0且modifyNumber为负数，
     * 又或者积分数不够扣除，均为扣除失败
     * @param username 账号
     * @param modifyNumber 修改的积分
     * @return 是否修改成功
     */
    @Override
    @Transactional
    public CommonplaceResult modifyScore(String username, Integer modifyNumber) {
        //查询积分信息
        Account account = accountMapper.selectOneByUsername(username);
        if (account == null) {
            return CommonplaceResult.buildErrorNoData("账号异常！");
        }
        int score = account.getScore();
        //积分为0，不能进行扣除，非法操作
        if (score == 0 && modifyNumber < 0) {
            throw new IllegalOperateException();
        }
        //积分不足扣除
        if (score < Math.abs(modifyNumber) && modifyNumber < 0) {
            return CommonplaceResult.buildErrorNoData("积分不足，扣除失败！");
        }
        //正常扣除或者添加
        int row = accountMapper.modifyScoreByUsername(username, modifyNumber);
        return row > 0 ? CommonplaceResult.buildSuccessNoData("修改成功！") :
                CommonplaceResult.buildErrorNoData("服务器异常");
    }

    /**
     * 购买图书，需要使用全局事务处理
     * TODO 后续整合seata和调用订单微服务
     * 流程：检查用户余额 -> 调用订单微服务创建订单 -> 扣除用户余额 -> 调用图书微服务扣除库存 -> 修改订单状态为完成订单
     * @param bookId 图书id
     * @param count 数量
     * @param username 购买账号
     * @param useScore 是否使用积分
     */
    @Override
    @SuppressWarnings("all")
//    @GlobalTransaction
    public CommonplaceResult purchaseBook(Integer bookId, Integer count, String username, Boolean useScore) {
        //调用图书微服务，得到图书信息+库存信息，通过断点调试可以得知，此时得到的对象是LinkedHashMap
        Object data =  bookService.selectBookAndStorageByBookId(bookId).getData();
        try {
            //转化为Book实体类
            Book book = jsonUtil.mapToBean((Map) data, Book.class);
            log.info(String.valueOf(book));
            BookStorage bookStorage = book.getBookStorage();
            if (count > bookStorage.getResidueCount()) {
                return CommonplaceResult.buildErrorNoData("库存不足，购买失败！");
            }
            //得到需要支付的金额
            int money = book.getPrice() * count;
            int score = money / 10;
            String orderContent = "下单了" + count + "本" + book.getBookName();
            String serialName = UuidUtils.generateUuid().substring(0, 30);
            //构建一个订单并且指定基本数据信息
            Order order = new Order(serialName, bookId, username, orderContent, count, money, score,
                    new Date(), new Date(), new Date(), new Date(), 0);
            //1、开始创建订单
            orderService.insertOrder(order);

            //2、扣除余额
            CommonplaceResult commonplaceResult = this.modifyBalance(username, -money, useScore);
            if ((boolean) commonplaceResult.getData()) {
                //扣除成功，则进行下一步，扣除库存
                Map<String, Integer> pojo = new HashMap<>();
                pojo.put("id", bookStorage.getId());
                pojo.put("count", count);
                //3、扣除库存
                CommonplaceResult storageCalledResult = bookService.subtractStorageById(pojo);
                if ((boolean) storageCalledResult.getData()) {
                    //TODO 修改订单状态
                    //4、修改订单状态
                    order.setOrderPaymentStatus(1);
                    orderService.updateOrder(order);
                    this.modifyScore(username, score);
                    return CommonplaceResult.buildSuccessNoData("操作成功！");
                } else {
                    throw new RuntimeException("库存扣除失败");
                }
            } else {
//                throw new RemainInsufficientException();
                return CommonplaceResult.buildErrorNoData("余额不足！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return CommonplaceResult.buildSuccessNoData("服务器异常！");
    }

    /**
     * 修改余额
     * @param username 账号
     * @param count 修改的数量，如果为负数则为扣除，否则为充值
     * @param useScore 是否使用抵扣，只有在count为负数时才有效，count为正数时为null
     * @return 操作是否成功
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public CommonplaceResult modifyBalance(String username, int count, boolean useScore) {
        if (StringUtils.isEmpty(username)) {
            return CommonplaceResult.buildError(false, "非法账号！");
        }
        Account account = accountMapper.selectOneByUsername(username);
        log.info("修改余额操作：" + account);
        if (ObjectUtils.isEmpty(account)) {
            return CommonplaceResult.buildError(false, "非法操作！账号不存在");
        }
        if (count > 0) {
            //充值操作
            return addBalance(username, count);
        } else if (count < 0) {
            //消费操作，首先进行余额判断
            if (account.getBalance() < Math.abs(count)) {
                return CommonplaceResult.buildError(false, "余额不足！");
            }
            return subtractBalance(account, count, useScore);
        }
        return CommonplaceResult.buildError(false, "修改数量不能为0！");
    }

    /**
     * 扣除余额逻辑，判断是否使用优惠券，如果使用积分，如果使用，则进行相应的抵扣，10积分=1余额，且最多只能抵扣10元
     * @param account 操作账号
     * @param count 操作数量
     * @param useScore 是否使用积分
     */
    private CommonplaceResult subtractBalance(Account account, Integer count, Boolean useScore) {
        if (useScore) {
            //使用了积分抵扣，需要判断积分是否为0，如果为0，则提示不能抵扣，否则进行相应的抵扣操作3
            int score = account.getScore();
            if (score == 0) {
                //直接进行扣除
                return accountMapper.modifyBalance(account.getUsername(), count) > 0 ? CommonplaceResult.buildSuccess(true, "扣费成功") :
                        CommonplaceResult.buildError(false, "扣费失败");
            } else if (score > 0) {
                //balance = balance - count + score / 10
                int subScore = 100;
                if (score > 100) {
                    //积分太多，但也只能抵扣10元
                    count = count + subScore / 10;
                    return accountMapper.subtractScoreAndBalance(account.getUsername(), subScore, count) > 0 ? CommonplaceResult.buildSuccess(true, "购买成功")
                            : CommonplaceResult.buildError(false, "网络异常");
                } else {
                    //积分并不会满足抵扣过多，直接进行扣除，且把积分归零
//                    accountMapper.subtractScoreAndBalance(account.getUsername(), score / 10, count);
                    count = count + score / 10;
                    return accountMapper.subtractScoreAndBalance(account.getUsername(), score, count) > 0 ? CommonplaceResult.buildSuccess(true, "购买成功")
                            : CommonplaceResult.buildError(false, "网络异常");
                }
            }
            return CommonplaceResult.buildError(false, "操作异常");
        } else {
            //没有使用积分抵扣，直接进行扣除
            return accountMapper.modifyBalance(account.getUsername(), count) > 0 ? CommonplaceResult.buildSuccess(true, "扣费成功") :
                    CommonplaceResult.buildError(false, "扣费失败");
        }
    }

    /**
     * 充值金额
     * @param username 操作账号
     * @param count 充值数量
     */
    private CommonplaceResult addBalance(String username, Integer count) {
        return accountMapper.modifyBalance(username, count) > 0 ? CommonplaceResult.buildSuccessNoData("充值成功") :
                CommonplaceResult.buildErrorNoData("充值失败！");
    }
}
