package com.wzz.service;


import com.wzz.pojo.LeaveMessage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LeaveMessageService {

    //添加一条留言
    int addLeaveMessage(String title,String content, String name, String email,String time);

    //查询最近的留言
    List<LeaveMessage> queryAllLeaveMessageByLimit();

    //查询所有留言
    List<LeaveMessage> queryAllLeaveMessage();

    //删除留言
    int deleteLeaveMessage(int id);

    //根据id查询留言
    LeaveMessage queryLeaveMessageById(int id);

    //更新留言信息
    int updateLeaveMessage(int id,String title,String content,String name,String email);

}
