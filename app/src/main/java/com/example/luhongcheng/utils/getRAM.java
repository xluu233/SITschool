package com.example.luhongcheng.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.util.Log;

import java.text.DecimalFormat;

public class getRAM {

    /*显示RAM的可用和总容量，RAM相当于电脑的内存条*/
    public static String showRAMInfo(Context context){
        ActivityManager am=(ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo mi=new ActivityManager.MemoryInfo();
        am.getMemoryInfo(mi);
        String[] available=fileSize(mi.availMem);
        String[] total=fileSize(mi.totalMem);
        //Log.d("ram","RAM "+available[0]+available[1]+"/"+total[0]+total[1]);
        return  total[0];
    }

    /*返回为字符串数组[0]为大小[1]为单位KB或者MB*/
    private static String[] fileSize(long size){
        String str="";
        if(size>=1000){
            str="KB";
            size/=1000;
            if(size>=1000){
                str="MB";
                size/=1000;
            }
        }
        /*将每3个数字用,分隔如:1,000*/
        DecimalFormat formatter=new DecimalFormat();
        formatter.setGroupingSize(3);
        String result[]=new String[2];
        result[0]=formatter.format(size);
        result[1]=str;
        return result;
    }

}
