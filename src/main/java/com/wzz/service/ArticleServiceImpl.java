package com.wzz.service;

import com.wzz.mapper.ArticleMapper;
import com.wzz.pojo.Article;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ArticleServiceImpl implements ArticleService{

    @Autowired
    private ArticleMapper articleMapper;

    //新增一篇文章
    @Override
    public int addArticle(Article article) {
        return articleMapper.addArticle(article);
    }

    //查询所有书籍信息
    @Override
    public List<Article> queryAllArticle() {
        return articleMapper.queryAllArticle();
    }

    //分页查询书籍
    @Override
    public List<Article> queryArticleByLimit(int start, int pageSize) {
        return articleMapper.queryArticleByLimit(start,pageSize);
    }

    //根据id查询文章
    @Override
    public Article queryArticleById(int id) {
        return articleMapper.queryArticleById(id);
    }

    //文章分类查询
    @Override
    public List<Map<String, String>> queryArticleType() {
        return articleMapper.queryArticleType();
    }

    //根据文章类型查询
    @Override
    public List<Article> queryArticleByType(String type, int start, int pageSize) {
        return articleMapper.queryArticleByType(type,start,pageSize);
    }

    //根据id删除文章
    @Override
    public int deleteArticle(int id) {
        return articleMapper.deleteArticle(id);
    }

    //根据id更新文章信息
    @Override
    public int updateArticle(int id, String title, String author, String content, String tags, String type, int likes, String updateTime) {
        return articleMapper.updateArticle(id, title, author, content, tags, type, likes,updateTime);
    }

    //文章检索并分页limit
    @Override
    public List<Article> queryArticleByTitleLimit(String title, int start, int pageSize) {
        return articleMapper.queryArticleByTitleLimit(title,start,pageSize);
    }

    //查询最近的五篇文章
    @Override
    public List<Article> queryArticleRecent() {
        return articleMapper.queryArticleRecent();
    }

    //根据标签查询文章
    @Override
    public List<Article> queryArticleByTags(String tag, int start, int pageSize) {
        return articleMapper.queryArticleByTags(tag, start, pageSize);
    }

    //查询标签的数
    @Override
    public List<Map<String, Object>> queryAllTags() {
        return articleMapper.queryAllTags();
    }

    //点赞数增加
    @Override
    public int updateLikes(int id) {
        return articleMapper.updateLikes(id);
    }
}
