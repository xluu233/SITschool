package com.example.luhongcheng;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.luhongcheng.about.about0;
import com.example.luhongcheng.about.about1;
import com.example.luhongcheng.about.about2;
import com.example.luhongcheng.about.about3;
import com.example.luhongcheng.about.about4;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.key;

/**
 * Created by alex233 on 2018/4/21.
 */

public class item13 extends AppCompatActivity {
    private ListView listview;
    private List<about> aboutList = new ArrayList<about>();

    private String[] data = {
            "功能介绍",
            "加入team",
            "检查更新",
            "反馈帮助",
            "捐赠开发者"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item13);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                item13.this, android.R.layout.simple_list_item_1,data);



        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                switch (position) {
                    case 0:
                        //版本介绍
                        Intent intent=new Intent(item13.this, about0.class);
                        startActivity(intent);
                        break;
                    case 1:
                        //转到QQ群
                        Intent intent1=new Intent(item13.this,about1.class);
                        startActivity(intent1);
                        break;
                    case 2:
                        //检查更新
                        Intent intent2=new Intent(item13.this, about2.class);
                        startActivity(intent2);
                        break;
                    case 3:
                       /* Intent intent3=new Intent(item13.this, about3.class);
                        startActivity(intent3);*/
                        Toast.makeText(item13.this,"此功能未开放", Toast.LENGTH_SHORT).show();
                        break;
                    case 4:
                       /* Intent intent4=new Intent(item13.this, about4.class);
                        startActivity(intent4);   */
                        Toast.makeText(item13.this,"赞赏未开放，可以到应用商店给个好评哟", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;


                }
            }
        });


    }

    public class about {
        private String name;
        private int  imageId;
        public about(String name,int imageId){
            this.name = name;
            this.imageId = imageId;
        }
        public String getName(){
            return  name;
        }
        public int getImageId(){
            return imageId;
        }
    }


}
