package com.example.luhongcheng.OneSelf;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.luhongcheng.R;

public class ShowOnePerson extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.show_one_zheye);
        super.onCreate(savedInstanceState);

        Intent intent = new Intent();
        String id = intent.getStringExtra("person_id");

    }
}
