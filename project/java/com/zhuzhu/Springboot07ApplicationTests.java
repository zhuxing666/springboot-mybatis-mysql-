package com.zhuzhu;

import com.zhuzhu.store.entity.User;
import com.zhuzhu.store.mapper.UserMapper;
import com.zhuzhu.store.service.UserService;
import com.zhuzhu.store.service.exception.ServiceException;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
class Springboot07ApplicationTests {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserService userService;

    @Test
    void test() {
        User user =new User();
        user.setUsername("小可爱");
        user.setPassword("666888");
        Integer count = userMapper.Insert(user);
        System.out.println(count);
    }
    @Test
    void test2() {

        String username ="姚曼老婆";
        User user = userMapper.SelectByUserName(username);
        System.out.println(user);

    }

    @Test
    void test3() {
        User user=new User();
        user.setUsername("小可可");
        user.setPassword("11111");
        userService.Register(user);
        System.out.println("插入成功");
        try {

        }catch (ServiceException sex)
        {
            sex.printStackTrace();
        }

    }


    @Test
    void login() {

        User user = userService.Login("多啦a孟","666");
        System.out.println(user);
    }

    @Test
    void SelectByUid() {

        User user = userMapper.SelectByUid(12);
        System.out.println(user);
    }

    @Test
    void Update() {

        Integer count = userMapper.updatePasswordByUid( 17 , "6666", "曼老公", new Date());
        System.out.println(count);


    }

    @Test
    public void changePassword()
    {
        userService.changePassword(12, "苏诺莎", "123", "123456");
    }

    @Test
    public void updateInfoByUid()
    {
        User user=new User();
        user.setUid(19);
        user.setPhone("15161616115");
        user.setEmail("4554154@qq.com");
        user.setGender(1);
        Integer row = userMapper.updateInfoByUid(user);
        System.out.println(row);
    }

    @Test
    public void getByUid()
    {
        User user = userService.getByUid(20);
        System.out.println(user);
    }


    @Test
    public void changeInfo(){
            User user =new User();
            user.setPhone("6686868868");
            user.setEmail("22222@qq.com");
            user.setGender(0);
            userService.changeInfo(19,"小妞啊",user);
    }
    @Test
    public void  updateAvatarByUid()
    {
        Integer row = userMapper.updateAvatarByUid(19, "uploda/xiaoxai.png", "可达鸭", new Date());
        System.out.println(row);
    }

    @Test
    public void updateAvatarByUidService()
    {
         userService.updateAvatarByUid(19,"999uplodaxiaoke.jpg","宝可梦");
    }



}
