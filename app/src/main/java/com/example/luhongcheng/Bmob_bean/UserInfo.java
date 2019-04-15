package com.example.luhongcheng.Bmob_bean;

import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

public class UserInfo extends BmobObject {
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    String nickname;

    public String getQQ() {
        return QQ;
    }

    public void setQQ(String QQ) {
        this.QQ = QQ;
    }

    String QQ;

    public String getQM() {
        return QM;
    }

    public void setQM(String QM) {
        this.QM = QM;
    }

    String QM;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    String name;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    String ID;

    public BmobFile getIcon() {
        return icon;
    }

    public void setIcon(BmobFile icon) {
        this.icon = icon;
    }

    BmobFile icon;
    public  String geticonUrl(){
        return  icon.getUrl();
    }

    public String getPassid() {
        return passid;
    }

    public void setPassid(String passid) {
        this.passid = passid;
    }

    String passid;

    public String getXueyuan() {
        return xueyuan;
    }

    public void setXueyuan(String xueyuan) {
        this.xueyuan = xueyuan;
    }

    String xueyuan;

    public List<String> getFensi() {
        return fensi;
    }

    public void setFensi(List<String> fensi) {
        this.fensi = fensi;
    }

    List<String> fensi;//粉丝列表

    public List<String> getGuanzhu() {
        return guanzhu;
    }

    public void setGuanzhu(List<String> guanzhu) {
        this.guanzhu = guanzhu;
    }

    List<String> guanzhu;//关注列表


    public List<String> getMy_SS() {
        return My_SS;
    }

    public void setMy_SS(List<String> my_SS) {
        My_SS = my_SS;
    }

    List<String> My_SS;//说说

    public List<String> getMy_Collection() {
        return My_Collection;
    }

    public void setMy_Collection(List<String> my_Collection) {
        My_Collection = my_Collection;
    }

    List<String> My_Collection;//我的收藏

    public List<String> getMy_Likes() {
        return My_Likes;
    }

    public void setMy_Likes(List<String> my_Likes) {
        My_Likes = my_Likes;
    }

    List<String> My_Likes;//我的喜欢

    public String getJifen() {
        return jifen;
    }

    public void setJifen(String jifen) {
        this.jifen = jifen;
    }

    String jifen;
}
