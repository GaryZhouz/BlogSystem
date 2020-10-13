package com.wzz.pojo;

public class FriendLink {

    //友联id
    private int id;

    //博客名字
    private String  blogName;

    //博客链接
    private String url;

    public FriendLink() {
    }

    public FriendLink(int id, String blogName, String url) {
        this.id = id;
        this.blogName = blogName;
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBlogName() {
        return blogName;
    }

    public void setBlogName(String blogName) {
        this.blogName = blogName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "friendLink{" +
                "id=" + id +
                ", blogName='" + blogName + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
