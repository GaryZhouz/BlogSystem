package com.wzz.service;

import com.wzz.mapper.CommentMapper;
import com.wzz.pojo.Comment;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService{

    @Autowired
    private CommentMapper commentMapper;

    //查询一篇文章所有评论条数
    @Override
    public int querySumByArticle(int articleId) {
        return commentMapper.querySumByArticle(articleId);
    }

    //查询一篇文章的所有顶级评论
    @Override
    public List<Comment> queryAllCommentPid(int articleId) {
        return commentMapper.queryAllCommentPid(articleId);
    }

    //根据id查询评论
    @Override
    public Comment queryCommentById(int id) {
        return commentMapper.queryCommentById(id);
    }

    //查询一篇文章所有的评论
    @Override
    public List<Comment> queryAllComment(int articleId) {
        return commentMapper.queryAllComment(articleId);
    }

    //增加一条最顶级评论
    @Override
    public int addTopComment(String username, String articleId, String commentDate, String content) {
        return commentMapper.addTopComment(username, articleId, commentDate, content);
    }

    //添加一条评论(answererName,articleId,pid,content,time)
    @Override
    public int addAComment(String answererName, int articleId, int pid, String content, String time) {
        return commentMapper.addAComment(answererName, articleId, pid, content, time);
    }

    //顶楼下回复按钮的表单数据(回复当前顶楼)
    @Override
    public int addCommentHaveRespondentName(String answererName, String respondentName, int articleId, int pid, String content, String time) {
        return commentMapper.addCommentHaveRespondentName(answererName, respondentName, articleId, pid, content, time);
    }

    //查询某个人的所有的评论
    @Override
    public List<Comment> queryAllCommentByUsername(String username) {
        return commentMapper.queryAllCommentByUsername(username);
    }

    //删除一条评论及指向它的子评论
    @Override
    public int deleteCommentById(int id) {
        return commentMapper.deleteCommentById(id);
    }

    //查询所有评论
    @Override
    public List<Comment> queryAllComments() {
        return commentMapper.queryAllComments();
    }
}
