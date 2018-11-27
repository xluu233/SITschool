package com.example.luhongcheng.OAitem;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.luhongcheng.R;
import com.example.luhongcheng.TopToBottomFinishLayout;
import com.example.luhongcheng.connect_vpn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by alex233 on 2018/4/21.
 */

public class item0 extends Activity {

    GridView gridView2;
    private List<Map<String, Object>> dataList;
    protected  void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item0);
        TopToBottomFinishLayout bottomFinishLayout = (TopToBottomFinishLayout) findViewById(R.id.layout);
        bottomFinishLayout.setOnFinishListener(new TopToBottomFinishLayout.OnFinishListener() {
                    @Override
                    public void onFinish() {
                        finish();
                    }
                });

        gridView2 = (GridView)findViewById(R.id.gridview2);

        initData();
        String[] from={"ItemImage","ItemText"};
        int[] to={R.id.ItemImage,R.id.ItemText};
        SimpleAdapter adapter2=new SimpleAdapter(item0.this, dataList, R.layout.gridview_item, from, to);
        gridView2.setAdapter(adapter2);
        gridView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {
                switch (position) {
                    case 0:
                        Intent intent=new Intent(item0.this,more_item0.class);
                        startActivity(intent);
                        break;
                    case 1:
                        Intent intent1=new Intent(item0.this,more_item1.class);
                        startActivity(intent1);
                        break;
                    case 2:
                        Intent intent2=new Intent(item0.this,more_item2.class);
                        startActivity(intent2);
                        break;
                    case 3:
                        PackageManager packageManager = getPackageManager();
                        Intent intent3 = new Intent();
                        intent3 = packageManager.getLaunchIntentForPackage("com.topsec.topsap");
                        if(intent3==null){
                            Toast.makeText(getApplicationContext(), "未安装！", Toast.LENGTH_LONG).show();
                            Intent intent4= new Intent(item0.this,connect_vpn.class);
                            startActivity(intent4);
                        }else{
                            startActivity(intent3);
                        }

                        break;
                    default:
                        break;
                }
            }
        });


    }

    void initData() {
        int icno[] = { R.mipmap.jidian,R.mipmap.jihua,R.mipmap.abc,R.drawable.vpn};
        String name[]={"绩点","教学计划","校外考试成绩","内网"};

        dataList = new ArrayList<Map<String, Object>>();
        for (int i = 0; i <icno.length; i++) {
            Map<String, Object> map=new HashMap<String, Object>();
            map.put("ItemImage", icno[i]);
            map.put("ItemText",name[i]);
            dataList.add(map);
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.bottom_silent,R.anim.bottom_out);
    }

    public void close(View view) {
        this.finish();
    }


}
