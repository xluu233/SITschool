package com.example.luhongcheng.Bmob;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

public class diudiu extends BmobObject {
    public String getQQ() {
        return QQ;
    }

    public void setQQ(String QQ) {
        this.QQ = QQ;
    }

    String QQ;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    String title;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    String content;

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
}
