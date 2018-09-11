package com.example.luhongcheng;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

public class OneSelf extends Activity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.oneself);
        Toast.makeText(getApplicationContext(),"个人中心暂未开放，敬请期待",Toast.LENGTH_SHORT).show();
    }

}
