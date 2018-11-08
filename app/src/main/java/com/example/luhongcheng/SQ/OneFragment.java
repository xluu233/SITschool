package com.example.luhongcheng.SQ;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.Resource;
import com.example.luhongcheng.Bmob.SQVP;
import com.example.luhongcheng.Bmob.news;
import com.example.luhongcheng.ImageLunhuanAdapter2;
import com.example.luhongcheng.R;
import com.example.luhongcheng.zixun.zhuyeDisplayActvivity;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;


public class OneFragment extends Fragment {
    public static OneFragment newInstance() {
        return new OneFragment();
    }
    /*轮换图片定义的*/
    //统计下载了几张图片
    int n=0;
    //统计当前viewpager轮播到第几页
    int p=0;
    private ViewPager vp;
    //准备好三张网络图片的地址
    String imageUrl[]=new String[4];
    String[] clickUrl = new String[4];
    //装载下载图片的集合
    private List<ImageView> data;
    //控制图片是否开始轮播的开关,默认关的
    private boolean isStart=false;
    //开始图片轮播的线程
    private com.example.luhongcheng.SQ.OneFragment.MyThread t;
    //存放代表viewpager播到第几张的小圆点
    private LinearLayout ll_tag;
    //存储小圆点的一维数组
    private ImageView tag[];
    @SuppressLint("HandlerLeak")
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
                        vp.setAdapter(new ImageLunhuanAdapter2(data,getActivity(),clickUrl));
                        //创建小圆点
                        creatTag();
                        //把开关打开
                        isStart=true;
                        t= new com.example.luhongcheng.SQ.OneFragment.MyThread();
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

    FloatingActionButton choose_box;
    SwipeRefreshLayout refresh;
    ListView listView;
    List<SSS> mlist;
    List<String> news_url;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getUrl();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View v = inflater.inflate(R.layout.sq_fragment_one, container, false);
        return v;
    }


    @SuppressLint("ResourceAsColor")
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        vp=(ViewPager) getView().findViewById(R.id.vp);
        ll_tag=(LinearLayout) getView().findViewById(R.id.ll_tag);
        choose_box = (FloatingActionButton)getActivity().findViewById(R.id.choose_box);
        Bmob.initialize(getActivity(), "69d2a14bfc1139c1e9af3a9678b0f1ed");
        // 设置页面间距
        //vp.setPageMargin(20);
        vp.setPageTransformer(true, new ZoomOutPageTransformer());
        listView = (ListView)getActivity().findViewById(R.id.my_news);
        refresh = (SwipeRefreshLayout)getActivity().findViewById(R.id.news_refresh);
        refresh.setColorSchemeColors(R.color.red_300);

        onClick();
        getArticle();
    }

    @SuppressLint("ResourceAsColor")
    private void onClick() {
        choose_box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });

        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        //加载数据
                        getArticle();
                        //关闭刷新
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                refresh.setRefreshing(false);
                            }
                        });

                    }
                }).start();


            }
        });
    }

    private void getArticle() {
        mlist = new ArrayList<SSS>();
        mlist.clear();

        Thread getnews = new Thread(new Runnable() {
            @Override
            public void run() {

                BmobQuery<news> query = new BmobQuery<news>();
                //query.order("-createdAt");
                //query.setLimit(500);

                query.findObjects(new FindListener<news>(){
                    @Override
                    public void done(final List<news> list, BmobException e) {
                        if (list != null) {
                            final String[] title  =  new String[list.size()];
                            final String[] image = new String[list.size()];
                            final String[] time = new String[list.size()];
                            final String[] url = new String[list.size()];

                            for(int i = 0;i<list.size();i++){
                                title[i] = list.get(i).getTitle();
                                image[i] = list.get(i).getNewsImageUrl();
                                time[i] = list.get(i).getCreatedAt();
                                url[i] = list.get(i).getUrl();

                                mlist.add(new SSS(title[i],time[i],image[i],url[i]));
                            }


                        }
                        mHandler2.obtainMessage(0).sendToTarget();

                    }

                });


            }
        });
        getnews.start();

    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler2 = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    listView.setAdapter(new newsAdaper(mlist));
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            SSS xixi = mlist.get(position);
                            Intent intent = new Intent(getActivity(),zhuyeDisplayActvivity.class);
                            intent.putExtra("news_url", xixi.getIconUrl());
                            startActivity(intent);
                        }
                    });
                    break;
                default:
                    break;
            }
        }

    };


    public class newsAdaper extends BaseAdapter {

        List<SSS> list;
        public newsAdaper(List<SSS> list) {
            super();
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @SuppressLint("NewApi")
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if(convertView==null){
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_news, null);
                holder = new OneFragment.newsAdaper.ViewHolder();
                holder.time = (TextView)convertView.findViewById(R.id.article_time);
                holder.img = (ImageView) convertView.findViewById(R.id.article_image);
                holder.title = (TextView) convertView.findViewById(R.id.article_title);
                convertView.setTag(holder);
            }else{
                holder = (OneFragment.newsAdaper.ViewHolder) convertView.getTag();
            }
            final SSS news = list.get(position);
            holder.title.setText(news.getTitle());
            holder.time.setText(news.getTime());


            if (news.getImageUrl().length()>0){
                Glide.with(getContext())
                        .load(news.getImageUrl())
                        .placeholder(R.drawable.loading)
                        .error(R.drawable.error)
                        .override(600, 200)
                        .into(holder.img);
            }


            return convertView;
        }

        class ViewHolder {
            ImageView img;
            TextView title,time;
        }

    }



    public void getUrl(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                BmobQuery<SQVP> query = new BmobQuery<SQVP>();
                query.findObjects(new FindListener<SQVP>(){
                    @Override
                    public void done(List<SQVP> list, BmobException e) {
                        List<SQVP> lists = new ArrayList<>();
                        if (list != null) {
                            for(int i = 0;i<list.size();i++){
                                clickUrl[i] = list.get(i).getUrl();
                                imageUrl[i] = list.get(i).getImgUrl();
                                //Log.d("imageURL",list.get(i).getImgUrl());
                                //Log.d("URL",list.get(i).getUrl());
                            }
                            init();
                        }
                    }
                });
            }
        }); //声明一个子线程
        thread.start();
    }

    /*轮换图片*/
    private void init() {
        // TODO Auto-generated method stub
        //Toast.makeText(getActivity(), clickUrl[position], Toast.LENGTH_SHORT).show();
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
