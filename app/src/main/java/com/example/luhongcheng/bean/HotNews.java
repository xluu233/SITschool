package com.example.luhongcheng.bean;

public class HotNews {
    String image;
    String url;
    String title;

    public HotNews(String title, String image, String time, String url) {
        this.title = title;
        this.time = time;
        this.image = image;
        this.url = url;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    String time;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
