package com.example.luhongcheng;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class ImageFullDisplay extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.imagefull);
        ImageView iv = (ImageView)findViewById(R.id.iv);
        String url = getIntent().getStringExtra("url2");

        Glide.with(this)
                .load(url)
                .placeholder(R.drawable.loading)
                .error(R.drawable.error)
                .fitCenter()
                .into(iv);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.black));//设置状态栏背景色
        }

        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                close();
            }

        });
    }

    private void close() {
        this.finish();
    }


}
