package com.wzz.service;

import com.wzz.pojo.FriendLink;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FriendService {

    //查询所有的友链好友信息
    List<FriendLink> queryAllFriend();

    //添加友链
    int addFriendLink(String name,String url);

    //删除友链
    int deleteLink(int id);

    //根据id查询友链
    FriendLink queryLinkById(int id);

    //更新友链信息
    int updateLink(int id,String name,String url);
}
