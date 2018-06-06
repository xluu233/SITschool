package com.example.luhongcheng;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by alex233 on 2018/4/21.
 */

public class item2 extends AppCompatActivity {

    private Spinner spinner;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item2);


        spinner = (Spinner) findViewById(R.id.title2);
        final String arr[] = new String[]{"第一周", "第二周", "第三周", "第四周", "第五周", "第六周", "第七周","第八周","第九周","第十周",
                "十一周", "十二周","十三周","十四周","十五周","十六周","十七周","十八周","十九周","二十周",
                "二十一","二十二"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arr);
        spinner.setAdapter(arrayAdapter);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Spinner spinner = (Spinner) parent;
                Toast.makeText(getApplicationContext(), "你选择了" + spinner.getItemAtPosition(position), Toast.LENGTH_SHORT).show();
            } //给选项设置点击事件，并通过Toast显示

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getApplicationContext(), "没有改变处理", Toast.LENGTH_SHORT).show();
            }
        });



    }

    private class WebPlugin {

        @JavascriptInterface
        public void test() {
            Log.e("miquan", "kkkkkk");
            Toast.makeText(item2.this, "test toast ", Toast.LENGTH_SHORT).show();
        }

        @JavascriptInterface
        public String test2() {
            return "something";
        }
    }
}