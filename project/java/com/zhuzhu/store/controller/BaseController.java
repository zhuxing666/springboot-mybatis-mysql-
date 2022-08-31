package com.zhuzhu.store.controller;

import com.zhuzhu.store.config.ex.*;
import com.zhuzhu.store.service.exception.*;
import com.zhuzhu.store.util.JsonResult;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpSession;

public class BaseController {
    /**
     * 操作成功的状态码
     */
    public static final int OK = 200;

    /**
     * @ExceptionHandler用于统一处理方法抛出的异常
     */
    @ExceptionHandler({ServiceException.class, FileUploadException.class})
    public JsonResult handleException(Throwable e) {
        JsonResult result = new JsonResult(e);
        if (e instanceof UsernameDuplicatedException) {
            result.setState(4000);
            result.setMessage("用户名被占异常");
        } else if (e instanceof UsernameNotFoundException) {
            result.setState(4001);
            result.setMessage("用户数据不存在异常");
        } else if (e instanceof PasswordNotMatchException) {
            result.setState(4002);
            result.setMessage("用户名的密码错误的异常");
        } else if (e instanceof AdressCountLimitException) {
            result.setState(4003);
            result.setMessage("用户的收货地址超出上限");
        } else if (e instanceof AddressNotFoundException) {
            result.setState(4004);
            result.setMessage("用户的收货地址不存在");
        } else if (e instanceof AccessDeniedException) {
            result.setState(4005);
            result.setMessage("用户非法访问收货地址");
        } else if (e instanceof ProductNotFoundException) {
            result.setState(4006);
            result.setMessage("尝试访问的商品数据不存在");
        }else if (e instanceof CartNotFoundException) {
            result.setState(4007);
            result.setMessage("购物车商品没有找到异常");
        }
        else if (e instanceof InsertException) {
            result.setState(5000);
            result.setMessage("插入数据是产生未知的异常");
        } else if (e instanceof updateException) {
            result.setState(5001);
            result.setMessage("更新数据是产生未知的异常");
        }else if (e instanceof deleteException) {
            result.setState(5002);
            result.setMessage("删除数据产生未知的异常");
        }
        else if (e instanceof FileEmptyException) {
            result.setState(6000);
        } else if (e instanceof FileSizeException) {
            result.setState(6001);
        } else if (e instanceof FileTypeException) {
            result.setState(6002);
        } else if (e instanceof FileStateException) {
            result.setState(6003);
        } else if (e instanceof FileUploadException) {
            result.setState(6004);
        }
        return result;
    }


    /**
     * 获取当前用的uid
     *
     * @param session return user 当前登陆的uid
     */

    protected final Integer getuidFromSession(HttpSession session) {

        return Integer.valueOf(session.getAttribute("uid").toString());
    }

    /**
     * 获取当前用的username
     *
     * @param session return user 当前登陆的用户名称
     */
    protected final String getUsernameFromSession(HttpSession session) {
        return session.getAttribute("username").toString();
    }
}
