package com.wzz.controller;

import com.wzz.pojo.Article;
import com.wzz.pojo.Comment;
import com.wzz.pojo.User;
import com.wzz.service.ArticleServiceImpl;
import com.wzz.service.CommentServiceImpl;
import com.wzz.service.UserServiceImpl;
import com.wzz.utils.MyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
public class CommentController {

    @Autowired
    private CommentServiceImpl commentService;

    /*
    添加一条最顶级评论
     */
    @PostMapping("/addTopComment")
    public String addTopComment(String username,String articleId,String content){
        commentService.addTopComment(username,articleId,MyUtils.formatTime(new Date()),content);
        return "redirect:/articlePage/"+articleId;
    }


    /*
    顶楼下 添加一条新评论按钮下的表单数据(仅回复 没有回复某个人)
     */
    @PostMapping("/addSecendComment")
    public String addSecendComment(String username,int articleId,int pid,String content){
        commentService.addAComment(username,articleId,pid,content,MyUtils.formatTime(new Date()));
        return "redirect:/articlePage/"+articleId;
    }

    /*
    顶楼下回复按钮的表单数据(回复当前顶楼)
     */
    @PostMapping("/addCommentHaveRespondentName")
    public String addCommentHaveRespondentName(String username,String respondentName,int articleId,int pid,String content){
        commentService.addCommentHaveRespondentName(username,respondentName,articleId,pid,content,MyUtils.formatTime(new Date()));
        return "redirect:/articlePage/"+articleId;
    }

    /*
    普通后台用户删除自己的评论 及自己下面的子评论
     */
    @GetMapping("/userAdmin/deleteComment/{id}")
    public String deleteComment(@PathVariable int id, HttpSession session){
        commentService.deleteCommentById(id);
        return "redirect:/userAdmin/toCommentList/"+session.getAttribute("username");
    }

    /*
    管理员后台用户删除评论 及下面的子评论
     */
    @GetMapping("/admin/deleteComments/{id}")
    public String deleteComments(@PathVariable int id, HttpSession session){
        commentService.deleteCommentById(id);
        return "redirect:/admin/toCommentManage";
    }

}
