package com.example.luhongcheng.SQ;

import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.luhongcheng.R;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

public class ssDisPlay extends AppCompatActivity {
    String personID,objectID,iconUrl,imageUrl,content,nickname,qm;
    String label;
    ImageView im_image,im_icon;
    TextView tv_content,tv_nickname,tv_qm,tv_label;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ssdisplay);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.green_300));//设置状态栏背景色
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        personID = getIntent().getStringExtra("personID");
        objectID = getIntent().getStringExtra("objectID");

        iconUrl = getIntent().getStringExtra("iconUrl");
        imageUrl = getIntent().getStringExtra("imageUrl");

        content = getIntent().getStringExtra("content");
        nickname = getIntent().getStringExtra("nickname");
        qm = getIntent().getStringExtra("qm");
        label = getIntent().getStringExtra("label");

        tv_content =(TextView)findViewById(R.id.content);
        tv_nickname =(TextView)findViewById(R.id.nickname);
        tv_qm =(TextView)findViewById(R.id.qm);
        tv_label =(TextView)findViewById(R.id.label);

        im_image = (ImageView)findViewById(R.id.img);
        im_icon = (ImageView)findViewById(R.id.icon);

        tv_content.setText(content);
        tv_qm.setText(qm);
        tv_nickname.setText(nickname);

        if (label != null){

            if (label.equals("A1")){
                tv_label.setText("#今日最佳#");
            }else if (label.equals("A2")){
                tv_label.setText("#一日三餐#");
            }else if (label.equals("A3")){
                tv_label.setText("#表白墙#");
            } else if (label.equals("A4")){
                tv_label.setText("#众话说#");
            } else if (label.equals("A5")){
                tv_label.setText("#工具推荐#");
            } else if (label.equals("A6")){
                tv_label.setText("#学习交流#");
            } else if (label.equals("A7")){
                tv_label.setText("#安利#");
            } else if (label.equals("A8")){
                tv_label.setText("#需求池#");
            } else if (label.equals("A9")){
                tv_label.setText("#考研党#");
            } else if (label.equals("A10")){
                tv_label.setText("#周边推荐#");
            } else if (label.equals("A11")){
                tv_label.setText("#每日一听#");
            }else if (label.equals("A12")){
                tv_label.setText("#晨读打卡#");
            } else if (label.equals("A13")){
                tv_label.setText("#谈天说地#");
            }

        }

        Glide.with(this)
                .load(iconUrl)
                .placeholder(R.drawable.loading)
                .error(R.drawable.error)
                .fitCenter()
                .into(im_icon);

        Glide.with(this)
                .load(imageUrl)
                .placeholder(R.drawable.loading)
                .error(R.drawable.error)
                .fitCenter()
                .into(im_image);





    }
}