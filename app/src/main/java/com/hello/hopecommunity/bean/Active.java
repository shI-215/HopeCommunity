package com.hello.hopecommunity.bean;

public class Active {
    private int actId;
    private String actName;
    private String actDescribe;
    private String actAddress;
    private String actStart;
    private int actStatus;
    private int actOk;
    private User user;

    @Override
    public String toString() {
        return "Active{" +
                "actId=" + actId +
                ", actName='" + actName + '\'' +
                ", actDescribe='" + actDescribe + '\'' +
                ", actAddress='" + actAddress + '\'' +
                ", actStart='" + actStart + '\'' +
                ", actStatus=" + actStatus +
                ", actOk=" + actOk +
                ", user=" + user +
                '}';
    }

    public int getActId() {
        return actId;
    }

    public void setActId(int actId) {
        this.actId = actId;
    }

    public String getActName() {
        return actName;
    }

    public void setActName(String actName) {
        this.actName = actName;
    }

    public String getActDescribe() {
        return actDescribe;
    }

    public void setActDescribe(String actDescribe) {
        this.actDescribe = actDescribe;
    }

    public String getActAddress() {
        return actAddress;
    }

    public void setActAddress(String actAddress) {
        this.actAddress = actAddress;
    }

    public String getActStart() {
        return actStart;
    }

    public void setActStart(String actStart) {
        this.actStart = actStart;
    }

    public int getActStatus() {
        return actStatus;
    }

    public void setActStatus(int actStatus) {
        this.actStatus = actStatus;
    }

    public int getActOk() {
        return actOk;
    }

    public void setActOk(int actOk) {
        this.actOk = actOk;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
