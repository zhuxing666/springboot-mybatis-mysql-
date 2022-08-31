package com.zhuzhu.store.controller;

import com.zhuzhu.store.service.CartService;
import com.zhuzhu.store.util.JsonResult;
;
import com.zhuzhu.store.vo.CartVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("carts")
public class CartController extends BaseController{
    @Autowired
    private CartService cartService;
    @RequestMapping("/add_to_cart")
    public JsonResult addCart(Integer pid, Integer amount, HttpSession session){
        Integer uid = getuidFromSession(session);
        String username = getUsernameFromSession(session);
        cartService.addToCart(uid,pid,amount,username);
        return new JsonResult(OK);
    }

    @RequestMapping({"","/"})
    public JsonResult  getVOByUid(HttpSession session)
    {
        Integer uid = getuidFromSession(session);
        List<CartVO> data =cartService.getVOByUid(uid);
        return new JsonResult(OK,data);
    }

    @RequestMapping("{cid}/num/add")
    public JsonResult addNum(@PathVariable("cid") Integer cid, HttpSession session)
    {
        Integer uid = getuidFromSession(session);
        String username = getUsernameFromSession(session);
        Integer data = cartService.addNum(cid, uid, username);
        return new JsonResult(OK,data);
    }
}
