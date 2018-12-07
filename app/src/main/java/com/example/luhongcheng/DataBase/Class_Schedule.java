package com.example.luhongcheng.DataBase;

import org.litepal.crud.LitePalSupport;

public class Class_Schedule extends LitePalSupport {

    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
    }

    int name;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    String content;
}
