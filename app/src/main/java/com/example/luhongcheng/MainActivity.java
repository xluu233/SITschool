package com.example.luhongcheng;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.luhongcheng.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static com.example.luhongcheng.R.id.image;
import static com.example.luhongcheng.R.id.password;
import static com.example.luhongcheng.R.id.username;

public class MainActivity extends Activity {
    /*以下是GridView定义的*/
    private GridView gridView;
    private List<Map<String, Object>> dataList;
    private SimpleAdapter adapter1;

    /*注销APP*/
    private long mExitTime;

    /*以下是轮换图片定义的*/
    private int imageIds[];
    private String[] titles;
    private ArrayList<ImageView> images;
    private ArrayList<View> dots;
    private TextView title;
    private ViewPager mViewPager;
    private ViewPagerAdapter adapter2;
    private int oldPosition = 0;//记录上一次点的位置
    private int currentItem; //当前页面
    private ScheduledExecutorService scheduledExecutorService;
    private List<String> urlList;

    String username;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //test();

        //显示的图片
        urlList = new ArrayList<String>();
        urlList.add("http://www.sit.edu.cn/page/main297/images/1.jpg");
        urlList.add("http://www.sit.edu.cn/page/main297/images/2.jpg");
        urlList.add("http://www.sit.edu.cn/page/main297/images/3.jpg");
        urlList.add("http://www.sit.edu.cn/page/main297/images/4.jpg");


        //图片ID
        imageIds = new int[]{
                R.drawable.e,
                R.drawable.b,
                R.drawable.c,
                R.drawable.d,
                R.drawable.a
        };

        //图片标题
        titles = new String[]{
                "QQ群",
                "B",
                "C",
                "D",
                "A"
        };


        images = new ArrayList<ImageView>();
        for(int i =0; i < imageIds.length; i++){
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(imageIds[i]);
            images.add(imageView);
        }




        //显示的点
        dots = new ArrayList<View>();
        dots.add(findViewById(R.id.dot_0));
        dots.add(findViewById(R.id.dot_1));
        dots.add(findViewById(R.id.dot_2));
        dots.add(findViewById(R.id.dot_3));
        dots.add(findViewById(R.id.dot_4));
        title = (TextView) findViewById(R.id.title);
        title.setText(titles[0]);
        mViewPager = (ViewPager) findViewById(R.id.vp);
        adapter2 = new ViewPagerAdapter();
        mViewPager.setAdapter(adapter2);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                // TODO Auto-generated method stub
                title.setText(titles[position]);
                dots.get(oldPosition).setBackgroundResource(R.drawable.dot_normal);
                dots.get(position).setBackgroundResource(R.drawable.dot_focused);
                oldPosition = position;
                currentItem = position;
            }
            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub
            }
            @Override
            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub
            }
        });

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
                        Intent intent=new Intent(MainActivity.this,item0.class);
                        startActivity(intent);
                        break;
                    case 1:
                        Intent intent1=new Intent(MainActivity.this,item1.class);
                        startActivity(intent1);
                        break;
                    case 2:
                        Intent intent2=new Intent(MainActivity.this,item2.class);
                        startActivity(intent2);
                        break;
                    case 3:
                        Intent intent3=new Intent(MainActivity.this,item3.class);
                        startActivity(intent3);
                        break;
                    case 4:
                        Intent intent4=new Intent(MainActivity.this,item4.class);
                        startActivity(intent4);
                        break;
                    case 5:
                        Intent intent5=new Intent(MainActivity.this,item5.class);
                        startActivity(intent5);
                        break;
                    case 6:
                        Intent intent6=new Intent(MainActivity.this,item6.class);
                        startActivity(intent6);
                        break;
                    case 7:
                        Intent intent7=new Intent(MainActivity.this,item7.class);
                        startActivity(intent7);
                        break;
                    case 8:
                        Intent intent8=new Intent(MainActivity.this,item8.class);
                        startActivity(intent8);
                        break;
                    case 9:
                        Intent intent9=new Intent(MainActivity.this,item9.class);
                        startActivity(intent9);
                        break;
                    case 10:
                        Intent intent10=new Intent(MainActivity.this,item10.class);
                        startActivity(intent10);
                        break;
                    case 11:
                        Intent intent11=new Intent(MainActivity.this,item11.class);
                        startActivity(intent11);
                        break;
                    case 12:
                        Intent intent12=new Intent(MainActivity.this,item12.class);
                        startActivity(intent12);
                        break;
                    case 13:
                        Intent intent13=new Intent(MainActivity.this,item13.class);
                        startActivity(intent13);
                        break;
                    case 14:
                        Intent intent14 = new Intent(MainActivity.this,item14.class);
                        startActivity(intent14);
                        break;
                    default:
                        break;
                }
            }
        });
        /*点击事件设置完毕*/
    }


    /***

    private  void test(){

        SharedPreferences spCount = getSharedPreferences("userid", 0);
        username= spCount.getString("username", "");
        password= spCount.getString("password", "");
        if (username.length()==0 & password.length()==0){
                Intent intent = new Intent(MainActivity.this,startactivity.class);
                startActivity(intent);
        }

    }

     ***///

    void initData() {
        //图标
        int icno[] = { R.mipmap.g12,R.mipmap.g1, R.mipmap.g2,R.mipmap.g3,R.mipmap.g4,R.mipmap.g5,R.mipmap.g6,R.mipmap.g7,R.mipmap.g8,
                R.mipmap.g9,R.mipmap.g10,R.mipmap.g11,R.mipmap.g0,R.mipmap.g13,R.mipmap.g15};
        //图标下的文字
        String name[]={"个人信息","第二课堂","我的课表","考试安排","成绩查看",
                "电费查询","学生事务","学习课堂","校园文化",
                "二级学院","文件下载","校园公告","我的账号","关于our","其他系统"};
        dataList = new ArrayList<Map<String, Object>>();
        for (int i = 0; i <icno.length; i++) {
            Map<String, Object> map=new HashMap<String, Object>();
            map.put("ItemImage", icno[i]);
            map.put("ItemText",name[i]);
            dataList.add(map);
        }
    }

    /*lunhuan.java*/
    private class ViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return images.size();
        }

        //是否是同一张图片
        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            // TODO Auto-generated method stub
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(ViewGroup view, int position, Object object) {
            // TODO Auto-generated method stub
//            super.destroyItem(container, position, object);
//            view.removeViewAt(position);
            view.removeView(images.get(position));

        }

        @Override
        public Object instantiateItem(ViewGroup view, int position) {
            // TODO Auto-generated method stub
            view.addView(images.get(position));

            return images.get(position);
        }
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();

        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

        //每隔2秒钟切换一张图片
        //scheduledExecutorService.scheduleWithFixedDelay(new ViewPagerTask(), 4, 4, TimeUnit.SECONDS);
    }

    //切换图片
    private class ViewPagerTask implements Runnable {

        @Override
        public void run() {
            // TODO Auto-generated method stub
            currentItem = (currentItem +1) % imageIds.length;
            //更新界面
//            handler.sendEmptyMessage(0);
            handler.obtainMessage().sendToTarget();
        }

    }

    private Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            //设置当前页面
            mViewPager.setCurrentItem(currentItem);
        }

    };

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
    }

    /*注销APP*/
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void exit() {
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            Toast.makeText(getApplicationContext(), "再按一次退出程序",
                    Toast.LENGTH_SHORT).show();
            mExitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
        }
    }
    /*注销完了*/

}