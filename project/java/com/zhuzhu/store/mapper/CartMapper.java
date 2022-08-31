package com.zhuzhu.store.mapper;

import com.zhuzhu.store.entity.Cart;
import com.zhuzhu.store.vo.CartVO;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface CartMapper {

    /**
     * 添加商品到购物车
     *
     * @param cart
     * @return 受影响的行数
     */
    @Insert("insert into t_cart (cid, uid, pid, price, num, created_user, created_time, modified_user, modified_time) values (#{cid},#{uid},#{pid},#{price},#{num},#{createdUser},#{createdTime},#{modifiedUser},#{modifiedTime})")
    Integer insert(Cart cart);

    /**
     * 跟新购物车商品的数据
     *
     * @param cid          购物车数据
     * @param num          跟新的数量
     * @param modifiedUser 修改者
     * @param modifiedTime 修改时间
     * @return 受影响的行数
     */
    @Update("update t_cart set  num=#{num},modified_user=#{modifiedUser},modified_time=#{modifiedTime} where cid=#{cid}")
    Integer updateNumByCid(@Param("cid") Integer cid, @Param("num") Integer num, @Param("modifiedUser") String modifiedUser, @Param("modifiedTime") Date modifiedTime);

    /**
     * 根据亭湖id和商品id来查询购物车中的数据
     *
     * @param uid 用户的id
     * @param pid 商品的id
     * @return cart对象
     */
    @Select("select * from t_cart where uid=#{uid} and pid=#{pid}")
    @Results(id = "CartresultMap", value = {
            @Result(property = "createdUser", column = "created_user"),
            @Result(property = "createdTime", column = "created_time"),
            @Result(property = "modifiedUser", column = "modified_user"),
            @Result(property = "modifiedTime", column = "modified_time")
    })
    Cart findByUidAndPid(Integer uid, Integer pid);


    /**
     * @param uid 根据uid查找CartVO
     * @return CartVo
     */
    // VO 对象没有实体类没有多表查询结果
    @Select("select cid,uid,pid,t_cart.price,t_cart.num,t_product.title,t_product.image,t_product.price as realprice from t_cart Left join t_product on t_cart.pid =t_product.id where uid =#{uid} order by t_cart.created_time DESC")
    @Results(id = "CartVOresulyMap", value = {
            @Result(property = "createdUser", column = "created_user"),
            @Result(property = "createdTime", column = "created_time"),
            @Result(property = "modifiedUser", column = "modified_user"),
            @Result(property = "modifiedTime", column = "modified_time")
    })
    List<CartVO> findByUid(Integer uid);


    @Select("select * from t_cart where cid =#{cid}")
    @ResultMap("CartresultMap")
    Cart findByCid(Integer cid);
}
