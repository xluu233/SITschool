package com.example.luhongcheng;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import com.example.luhongcheng.Bmob.Tips;
import com.example.luhongcheng.Bmob.lan;
import com.example.luhongcheng.about.about0;
import com.example.luhongcheng.userCard.userCardinfo;
import com.example.luhongcheng.zixun.news;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.b.V;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

import static com.example.luhongcheng.FourFragment.TAG;

/**
 * Created by Administrator on 2018/4/7.
 */

@SuppressLint("ValidFragment")
public class OneFragment extends Fragment{
    private String context;
    public OneFragment(String context){
        this.context = context;
    }
    /*以下是GridView定义的*/
    private GridView gridView;
    private List<Map<String, Object>> dataList;
    private SimpleAdapter adapter;


    /*轮换图片定义的*/
    //统计下载了几张图片
    int n=0;
    //统计当前viewpager轮播到第几页
    int p=0;
    private ViewPager vp;
    //准备好三张网络图片的地址
    private String imageUrl[]=new String[]
            {"http://www.sit.edu.cn/page/main297/images/1.jpg",
                    "http://www.sit.edu.cn/page/main297/images/2.jpg",
                    "http://www.sit.edu.cn/page/main297/images/3.jpg",
                    "http://www.sit.edu.cn/page/main297/images/4.jpg"};
    //装载下载图片的集合
    private List<ImageView> data;
    //控制图片是否开始轮播的开关,默认关的
    private boolean isStart=false;
    //开始图片轮播的线程
    private MyThread t;
    //存放代表viewpager播到第几张的小圆点
    private LinearLayout ll_tag;
    //存储小圆点的一维数组
    private ImageView tag[];
    private Handler mHandler=new Handler(){
        public void handleMessage(android.os.Message msg) {
            switch(msg.what){
                case 0:
                    n++;
                    Bitmap bitmap=(Bitmap) msg.obj;
                    ImageView iv=new ImageView(getActivity());
                    iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    iv.setImageBitmap(bitmap);
                    //把图片添加到集合里
                    data.add(iv);
                    //当接收到第三张图片的时候，设置适配器,
                    if(n==imageUrl.length){
                        vp.setAdapter(new ImageLunhuanAdapter(data,getActivity()));
                        //创建小圆点
                        creatTag();
                        //把开关打开
                        isStart=true;
                        t= new MyThread();
                        //启动轮播图片线程
                        t.start();
                    }
                    break;
                case 1:
                    //接受到的线程发过来的p数字
                    int page=(Integer) msg.obj;
                    vp.setCurrentItem(page);
                    break;
            }
        };
    };

    private Toolbar mToolbar;
    Button more,more2;
    TextView tips,QQ,AA,havefun;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.a_fragment, container, false);
        //((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        return  view;
    }

    @Override
    public void onActivityCreated( Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        gridView = (GridView) getView().findViewById(R.id.gridview);
        initData();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getActivity().getWindow().setStatusBarColor(getResources().getColor(R.color.red_300));//设置状态栏背景色
        }

        mToolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        mToolbar.inflateMenu(R.menu.menu);
        mToolbar.setTitle("SITschool");
        mToolbar.setSubtitle("明德、明学、明事");


        Bmob.initialize(getActivity(), "69d2a14bfc1139c1e9af3a9678b0f1ed");
        gettip();
        tips = (TextView) getActivity().findViewById(R.id.tips);
        more = (Button) getActivity().findViewById(R.id.more);
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),MoreTips.class);
                startActivity(intent);
            }
        });

        havefun = (TextView) getActivity().findViewById(R.id.havefun);


        ImageButton one = (ImageButton) getActivity().findViewById(R.id.OneSelf);
        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(getActivity(),OneSelf.class);
                startActivity(intent);
            }
        });

        String[] from={"ItemImage","ItemText"};
        int[] to={R.id.ItemImage,R.id.ItemText};
        adapter=new SimpleAdapter(getActivity(), dataList, R.layout.gridview_item, from, to);
        gridView.setAdapter(adapter);


   /* 给item设置点击事件*/
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {
                switch (position) {
                    case 0:
                        Intent intent=new Intent(getActivity(),item2.class);
                        startActivity(intent);
                        break;
                    case 1:
                        Intent intent1=new Intent(getActivity(),item1.class);
                        startActivity(intent1);
                        break;
                    case 2:
                        Intent intent2=new Intent(getActivity(),item3.class);
                        startActivity(intent2);
                        break;
                    case 3:
                        Intent intent3=new Intent(getActivity(),item4.class);
                        startActivity(intent3);
                        break;
                    case 4:
                        Intent intent4=new Intent(getActivity(),item5.class);
                        startActivity(intent4);
                        break;
                    case 5:
                        Intent intent5=new Intent(getActivity(),item6.class);
                        startActivity(intent5);
                        break;
                    case 6:
                        Intent intent6=new Intent(getActivity(),item7.class);
                        startActivity(intent6);
                        break;
                    case 7:
                        Intent intent7 = new Intent(getActivity(),userCardinfo.class);
                        startActivity(intent7);
                        break;
                    case 8:
                        Intent intent8 = new Intent(getActivity(),item8.class);
                        startActivity(intent8);
                        break;
                        /*
                    case 9:
                        Intent intent9 = new Intent(getActivity(),item9.class);
                        startActivity(intent9);
                        Toast.makeText(getActivity(),"暂未开放",Toast.LENGTH_SHORT).show();
                        break;
                        */
                    case 9:
                        Intent intent10 = new Intent(getActivity(),news.class);
                        startActivity(intent10);
                        break;
                    case 10:
                        startActivity(new Intent(getActivity(),item0.class));
                        getActivity().overridePendingTransition(R.anim.bottom_in,R.anim.bottom_silent);

                        //Intent intent11 = new Intent(getActivity(),item0.class);
                        //startActivity(intent11);

                        break;
                    default:
                        break;
                }
            }
        });
        /*点击事件设置完毕*/
        init();
        getlan();
    }

    public void gettip(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                BmobQuery<Tips> query = new BmobQuery<Tips>();
                query.findObjects(new FindListener<Tips>(){
                    @Override
                    public void done(List<Tips> list, BmobException e) {
                        List<Tips> lists = new ArrayList<>();
                        if (list != null) {
                            final String[] tip  =  new String[list.size()];
                            for(int i = 0;i<list.size();i++){
                                tip[i] = list.get(i).getTips();
                            }
                            tips.setText(tip[list.size() - 1]);
                        }
                    }
                });
            }
        }); //子线程
        thread.start();
    }

    public void getlan(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                BmobQuery<lan> query = new BmobQuery<lan>();
                query.findObjects(new FindListener<lan>(){
                    @Override
                    public void done(List<lan> list, BmobException e) {
                        List<lan> lists = new ArrayList<>();
                        if (list != null) {
                            final String[] lan = new String[list.size()];
                            String[] sub = new  String[list.size()];
                            int i;
                            for(i = 0;i<list.size();i++){
                                lan[i] = list.get(i).getYouqudejuzi();
                                sub[i] = list.get(i).getSubtitle();
                            }
                            havefun.setText(lan[0]);
                            mToolbar.setSubtitle(sub[0]);
                        }
                    }
                });
            }
        }); //子线程
        thread.start();
    }

    //toolbar
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.toolbar_action1:
                //Toast.makeText(getActivity(),"更多功能敬请期待",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(),about0.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }



    void initData() {
        //图标
        int icno[] = { R.mipmap.g16,R.mipmap.g1,R.mipmap.g7,R.mipmap.g4,
                        R.mipmap.g5,R.mipmap.g9,R.mipmap.g3,R.drawable.card,
                        R.drawable.library,R.drawable.zixun,
                        R.mipmap.g15};
        //图标下的文字
        String name[]={"部门","第二课堂","OA主页","成绩",
                "电费","学院","考试","学生卡","读书馆","资讯","更多"};

        dataList = new ArrayList<Map<String, Object>>();
        for (int i = 0; i <icno.length; i++) {
            Map<String, Object> map=new HashMap<String, Object>();
            map.put("ItemImage", icno[i]);
            map.put("ItemText",name[i]);
            dataList.add(map);
        }
    }


    /*轮换图片*/
    private void init() {
        // TODO Auto-generated method stub
        vp=(ViewPager) getView().findViewById(R.id.vp);
        ll_tag=(LinearLayout) getView().findViewById(R.id.ll_tag);
        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                // TODO Auto-generated method stub
                //把当前的页数赋值给P
                p=position;
                //得到当前图片的索引,如果图片只有三张，那么只有0，1，2这三种情况
                int currentIndex=(position%imageUrl.length);
                for(int i=0;i<tag.length;i++){
                    if(i==currentIndex){
                        tag[i].setBackgroundResource(R.drawable.dot_focused);
                    }else{
                        tag[i].setBackgroundResource(R.drawable.dot_normal);
                    }
                }
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub

            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }

        });
        //构造一个存储照片的集合
        data=new ArrayList<ImageView>();
        //从网络上把图片下载下来
        for(int i=0;i<imageUrl.length;i++){
            getImageFromNet(imageUrl[i]);
        }
    }

    private void getImageFromNet(final String imagePath) {
        // TODO Auto-generated method stub
        new Thread(){
            public void run() {
                try {
                    URL url=new URL(imagePath);
                    HttpURLConnection con=(HttpURLConnection) url.openConnection();
                    con.setRequestMethod("GET");
                    con.setConnectTimeout(10*1000);
                    InputStream is=con.getInputStream();
                    //把流转换为bitmap
                    Bitmap bitmap= BitmapFactory.decodeStream(is);
                    Message message=new Message();
                    message.what=0;
                    message.obj=bitmap;
                    //把这个bitmap发送到hanlder那里去处理
                    mHandler.sendMessage(message);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            };
        }.start();

    }


    //控制图片轮播
    class MyThread extends Thread{
        @Override
        public void run() {
            // TODO Auto-generated method stub
            super.run();
            while(isStart){
                Message message=new Message();
                message.what=1;
                message.obj=p;
                mHandler.sendMessage(message);
                try {
                    //睡眠5秒,在isStart为真的情况下，一直每隔三秒循环
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                p++;
            }
        }
    }

    protected void creatTag() {
        tag=new ImageView[imageUrl.length];
        for(int i=0;i<imageUrl.length;i++){

            tag[i]=new ImageView(getActivity());
            //第一张图片画的小圆点是白点
            if(i==0){
                tag[i].setBackgroundResource(R.drawable.dot_focused);
            }else{
                //其它的画灰点
                tag[i].setBackgroundResource(R.drawable.dot_normal);
            }
            //设置上下左右的间隔
            tag[i].setPadding(0,0,40,40);
            tag[i].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            //添加到viewpager底部的线性布局里面
            ll_tag.addView(tag[i]);
        }
    }




}

