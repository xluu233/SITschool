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

public class item9 extends AppCompatActivity {

    /*以下是GridView定义的*/
    private GridView gridView;
    private List<Map<String, Object>> dataList;
    private SimpleAdapter adapter1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item9);

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
                        intent0.setData(Uri.parse("http://cs.sit.edu.cn/"));//Url 就是你要打开的网址
                        intent0.setAction(Intent.ACTION_VIEW);
                        startActivity(intent0); //启动浏览器
                        break;
                    case 1:
                        Intent intent1 = new Intent();
                        intent1.setData(Uri.parse("http://me.sit.edu.cn"));//Url 就是你要打开的网址
                        intent1.setAction(Intent.ACTION_VIEW);
                        startActivity(intent1); //启动浏览器
                        break;
                    case 2:
                        Intent intent2 = new Intent();
                        intent2.setData(Uri.parse("http://consafe.sit.edu.cn/"));//Url 就是你要打开的网址
                        intent2.setAction(Intent.ACTION_VIEW);
                        startActivity(intent2); //启动浏览器
                        break;
                    case 3:
                        Intent intent3 = new Intent();
                        intent3.setData(Uri.parse("http://parfum.sit.edu.cn"));//Url 就是你要打开的网址
                        intent3.setAction(Intent.ACTION_VIEW);
                        startActivity(intent3); //启动浏览器
                        break;
                    case 4:
                        Intent intent4 = new Intent();
                        intent4.setData(Uri.parse("http://artdes.sit.edu.cn"));//Url 就是你要打开的网址
                        intent4.setAction(Intent.ACTION_VIEW);
                        startActivity(intent4); //启动浏览器
                        break;
                    case 5:
                        Intent intent5 = new Intent();
                        intent5.setData(Uri.parse("http://sem.sit.edu.cn/index.asp"));//Url 就是你要打开的网址
                        intent5.setAction(Intent.ACTION_VIEW);
                        startActivity(intent5); //启动浏览器
                        break;
                    case 6:
                        Intent intent6 = new Intent();
                        intent6.setData(Uri.parse("http://materials.sit.edu.cn"));//Url 就是你要打开的网址
                        intent6.setAction(Intent.ACTION_VIEW);
                        startActivity(intent6); //启动浏览器
                        break;
                    case 7:
                        Intent intent7 = new Intent();
                        intent7.setData(Uri.parse("http://chenv.sit.edu.cn"));//Url 就是你要打开的网址
                        intent7.setAction(Intent.ACTION_VIEW);
                        startActivity(intent7); //启动浏览器
                        break;
                    case 8:
                        Intent intent8 = new Intent();
                        intent8.setData(Uri.parse("http://fl.sit.edu.cn"));//Url 就是你要打开的网址
                        intent8.setAction(Intent.ACTION_VIEW);
                        startActivity(intent8); //启动浏览器
                        break;
                    case 9:
                        Intent intent9 = new Intent();
                        intent9.setData(Uri.parse("http://humanity.sit.edu.cn/"));//Url 就是你要打开的网址
                        intent9.setAction(Intent.ACTION_VIEW);
                        startActivity(intent9); //启动浏览器
                        break;
                    case 10:
                        Intent intent10 = new Intent();
                        intent10.setData(Uri.parse("http://mks.sit.edu.cn"));//Url 就是你要打开的网址
                        intent10.setAction(Intent.ACTION_VIEW);
                        startActivity(intent10); //启动浏览器
                        break;
                    case 11:
                        Intent intent11 = new Intent();
                        intent11.setData(Uri.parse("http://pro.sit.edu.cn"));//Url 就是你要打开的网址
                        intent11.setAction(Intent.ACTION_VIEW);
                        startActivity(intent11); //启动浏览器
                        break;
                    case 12:
                        Intent intent12 = new Intent();
                        intent12.setData(Uri.parse("http://rt.sit.edu.cn"));//Url 就是你要打开的网址
                        intent12.setAction(Intent.ACTION_VIEW);
                        startActivity(intent12); //启动浏览器
                        break;
                    case 13:
                        Intent intent13 = new Intent();
                        intent13.setData(Uri.parse("http://ee.sit.edu.cn/"));//Url 就是你要打开的网址
                        intent13.setAction(Intent.ACTION_VIEW);
                        startActivity(intent13); //启动浏览器
                        break;
                    case 14:
                        Intent intent14 = new Intent();
                        intent14.setData(Uri.parse("http://ei.sit.edu.cn"));//Url 就是你要打开的网址
                        intent14.setAction(Intent.ACTION_VIEW);
                        startActivity(intent14); //启动浏览器
                        break;
                    case 15:
                        Intent intent15 = new Intent();
                        intent15.setData(Uri.parse("http://physi.sit.edu.cn"));//Url 就是你要打开的网址
                        intent15.setAction(Intent.ACTION_VIEW);
                        startActivity(intent15); //启动浏览器
                        break;
                    case 16:
                        Intent intent16 = new Intent();
                        intent16.setData(Uri.parse("http://pe.sit.edu.cn"));//Url 就是你要打开的网址
                        intent16.setAction(Intent.ACTION_VIEW);
                        startActivity(intent16); //启动浏览器
                        break;
                    case 17:
                        Intent intent17 = new Intent();
                        intent17.setData(Uri.parse("http://ecology.sit.edu.cn"));//Url 就是你要打开的网址
                        intent17.setAction(Intent.ACTION_VIEW);
                        startActivity(intent17); //启动浏览器
                        break;
                    case 18:
                        Intent intent18 = new Intent();
                        intent18.setData(Uri.parse("http://sce.sit.edu.cn/"));//Url 就是你要打开的网址
                        intent18.setAction(Intent.ACTION_VIEW);
                        startActivity(intent18); //启动浏览器
                        break;
                    case 19:
                        Intent intent19 = new Intent();
                        intent19.setData(Uri.parse("http://iec.sit.edu.cn"));//Url 就是你要打开的网址
                        intent19.setAction(Intent.ACTION_VIEW);
                        startActivity(intent19); //启动浏览器
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
                        R.mipmap.list3,R.mipmap.list1,R.mipmap.list3,R.mipmap.list1,R.mipmap.list3};
        //图标下的文字
        String name[]={
                "计算机科学与信息工程学院",//http://cs.sit.edu.cn/
                "机械工程学院",//http://me.sit.edu.cn
                "城市建设与安全工程学院",//http://consafe.sit.edu.cn/
                "香料香精联合党委 ",//http://parfum.sit.edu.cn
                "艺术与设计学院",//http://artdes.sit.edu.cn
                "经济与管理学院",//http://sem.sit.edu.cn/index.asp
                "材料科学与工程学院",//http://materials.sit.edu.cn
                "化学与环境工程学院",//http://chenv.sit.edu.cn
                "外国语学院",//http://fl.sit.edu.cn
                "人文学院",//http://humanity.sit.edu.cn/
                "马克思主义学院",//http://mks.sit.edu.cn
                "高等职业学院",//http://pro.sit.edu.cn
                "轨道交通学院",//http://rt.sit.edu.cn
                "电气与电子工程学院",//http://ee.sit.edu.cn/
                "工程创新学院",//http://ei.sit.edu.cn
                "理学院",//http://physi.sit.edu.cn
                "体育教育部",//http://pe.sit.edu.cn
                "生态技术与工程学院",//http://ecology.sit.edu.cn
                "继续教育学院",//http://sce.sit.edu.cn/
                "国际教育中心"//http://iec.sit.edu.cn
                //20个学院
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