package com.onlinebookstore.service;

import com.onlinebookstore.common.CommonplaceResult;
import com.onlinebookstore.entity.userserver.Address;

/**
 * @author rkc
 * @date 2020/9/12 19:18
 * @version 1.0
 */
public interface AddressService {

    /**
     * 设置默认收货地址
     * @param addressId 收货地址id
     * @param account 账户
     * @return CommonplaceResult
     */
    CommonplaceResult setDefaultAddress(Integer addressId, String account);

    /**
     * 查询默认地址
     * @param username 账号
     * @return 地址
     */
    CommonplaceResult selectDefaultAddress(String username);

    /**
     * 根据id查询地址
     * @param id id
     * @return 地址对象
     */
    CommonplaceResult selectOneById(Integer id);

    /**
     * 根据账号查询所有地址
     * @param username 账号
     * @return 地址集合
     */
    CommonplaceResult selectAddressByAccountUsername(String username);

    /**
     * 查询所有地址
     * @return 地址集合
     */
    CommonplaceResult selectAllAddress();

    /**
     * 添加地址
     * @param addr 地址对象
     * @return 影响行数
     */
    CommonplaceResult addAddress(Address addr);

    /**
     * 根据id删除地址
     * @param id id
     * @return 影响行数
     */
    CommonplaceResult deleteAddressById(Integer id);

    /**
     * 更新地址对象
     * @param addr 新地址信息
     * @return 影响行数
     */
    CommonplaceResult updateAddress(Address addr);

    /**
     * 根据id获取地址+账户
     * @return 地址+账户
     */
    CommonplaceResult getAddressWithAccountById(Integer id);
}
