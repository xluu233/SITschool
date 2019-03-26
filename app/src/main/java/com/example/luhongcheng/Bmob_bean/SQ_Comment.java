package com.example.luhongcheng.Bmob_bean;



import cn.bmob.v3.BmobObject;


public class SQ_Comment extends BmobObject {


    public UserInfo getAuthor() {
        return author;
    }

    public void setAuthor(UserInfo author) {
        this.author = author;
    }

    UserInfo author;

    public SQ getPost() {
        return post;
    }

    public void setPost(SQ post) {
        this.post = post;
    }

    SQ post;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    String content;

}
