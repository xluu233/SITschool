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
import com.bumptech.glide.request.RequestOptions;
import com.example.luhongcheng.R;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

public class ssDisPlay extends AppCompatActivity {
    String personID,objectID,iconUrl,imageUrl,content,nickname,qm,zan_nums;
    String label;
    ImageView im_image,im_icon;
    TextView tv_content,tv_nickname,tv_qm,tv_label,zan;
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
        zan_nums =getIntent().getStringExtra("zan_nums");
        qm = getIntent().getStringExtra("qm");
        label = getIntent().getStringExtra("label");

        zan = (TextView)findViewById(R.id.zan_nums);
        tv_content =(TextView)findViewById(R.id.content);
        tv_nickname =(TextView)findViewById(R.id.nickname);
        tv_qm =(TextView)findViewById(R.id.qm);
        tv_label =(TextView)findViewById(R.id.label);

        im_image = (ImageView)findViewById(R.id.img);
        im_icon = (ImageView)findViewById(R.id.icon);

        zan.setText(zan_nums);
        tv_content.setText(content);
        tv_qm.setText(qm);
        tv_nickname.setText(nickname);

        if (label != null){

            if (label.equals("A1")){
                tv_label.setText(R.string.A1);
            }else if (label.equals("A2")){
                tv_label.setText(R.string.A6);
            }else if (label.equals("A3")){
                tv_label.setText(R.string.A3);
            } else if (label.equals("A4")){
                tv_label.setText(R.string.A2);
            } else if (label.equals("A5")){
                tv_label.setText(R.string.A8);
            } else if (label.equals("A6")){
                tv_label.setText(R.string.A4);
            } else if (label.equals("A7")){
                tv_label.setText(R.string.A5);
            } else if (label.equals("A8")){
                tv_label.setText(R.string.A7);
            } else if (label.equals("A9")){
                tv_label.setText(R.string.A9);
            } else if (label.equals("A10")){
                tv_label.setText(R.string.A10);
            } else if (label.equals("A11")){
                tv_label.setText(R.string.A11);
            }else if (label.equals("A12")){
                tv_label.setText(R.string.A12);
            } else if (label.equals("A13")){
                tv_label.setText("#谈天说地#");
            }

        }

        Glide.with(this)
                .load(iconUrl)
                .apply(new RequestOptions().placeholder(R.drawable.loading))
                .apply(new RequestOptions() .error(R.drawable.error))
                .apply(new RequestOptions() .fitCenter())
                .into(im_icon);

        Glide.with(this)
                .load(imageUrl)
                .apply(new RequestOptions().placeholder(R.drawable.loading))
                .apply(new RequestOptions() .error(R.drawable.error))
                .apply(new RequestOptions() .fitCenter())
                .into(im_image);





    }
}
