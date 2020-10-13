package com.wzz.config;

import com.wzz.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebAppConfigurer implements WebMvcConfigurer {

    @Bean
    public AdminInterceptor getAdminInterceptor() {
        return  new AdminInterceptor();
    }

    @Bean
    public UserInterceptor getUserInterceptor() {
        return  new UserInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 可添加多个
        //拦截未登录进入超级管理员的界面
        registry.addInterceptor(getAdminInterceptor()).addPathPatterns("/admin/**");
        registry.addInterceptor(getUserInterceptor()).addPathPatterns("/userAdmin/**");
    }

}
