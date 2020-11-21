package com.onlinebookstore.controller;

import com.onlinebookstore.common.CommonplaceResult;
import com.onlinebookstore.entity.userserver.Address;
import com.onlinebookstore.entity.userserver.User;
import com.onlinebookstore.service.AddressService;
import com.onlinebookstore.util.userutil.UserConstantPool;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author rkc
 * @date 2020/9/16 8:27
 * @version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/address/pri")
public class AddressController {

    @Resource
    private AddressService addressService;

    /**
     * 设置默认收货地址
     * @param addressId 收货地址id
     * @param account 账户
     * @return CommonplaceResult
     */
    @GetMapping("setDefaultAddress/{var1}/{var2}")
    public CommonplaceResult setDefaultAddress(@PathVariable("var1") int addressId, @PathVariable("var2") String account) {
        return addressService.setDefaultAddress(addressId, account);
    }

    /**
     * 根据用户获取默认地址
     * @param user user
     * @return CommonplaceResult
     */
    @PostMapping("selectDefaultAddress")
    public CommonplaceResult selectDefaultAddress(@RequestBody User user) {
        if (StringUtils.isEmpty(user.getAccountUsername())) return CommonplaceResult.buildErrorNoData("没有默认地址！");
        return addressService.selectDefaultAddress(user.getAccountUsername());
    }

    /**
     * 根据地址的id查询地址信息
     * @param id id
     */
    @GetMapping("selectOne/{id}")
    public CommonplaceResult selectOneById(@PathVariable("id") Integer id) {
        return addressService.selectOneById(id);
    }

    /**
     * 根据账号查询所有绑定地址
     * @param account account
     */
    @GetMapping("selectByAccount/{account}")
    public CommonplaceResult selectByAccount(@PathVariable("account") String account) {
        return addressService.selectAddressByAccountUsername(account);
    }

    /**
     * 添加地址
     * @param address 地址对象
     */
    @PostMapping("addAddress")
    public CommonplaceResult addAddress(@RequestBody Address address) {
        return addressService.addAddress(address);
    }

    /**
     * 根据id删除地址
     * @param id id
     */
    @PostMapping("deleteAddress/{id}")
    public CommonplaceResult deleteAddress(@PathVariable("id") Integer id) {
        return addressService.deleteAddressById(id);
    }

    /**
     * 更新地址
     * @param addr 地址信息
     */
    @PostMapping("updateAddress")
    public CommonplaceResult updateAddress(@RequestBody Address addr) {
        log.info(String.valueOf(addr));
        return addressService.updateAddress(addr);
    }
}
