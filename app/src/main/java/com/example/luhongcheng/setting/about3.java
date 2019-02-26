package com.example.luhongcheng.setting;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.luhongcheng.R;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by alex233 on 2018/5/9.
 */

public class about3  extends AppCompatActivity {
    EditText mContent;
    EditText mCall;
    @SuppressLint("WrongViewCast")
    @Override
    protected  void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about3);
        Bmob.initialize(this, "69d2a14bfc1139c1e9af3a9678b0f1ed");
        mContent = (EditText) findViewById(R.id.content);
        mCall = (EditText) findViewById(R.id.cell);

        Button send = (Button) findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String call = mCall.getText().toString();
                String content = mContent.getText().toString();
                if (call.length() != 0 && content.length() != 0){
                    com.example.luhongcheng.Bmob_bean.fankui p2 = new com.example.luhongcheng.Bmob_bean.fankui();
                    p2.setCall(call);
                    p2.setContent(content);
                    p2.save(new SaveListener<String>() {
                        @Override
                        public void done(String objectId, BmobException e) {
                            if(e==null){
                                Toast.makeText(about3.this,"反馈发送成功",Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(about3.this,"反馈发送失败，请检查网络",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else {
                    Toast.makeText(about3.this,"请输入完整信息",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
