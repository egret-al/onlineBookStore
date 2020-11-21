package com.onlinebookstore.service.impl;

import com.onlinebookstore.common.CommonplaceResult;
import com.onlinebookstore.entity.userserver.Address;
import com.onlinebookstore.mapper.AddressMapper;
import com.onlinebookstore.service.AddressService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author rkc
 * @date 2020/9/12 19:19
 * @version 1.0
 */
@Slf4j
@Service
public class AddressServiceImpl implements AddressService {
    @Resource
    private AddressMapper addressMapper;

    /**
     * 设置默认收货地址
     * @param addressId 收货地址id
     * @param account 账户
     * @return CommonplaceResult
     */
    @Override
    public CommonplaceResult setDefaultAddress(Integer addressId, String account) {
        return addressMapper.setDefaultAddress(addressId, account) > 0 ? CommonplaceResult.buildSuccessNoData("设置成功！")
                : CommonplaceResult.buildErrorNoData("设置失败！");
    }

    /**
     * 查询默认地址
     * @param username 账号
     * @return 地址
     */
    @Override
    public CommonplaceResult selectDefaultAddress(String username) {
        Address address = addressMapper.selectDefaultAddress(username);
        if (ObjectUtils.isEmpty(address)) {
            return CommonplaceResult.buildErrorNoData("没有默认地址");
        }
        return CommonplaceResult.buildSuccessNoMessage(address);
    }

    /**
     * 根据id查询
     * @param id id
     * @return 结果集
     */
    @Override
    public CommonplaceResult selectOneById(Integer id) {
        Address address = addressMapper.selectOneById(id);
        return ObjectUtils.isEmpty(address) ? CommonplaceResult.buildErrorNoData("没有该数据！") :
                CommonplaceResult.buildSuccess(address, "查询成功");
    }

    /**
     * 根据账号查询地址信息
     * @param username 账号
     * @return 结果集
     */
    @Override
    public CommonplaceResult selectAddressByAccountUsername(String username) {
        List<Address> addresses = addressMapper.selectAddressByAccountUsername(username);
        return addresses.size() == 0 ? CommonplaceResult.buildError(addresses, "没有任何地址信息！") :
                CommonplaceResult.buildSuccess(addresses, "查询成功！");
    }

    /**
     * 查询所有地址
     * @return 结果集
     */
    @Override
    public CommonplaceResult selectAllAddress() {
        List<Address> addresses = addressMapper.selectAllAddress();
        return CommonplaceResult.buildSuccess(addresses, "查询成功！");
    }

    /**
     * 添加地址
     * @param addr 地址对象
     * @return 结果集
     */
    @Override
    public CommonplaceResult addAddress(Address addr) {
        return addressMapper.addAddress(addr) > 0 ? CommonplaceResult.buildSuccessNoData("添加成功") :
                CommonplaceResult.buildErrorNoData("添加失败！");
    }

    /**
     * 根据id删除地址
     * @param id id
     * @return 结果集
     */
    @Override
    public CommonplaceResult deleteAddressById(Integer id) {
        return addressMapper.deleteAddressById(id) > 0 ? CommonplaceResult.buildSuccessNoData("删除成功") :
                CommonplaceResult.buildErrorNoData("删除失败！");
    }

    /**
     * 更新地址
     * @param addr 新地址信息
     * @return 结果集
     */
    @Override
    public CommonplaceResult updateAddress(Address addr) {
        return addressMapper.updateAddress(addr) > 0 ? CommonplaceResult.buildSuccessNoData("更新成功") :
                CommonplaceResult.buildErrorNoData("更新失败！");
    }

    /**
     * 根据id获取地址+账户
     * @param id id
     * @return 结果集
     */
    @Override
    public CommonplaceResult getAddressWithAccountById(Integer id) {
        Address address = addressMapper.getAddressWithAccountById(id);
        return ObjectUtils.isEmpty(address) ? CommonplaceResult.buildErrorNoData("没有该数据！") :
                CommonplaceResult.buildSuccess(address, "查询成功");
    }
}
