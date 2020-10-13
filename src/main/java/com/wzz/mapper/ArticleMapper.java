package com.wzz.mapper;

import com.wzz.pojo.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface ArticleMapper {

    //新增一篇文章
    int addArticle(Article article);

    //查询所有文章
    List<Article> queryAllArticle();

    //分页查询limit
    List<Article> queryArticleByLimit(@Param("start") int start,@Param("pageSize") int pageSize);

    //根据文章id查询文章
    Article queryArticleById(@Param("id") int id);

    //分组查询文章类型
    List<Map<String,String>> queryArticleType();

    //根据文章类型查询文章 分页
    List<Article> queryArticleByType(@Param("type") String type,@Param("start") int start,@Param("pageSize") int pageSize);

    //根据文章id删除文章
    int deleteArticle(@Param("id") int id);

    //根据id更新文章信息
    int updateArticle(@Param("id") int id,@Param("title")String title,@Param("author")String author,
                      @Param("content")String content,@Param("tags")String tags,
                      @Param("type")String type,@Param("likes")int likes ,@Param("updateTime") String updateTime);

    //文章检索并分页limit
    List<Article> queryArticleByTitleLimit(@Param("title") String title,@Param("start") int start,@Param("pageSize") int pageSize);

    //查询最近的五篇文章
    List<Article> queryArticleRecent();

    //根据标签查询文章
    List<Article> queryArticleByTags(@Param("tag")String tag,@Param("start") int start,@Param("pageSize") int pageSize);

    //查询标签的数
    List<Map<String,Object>> queryAllTags();

    //点赞数增加
    int updateLikes(@Param("id") int id);
}
