package com.wzz.pojo;


public class User {

    //用户id
    private int id;

    //用户角色(1普通用户  2管理员)
    private int roleId;

    //用户名 用于登陆
    private String username;

    //密码
    private String password;

    //盐值
    private String salt;

    //真实姓名
    private String trueName;

    //性别(0女 1男)
    private int gender;

    //qq
    private String qq;

    //wechat
    private String wechat;

    //个人简介
    private String personalBrief;

    //个人头像url
    private String avatarImgUrl;

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getSalt() {
        return salt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTrueName() {
        return trueName;
    }

    public void setTrueName(String trueName) {
        this.trueName = trueName;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }


    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }


    public String getPersonalBrief() {
        return personalBrief;
    }

    public void setPersonalBrief(String personalBrief) {
        this.personalBrief = personalBrief;
    }

    public String getAvatarImgUrl() {
        return avatarImgUrl;
    }

    public void setAvatarImgUrl(String avatarImgUrl) {
        this.avatarImgUrl = avatarImgUrl;
    }

    public User() {
    }

    public User(String username, String password, String trueName, int gender, String qq, String wechat, String personalBrief, String avatarImgUrl) {
        this.username = username;
        this.password = password;
        this.trueName = trueName;
        this.gender = gender;
        this.qq = qq;
        this.wechat = wechat;
        this.personalBrief = personalBrief;
        this.avatarImgUrl = avatarImgUrl;
    }

    public User(int id, int roleId, String username, String password, String salt, String trueName, int gender, String qq, String wechat, String personalBrief, String avatarImgUrl) {
        this.id = id;
        this.roleId = roleId;
        this.username = username;
        this.password = password;
        this.salt = salt;
        this.trueName = trueName;
        this.gender = gender;
        this.qq = qq;
        this.wechat = wechat;
        this.personalBrief = personalBrief;
        this.avatarImgUrl = avatarImgUrl;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", roleId=" + roleId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", trueName='" + trueName + '\'' +
                ", gender=" + gender +
                ", qq='" + qq + '\'' +
                ", wechat='" + wechat + '\'' +
                ", personalBrief='" + personalBrief + '\'' +
                ", avatarImgUrl='" + avatarImgUrl + '\'' +
                '}';
    }
}
