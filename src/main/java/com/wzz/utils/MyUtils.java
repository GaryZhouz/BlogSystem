package com.wzz.utils;

import com.wzz.mapper.CommentMapper;
import com.wzz.pojo.Article;
import com.wzz.pojo.Comment;
import com.wzz.service.CommentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;

import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class MyUtils {

    //从html代码中提取出纯文本
    public static String StripHT(String strHtml) {
        String txtcontent = strHtml.replaceAll("</?[^>]+>", ""); //剔出<html>的标签
        txtcontent = txtcontent.replaceAll("<a>\\s*|\t|\r|\n</a>", "");//去除字符串中的空格,回车,换行符,制表符
        txtcontent = txtcontent.replaceAll("&nbsp;", "");

        return txtcontent;
    }

    //时间格式化
    public static String formatTime(Date date){
        SimpleDateFormat dateFm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //格式化当前系统日期
        String dateTime = dateFm.format(date);
        return dateTime;
    }

    //文章内容处理
    public static List<Article> contentHand(List<Article> articles){
        for(Article article : articles){
            //提取html代码中的纯文本 展示到首页预览  197个字
            if (StripHT(article.getArticleContent()).length()>=197) {//总字数超过197个 截取预览
                article.setArticleContent(StripHT(article.getArticleContent()).substring(0, 197));
            }else{//少于197  不截取
                article.setArticleContent(StripHT(article.getArticleContent()));
            }
        }
        return articles;
    }

    //子评论递归查询顶级评论
    static Comment comment = null;
    public static int queryTopCommentId(CommentServiceImpl commentService, int id, int pid){//查询子评论所属的最顶级评论的id
        if (pid!=0){//如果当前传递进来的父id不为0
            comment = commentService.queryCommentById(pid);
            int currentId = comment.getId();
            int currentPid = comment.getPid();//父亲的父亲..的pid
            return queryTopCommentId(commentService,currentId,currentPid);
        }else{//查询到了父节点
            return comment.getId();
        }
    }

    //盐值加密
    public static String saltEncryption(String password, String salt) throws NoSuchAlgorithmException {
//        //截取前六位作为盐
//        UUID.randomUUID().toString().substring(0,6);
        //盐值md5加密
        String current = password + salt;
        return DigestUtils.md5DigestAsHex(current.getBytes());
    }


}
