package com.zhuzhu.store.config;

import com.zhuzhu.store.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServlet;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class LoginInterceptorConfig implements WebMvcConfigurer {
    // 创建自定义拦截器
    HandlerInterceptor interceptor =new LoginInterceptor();
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 配置不拦截路径，存在集合List

        List<String> patterns =new ArrayList<String>();
        patterns.add("/bootstrap3/**");
        patterns.add("/css/**");
        patterns.add("/images/**");
        patterns.add("/js/**");
        patterns.add("/web/login.html");
        patterns.add("/web/index.html");
        patterns.add("/web/register.html");
        patterns.add("/web/product.html");
        patterns.add("/users/regist");
        patterns.add("/users/login");
        patterns.add("/users/changepassword");
        patterns.add("/districts/**");
        patterns.add("/products/**");

        // 完成拦截器的注册
        registry.addInterceptor(interceptor).addPathPatterns("/**").excludePathPatterns(patterns);
    }
}
