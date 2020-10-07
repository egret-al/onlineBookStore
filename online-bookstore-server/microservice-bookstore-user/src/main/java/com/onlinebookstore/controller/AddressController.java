package com.onlinebookstore.controller;

import com.onlinebookstore.common.CommonplaceResult;
import com.onlinebookstore.entity.userserver.Address;
import com.onlinebookstore.service.AddressService;
import com.onlinebookstore.util.userutil.UserConstantPool;
import lombok.extern.slf4j.Slf4j;
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
     * 根据地址的id查询地址信息
     * @param id id
     */
    @GetMapping("selectOne/{id}")
    public CommonplaceResult selectOneById(@PathVariable("id") Integer id) {
        return addressService.selectOneById(id);
    }

    /**
     * 根据账号查询所有绑定地址
     * @param pojo map
     */
    @PostMapping("selectByAccount")
    public CommonplaceResult selectByAccount(@RequestBody Map<String, String> pojo) {
        String username = pojo.get(UserConstantPool.USERNAME);
        log.info("账号：" + username);
        return addressService.selectAddressByAccountUsername(username);
    }

    /**
     * 添加地址
     * 数据格式
     * {
     *     'account_username': 'xxx',
     *     'address': 'xxx'
     * }
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
     * 数据格式
     * {
     *     'id': 'xxx',
     *     'address': 'xxx'
     * }
     * @param addr 地址信息
     */
    @PostMapping("updateAddress")
    public CommonplaceResult updateAddress(@RequestBody Address addr) {
        return addressService.updateAddress(addr);
    }
}
