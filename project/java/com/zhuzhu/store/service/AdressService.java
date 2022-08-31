package com.zhuzhu.store.service;

import com.zhuzhu.store.entity.Address;

import java.util.List;

public interface AdressService {

    /**
     * 添加新的收货地址
     *
     * @param uid      用户的uid
     * @param username 用户的姓名可以当做日志里面用
     * @param address  添加的信息
     */
    void addNewaddress(Integer uid, String username, Address address);


    List<Address> findByUid(Integer uid);

    /**
     * 修改用户的收货地址为默认收货地址 1就是默认
     *
     * @param aid      收货地址的id
     * @param uid      用户的id
     * @param username 修改执行人
     */
    void setDefault(Integer aid, Integer uid, String username);


    /**
     * 删除用户选中的收货地址的数据
     *
     * @param aid      收货地址id
     * @param uid      用户id
     * @param username 用户姓名
     */
    void delete(Integer aid, Integer uid, String username);
}
