package com.weifupro.bean;

/**
 * Created by "huangsays"  on 2017/7/7.19:06"huangays@gmail.com"
 */

public class User {
    private int id;
    private String userId;
    private String passWord;
    private String job;
    private String nickName;
    private int sex;
    private String img;
    private String phoneImg;
    private String area;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getPhoneImg() {
        return phoneImg;
    }

    public void setPhoneImg(String phoneImg) {
        this.phoneImg = phoneImg;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", passWord='" + passWord + '\'' +
                ", job='" + job + '\'' +
                ", nickName='" + nickName + '\'' +
                ", sex=" + sex +
                ", img='" + img + '\'' +
                ", phoneImg='" + phoneImg + '\'' +
                ", area='" + area + '\'' +
                '}';
    }
}
