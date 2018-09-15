package com.example.luhongcheng.Bmob;



import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

public class SWZL extends BmobObject {
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private String title;//标题

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    private String content;//内容


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    private String time;

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    private String adress;

    public BmobFile getIcon() {
        return icon;
    }

    public void setIcon(BmobFile icon) {
        this.icon = icon;
    }

    BmobFile icon;
    public  String getimageUrl(){
        return icon.getFileUrl();
    }


}
