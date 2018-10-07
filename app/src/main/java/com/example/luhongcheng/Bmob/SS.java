package com.example.luhongcheng.Bmob;

import java.lang.reflect.Array;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

public class SS extends BmobObject {
    Array pinglun;
    Number zan;
    BmobFile img;
    String label;
    String ID;
    String content;

    public BmobFile getImg() {
        return img;
    }

    public void setImg(BmobFile img) {
        this.img = img;
    }

    public  String getimgUrl(){
        return  img.getUrl();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }


    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }


}
