package com.hello.hopecommunity.bean;

public class User {

    /**
     * userAddress : 广东省-珠海市-香洲区
     * userAutograph : 123
     * userBirthday : 2019-02-02
     * userId : 1
     * userName : 12345678900
     * userPicture : 123
     * userPwd : 12345678900
     * userSex : 女
     * userTel : 12345678901
     */

    private String userAddress;
    private String userAutograph;
    private String userBirthday;
    private int userId;
    private String userName;
    private String userPicture;
    private String userPwd;
    private String userSex;
    private String userTel;

    @Override
    public String toString() {
        return "User{" +
                "userAddress='" + userAddress + '\'' +
                ", userAutograph='" + userAutograph + '\'' +
                ", userBirthday='" + userBirthday + '\'' +
                ", userId=" + userId +
                ", userName='" + userName + '\'' +
                ", userPicture='" + userPicture + '\'' +
                ", userPwd='" + userPwd + '\'' +
                ", userSex='" + userSex + '\'' +
                ", userTel='" + userTel + '\'' +
                '}';
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getUserAutograph() {
        return userAutograph;
    }

    public void setUserAutograph(String userAutograph) {
        this.userAutograph = userAutograph;
    }

    public String getUserBirthday() {
        return userBirthday;
    }

    public void setUserBirthday(String userBirthday) {
        this.userBirthday = userBirthday;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPicture() {
        return userPicture;
    }

    public void setUserPicture(String userPicture) {
        this.userPicture = userPicture;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public String getUserSex() {
        return userSex;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex;
    }

    public String getUserTel() {
        return userTel;
    }

    public void setUserTel(String userTel) {
        this.userTel = userTel;
    }
}
