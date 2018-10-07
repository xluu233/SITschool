package com.example.luhongcheng;

/**
 * Created by alex233 on 2018/6/7.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;

public class ImageLunhuanAdapter extends PagerAdapter {
    private List<ImageView> data;
    Context context;
    public ImageLunhuanAdapter(List<ImageView> data, Context context) {
        this.data=data;
        this.context=context;
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
        final ImageView image=data.get(position%data.size());
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
                        break;
                    case 1:
                      //  System.out.println("第er个");
                        break;
                    case 2:
                       // System.out.println("第san个");
                        break;
                    case 3:
                       // System.out.println("第si个");
                        break;
                }
            }
        });
        container.addView(data.get(position%data.size()));
        return data.get(position%data.size());
    }



}