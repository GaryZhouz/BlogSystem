package com.wzz.mapper;

import com.wzz.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface UserMapper {

    //根据用户名查询用户
    User queryUserByUsername(@Param("username") String username);

    //添加用户
    int addUser(User user);

    //修改密码(用户名)
    int updatePwd(@Param("username") String username,@Param("newPwd") String newPwd,@Param("salt")String salt);

    //查询id为1的管路员账户
    User queryAdminUser();

    //修改id为1的网站超级管理员的个人信息
    int updateAdminUserInfo(@Param("username") String username,@Param("trueName") String trueName,
                            @Param("gender") String gender,@Param("qq") String qq,@Param("wechat") String wechat,
                            @Param("personalBrief") String personalBrief,@Param("avatarImgUrl") String avatarImgUrl);

    //查询所有用户信息,不包括超级管理员自己
    List<User> queryAllUser();

    //根据用户名删除用户
    int deleteUserByUsername(@Param("username") String username);

    //limit分页查询
    List<User> queryByLimit(@Param("start") int start,@Param("pageSize") int pageSize);

    //管理员修改普通用户信息(用户名修改  不包括用户头像修改)
    int updateUserInfo(@Param("oldUsername") String oldUsername,@Param("username") String username,
                       @Param("trueName") String trueName,@Param("gender") String gender,@Param("qq") String qq,
                       @Param("wechat") String wechat,@Param("signature")String signature);

    //管理员用户用户管理界面 模糊搜索和分页
    List<User> queryUserByLike(Map<String ,Object> map);

    //普通用户自行修改用户信息(用户名修改)
    int updateCommonUserInfo(@Param("username") String username,
                       @Param("trueName") String trueName,@Param("gender") String gender,@Param("qq") String qq,
                       @Param("wechat") String wechat,@Param("signature")String signature,@Param("avatarImgUrl") String avatarImgUrl);

}
