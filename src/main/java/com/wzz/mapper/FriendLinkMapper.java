package com.wzz.mapper;

import com.wzz.pojo.FriendLink;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface FriendLinkMapper {

    //查询所有的友链好友信息
    List<FriendLink> queryAllFriend();

    //添加友链
    int addFriendLink(@Param("name") String name,@Param("url") String url);

    //删除友链
    int deleteLink(@Param("id") int id);

    //根据id查询友链
    FriendLink queryLinkById(@Param("id") int id);

    //更新友链信息
    int updateLink(@Param("id") int id,@Param("name") String name,@Param("url") String url);
}
