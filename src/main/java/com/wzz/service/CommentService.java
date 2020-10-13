package com.wzz.service;

import com.wzz.pojo.Comment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CommentService {

    //查询一篇文章所有评论条数
    int querySumByArticle(int articleId);

    //查询一篇文章的所有的顶级评论 及pid为0的顶楼评论
    List<Comment> queryAllCommentPid(int articleId);

    //根据id查询评论
    Comment queryCommentById(int id);

    //查询一篇文章所有的评论
    List<Comment> queryAllComment(int articleId);

    //增加一条最顶级评论
    int addTopComment(String username, String articleId, String commentDate,String content);

    //添加一条评论(answererName,articleId,pid,content,time)
    int addAComment(String answererName,int articleId,int pid, String content, String time);

    //顶楼下回复按钮的表单数据(回复当前顶楼)
    int addCommentHaveRespondentName(String answererName, String respondentName,int articleId,int pid,String content, String time);

    //查询某个人的所有的评论
    List<Comment> queryAllCommentByUsername(String username);

    //删除一条评论及指向它的子评论
    int deleteCommentById(int id);

    //查询所有评论
    List<Comment> queryAllComments();
}
