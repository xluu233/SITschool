package com.example.luhongcheng.OneSelf;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.luhongcheng.Bmob_bean.UserInfo;
import com.example.luhongcheng.R;
import com.example.luhongcheng.View.CircleImageView;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;


public class ShowOnePerson extends AppCompatActivity {
    Toolbar toolbar;
    CircleImageView icon;
    TextView nickname,qm,friends,bt_guanzhu,xueyuan;

    private String id = null;
    private String myid = null;
    List<String> myGuanzhu = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.show_one_zheye);
        super.onCreate(savedInstanceState);
        id = getIntent().getStringExtra("id");
        icon = findViewById(R.id.show_icon);
        nickname = findViewById(R.id.show_nickname);
        qm = findViewById(R.id.show_qm);
        friends = findViewById(R.id.show_friends);
        bt_guanzhu = findViewById(R.id.show_guanzhu);
        xueyuan = findViewById(R.id.show_xueyuan);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getWindow().setStatusBarColor(getResources().getColor(R.color.colorAccent));//设置状态栏背景色


        if (id!=null){
            getData();
        }

        SharedPreferences sp=getSharedPreferences("personID",0);
        myid =  sp.getString("ID","");
        if (myid!=null){
            getMyData();
        }
    }

    private void getMyData() {
        BmobQuery<UserInfo> query = new BmobQuery<>();
        query.getObject(myid, new QueryListener<UserInfo>() {
            @Override
            public void done(UserInfo userInfo, BmobException e) {
                if (e==null){
                    myGuanzhu = userInfo.getGuanzhu();
                    if (myGuanzhu.contains(id)){
                        bt_guanzhu.setText("已关注");
                        bt_guanzhu.setClickable(false);
                    }else {
                        bt_guanzhu.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (!id.equals(myid)){
                                    myGuanzhu.add(id);
                                    setGuanzhu();
                                }else {
                                    bt_guanzhu.setVisibility(View.GONE);
                                }
                            }
                        });
                    }
                }
            }
        });
    }

    private void setGuanzhu() {
        UserInfo object = new  UserInfo();
        object.setGuanzhu(myGuanzhu);
        object.update(myid, new UpdateListener() {
            @Override
            public void done(BmobException e1) {
                if(e1==null){
                    Toast.makeText(getApplicationContext(), "关注成功", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(), "关注失败", Toast.LENGTH_SHORT).show();
                    //Log.i("bmob","更新失败："+e1.getMessage()+","+e1.getErrorCode());
                }
            }
        });

    }

    private void getData() {

        BmobQuery<UserInfo> query = new BmobQuery<>();
        query.getObject(id, new QueryListener<UserInfo>() {
            @Override
            public void done(UserInfo userInfo, BmobException e) {
                if (e==null){
                    if(userInfo.getNickname().length() == 0){
                        nickname.setText(userInfo.getName());
                    }else {
                        nickname.setText(userInfo.getNickname());
                    }

                    if (userInfo.getQM().length() != 0){
                        qm.setText(userInfo.getQM());
                    }

                    xueyuan.setText(userInfo.getXueyuan());
                    friends.setText("关注："+userInfo.getGuanzhu().size()+"     粉丝："+userInfo.getFensi().size());

                    if (userInfo.geticonUrl().length() != 0){
                        Glide.with(getApplicationContext())
                                .load(userInfo.geticonUrl())
                                .apply(new RequestOptions().placeholder(R.drawable.loading))
                                .apply(new RequestOptions() .error(R.drawable.error))
                                .apply(new RequestOptions().fitCenter())
                                .into(icon);
                    }

                }
            }
        });
    }
}
