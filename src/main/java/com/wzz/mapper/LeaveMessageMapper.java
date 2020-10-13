package com.wzz.mapper;

import com.wzz.pojo.LeaveMessage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface LeaveMessageMapper {

    //添加一条留言
    int addLeaveMessage(@Param("title") String title,@Param("content") String content,@Param("name") String name,@Param("email") String email,@Param("time") String time);

    //查询最近的6条留言
    List<LeaveMessage> queryAllLeaveMessageByLimit();

    //查询所有留言
    List<LeaveMessage> queryAllLeaveMessage();

    //删除留言
    int deleteLeaveMessage(@Param("id")int id);

    //根据id查询留言
    LeaveMessage queryLeaveMessageById(@Param("id") int id);

    //更新留言信息
    int updateLeaveMessage(@Param("id") int id,@Param("title") String title,@Param("content") String content,@Param("name") String name,@Param("email") String email);

}
