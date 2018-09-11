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
    public Object instantiateItem(ViewGroup container, int position) {
        final ImageView image=data.get(position%data.size());
        //如果View已经在之前添加到了一个父组件，则必须先remove，否则会抛出IllegalStateException。
        ViewParent vp=image.getParent();
        if(vp!=null){
            ViewGroup vg=(ViewGroup) vp;
            vg.removeView(image);
        }
        image.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                /*
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, "SITschool上应大学生助手集成OA系统部分查询及资讯功能，可在Android端实现查询成绩，查询电费，查询第二课堂，查询考试安排等等一系列功能，目前在酷安已发布，快来下载吧：https://www.coolapk.com/apk/187672");
                intent.setType("text/plain");
                context.startActivity(Intent.createChooser(intent, "分享到"));
                */
            }
        });
        container.addView(data.get(position%data.size()));
        return data.get(position%data.size());
    }



}