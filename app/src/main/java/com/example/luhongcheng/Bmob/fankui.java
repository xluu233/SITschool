package com.example.luhongcheng.Bmob;

import cn.bmob.v3.BmobObject;

public class fankui extends BmobObject {
    public String getContent(String content) {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    String content;

    public String getCall() {
        return call;
    }

    public void setCall(String call) {
        this.call = call;
    }

    String call;
}
