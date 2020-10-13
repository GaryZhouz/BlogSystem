package com.wzz.config;

import com.wzz.pojo.User;
import com.wzz.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Objects;

//普通用户的后台拦截器
@Component
public class UserInterceptor implements HandlerInterceptor {

    @Autowired
    private UserServiceImpl userService;

    //这个方法是在访问接口之前执行的，我们只需要在这里写验证登陆状态的业务逻辑，就可以在用户调用指定接口之前验证登陆状态了
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //每一个项目对于登陆的实现逻辑都有所区别，我这里使用最简单的Session提取User来验证登陆。
        HttpSession session = request.getSession();

        //如果session中没有user，表示没登陆
        if (session.getAttribute("username") == null || session.getAttribute("userRole") == null){
            response.sendRedirect("/toLogin");
            return false;
        }

        //这里的User是登陆时放入session的
        String username = session.getAttribute("username").toString();
        String userRole = session.getAttribute("userRole").toString();

        User user = userService.queryUserByUsername(username);
        //roleId = 2 代表是管理员 当前是普通用户的拦截器
        if (user != null && Integer.parseInt(userRole) == user.getRoleId() && user.getRoleId() != 2){
            return true;
        }
        //当前不满足条件,直接跳转拦截
        response.sendRedirect("/toLogin");
        return false;
    }

}
