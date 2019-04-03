package com.example.luhongcheng.bean;

import java.util.ArrayList;
import java.util.List;

public class QA {
    public QA(List<String> url, String title, String content, String time, String id, List<String> my_Likes, String author_id) {
        this.url = url;
        this.title = title;
        this.content = content;
        this.time = time;
        this.item_id = id;
        this.my_likes = my_Likes;
        this.author_id = author_id;
    }


    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    String item_id;

    public String getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(String author_id) {
        this.author_id = author_id;
    }

    String author_id;
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
