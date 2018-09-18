package com.example.luhongcheng.Bmob;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

public class _User  extends BmobUser{
    public String getXueyuan() {
        return xueyuan;
    }

    public void setXueyuan(String xueyuan) {
        this.xueyuan = xueyuan;
    }

    String xueyuan;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    String name;

    public String getQianming() {
        return qianming;
    }

    public void setQianming(String qianming) {
        this.qianming = qianming;
    }

    String qianming;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    String nickname;

    public BmobFile getImage() {
        return image;
    }

    public void setImage(BmobFile image) {
        this.image = image;
    }

    private BmobFile image;

    public  String geticonUrl(){
        return image.getFileUrl();
    }

    public String getMima() {
        return mima;
    }

    public void setMima(String mima) {
        this.mima = mima;
    }

    String mima;

    public BmobFile getLink() {
        return link;
    }

    public void setLink(BmobFile link) {
        this.link = link;
    }

    BmobFile link;

    public  String getlinkUrl(){
        return link.getFileUrl();
    }

}
