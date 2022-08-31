package com.zhuzhu.store.service;

import com.zhuzhu.store.vo.CartVO;

import java.util.List;

public interface CartService {

    /**
     * 将商品添加到购物车中饭
     * @param uid 用户id
     * @param pid 商品id
     * @param amount 新增的数量
     * @param username 用户名
     */
    void addToCart(Integer uid,Integer pid,Integer amount,String username);

    /**
     *
     * @param uid
     * @return
     */
    List<CartVO> getVOByUid(Integer uid);

    /**
     *  增加购物车商品数量
     * @param cid
     * @param uid
     * @param username
     * return 受影响的行数
     */
    Integer addNum(Integer cid,Integer uid,String username);

}
