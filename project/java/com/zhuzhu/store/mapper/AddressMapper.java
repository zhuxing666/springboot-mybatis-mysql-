package com.zhuzhu.store.mapper;

import com.zhuzhu.store.entity.Address;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;

@Repository
public interface AddressMapper {
    /**
     * 插入收货地址数据
     *
     * @param address 收货地址数据
     * @return 受影响的行数
     */


    @Insert("insert into t_address (aid, uid, name, province_name, province_code, city_name, city_code, area_name, area_code, zip, address, phone, tel, tag, is_default, created_user, created_time, modified_user, modified_time) values(null,#{uid}, #{name}, #{provinceName}, #{provinceCode}, #{cityName}, #{cityCode}, #{areaName}, #{areaCode}, #{ zip}, #{address}, #{phone}, #{tel}, #{tag}, #{isDefault}, #{createdUser}, #{createdTime}, #{modifiedUser}, #{modifiedTime})")
    Integer insert(Address address);


    /**
     * 统计某用户的收货地址数据的数量
     *
     * @param uid 用户的id
     * @return 该用户的收货地址数据的数量
     */
    @Select("select count(*) " +
            " from t_address where uid=#{uid}")
    Integer countByUid(Integer uid);

    /**
     * 根据uid查询收货地址
     *
     * @param uid
     * @return adress全部信息
     */

    @Select("select * from t_address where uid=#{uid} order by is_default  DESC ,created_time DESC ")
    @Results(id = "addressResultMap", value = {
            @Result(column = "aid", property = "aid"),
            @Result(column = "province_code", property = "provinceCode"),
            @Result(column = "province_name", property = "provinceName"),
            @Result(column = "city_code", property = "cityCode"),
            @Result(column = "city_name", property = "cityName"),
            @Result(column = "area_code", property = "areaCode"),
            @Result(column = "area_name", property = "areaName"),
            @Result(column = "is_default", property = "isDefault"),
            @Result(column = "created_user", property = "createdUser"),
            @Result(column = "created_time", property = "createdTime"),
            @Result(column = "modified_user", property = "modifiedUser"),
            @Result(column = "modified_time", property = "modifiedTime")
    })
    List<Address> findByUid(Integer uid);

    /**
     * 根据aid超找用户是否存在
     *
     * @param aid
     * @return address
     */
    @Select("select *from t_address where aid=#{aid}")
    @ResultMap("addressResultMap")
    Address findByAid(Integer aid);

    /**
     * @param uid
     * @return 受影响行数
     */
    @Update("update t_address set is_default=0 where uid=#{uid}")
    Integer updateNodefault(Integer uid);


    /**
     * @param aid
     * @param modifiedUser
     * @param modifiedTime
     * @return 受影响的行数
     */
    @Update("update t_address set is_default =1 ,modified_user=#{modifiedUser},modified_time=#{modifiedTime} where aid=#{aid}")
    Integer updateDefaultByAid(@Param("aid") Integer aid, @Param("modifiedUser") String modifiedUser, @Param("modifiedTime") Date modifiedTime);

    /**
     * 根据地址aid删除用户地址信息
     * @param aid
     * @return 受影响的行数
     */
    @Delete("delete from t_address where aid=#{aid}")
    Integer deleteByAid(Integer aid);

    /**
     * 如果删除的是默认地址，把最新的地址设为默认
     * @param uid
     * @return
     */
    @Select("select * from t_address where uid=#{uid} order by modified_time DESC limit 0,1")
    @ResultMap("addressResultMap")
    Address findLastModified(Integer uid);
}