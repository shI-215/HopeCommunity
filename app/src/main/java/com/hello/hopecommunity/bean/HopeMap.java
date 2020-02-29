package com.hello.hopecommunity.bean;

import java.util.List;

/**
 * Created with Android Studio.
 *
 * @Auther: Oji
 * @Date: 2020/02/29/13:27
 * @Description:
 */
public class HopeMap {

    private Active active;
    private List<Image> images;

    public Active getActive() {
        return active;
    }

    public void setActive(Active active) {
        this.active = active;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }
}
