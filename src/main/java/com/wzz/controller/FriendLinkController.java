package com.wzz.controller;

import com.wzz.pojo.FriendLink;
import com.wzz.pojo.User;
import com.wzz.service.FriendServiceImpl;
import com.wzz.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class FriendLinkController {

    @Autowired
    private FriendServiceImpl friendService;
    @Autowired
    private UserServiceImpl userService;
    /*
    友链界面信息传递
     */
    @GetMapping("/friendLinkPage")
    public String friendLinkPage(Model model){
        List<FriendLink> friendLinks = friendService.queryAllFriend();
        model.addAttribute("friendLinks",friendLinks);

        //头像信息传递过去
        User user = userService.queryAdminUser();
        model.addAttribute("topbar_imgUrl",user.getAvatarImgUrl());
        return "friendLink";
    }

    /*
        后台用户管理所有友链信息
    */
    @GetMapping("/admin/toManageFriendLink")
    public String toManageFriendLink(Model model){
        List<FriendLink> friendLinks = friendService.queryAllFriend();
        model.addAttribute("friendLinks",friendLinks);
        return "admin/friendLinkList";
    }

    /*
    后台管理员用户添加友链
     */
    @PostMapping("/admin/addFriendLink")
    public String addFriendLink(String name,String url){
        friendService.addFriendLink(name,url);
        return "redirect:/admin/toManageFriendLink";
    }

    /*
    后台用户删除友链
     */
    @GetMapping("/admin/deleteLink/{id}")
    public String deleteLink(@PathVariable int id){
        friendService.deleteLink(id);
        return "redirect:/admin/toManageFriendLink";
    }

    /*
    后台更新友链信息
     */
    @PostMapping("/admin/updateFriendLink")
    public String updateFriendLink(int id,String name,String url){
        friendService.updateLink(id, name, url);
        return "redirect:/admin/toManageFriendLink";
    }


}
