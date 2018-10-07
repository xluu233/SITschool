package com.example.luhongcheng.Bmob;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

public class _User  extends BmobUser{
    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    String ID;


    //这个ID是UserInfo表的objectID，将其存在_User中


}
