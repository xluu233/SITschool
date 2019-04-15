package com.example.luhongcheng.bean;

public class PingLun {
    public PingLun(String content, String objectId,String author_id,String time) {
        this.content = content;
        this.item_id = objectId;
        this.author_id = author_id;
        this.time = time;
    }


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    String time;
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    String content;//评论


    public String getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(String author_id) {
        this.author_id = author_id;
    }

    String author_id;//评论人的id

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    String item_id;
}
