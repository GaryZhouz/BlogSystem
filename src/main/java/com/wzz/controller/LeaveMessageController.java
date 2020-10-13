package com.wzz.controller;

import com.wzz.service.LeaveMessageServiceImpl;
import com.wzz.utils.MyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

import static com.wzz.utils.MyUtils.formatTime;

@Controller
public class LeaveMessageController {

    @Autowired
    private LeaveMessageServiceImpl leaveMessageService;

    /*
    接收留言参数并添加
     */
    @PostMapping("/addLeaveMessage")
    public String addLeaveMessage(String title,String content,String username,String email){
        leaveMessageService.addLeaveMessage(title, content, username, email,formatTime(new Date()));
        return "redirect:/";
    }

    /*
    删除留言
     */
    @GetMapping("/admin/deleteLeaveMessage/{id}")
    public String deleteLeaveMessage(@PathVariable int id){
        leaveMessageService.deleteLeaveMessage(id);
        return "redirect:/admin/toManageLeaveMessage";
    }

    /*
    更新留言
     */
    @RequestMapping("/admin/updateLeaveMessage")
    public String updateLeaveMessage(int id, String title, String content, String name, String email){
        leaveMessageService.updateLeaveMessage(id, title, content, name, email);
        return "redirect:/admin/toManageLeaveMessage";
    }
}
