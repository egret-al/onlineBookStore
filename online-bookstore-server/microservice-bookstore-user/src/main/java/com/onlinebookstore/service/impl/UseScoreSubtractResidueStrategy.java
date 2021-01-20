package com.onlinebookstore.service.impl;

import com.onlinebookstore.common.CommonplaceResult;
import com.onlinebookstore.entity.userserver.Account;
import com.onlinebookstore.mapper.AccountMapper;
import com.onlinebookstore.service.SubtractResidueStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author rkc
 * @version 1.0
 * @date 2021/1/20 11:56
 */
@Service
public class UseScoreSubtractResidueStrategy implements SubtractResidueStrategy {

    @Autowired
    private AccountMapper accountMapper;

    @Override
    public CommonplaceResult subtractBalance(Account account, Integer count) {
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
    }
}
