package com.example.luhongcheng.bean;

import java.util.ArrayList;
import java.util.List;

public class SQ_QA {
    public SQ_QA(List<String> url, String title, String content, String time, String id, List<String> my_Likes) {
        this.url = url;
        this.title = title;
        this.content = content;
        this.time = time;
        this.id = id;
        this.my_likes = my_Likes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    String id;
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

    List<String> url = new ArrayList<>(); //图片集合


    public List<String> getMy_likes() {
        return my_likes;
    }

    public void setMy_likes(List<String> my_likes) {
        this.my_likes = my_likes;
    }

    List<String> my_likes; //用户的喜欢合集


}
