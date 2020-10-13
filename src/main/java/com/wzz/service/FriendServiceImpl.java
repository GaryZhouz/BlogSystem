package com.wzz.service;

import com.wzz.mapper.FriendLinkMapper;
import com.wzz.pojo.FriendLink;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FriendServiceImpl implements FriendService{

    @Autowired
    private FriendLinkMapper friendLinkMapper;

    //查询所有友联信息
    @Override
    public List<FriendLink> queryAllFriend() {
        return friendLinkMapper.queryAllFriend();
    }

    //添加友链
    @Override
    public int addFriendLink(String name, String url) {
        return friendLinkMapper.addFriendLink(name,url);
    }

    //删除友链
    @Override
    public int deleteLink(int id) {
        return friendLinkMapper.deleteLink(id);
    }

    //根据id查询友链
    @Override
    public FriendLink queryLinkById(int id) {
        return friendLinkMapper.queryLinkById(id);
    }

    //更新友链信息
    @Override
    public int updateLink(int id, String name, String url) {
        return friendLinkMapper.updateLink(id, name, url);
    }
}
