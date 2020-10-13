package com.wzz.service;

import com.wzz.mapper.UserMapper;
import com.wzz.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    //根据username查询用户
    @Override
    public User queryUserByUsername(String username) {
        return userMapper.queryUserByUsername(username);
    }

    //注册用户
    @Override
    public int addUser(User user) {
        return userMapper.addUser(user);
    }

    //密码修改
    @Override
    public int updatePwd(String username, String newPwd, String salt) {
        return userMapper.updatePwd(username, newPwd, salt);
    }

    //查询管理员
    @Override
    public User queryAdminUser() {
        return userMapper.queryAdminUser();
    }

    //更新网站管理员个人信息
    @Override
    public int updateAdminUserInfo(String username, String trueName, String gender, String qq, String wechat, String personalBrief, String avatarImgUrl) {
        return userMapper.updateAdminUserInfo(username, trueName, gender, qq, wechat, personalBrief, avatarImgUrl);
    }

    //查询所有用户(不包括唯一的网站管理员)
    @Override
    public List<User> queryAllUser() {
        return userMapper.queryAllUser();
    }

    //根据用户名删除用户
    @Override
    public int deleteUserByUsername(String username) {
        return userMapper.deleteUserByUsername(username);
    }

    @Override
    public List<User> queryUserByLike(Map<String, Object> map) {
        return userMapper.queryUserByLike(map);
    }

    //limit分页查询
    @Override
    public List<User> queryByLimit(int start, int pageSize) {
        return userMapper.queryByLimit(start, pageSize);
    }

    //修改用户信息(用户名修改)
    @Override
    public int updateUserInfo(String oldUsername, String username, String trueName, String gender, String qq, String wechat, String signature) {
        return userMapper.updateUserInfo(oldUsername, username, trueName, gender, qq, wechat, signature);
    }

    @Override
    public int updateCommonUserInfo(String username, String trueName, String gender, String qq, String wechat, String signature, String avatarImgUrl) {
        return userMapper.updateCommonUserInfo(username, trueName, gender, qq, wechat, signature, avatarImgUrl);
    }
}
