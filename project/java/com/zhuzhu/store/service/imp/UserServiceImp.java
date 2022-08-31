package com.zhuzhu.store.service.imp;

import com.zhuzhu.store.entity.User;
import com.zhuzhu.store.mapper.UserMapper;
import com.zhuzhu.store.service.UserService;
import com.zhuzhu.store.service.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.UUID;

@Service
public class UserServiceImp implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public void Register(User user) {
        //更具姓名查找是否被注册过
        User u = userMapper.SelectByUserName(user.getUsername());

        if (u != null) {
            //抛异常用户名被占用
            throw new UsernameDuplicatedException("用户名被占用");
        }

        // 补全数据：加密后的密码
        //随机生成盐值
        String salt = UUID.randomUUID().toString().toUpperCase();
        String md5Password = getMd5Password(user.getPassword(), salt);

        //补全数据：加密算法密码
        user.setPassword(md5Password);
        // 补全数据：盐值
        user.setSalt(salt);
        //补全数据：is_delete设置成0
        user.setIsDelete(0);

        //补全数据：4个日志字段信息
        Date date = new Date();
        user.setCreatedUser(user.getUsername());
        user.setCreatedTime(date);
        user.setModifiedUser(user.getUsername());
        user.setModifiedTime(date);


        //执行业务的实现功能
        Integer row = userMapper.Insert(user);
        if (row != 1) {
            throw new InsertException("用户注册过程中产生了未知的异常");
        }
    }


    @Override
    public User Login(String username, String password) {
        // 进行数据查询
        User user = userMapper.SelectByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException("用户数据不存在");
        }
        // 监测用户填写的密码和数据库的密码是匹配进行比较
        // 获取老的密码，也就是数据库密码
        String oldpassword = user.getPassword();
        // 将用户填写的密码按Md5进行加密，在进行比较
        String salt = user.getSalt();
        String newmd5Password = getMd5Password(password, salt);

        // 进行比对密码
        if (!newmd5Password.equals(oldpassword)) {
            throw new PasswordNotMatchException("用户密码验证错误");
        }

        // 判断用户iS_deletet 字段是否有为1,1为标记为删除，0没有删除还在
        if (user.getIsDelete() == 1) {
            throw new UsernameNotFoundException("用户可能注销了，不存在了");
        }

        //将数据返回
        // 提升系统的性能
        User user1 = new User();
        user1.setUid(user.getUid());
        user1.setUsername(user.getUsername());
        user1.setPassword(user.getPassword());
        user1.setAvatar(user.getAvatar());
        return user1;
    }


    @Override
    public void changePassword(Integer uid, String username, String oldPassword, String newPassword) {
        User result = userMapper.SelectByUid(uid);
        if (result == null || result.getIsDelete() == 1) {
            throw new UsernameNotFoundException("用户数据不存在");
        }
        String oldmd5Password = getMd5Password(oldPassword, result.getSalt());
        if (!result.getPassword().equals(oldmd5Password)) {
            throw new PasswordNotMatchException("密码错误");
        }

        // 将新的密码设置到数据库中
        String newmd5Password = getMd5Password(newPassword, result.getSalt());

        Integer row = userMapper.updatePasswordByUid(uid, newmd5Password, username, new Date());

        if (row != 1) {
            throw new updateException("更新时修改数据产生未知的异常");
        }
    }

    @Override
    public User getByUid(Integer uid) {
        User result = userMapper.SelectByUid(uid);
        if (result == null || result.getIsDelete() == 1) {
            throw new UsernameNotFoundException("用户数据不存在");
        }
        // 自己封装用户信息，选择自己需要的信息,提高性能
        User user = new User();
        user.setUsername(result.getUsername());
        user.setPhone(result.getPhone());
        user.setEmail(result.getEmail());
        user.setGender(result.getGender());
        return user;
    }


    // uername 是修改者的名字
    @Override
    public void changeInfo(Integer uid, String username, User user) {
        User result = userMapper.SelectByUid(uid);
        if (result == null || result.getIsDelete() == 1) {
            throw new UsernameNotFoundException("用户数据不存在");
        }
        user.setUid(uid);
        user.setModifiedUser(username);
        user.setModifiedTime(new Date());
        Integer rows= userMapper.updateInfoByUid(user);
        if (rows != 1)
        {
            throw new updateException("更新数据产生未知的异常");
        }
    }


    @Override
    public void updateAvatarByUid(Integer uid, String avatar, String username) {
        User result = userMapper.SelectByUid(uid);
        if (result == null|| result.getIsDelete() == 1)
        {
            throw new UsernameNotFoundException("用户数据不存在");
        }
        Integer rows = userMapper.updateAvatarByUid(uid, avatar, username, new Date());
        if(rows !=1)
        {
            throw new updateException("更新用户头像产生未知的异常");
        }
    }

    /**
     * 执行密码加密
     *
     * @param password 原始密码
     * @param salt     盐值
     * @return 加密后的密文
     */
    private String getMd5Password(String password, String salt) {
        /**
         * 加密规则：
         * 1、无视原始密码的强度
         * 2、使用UUID作为盐值，在原始密码的左右两侧拼接
         * 3、循环加密3次
         */
        for (int i = 0; i < 3; i++) {
            password = DigestUtils.md5DigestAsHex((salt + password + salt).getBytes()).toUpperCase();
        }
        return password;
    }
}





