package com.wzz.service;

import com.wzz.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface UserService {

    //根据用户名查询用户
    User queryUserByUsername(String username);

    //添加用户
    int addUser(User user);

    //修改密码(用户名)
    int updatePwd(String username,String newPwd,String salt);

    //查询id为1的管路员账户
    User queryAdminUser();

    //修改id为1的网站超级管理员的个人信息
    int updateAdminUserInfo(String username, String trueName,
                            String gender,String qq,String wechat,
                            String personalBrief,String avatarImgUrl);

    //查询所有用户信息,不包括超级管理员自己
    List<User> queryAllUser();

    //根据用户名删除用户
    int deleteUserByUsername(String username);

    //管理员用户用户管理界面 模糊搜索和分页
    List<User> queryUserByLike(Map<String ,Object> map);

    //limit分页查询
    List<User> queryByLimit(int start,int pageSize);

    //修改用户信息(用户名修改)
    int updateUserInfo(String oldUsername,String username,
                       String trueName,String gender,String qq,
                       String wechat,String signature);

    //普通用户自行修改用户信息(用户名修改)
    int updateCommonUserInfo(String username, String trueName,String gender,String qq,
                             String wechat,String signature,String avatarImgUrl);

}
