package com.wzz.pojo;

public class LeaveMessage {

    //留言id
    private int id;

    //留言标题
    private String title;

    //留言内容
    private String content;

    //留言人姓名
    private String name;

    //留言人邮箱
    private String email;

    //留言时间
    private String time;


    public LeaveMessage() {
    }

    public LeaveMessage(int id, String title, String content, String name, String email, String time) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.name = name;
        this.email = email;
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "LeaveMessage{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
