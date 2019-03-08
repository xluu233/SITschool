package com.example.luhongcheng.SIT_SQ_other;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.luhongcheng.Bmob_bean.QA;
import com.example.luhongcheng.Bmob_bean.UserInfo;
import com.example.luhongcheng.ImageView.CircleImageView;
import com.example.luhongcheng.R;
import com.example.luhongcheng.View.NineGridLayout;
import com.example.luhongcheng.View.NineGridTestLayout;
import com.example.luhongcheng.bean.SQ_QA;
import com.example.luhongcheng.utils.BaseStatusBarActivity;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;

public class SQ_SecondLayout extends BaseStatusBarActivity {
    String item_id;
    String user_id;
    String my_id;


    private List<String> urllist = new ArrayList<>(); //图片地址合集
    private List<String> my_Likes = new ArrayList<>(); //我的喜欢合集
    private List<String> my_guanzhu = new ArrayList<>(); //我的关注合集

    CircleImageView icon;
    TextView nickname,qm;

    TextView ss_title,ss_content,ss_time,guanzhu;
    ImageView zan;
    NineGridTestLayout gridview;
    ListView listView;
    EditText msg;
    ImageView send;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.sq_second_layout);
        super.onCreate(savedInstanceState);
        initView();
        item_id = getIntent().getStringExtra("item_id");
        user_id = getIntent().getStringExtra("user_id");

        get_UserInfo();



        SharedPreferences sp=getSharedPreferences("personID",0);
        my_id =  sp.getString("ID","");
        if (user_id == my_id){
            guanzhu.setVisibility(View.GONE);
        }

    }

    private void initView() {
        icon = findViewById(R.id.icon);
        nickname = findViewById(R.id.nickname);
        qm = findViewById(R.id.qm);
        ss_title = findViewById(R.id.title);
        ss_content = findViewById(R.id.content);
        ss_time = findViewById(R.id.time);
        zan = findViewById(R.id.zan);
        guanzhu = findViewById(R.id.secondlayout_guanzhu);
        gridview = findViewById(R.id.layout_nine_grid);
        listView = findViewById(R.id.comment_listview);
        msg = findViewById(R.id.msg);
        send = findViewById(R.id.send_msg);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    //当前作者的信息
    private void get_UserInfo() {
        if (user_id.length() != 0 || item_id.length() !=0){
            Thread collection = new Thread(new Runnable() {
                @Override
                public void run() {
                    BmobQuery<UserInfo> query2 = new BmobQuery<UserInfo>();
                    query2.getObject(user_id, new QueryListener<UserInfo>() {
                        @Override
                        public void done(UserInfo userInfo, BmobException e) {
                            if (e == null) {


                                String icon_url = userInfo.geticonUrl();
                                String qm2 = userInfo.getQM();
                                String nickname2 = userInfo.getNickname();
                                if (nickname2 == null){
                                    nickname2 = userInfo.getName().replace("你好：","");
                                }

                                getDate(); //说说的信息
                                get_MyInfo(); //我的信息

                                Glide.with(getApplicationContext())
                                        .load(icon_url)
                                        .apply(new RequestOptions().placeholder(R.drawable.loading))
                                        .apply(new RequestOptions() .error(R.drawable.error))
                                        .into(icon);
                                nickname.setText(nickname2);
                                qm.setText(qm2);



                            } else {
                                //Log.i("bmob图片", "失败：" + e.getMessage() + "," + e.getErrorCode());
                            }
                        }
                    });
                }
            });
            collection.start();
        }else {
            Toast.makeText(this,"没有获取到ID",Toast.LENGTH_SHORT).show();
        }
    }

    private void get_MyInfo() {
        if (my_id.length() !=0){
            Thread collection_info = new Thread(new Runnable() {
                @Override
                public void run() {
                    BmobQuery<UserInfo> query2 = new BmobQuery<UserInfo>();
                    query2.getObject(my_id, new QueryListener<UserInfo>() {
                        @Override
                        public void done(UserInfo userInfo, BmobException e) {
                            if (e == null) {

                                my_guanzhu = userInfo.getGuanzhu();
                                my_Likes = userInfo.getMy_Likes();

                                if (my_guanzhu.contains(user_id)){
                                    guanzhu.setText("已关注");
                                }


                                if (my_Likes.contains(item_id)){
                                    zan.setImageResource(R.drawable.sq_zan_2);
                                }



                            } else {
                                //Log.i("bmob图片", "失败：" + e.getMessage() + "," + e.getErrorCode());
                            }
                        }
                    });
                }
            });
            collection_info.start();
        }else {
            Toast.makeText(this,"没有获取到ID",Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    protected int getStatusBarColor() {
        return getResources().getColor(R.color.moren_back);
    }


    private void getDate() {
        Thread qa_second = new Thread(new Runnable() {
            @Override
            public void run() {
                BmobQuery<QA> query = new BmobQuery<QA>();
                query.getObject(item_id, new QueryListener<QA>() {
                    @Override
                    public void done(QA qa, BmobException e) {
                        String title = qa.getTitle();
                        String content = qa.getContent();
                        String time = qa.getCreatedAt();

                        urllist = qa.getImage();

                        ss_title.setText(title);
                        ss_content.setText(content);
                        ss_time.setText(time);

                        if (urllist != null){
                            gridview.setUrlList(urllist);
                        }

                    }
                });


            }
        });
        qa_second.start();

    }

    public void guanzhu(View view) {
    }
}
