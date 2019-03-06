package com.example.luhongcheng.bean;

import java.util.ArrayList;
import java.util.List;

public class SQ_QA {
    public SQ_QA(List<String> url, String title, String content, String time) {
        this.url = url;
        this.title = title;
        this.content = content;
        this.time = time;
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

    String title;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    String content;

    public List<String> getUrl() {
        return url;
    }

    public void setUrl(List<String> url) {
        this.url = url;
    }

    List<String> url = new ArrayList<>();
}
