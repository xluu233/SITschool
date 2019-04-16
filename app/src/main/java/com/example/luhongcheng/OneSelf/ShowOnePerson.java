package com.example.luhongcheng.OneSelf;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;

import com.example.luhongcheng.R;

public class ShowOnePerson extends AppCompatActivity {
    Toolbar toolbar;
    CollapsingToolbarLayout layout;

    private String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.show_one_zheye);
        super.onCreate(savedInstanceState);
        Intent intent = new Intent();
        id = intent.getStringExtra("id");
        layout = findViewById(R.id.toolbar_layout);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        layout.setTitle("shawanyia");

    }
}
