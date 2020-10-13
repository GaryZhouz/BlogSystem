package com.wzz.pojo;

import java.util.Date;

import static com.wzz.utils.MyUtils.formatTime;

public class Article {

    //文章id  主键
    private int id;

    //文章作者
    private String author;

    //文章标题
    private  String articleTitle;

    //文章内容
    private String articleContent;

    //文章类型
    private String articleType;

    //文章标签
    private String articleTags;

    //创建时间
    private String publishDate;

    //最后一次更改时间
    private String updateDate;

    //文章喜欢数
    private int likes;

    public Article(String author, String articleTitle, String articleContent, String articleType, String articleTags) {
        this.author = author;
        this.articleTitle = articleTitle;
        this.articleContent = articleContent;
        this.articleType = articleType;
        this.articleTags = articleTags;
        //自己传当前的时间
        this.updateDate = formatTime(new Date());
    }

    public Article() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public String getArticleContent() {
        return articleContent;
    }

    public void setArticleContent(String articleContent) {
        this.articleContent = articleContent;
    }

    public String getArticleType() {
        return articleType;
    }

    public void setArticleType(String articleType) {
        this.articleType = articleType;
    }

    public String getArticleTags() {
        return articleTags;
    }

    public void setArticleTags(String articleTags) {
        this.articleTags = articleTags;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", author='" + author + '\'' +
                ", articleTitle='" + articleTitle + '\'' +
                ", articleContent='" + articleContent + '\'' +
                ", articleType='" + articleType + '\'' +
                ", articleTags='" + articleTags + '\'' +
                ", publishDate='" + publishDate + '\'' +
                ", updateDate='" + updateDate + '\'' +
                ", likes=" + likes +
                '}';
    }
}
