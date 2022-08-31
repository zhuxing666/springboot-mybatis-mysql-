package com.zhuzhu;


import com.zhuzhu.store.entity.Cart;
import com.zhuzhu.store.mapper.CartMapper;
import com.zhuzhu.store.service.CartService;
import com.zhuzhu.store.vo.CartVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.ConditionalOnRepositoryType;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


@SpringBootTest(classes = Springboot07Application.class)
class Springboot07ApplicationTests3 {
    @Autowired
    private CartMapper cartMapper;
    @Autowired
    private CartService cartService;
    @Test
    public void addCart() {

        cartService.addToCart(12,10000011,10,"李婷");

    }
    @Test
    public void selectByCid() {

        List<CartVO> byUid = cartMapper.findByUid(19);
        System.out.println(byUid);

    }
}










