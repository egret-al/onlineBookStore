package com.onlinebookstore.mapper;

import com.onlinebookstore.entity.Address;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author rkc
 * @date 2020/9/12 18:22
 * @version 1.0
 */
@Mapper
public interface AddressMapper {

    /**
     * 根据id查询地址
     * @param id id
     * @return 地址对象
     */
    Address selectOneById(Integer id);

    /**
     * 根据账号查询所有地址
     * @param username 账号
     * @return 地址集合
     */
    List<Address> selectAddressByAccountUsername(String username);

    /**
     * 查询所有地址
     * @return 地址集合
     */
    List<Address> selectAllAddress();

    /**
     * 添加地址
     * @param addr 地址对象
     * @return 影响行数
     */
    int addAddress(Address addr);

    /**
     * 根据id删除地址
     * @param id id
     * @return 影响行数
     */
    int deleteAddressById(Integer id);

    /**
     * 更新地址对象
     * @param addr 新地址信息
     * @return 影响行数
     */
    int updateAddress(Address addr);

    /**
     * 根据id获取地址+账户
     * @return 地址+账户
     */
    Address getAddressWithAccountById(Integer id);
}
