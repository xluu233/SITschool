package com.example.luhongcheng.Bmob;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

public class LOGO extends BmobObject {


    public String getLoginNum() {
        return LoginNum;
    }

    public void setLoginNum(String loginNum) {
        LoginNum = loginNum;
    }

    String LoginNum;
    public BmobFile getImage() {
        return image;
    }

    public void setImage(BmobFile image) {
        this.image = image;
    }

    BmobFile image;

    public  String getimageUrl(){
        return image.getFileUrl();
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    String Url;



}
