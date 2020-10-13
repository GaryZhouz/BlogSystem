package com.wzz.pojo;

public class Comment {

    //评论id
    private int id;

    //父评论id
    private int pid;

    //评论的文章id
    private int articleId;

    //评论者的username
    private String  answererName;

    //被评论者的username
    private String respondentName;

    //评论时间
    private String commentDate;

    //评论内容
    private String commentContent;

    //评论者的头像信息
    private String avatarImgUrl;

    public Comment() {
    }

    public Comment(int id, int pid, int articleId, String answererName, String respondentName, String commentDate, String commentContent, String avatarImgUrl) {
        this.id = id;
        this.pid = pid;
        this.articleId = articleId;
        this.answererName = answererName;
        this.respondentName = respondentName;
        this.commentDate = commentDate;
        this.commentContent = commentContent;
        this.avatarImgUrl = avatarImgUrl;
    }

    public String getRespondentName() {
        return respondentName;
    }

    public void setRespondentName(String respondentName) {
        this.respondentName = respondentName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    public String getAnswererName() {
        return answererName;
    }

    public void setAnswererName(String answererName) {
        this.answererName = answererName;
    }

    public String getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(String commentDate) {
        this.commentDate = commentDate;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public String getAvatarImgUrl() {
        return avatarImgUrl;
    }

    public void setAvatarImgUrl(String avatarImgUrl) {
        this.avatarImgUrl = avatarImgUrl;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", pid=" + pid +
                ", articleId=" + articleId +
                ", answererName='" + answererName + '\'' +
                ", commentDate='" + commentDate + '\'' +
                ", commentContent='" + commentContent + '\'' +
                ", avatarImgUrl='" + avatarImgUrl + '\'' +
                '}';
    }
}
