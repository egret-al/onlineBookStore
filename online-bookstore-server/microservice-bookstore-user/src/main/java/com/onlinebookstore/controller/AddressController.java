package com.onlinebookstore.controller;

import com.onlinebookstore.annotation.JsonObject;
import com.onlinebookstore.common.CommonplaceResult;
import com.onlinebookstore.entity.userserver.Address;
import com.onlinebookstore.entity.userserver.User;
import com.onlinebookstore.service.AddressService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

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
     * @param username 账户
     * @return CommonplaceResult
     */
    @PostMapping("setDefaultAddress")
    public CommonplaceResult setDefaultAddress(@JsonObject("addressId") int addressId, @JsonObject("username") String username) {
        return addressService.setDefaultAddress(addressId, username);
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
     * @param username username
     */
    @PostMapping("selectByAccount")
    public CommonplaceResult selectByAccount(@JsonObject("username") String username) {
        return addressService.selectAddressByAccountUsername(username);
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
     * 根据id删除地址，如果是默认地址则删除失败
     * @param addressId addressId
     */
    @PostMapping("deleteAddress")
    public CommonplaceResult deleteAddress(@JsonObject("username") String username, @JsonObject("addressId") Integer addressId) {
        return addressService.deleteAddressById(username, addressId);
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
