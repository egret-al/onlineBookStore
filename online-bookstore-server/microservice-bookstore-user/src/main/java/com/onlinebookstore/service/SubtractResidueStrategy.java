package com.onlinebookstore.service;

import com.onlinebookstore.common.CommonplaceResult;
import com.onlinebookstore.entity.userserver.Account;

/**
 * 扣费策略接口
 * @author rkc
 * @version 1.0
 * @date 2021/1/20 11:53
 */
public interface SubtractResidueStrategy {

    CommonplaceResult subtractBalance(Account account, Integer count);
}
