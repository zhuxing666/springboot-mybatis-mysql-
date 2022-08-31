package com.zhuzhu.store.service;

import com.zhuzhu.store.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

public interface UserService {
    /**
    * 注册功能
    * @param user
    * return 判断是否插入成功
    * */
    public void Register(User user);


    /**
    * 登录功能
    * @param username
    * return  User 避免用户获取多次查询操作数据库
    * */
    public User Login(String username,String password);


    /**
     * 根据uid查询用户信息
     * @param uid
     * return  User 避免用户获取多次查询操作数据库
     * */

    public User getByUid(Integer uid);


    /**
     * 登录功能
     * @param username
     * return  User 避免用户获取多次查询操作数据库
     * */
    public  void changePassword(Integer uid, String username, String oldPassword,String newPassword);


    /**
     * 修改用户功能
     * @param uid 用户的id
     * @param username 当前用户的登录姓名
     * @param  user 用户的数据信息
     * */
    public  void changeInfo(Integer uid,String username,User user);

    // /**回车快捷
    /**
     *
     * @param uid  用户的id
     * @param avatar  用户的头像路径
     * @param username 用户的名称
     * @return
     */
    void updateAvatarByUid(Integer uid,String avatar,String username);




}
