package com.example.luhongcheng.OneSelf;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.luhongcheng.Bmob_bean.UserInfo;
import com.example.luhongcheng.R;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

public class MyLove extends AppCompatActivity {
    ListView guanzhu;
    ListView fensi;
    String id;
    List<String> gz = new ArrayList<>();
    List<String> fs = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mylove);
        guanzhu = (ListView)findViewById(R.id.guanzhu);
        fensi = (ListView)findViewById(R.id.fensi);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        startBmob();
    }

    private void startBmob() {
        SharedPreferences sp2=getSharedPreferences("personID",0);
        id = sp2.getString("ID","");

        BmobQuery<UserInfo> query2 = new BmobQuery<UserInfo>();
        query2.getObject(id, new QueryListener<UserInfo>() {
            @Override
            public void done(UserInfo object, BmobException e) {
                if (e == null) {

                    gz = object.getGuanzhu();
                    fs = object.getFensi();
                    mHandler.obtainMessage(0).sendToTarget();

                } else {
                    //Log.i("bmob图片", "失败：" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });

    }


    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    FSAdaper adaper1 = new FSAdaper(gz);
                    FSAdaper adaper2 = new FSAdaper(fs);

                    guanzhu.setAdapter(adaper1);
                    fensi.setAdapter(adaper2);

                    break;
                default:
                    break;
            }
        }
    };


    public class FSAdaper extends BaseAdapter {
        List<String> list;
        String icon_url;
        String nickname;
        String qianming;
        public FSAdaper(List<String> list) {
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
            FSAdaper.ViewHolder holder = null;
            if(convertView==null){
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.mylove_item, null);
                holder = new FSAdaper.ViewHolder();
                holder.icon = (ImageView) convertView.findViewById(R.id.icon);
                holder.title = (TextView) convertView.findViewById(R.id.title);
                holder.qm = (TextView) convertView.findViewById(R.id.qm);
                convertView.setTag(holder);
            }else{
                holder = (FSAdaper.ViewHolder) convertView.getTag();
            }

            BmobQuery<UserInfo> query = new BmobQuery<UserInfo>();
            final ViewHolder finalHolder1 = holder;
            query.getObject(list.get(position), new QueryListener<UserInfo>() {
                @Override
                public void done(UserInfo object, BmobException e) {
                    if (e == null) {

                        nickname = object.getNickname();
                        icon_url = object.geticonUrl();
                        qianming = object.getQM();


                        finalHolder1.title.setText(nickname);
                        finalHolder1.qm.setText(qianming);
                        Glide.with(getApplicationContext())
                                .load(icon_url)
                                .error(R.drawable.error)
                                .into(finalHolder1.icon);

                    } else {
                        //Log.i("bmob图片", "失败：" + e.getMessage() + "," + e.getErrorCode());
                    }
                }
            });


            return convertView;
        }

        class ViewHolder {
            ImageView icon;
            TextView title,qm;
        }

    }





}
