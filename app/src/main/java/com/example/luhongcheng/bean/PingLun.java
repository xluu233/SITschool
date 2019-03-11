package com.example.luhongcheng.bean;

public class PingLun {
    public PingLun(String iconUrl, String nickname, String content, String objectId) {
        this.icon_url = iconUrl;
        this.nickname = nickname;
        this.content = content;
        this.author_id = objectId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    String content;//评论

    public String getIcon_url() {
        return icon_url;
    }

    public void setIcon_url(String icon_url) {
        this.icon_url = icon_url;
    }

    String icon_url;//头像

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    String nickname;//昵称

    public String getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(String author_id) {
        this.author_id = author_id;
    }

    String author_id;//评论人的id
}
