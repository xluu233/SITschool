package com.example.luhongcheng;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.luhongcheng.Bmob.SWZL;
import com.example.luhongcheng.Bmob.Tips;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class MoreTips extends Activity {

    ListView listView;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.moretips);
        listView = (ListView) findViewById(R.id.list_view);
        Bmob.initialize(getApplicationContext(), "69d2a14bfc1139c1e9af3a9678b0f1ed");
        getText();
    }

    private void getText() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                BmobQuery<Tips> query = new BmobQuery<Tips>();
                query.findObjects(new FindListener<Tips>() {
                    @Override
                    public void done(List<Tips> list, BmobException e) {
                        List<Tips> lists = new ArrayList<>();
                        if (list != null) {
                            final String[] title  =  new String[list.size()];
                            final String[] time  =  new String[list.size()];
                            for(int i = 0;i<list.size();i++){
                                title[i] = list.get(i).getTips();
                                time[i] = list.get(i).getCreatedAt();
                            }


                            class MyAdapter extends BaseAdapter {
                                private Context context ;
                                public MyAdapter(Context context){
                                    this.context = context;
                                }

                                @Override
                                public int getCount() {
                                    return title.length;
                                }

                                @Override
                                public Object getItem(int position) {
                                    return title[position];
                                }

                                @Override
                                public long getItemId(int position) {
                                    return position;
                                }

                                @Override
                                public View getView(int position, View convertView, ViewGroup parent) {
                                    ViewHolder viewHolder;
                                    if (convertView == null){
                                        LayoutInflater inflater = LayoutInflater.from(context);
                                        convertView = inflater.inflate(R.layout.tips_item, null);//实例化一个布局文件
                                        viewHolder = new ViewHolder();
                                        viewHolder.tv_title = (TextView)convertView.findViewById(R.id.tv);
                                        viewHolder.time = (TextView)convertView.findViewById(R.id.time);


                                        convertView.setTag(viewHolder);
                                    }else {
                                        viewHolder = (ViewHolder) convertView.getTag();
                                    }
                                    viewHolder.tv_title.setText(title[position]);
                                    viewHolder.time.setText(time[position]);
                                    return convertView;
                                }
                                class ViewHolder{
                                    TextView tv_title;
                                    TextView time;
                                }
                            }
                            listView.setAdapter(new MyAdapter(getApplicationContext()));
                        }
                    }
                });
            }
        });
        thread.start();
    }
}
