package com.example.luhongcheng.Bmob;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

public class SQVP extends BmobObject {


   public String getUrl() {
        return url;
   }


    public void setUrl(String url) {
        this.url = url;
    }

    String url;//点击链接

    public BmobFile getImage() {
        return image;
    }

    public void setImage(BmobFile image) {
        this.image = image;
    }

    BmobFile image;//图片链接

    public String getImgUrl() {
        return image.getUrl();
    }
}
