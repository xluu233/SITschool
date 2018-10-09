package com.example.luhongcheng;

/**
 * Created by alex233 on 2018/6/7.
 */

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;

import java.util.List;

public class ImageLunhuanAdapter2 extends PagerAdapter {
    private List<ImageView> data2;
    Context context2;
    String[]  click;
    public ImageLunhuanAdapter2(List<ImageView> data2, Context context2, String[] s) {
        this.data2=data2;
        this.context2=context2;
        this.click = s;
    }

    @Override
    public int getCount() {
        //返回一个无穷大的值，
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {

        return arg0==arg1;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //注意，这里什么也不做!!!

    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        final ImageView image=data2.get(position%data2.size());
        //如果View已经在之前添加到了一个父组件，则必须先remove，否则会抛出IllegalStateException。
        ViewParent vp=image.getParent();
        if(vp!=null){
            ViewGroup vg=(ViewGroup) vp;
            vg.removeView(image);
        }
        image.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                // System.out.println("第si个");
                switch (position) {
                    case 0:
                        // System.out.println("第一个");
                        Intent intent= new Intent();
                        intent.setAction("android.intent.action.VIEW");
                        intent.setData(Uri.parse(click[0]));
                        //调用activity的上下文，在adapter中跳转
                        context2.startActivity(intent);
                        break;
                    case 1:
                        Intent intent2= new Intent();
                        intent2.setAction("android.intent.action.VIEW");
                        intent2.setData(Uri.parse(click[1]));
                        context2.startActivity(intent2);
                        //  System.out.println("第er个");
                        break;
                    case 2:
                        Intent intent3= new Intent();
                        intent3.setAction("android.intent.action.VIEW");
                        intent3.setData(Uri.parse(click[2]));
                        context2.startActivity(intent3);
                        // System.out.println("第san个");
                        break;
                    case 3:
                        Intent intent4= new Intent();
                        intent4.setAction("android.intent.action.VIEW");
                        intent4.setData(Uri.parse(click[3]));
                        context2.startActivity(intent4);
                        // System.out.println("第si个");
                        break;
                }
            }
        });



        container.addView(data2.get(position%data2.size()));
        return data2.get(position%data2.size());
    }



}