package com.example.luhongcheng.bean;

public class Friends {


    public Friends(String name,String xueyuan, String qm, String id,String icon_url) {
        this.name = name;
        this.qm = qm;
        this.person_id = id;
        this.xueyuan = xueyuan;
        this.icon_url = icon_url;
    }

    String person_id;
    String nickname;
    String qm;
    String icon_url;
    String xueyuan;
    String name;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPerson_id() {
        return person_id;
    }

    public void setPerson_id(String person_id) {
        this.person_id = person_id;
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

    public String getXueyuan() {
        return xueyuan;
    }

    public void setXueyuan(String xueyuan) {
        this.xueyuan = xueyuan;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



}
