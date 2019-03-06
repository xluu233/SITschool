package com.example.luhongcheng.Bmob_bean;



import cn.bmob.v3.BmobObject;


public class QA_Likes extends BmobObject {


    public UserInfo getAuthor() {
        return author;
    }

    public void setAuthor(UserInfo author) {
        this.author = author;
    }

    UserInfo author;

    public QA getPost() {
        return post;
    }

    public void setPost(QA post) {
        this.post = post;
    }

    QA post;


}
