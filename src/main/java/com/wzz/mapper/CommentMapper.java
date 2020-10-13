package com.wzz.mapper;

import com.wzz.pojo.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CommentMapper {

    //查询一篇文章所有评论条数
    int querySumByArticle(@Param("id") int articleId);

    //查询一篇文章的所有的顶级评论 及pid为0的顶楼评论
    List<Comment> queryAllCommentPid(@Param("id") int articleId);

    //根据id查询评论
    Comment queryCommentById(@Param("id") int id);

    //查询一篇文章所有的评论
    List<Comment> queryAllComment(@Param("id") int articleId);

    //增加一条最顶级评论
    int addTopComment(@Param("username") String username,@Param("articleId") String articleId,@Param("commentDate") String commentDate,@Param("content") String content);

    //添加一条评论(answererName,articleId,pid,content,time)
    int addAComment(@Param("username")String answererName,@Param("articleId") int articleId,@Param("pid") int pid,@Param("content") String content,@Param("time") String time);

    //顶楼下回复按钮的表单数据(回复当前顶楼) 及 回复顶楼下的子评论(及n层子评论)
    int addCommentHaveRespondentName(@Param("username")String answererName,@Param("respondentName") String respondentName,@Param("articleId") int articleId,@Param("pid") int pid,@Param("content") String content,@Param("time") String time);

    //查询某个人的所有的评论
    List<Comment> queryAllCommentByUsername(@Param("username") String username);

    //删除一条评论及指向它的子评论
    int deleteCommentById(@Param("id") int id);

    //查询所有评论
    List<Comment> queryAllComments();
}
