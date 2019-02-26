package com.example.luhongcheng.Bmob_bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

public class update extends BmobObject {


    public BmobFile getAPK() {
        return APK;
    }
    public void setAPK(BmobFile APK) {
        this.APK = APK;
    }
    BmobFile APK;



    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
    String text;

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    String Code;


    public String getapkUrl(){
        return APK.getFileUrl();
    }


}
