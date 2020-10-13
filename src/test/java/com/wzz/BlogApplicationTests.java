package com.wzz;

import com.wzz.pojo.Comment;
import com.wzz.service.CommentServiceImpl;
import com.wzz.utils.MyUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

@SpringBootTest
class BlogApplicationTests {

    @Autowired
    private CommentServiceImpl commentService;

    @Test
    void contextLoads() {
//        List<Comment> topComments = commentService.queryAllCommentPid(1);
//        System.out.println(topComments);
        List<Map<Integer, Comment>> childComment = new ArrayList();//存放所有子评论的顶级评论id 和他的信息
        Map<Integer,Comment> map = new HashMap();//存放子评论的最顶级的评论的id,和它的信息
        List<Comment> childComments = commentService.queryAllComment(1);
        int count = 0;//帮助计数
        for (Comment comment : childComments) {
            if (comment.getPid()!=0){//不是顶级评论
                int i = MyUtils.queryTopCommentId(commentService, comment.getId(), comment.getPid());
                Comment targetComment = commentService.queryCommentById(comment.getId());
                targetComment.setPid(i);//设置当前的子评论id为最顶级的评论id  以便以传递前端判断
                map.put(count,targetComment);
                count++;
            }
        }
        childComment.add(map);
        System.out.println(childComment.toString());
    }

}

