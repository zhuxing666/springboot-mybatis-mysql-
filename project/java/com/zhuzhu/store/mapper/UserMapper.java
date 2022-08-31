package com.zhuzhu.store.mapper;

import com.zhuzhu.store.entity.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
//@Mapper
/*
 * 用户模块的持久层*/
public interface UserMapper {

    /**
     * 注册用户名和密码
     * @param user
     * return  user
     * */

    /*
     * 映射pojo中名字和数据库名字不一样
     * 注意result 要写在sql 下面*/

    @Insert("insert into t_user (uid,username, password, salt, phone, email, gender, avatar, is_delete, created_user, created_time, modified_user, modified_time) VALUES (null,#{username}, #{password}, #{salt}, #{phone}, #{email}, #{gender}, #{avatar}, #{isDelete}, #{createdUser}, #{createdTime}, #{modifiedUser}, #{modifiedTime})")
    public Integer Insert(User user);


    /**
     * 根据用户名查找
     * @param username
     * return  user，找不到返回null
     * */

    /*
     * 注意映射时column有空格也有可能报错
     * */
    @Select("select *from t_user where username=#{username}")
    @Results(id = "userresultMap", value = {
            @Result(property = "isDelete", column = "is_delete"),
            @Result(property = "createdUser", column = "created_user"),
            @Result(property = "createdTime", column = "created_time"),
            @Result(property = "modifiedUser", column = "modified_user"),
            @Result(property = "modifiedTime", column = "modified_time")})
    public User SelectByUserName(String username);


    /**
     * 根据uid来修改用户密码
     * @param uid
     *  return 返回对象，否则就返回null
     * */
    @ResultMap("userresultMap")
    @Select("select * from t_user where uid =#{uid}")
    public User SelectByUid(Integer uid);


    /**
     * 根据uid来修改用户密码
     * @param uid
     * @param password  用户输入的新密码
     * @param modifiedUser  修改的执行者
     * @param modifiedTime  修改数据的时间
     * return  受影响的行数
     * */
    @Update("update t_user set password=#{password},modified_user=#{modifiedUser}, modified_time=#{modifiedTime} where uid=#{uid}")
    public Integer updatePasswordByUid(Integer uid, String password, String modifiedUser, Date modifiedTime);


    /*
     * 根据uid来修改用户密码
     * @param user pojo对象
     * @param password  用户输入的新密码
     * @param modifiedUser  修改的执行者
     * @param modifiedTime  修改数据的时间
     * return  受影响的行数
     * */
    @Update("update t_user set phone=#{phone},email=#{email},gender=#{gender},modified_user=#{modifiedUser},modified_time=#{modifiedTime} where uid=#{uid}")
    public Integer updateInfoByUid(User user);


    /**
     * 修改头像功能
     * 根据uid来设置头像
     * @param uid  查询的uid
     * @param avatar  图片的路径
     * @param modifiedUser  修改的执行者
     * @param modifiedTime  修改数据的时间
     * return  受影响的行数
     * */
    @Update("update t_user set avatar=#{avatar},modified_user = #{modifiedUser},modified_time = #{modifiedTime} where uid=#{uid}")
    Integer updateAvatarByUid(@Param("uid") Integer uid, @Param("avatar") String avatar, @Param("modifiedUser") String modifiedUser, @Param("modifiedTime") Date modifiedTime);

}
