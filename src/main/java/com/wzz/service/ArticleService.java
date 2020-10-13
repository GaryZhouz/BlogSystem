package com.wzz.service;

import com.wzz.pojo.Article;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ArticleService {

    //新增一篇文章
    int addArticle(Article article);

    //查询所有文章
    List<Article> queryAllArticle();

    //分页查询limit
    List<Article> queryArticleByLimit(int start,int pageSize);

    //根据文章id查询文章
    Article queryArticleById(int id);

    //分组查询文章类型
    List<Map<String,String>> queryArticleType();

    //根据文章类型查询文章并支持分页
    List<Article> queryArticleByType(String type,int start,int pageSize);


    //根据文章id删除文章
    int deleteArticle(int id);

    //根据id更新文章信息
    int updateArticle(int id,String title,String author, String content,String tags, String type,int likes,String updateTime);

    //文章检索并分页limit
    List<Article> queryArticleByTitleLimit(String title,int start,int pageSize);

    //查询最近的五篇文章
    List<Article> queryArticleRecent();

    //根据标签查询文章
    List<Article> queryArticleByTags(String tag,int start,int pageSize);

    //查询标签的数
    List<Map<String,Object>> queryAllTags();

    //点赞数增加
    int updateLikes(int id);
}
