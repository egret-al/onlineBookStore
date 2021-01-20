package com.onlinebookstore.service.impl;

import com.onlinebookstore.common.CommonplaceResult;
import com.onlinebookstore.entity.userserver.Account;
import com.onlinebookstore.mapper.AccountMapper;
import com.onlinebookstore.service.SubtractResidueStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 不使用积分的扣费策略
 * @author rkc
 * @version 1.0
 * @date 2021/1/20 11:55
 */
@Service
public class NoUseScoreSubtractResidueStrategy implements SubtractResidueStrategy {

    @Autowired
    private AccountMapper accountMapper;

    @Override
    public CommonplaceResult subtractBalance(Account account, Integer count) {
        //没有使用积分抵扣，直接进行扣除
        return accountMapper.modifyBalance(account.getUsername(), count) > 0 ? CommonplaceResult.buildSuccess(true, "扣费成功") :
                CommonplaceResult.buildError(false, "扣费失败");
    }
}
