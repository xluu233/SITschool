
package com.example.luhongcheng;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by alex233 on 2018/4/21.
 */

public class item14 extends AppCompatActivity {

    /*以下是GridView定义的*/
    private GridView gridView;
    private List<Map<String, Object>> dataList;
    private SimpleAdapter adapter1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item14);

             /*gridview.java*/
        gridView = (GridView) findViewById(R.id.gridview);
        //初始化数据
        initData();

        String[] from={"ItemImage","ItemText"};
        int[] to={R.id.ItemImage,R.id.ItemText};
        adapter1=new SimpleAdapter(this, dataList, R.layout.gridview_item, from, to);
        gridView.setAdapter(adapter1);

   /* 给item设置点击事件*/
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {
                switch (position) {
                    case 0:
                        Intent intent0 = new Intent();
                        intent0.setData(Uri.parse("http://pao.sit.edu.cn/"));
                        intent0.setAction(Intent.ACTION_VIEW);
                        startActivity(intent0);
                        break;
                    case 1:
                        Intent intent1 = new Intent();
                        intent1.setData(Uri.parse("http://di.sit.edu.cn/"));
                        intent1.setAction(Intent.ACTION_VIEW);
                        startActivity(intent1);
                        break;
                    case 2:
                        Intent intent2 = new Intent();
                        intent2.setData(Uri.parse("http://od.sit.edu.cn/"));
                        intent2.setAction(Intent.ACTION_VIEW);
                        startActivity(intent2);
                        break;
                    case 3:
                        Intent intent3 = new Intent();
                        intent3.setData(Uri.parse("http://pubo.sit.edu.cn/"));
                        intent3.setAction(Intent.ACTION_VIEW);
                        startActivity(intent3);
                        break;
                    case 4:
                        Intent intent4 = new Intent();
                        intent4.setData(Uri.parse("http://sao.sit.edu.cn/"));
                        intent4.setAction(Intent.ACTION_VIEW);
                        startActivity(intent4);
                        break;
                    case 5:
                        Intent intent5 = new Intent();
                        intent5.setData(Uri.parse("http://opaf.sit.edu.cn"));
                        intent5.setAction(Intent.ACTION_VIEW);
                        startActivity(intent5);
                        break;
                    case 6:
                        Intent intent6 = new Intent();
                        intent6.setData(Uri.parse("http://lu.sit.edu.cn/"));
                        intent6.setAction(Intent.ACTION_VIEW);
                        startActivity(intent6);
                        break;
                    case 7:
                        Intent intent7 = new Intent();
                        intent7.setData(Uri.parse("http://youth.sit.edu.cn/"));
                        intent7.setAction(Intent.ACTION_VIEW);
                        startActivity(intent7);
                        break;
                    case 8:
                        Intent intent8 = new Intent();
                        intent8.setData(Uri.parse("http://po.sit.edu.cn/"));
                        intent8.setAction(Intent.ACTION_VIEW);
                        startActivity(intent8);
                        break;
                    case 9:
                        Intent intent9 = new Intent();
                        intent9.setData(Uri.parse("http://rdo.sit.edu.cn/"));
                        intent9.setAction(Intent.ACTION_VIEW);
                        startActivity(intent9);
                        break;
                    case 10:
                        Intent intent10 = new Intent();
                        intent10.setData(Uri.parse("http://tao.sit.edu.cn/"));
                        intent10.setAction(Intent.ACTION_VIEW);
                        startActivity(intent10);
                        break;
                    case 11:
                        Intent intent11 = new Intent();
                        intent11.setData(Uri.parse("http://cwc1.sit.edu.cn/"));
                        intent11.setAction(Intent.ACTION_VIEW);
                        startActivity(intent11);
                        break;
                    case 12:
                        Intent intent12 = new Intent();
                        intent12.setData(Uri.parse("http://log1.sit.edu.cn/"));
                        intent12.setAction(Intent.ACTION_VIEW);
                        startActivity(intent12);
                        break;
                    case 13:
                        Intent intent13 = new Intent();
                        intent13.setData(Uri.parse("http://lib3.sit.edu.cn/"));//Url 就是你要打开的网址
                        intent13.setAction(Intent.ACTION_VIEW);
                        startActivity(intent13);
                        break;
                    case 14:
                        Intent intent14 = new Intent();
                        intent14.setData(Uri.parse("http://arc.sit.edu.cn/"));//Url 就是你要打开的网址
                        intent14.setAction(Intent.ACTION_VIEW);
                        startActivity(intent14);
                        break;
                    case 15:
                        Intent intent15 = new Intent();
                        intent15.setData(Uri.parse("http://cbmo.sit.edu.cn/"));//Url 就是你要打开的网址
                        intent15.setAction(Intent.ACTION_VIEW);
                        startActivity(intent15);
                        break;
                    case 16:
                        Intent intent16 = new Intent();
                        intent16.setData(Uri.parse("http://hepri.sit.edu.cn/"));//Url 就是你要打开的网址
                        intent16.setAction(Intent.ACTION_VIEW);
                        startActivity(intent16);
                        break;
                    case 17:
                        Intent intent17 = new Intent();
                        intent17.setData(Uri.parse("http://log1.sit.edu.cn/s/60/t/244/main.htm"));//Url 就是你要打开的网址
                        intent17.setAction(Intent.ACTION_VIEW);
                        startActivity(intent17);
                        break;
                    case 18:
                        Intent intent18 = new Intent();
                        intent18.setData(Uri.parse("https://www.yiban.cn/login?go=http%3A%2F%2Fwww.yiban.cn%2F"));//Url 就是你要打开的网址
                        intent18.setAction(Intent.ACTION_VIEW);
                        startActivity(intent18);
                        break;
                    default:
                        break;
                }
            }
        });
        /*点击事件设置完毕*/
    }



    void initData() {
        //图标
        int icno[] = { R.mipmap.list23,R.mipmap.list24,R.mipmap.list23,R.mipmap.list24,R.mipmap.list23,
                R.mipmap.list1,R.mipmap.list3,R.mipmap.list1,R.mipmap.list3,R.mipmap.list1,
                R.mipmap.list23,R.mipmap.list24,R.mipmap.list23,R.mipmap.list24,R.mipmap.list23,
                R.mipmap.list3,R.mipmap.list1,R.mipmap.list3,R.mipmap.list24};
        //图标下的文字
        String name[]={
                "党委办公室",//http://pao.sit.edu.cn/
                "纪委",//http://di.sit.edu.cn/
                "组织部、统战部",//http://od.sit.edu.cn/
                "宣传部",//http://pubo.sit.edu.cn/
                "学生处 ",//http://sao.sit.edu.cn/
                "人民武装部",//http://opaf.sit.edu.cn/
                "工会",//http://lu.sit.edu.cn/
                "团委",//http://youth.sit.edu.cn/
                "校长办公室",//http://po.sit.edu.cn/
                "科学技术处",//http://rdo.sit.edu.cn/
                "教务处",//http://tao.sit.edu.cn/
                "财务处",//http://cwc1.sit.edu.cn/
                "后勤保障处",//http://log1.sit.edu.cn/
                "图书馆",//http://lib3.sit.edu.cn/
                "档案馆",//http://arc.sit.edu.cn/
                "徐汇区管理委员会",//http://cbmo.sit.edu.cn/
                "科学建设办公室",//http://hepri.sit.edu.cn/
                "门诊部",//http://log1.sit.edu.cn/s/60/t/244/main.htm
                "易班"
        };
        dataList = new ArrayList<Map<String, Object>>();
        for (int i = 0; i <icno.length; i++) {
            Map<String, Object> map=new HashMap<String, Object>();
            map.put("ItemImage", icno[i]);
            map.put("ItemText",name[i]);
            dataList.add(map);
        }
    }



}