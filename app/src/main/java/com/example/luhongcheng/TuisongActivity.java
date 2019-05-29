package com.example.luhongcheng;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by alex233 on 2018/6/15.
 */

public class TuisongActivity extends Activity{

    String Url;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tuisong);
        TextView title = (TextView) findViewById(R.id.title) ;
        TextView content = (TextView) findViewById(R.id.content);

        Intent intent1 =getIntent();
        /*取出Intent中附加的数据*/
        String a = intent1.getStringExtra("title");
        String b = intent1.getStringExtra("content");

        title.setText(a);
        content.setText(b);

        final Matcher m = Pattern.compile("(?i)http://[^\u4e00-\u9fa5]+").matcher(b);
        while(m.find()){
            Url = m.group();

            content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), WebDisplay.class);
                    intent.putExtra("news_url",Url);
                    intent.putExtra("title","推送");
                    startActivity(intent);
                }
            });
        }



    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            Intent intent = new Intent(getApplicationContext(),MainFragmentActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
