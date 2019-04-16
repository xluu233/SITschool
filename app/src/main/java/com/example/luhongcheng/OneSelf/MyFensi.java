package com.example.luhongcheng.OneSelf;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.luhongcheng.Bmob_bean.UserInfo;
import com.example.luhongcheng.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

public class MyFensi extends AppCompatActivity {

    ListView listView;
    SmartRefreshLayout refreshLayout;
    Toolbar toolbar;
    List<String> list = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myfriends);
        listView = findViewById(R.id.friends_lv);
        refreshLayout = findViewById(R.id.friends_refresh);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("我的粉丝");
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        getMyData();

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getMyData();
                refreshLayout.finishRefresh(2000);
            }
        });
    }

    private void getMyData() {
        SharedPreferences sp2=getSharedPreferences("personID",0);
        String id = sp2.getString("ID","");

        if (Objects.requireNonNull(id).length()!=0){
            BmobQuery<UserInfo> query = new BmobQuery<>();
            query.getObject(id, new QueryListener<UserInfo>() {
                @Override
                public void done(UserInfo object, BmobException e) {
                    if (e == null) {
                        list = object.getFensi();
                        if (list.size() !=0){
                            mHandler.obtainMessage(0).sendToTarget();
                        }
                    }
                }
            });
        }else {
            Toast.makeText(getApplicationContext(),"no personID",Toast.LENGTH_SHORT).show();
        }


    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    FriendsAdaper adaper = new FriendsAdaper(list);
                    listView.setAdapter(adaper);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent(getApplicationContext(), ShowOnePerson.class);
                            intent.putExtra("id",list.get(position));
                            startActivity(intent);
                        }
                    });
                    break;
                default:
                    break;
            }
        }
    };


    public class FriendsAdaper extends BaseAdapter {
        List<String> list;
        String icon_url;
        String nickname;
        String qianming;

        FriendsAdaper(List<String> list) {
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

        @SuppressLint({"ViewHolder", "InflateParams"})
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final MyFensi.FriendsAdaper.ViewHolder holder = new MyFensi.FriendsAdaper.ViewHolder();
            if(convertView==null){
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.person_item, null);
                holder.icon = convertView.findViewById(R.id.icon);
                holder.nickname = convertView.findViewById(R.id.nickname);
                holder.qm = convertView.findViewById(R.id.qm);
                holder.other = convertView.findViewById(R.id.other);
                convertView.setTag(holder);
            }


            BmobQuery<UserInfo> query = new BmobQuery<>();
            query.getObject(list.get(position), new QueryListener<UserInfo>() {
                @Override
                public void done(UserInfo object, BmobException e) {
                    if (e == null) {

                        nickname = object.getNickname();
                        icon_url = object.geticonUrl();
                        qianming = object.getQM();


                        holder.nickname.setText(nickname);
                        holder.qm.setText(qianming);
                        Glide.with(getApplicationContext())
                                .load(icon_url)
                                .apply(new RequestOptions().placeholder(R.drawable.loading))
                                .apply(new RequestOptions() .error(R.drawable.error))
                                .into(holder.icon);

                        holder.other.setText("关注："+object.getGuanzhu().size()+"    粉丝："+object.getFensi().size());

                    }
                }
            });


            return convertView;
        }

        class ViewHolder {
            ImageView icon;
            TextView nickname,qm,other;
        }

    }



}
