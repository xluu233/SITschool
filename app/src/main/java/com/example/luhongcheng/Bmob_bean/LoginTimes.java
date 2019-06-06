package com.example.luhongcheng.Bmob_bean;

import cn.bmob.v3.BmobObject;

public class LoginTimes extends BmobObject {


    String user;
    String PhoneModel;
    String API;
    String AndroidVersion;
    String devices_id;
    String Phone;
    String AppVersion;



    public String getAppVersion() {
        return AppVersion;
    }

    public void setAppVersion(String appVersion) {
        AppVersion = appVersion;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }


    public String getDevices_id() {
        return devices_id;
    }

    public void setDevices_id(String devices_id) {
        this.devices_id = devices_id;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getPhoneModel() {
        return PhoneModel;
    }

    public void setPhoneModel(String phoneModel) {
        PhoneModel = phoneModel;
    }

    public String getAPI() {
        return API;
    }

    public void setAPI(String API) {
        this.API = API;
    }

    public String getAndroidVersion() {
        return AndroidVersion;
    }

    public void setAndroidVersion(String androidVersion) {
        AndroidVersion = androidVersion;
    }


}
