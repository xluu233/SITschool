package com.example.luhongcheng.Bmob_bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

public class vp_one extends BmobObject {


    public BmobFile getImage() {
        return image;
    }

    public void setImage(BmobFile image) {
        this.image = image;
    }

    BmobFile image;

    public  String getImageUrl(){
        return image.getFileUrl();
    }

}
