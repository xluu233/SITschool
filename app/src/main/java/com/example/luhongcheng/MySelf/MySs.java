package com.example.luhongcheng.MySelf;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.luhongcheng.Bmob.SS;
import com.example.luhongcheng.Bmob.UserInfo;
import com.example.luhongcheng.ImageFullDisplay;
import com.example.luhongcheng.MBox.MBoxItem;
import com.example.luhongcheng.R;
import com.example.luhongcheng.SQ.PingLun;
import com.example.luhongcheng.SQ.SSS;
import com.example.luhongcheng.SQ.ssDisPlay;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

public class MySs extends AppCompatActivity {
    ListView mlistView;
    List<SSS> slist2 = new ArrayList<>();
    String you_id;

    String icon_url;
    String nickname;
    String qm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myss);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        cn.bmob.v3.Bmob.initialize(this, "69d2a14bfc1139c1e9af3a9678b0f1ed");
        icon_url = getIntent().getStringExtra("icon_url");
        nickname = getIntent().getStringExtra("nickname");
        qm = getIntent().getStringExtra("qm");

        SharedPreferences sp2=getSharedPreferences("personID",0);
        you_id = sp2.getString("ID","");

        mlistView = (ListView)findViewById(R.id.ss_listView2);
        initData2();
    }

    private void initData2() {
        BmobQuery<com.example.luhongcheng.Bmob.SS> find_myself = new BmobQuery<>();
        find_myself.addWhereEqualTo("ID", you_id);
        find_myself.order("-createdAt");
        find_myself.setLimit(100);
        find_myself.findObjects(new FindListener<com.example.luhongcheng.Bmob.SS>(){
            @Override
            public void done(List<com.example.luhongcheng.Bmob.SS> list, BmobException e) {
                List<com.example.luhongcheng.Bmob.SS> lists = new ArrayList<>();
                if (e == null){
                    if (list != null) {
                        final String[] content = new String[list.size()];
                        final String[] image   = new String[list.size()];
                        final String[] ssID    = new String[list.size()];
                        final String[] label   = new String[list.size()];
                        final String[] time    = new String[list.size()];
                        final String[] zan     = new String[list.size()];

                        for(int i = 0;i<list.size();i++){
                            content[i] = list.get(i).getContent();
                            image[i] = list.get(i).getimgUrl();
                            ssID[i] = list.get(i).getObjectId();
                            label[i] = list.get(i).getLabel();
                            time[i] = list.get(i).getCreatedAt();
                            zan[i] = list.get(i).getZan();

                            slist2.add(new SSS(content[i],image[i],ssID[i],label[i],time[i],zan[i]));
                            Log.d("xixi",content[i]+image[i]+ssID[i]+label[i]+time[i]+zan[i]);
                        }
                        mHandler3.obtainMessage(0).sendToTarget();
                        Toast.makeText(getApplicationContext(),"查询成功",Toast.LENGTH_SHORT).show();
                    }

                }else {
                    Toast.makeText(getApplicationContext(),"查询失败",Toast.LENGTH_SHORT).show();
                }

            }
        });


    }



    @SuppressLint("HandlerLeak")
    private Handler mHandler3 = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    // SSAdaper adapter = new SSAdaper(slist);
                    mlistView.setAdapter(new  SSAdaper2(slist2));
                    setListViewHeightBasedOnChildren2( mlistView);
                    break;

                default:
                    break;
            }
        }

    };


    /**
     * 动态设置ListView的高度
     * 因为Scrollview嵌套listview只显示一行
     * @param listView
     */
    public static void setListViewHeightBasedOnChildren2(ListView listView) {
        if(listView == null) return;
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }
        int totalHeight = 0;

        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        // totalHeight  =listAdapter.getCount()*250;
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight +(listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }


    public class SSAdaper2 extends BaseAdapter {
        private List<SSS> list;
        public SSAdaper2(List<SSS> list) {
            super();
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            SSAdaper2.ViewHolder holder = null;
            if(convertView==null){
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.ss_listview_item, null);
                holder = new SSAdaper2.ViewHolder();
                holder.pinglun =(ImageView)convertView.findViewById(R.id.pinglun);
                holder.zan_nums = (TextView)convertView.findViewById(R.id.zan_nums);
                holder.zan = (ImageView) convertView.findViewById(R.id.zan);
                holder.img = (ImageView) convertView.findViewById(R.id.img);
                holder.icon = (ImageView) convertView.findViewById(R.id.icon);
                holder.title = (TextView) convertView.findViewById(R.id.content);
                holder.nk = (TextView) convertView.findViewById(R.id.nickname);
                holder.qmm = (TextView) convertView.findViewById(R.id.qm);
                holder.tv_label = (TextView) convertView.findViewById(R.id.label);
                convertView.setTag(holder);
            }else{
                holder = (SSAdaper2.ViewHolder) convertView.getTag();
            }
            final SSS news = list.get(position);
            holder.title.setText(news.getTitle());
            holder.nk.setText(nickname);
            holder.qmm.setText(qm);
            holder.zan_nums.setText(String.valueOf(news.getZan()));


            holder.img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(),ImageFullDisplay.class);
                    intent.putExtra("url2",news.getImageUrl());
                    startActivity(intent);
                }
            });

            holder.icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(),ImageFullDisplay.class);
                    intent.putExtra("url2",news.getIconUrl());
                    startActivity(intent);
                }
            });

            holder.pinglun.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(),PingLun.class);
                    intent.putExtra("pinglun",news.getPersonID());
                    startActivity(intent);
                }
            });

            String label = news.getLabel();
            if (label != null){
                if (label.equals("A1")){
                    holder.tv_label.setText("#今日最佳#");
                }else if (label.equals("A2")){
                    holder.tv_label.setText("#一日三餐#");
                }else if (label.equals("A3")){
                    holder.tv_label.setText("#表白墙#");
                } else if (label.equals("A4")){
                    holder.tv_label.setText("#众话说#");
                } else if (label.equals("A5")){
                    holder.tv_label.setText("#工具推荐#");
                } else if (label.equals("A6")){
                    holder.tv_label.setText("#学习交流#");
                } else if (label.equals("A7")){
                    holder.tv_label.setText("#安利#");
                } else if (label.equals("A8")){
                    holder.tv_label.setText("#需求池#");
                } else if (label.equals("A9")){
                    holder.tv_label.setText("#考研党#");
                } else if (label.equals("A10")){
                    holder.tv_label.setText("#周边推荐#");
                } else if (label.equals("A11")){
                    holder.tv_label.setText("#每日一听#");
                }else if (label.equals("A12")){
                    holder.tv_label.setText("#晨读打卡#");
                } else if (label.equals("A13")){
                    holder.tv_label.setText("#谈天说地#");
                }
            }

            Glide.with(getApplicationContext())
                    .load(news.getImageUrl())
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.error)
                    .override(600, 200)
                    .into(holder.img);


            Glide.with(getApplicationContext())
                    .load(icon_url)
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.error)
                    .fitCenter()
                    .into(holder.icon);

            return convertView;
        }

        class ViewHolder {
            ImageView img,icon;
            TextView title,nk,qmm,tv_label;
            ImageView zan;
            TextView zan_nums;
            ImageView pinglun;
        }

    }



}
