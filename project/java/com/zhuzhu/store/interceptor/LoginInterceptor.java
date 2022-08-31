package com.zhuzhu.store.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // HttpServletRequest request获取sesssion对象
        Object uid = request.getSession().getAttribute("uid");
        // 没有登陆过系统，重定向
        if (uid ==null)
        {
            response.sendRedirect("/web/login.html");
            return false;
        }
        // 放行
        return true;
    }

    // 在ModelAndView返回之后处理
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
