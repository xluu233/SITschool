package com.example.luhongcheng;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.luhongcheng.Bmob.SWZL;

import java.util.ArrayList;
import java.util.List;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

@SuppressLint("ValidFragment")
public class ThreeFragment extends Fragment {
    private String context;
    public ThreeFragment(String context){
        this.context = context;
    }
    ListView listView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.c_fragment,container,false);
        return view;
    }

    @Override
    public void onActivityCreated( Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listView = (ListView) getActivity().findViewById(R.id.listView);
        Bmob.initialize(getActivity(), "69d2a14bfc1139c1e9af3a9678b0f1ed");
        get();
        Button send = (Button) getActivity().findViewById(R.id.bt1);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(getActivity(),send.class);
                startActivity(intent1);
            }
        });

        Button get = (Button) getActivity().findViewById(R.id.bt2);
        get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get();
            }
        });

    }


    public void get(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                BmobQuery<SWZL> query = new BmobQuery<SWZL>();
                query.findObjects(new FindListener<SWZL>(){
                    @Override
                    public void done(List<SWZL> list, BmobException e) {
                        List<SWZL> lists = new ArrayList<>();
                        if (list != null) {
                            //System.out.println("查询成功"+list.get(0).getTitle()+list.get(0).getContent()+list.get(0).getTime()+list.get(0).getAdress()+list.get(0).getIconUrl());
                            final String[] title  =  new String[list.size()];
                            final String[] content  =  new String[list.size()];
                            final String[] time  =  new String[list.size()];
                            final String[] adress  =  new String[list.size()];
                            for(int i = 0;i<list.size();i++){
                                title[i] = list.get(i).getTitle();
                                content[i] = list.get(i).getContent();
                                time[i] = list.get(i).getTime();
                                adress[i] = list.get(i).getAdress();

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
                                        convertView = inflater.inflate(R.layout.swzl_item_layout, null);//实例化一个布局文件
                                        viewHolder = new ViewHolder();
                                        viewHolder.tv_title = (TextView)convertView.findViewById(R.id.tv_title);
                                        viewHolder.tv_content = (TextView)convertView.findViewById(R.id.tv_content);
                                        viewHolder.tv_time = (TextView)convertView.findViewById(R.id.tv_time);
                                        viewHolder.tv_adress = (TextView)convertView.findViewById(R.id.tv_adress);

                                        convertView.setTag(viewHolder);
                                    }else {
                                        viewHolder = (ViewHolder) convertView.getTag();
                                    }
                                    viewHolder.tv_title.setText(title[position]);
                                    viewHolder.tv_content.setText(content[position]);
                                    viewHolder.tv_time.setText(time[position]);
                                    viewHolder.tv_adress.setText(adress[position]);

                                    return convertView;
                                }
                                class ViewHolder{
                                    TextView tv_title;
                                    TextView tv_content;
                                    TextView tv_time;
                                    TextView tv_adress;
                                }
                            }
                            listView.setAdapter(new MyAdapter(getActivity()));
                        }


                    }
                });
            }
        }); //声明一个子线程
        thread.start();


    }



}



