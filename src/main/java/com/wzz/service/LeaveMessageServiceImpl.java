package com.wzz.service;

import com.wzz.mapper.LeaveMessageMapper;
import com.wzz.pojo.LeaveMessage;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeaveMessageServiceImpl implements LeaveMessageService{

    @Autowired
    private LeaveMessageMapper leaveMessageMapper;

    //添加一条留言
    @Override
    public int addLeaveMessage(String title, String content, String name, String email,String time) {
        return leaveMessageMapper.addLeaveMessage(title, content, name, email,time);
    }

    //查询最新留言
    @Override
    public List<LeaveMessage> queryAllLeaveMessageByLimit() {
        return leaveMessageMapper.queryAllLeaveMessageByLimit();
    }

    //查询所有留言
    @Override
    public List<LeaveMessage> queryAllLeaveMessage() {
        return leaveMessageMapper.queryAllLeaveMessage();
    }

    //删除留言
    @Override
    public int deleteLeaveMessage(int id) {
        return leaveMessageMapper.deleteLeaveMessage(id);
    }

    //根据id查询留言
    @Override
    public LeaveMessage queryLeaveMessageById(int id) {
        return leaveMessageMapper.queryLeaveMessageById(id);
    }

    //更新留言信息
    @Override
    public int updateLeaveMessage(int id, String title, String content, String name, String email) {
        return leaveMessageMapper.updateLeaveMessage(id, title, content, name, email);
    }
}
