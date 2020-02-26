package com.hello.hopecommunity.bean;

public class Image {
    private int imgId;
    private String imgPath;
    private int imgType;
    private Active active;

    @Override
    public String toString() {
        return "image{" +
                "imgId=" + imgId +
                ", imgPath='" + imgPath + '\'' +
                ", imgType=" + imgType +
                ", active=" + active +
                '}';
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public int getImgType() {
        return imgType;
    }

    public void setImgType(int imgType) {
        this.imgType = imgType;
    }

    public Active getActive() {
        return active;
    }

    public void setActive(Active active) {
        this.active = active;
    }
}
