package com.example.luhongcheng.bean;

import java.util.List;

public class SQ {
    public SQ(String author_id, List<String> url, String content, String time, String item_id, List<String> my_likes, List<String> my_collection, List<String> my_GuanZhu) {
        this.author_id = author_id;
        this.url = url;
        this.content = content;
        this.item_id = item_id;
        this.my_likes = my_likes;
        this.my_collection = my_collection;
        this.time = time;
        this.my_guanzhu = my_GuanZhu;

    }

    private String zan_nums;
    private String nickname;
    private String icon_url;
    private String qm;
    private String content;
    private String item_id;
    private String author_id;
    private String time;
    private List<String> my_guanzhu;
    private List<String> my_collection;
    private List<String> my_likes;
    private List<String> url;

    public List<String> getMy_guanzhu() {
        return my_guanzhu;
    }

    public void setMy_guanzhu(List<String> my_guanzhu) {
        this.my_guanzhu = my_guanzhu;
    }

    public String getZan_nums() {
        return zan_nums;
    }

    public void setZan_nums(String zan_nums) {
        this.zan_nums = zan_nums;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getQm() {
        return qm;
    }

    public void setQm(String qm) {
        this.qm = qm;
    }

    public String getIcon_url() {
        return icon_url;
    }

    public void setIcon_url(String icon_url) {
        this.icon_url = icon_url;
    }

    public List<String> getMy_collection() {
        return my_collection;
    }

    public void setMy_collection(List<String> my_collection) {
        this.my_collection = my_collection;
    }

    public String getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(String author_id) {
        this.author_id = author_id;
    }

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getUrl() {
        return url;
    }

    public void setUrl(List<String> url) {
        this.url = url;
    }

    public List<String> getMy_likes() {
        return my_likes;
    }

    public void setMy_likes(List<String> my_likes) {
        this.my_likes = my_likes;
    }




}
