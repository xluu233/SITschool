package com.example.luhongcheng;

public class Friends {
    public Friends(String nk, String qm, String fs, String icon,String id) {
        this.nickname = nk;
        this.qm = qm;
        this.fensi = fs;
        this.icon_url =  icon;
        this.person_id = id;
    }

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

    String person_id;
    String nickname;

    public String getQm() {
        return qm;
    }

    public void setQm(String qm) {
        this.qm = qm;
    }

    String qm;

    public String getFensi() {
        return fensi;
    }

    public void setFensi(String fensi) {
        this.fensi = fensi;
    }

    String fensi;

    public String getIcon_url() {
        return icon_url;
    }

    public void setIcon_url(String icon_url) {
        this.icon_url = icon_url;
    }

    String icon_url;
}
