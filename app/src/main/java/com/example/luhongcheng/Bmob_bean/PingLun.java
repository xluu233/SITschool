package com.example.luhongcheng.Bmob_bean;

import java.util.List;

import cn.bmob.v3.BmobObject;

public class PingLun extends BmobObject {
    public String getSsID() {
        return ssID;
    }

    public void setSsID(String ssID) {
        this.ssID = ssID;
    }

    private  String ssID;

    public List<String> getPl() {
        return pl;
    }

    public void setPl(List<String> pl) {
        this.pl = pl;
    }

    private List<String> pl;
}
