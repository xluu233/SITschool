package com.example.luhongcheng;

/**
 * Created by Administrator on 2017/1/21.
 */

public class dianfei {
    private String name;   //房间号
    private String a1;  //存款余额
    private String a2; //电补余额
    private String a3; //估计余额
    private String a4;  //可用电量（度）

    public dianfei(String name, String a1, String a2, String a3, String a4) {
        this.name = name;
        this.a1 = a1;
        this.a2 = a2;
        this.a3 = a3;
        this.a4 = a4;
    }


    public String getname() {
        return name;
    }

    public String geta1() {
        return a1;
    }

    public String geta2() {
        return a2;
    }

    public String geta3() {
        return a3;
    }

    public String geta4() {
        return a4;
    }

}
