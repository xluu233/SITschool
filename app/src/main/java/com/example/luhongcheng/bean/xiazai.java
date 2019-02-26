package com.example.luhongcheng.bean;

public class xiazai {
    public xiazai(String title, String titleUrl) {
        this.title = title;
        this.url = titleUrl;

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    String title;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    String url;
}
