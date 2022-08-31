package com.zhuzhu.store.service.imp;

import com.zhuzhu.store.entity.Cart;
import com.zhuzhu.store.entity.Product;
import com.zhuzhu.store.mapper.CartMapper;
import com.zhuzhu.store.mapper.ProductMapper;
import com.zhuzhu.store.service.CartService;
import com.zhuzhu.store.service.exception.AccessDeniedException;
import com.zhuzhu.store.service.exception.CartNotFoundException;
import com.zhuzhu.store.service.exception.InsertException;
import com.zhuzhu.store.service.exception.updateException;
import com.zhuzhu.store.vo.CartVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CartServiceImp implements CartService {
    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private ProductMapper productMapper;

    @Override
    public void addToCart(Integer uid, Integer pid, Integer amount, String username) {
        Date date = new Date();
        // 查询当前要添加的这个购物车是否在表中已存在
        Cart result = cartMapper.findByUidAndPid(uid, pid);
        if (result == null) {
            // 商品没有被添加到购物车，进行添加
            Cart cart = new Cart();
            cart.setUid(uid);
            cart.setPid(pid);
            cart.setNum(amount);
            // 价格来自商品的中数据
            Product product = productMapper.findById(pid);
            cart.setPrice(product.getPrice());

            // 补全四个日志
            cart.setCreatedUser(username);
            cart.setCreatedTime(date);
            cart.setModifiedUser(username);
            cart.setModifiedTime(date);
            Integer rows = cartMapper.insert(cart);
            if (rows != 1) {
                throw new InsertException("插入数据产生未知的异常");
            }

        } else {// 表示商品已经存在其购物车，则跟新这调数据的num值
            Integer num = result.getNum() + amount;
            Integer rows = cartMapper.updateNumByCid(result.getCid(), num, username, date);
            if (rows != 1) {
                throw new updateException("更新数据产生未知的异常");
            }

        }

    }

    @Override
    public List<CartVO> getVOByUid(Integer uid) {
        return cartMapper.findByUid(uid);
    }

    @Override
    public Integer addNum(Integer cid, Integer uid, String username) {
        Cart result = cartMapper.findByCid(cid);
        if(result==null)
        {
            throw new CartNotFoundException("数据不存在");
        }
        if (result.getUid().equals(uid))
        {
            throw new AccessDeniedException("数据非法访问");
        }
        Integer num = result.getNum() + 1;
        Integer rows = cartMapper.updateNumByCid(cid, num, username, new Date());
        if (rows !=1)
        {
            throw new updateException("更新数据失败");
        }
        return num;
    }
}
