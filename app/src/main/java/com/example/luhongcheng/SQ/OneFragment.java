package com.example.luhongcheng.SQ;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.luhongcheng.Bmob.SQVP;
import com.example.luhongcheng.Bmob.UserInfo;
import com.example.luhongcheng.Box;
import com.example.luhongcheng.ImageFullDisplay;
import com.example.luhongcheng.ImageLunhuanAdapter;
import com.example.luhongcheng.ImageLunhuanAdapter2;
import com.example.luhongcheng.MBox.MBoxItem;
import com.example.luhongcheng.R;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    private List<Box> fruitList = new ArrayList<Box>();

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


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        vp=(ViewPager) getView().findViewById(R.id.vp);
        ll_tag=(LinearLayout) getView().findViewById(R.id.ll_tag);
        // 设置页面间距
        //vp.setPageMargin(20);
        vp.setPageTransformer(true, new ZoomOutPageTransformer());

        initFruits2();
        RecyclerView recyclerView = (RecyclerView) getActivity().findViewById(R.id.recycler_view2);
        StaggeredGridLayoutManager layoutManager2 = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager2);
        BoxAdapter2 adapter2 = new BoxAdapter2(fruitList);
        recyclerView.setAdapter(adapter2);

    }

    private void initFruits2() {
        Box orange = new Box("#今日最佳#", R.drawable.best_min);
        fruitList.add(orange);
        Box apple = new Box("#众话说#", R.drawable.talk_min);
        fruitList.add(apple);
        Box banana = new Box("#表白墙#", R.drawable.love_min);
        fruitList.add(banana);
        Box watermelon = new Box("#学习交流#", R.drawable.learn_min);
        fruitList.add(watermelon);
        Box pear = new Box("#安利#", R.drawable.anli_min);
        fruitList.add(pear);
        Box grape = new Box("#一日三餐#", R.drawable.food_min);
        fruitList.add(grape);
        Box pineapple = new Box("#需求池#", R.drawable.xuqiu_min);
        fruitList.add(pineapple);
        Box A1 = new Box("#工具推荐#", R.drawable.gongju_min);
        fruitList.add(A1);
        Box A3 = new Box("#考研党#", R.drawable.kaoyan);
        fruitList.add(A3);
        Box A4 = new Box("#周边推荐#", R.drawable.tuijian_min);
        fruitList.add(A4);
        Box A5 = new Box("#每日一听#", R.drawable.music_min);
        fruitList.add(A5);
        Box A6 = new Box("#晨读打卡#", R.drawable.read_min);
        fruitList.add(A6);
        Box A7 = new Box("#谈天说地#", R.drawable.talk_lala_min);
        fruitList.add(A7);

    }

    class BoxAdapter2 extends RecyclerView.Adapter<BoxAdapter2.ViewHolder>{
        private List<Box> mFruitList;
        class ViewHolder extends RecyclerView.ViewHolder {
            View fruitView;
            ImageView fruitImage;
            TextView fruitName;
            public ViewHolder(View view) {
                super(view);
                fruitView = view;
                fruitImage = (ImageView) view.findViewById(R.id.fruit_image);
                fruitName = (TextView) view.findViewById(R.id.fruit_name);
            }
        }


        public BoxAdapter2(List<Box> fruitList) {
            mFruitList = fruitList;
        }

        @Override
        public BoxAdapter2.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.box_item3, parent, false);
            final ViewHolder holder = new ViewHolder(view);
            holder.fruitView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getAdapterPosition();
                    Box fruit = mFruitList.get(position);
                    switch(position){
                        case 0:
                            //今日最佳
                            Intent intent = new Intent(getActivity(), MBoxItem.class);
                            intent.putExtra("flag","A1");
                            startActivity(intent);
                            break;
                        case 1:
                            //众话说
                            Intent intent1 = new Intent(getActivity(), MBoxItem.class);
                            intent1.putExtra("flag","A4");
                            startActivity(intent1);
                            break;
                        case 2:
                            //表白墙
                            Intent intent2 = new Intent(getActivity(), MBoxItem.class);
                            intent2.putExtra("flag","A3");
                            startActivity(intent2);
                            break;
                        case 3:
                            //学习交流
                            Intent intent3 = new Intent(getActivity(), MBoxItem.class);
                            intent3.putExtra("flag","A6");
                            startActivity(intent3);
                            break;
                        case 4:
                            //安利
                            Intent intent4 = new Intent(getActivity(), MBoxItem.class);
                            intent4.putExtra("flag","A7");
                            startActivity(intent4);
                            break;
                        case 5:
                            //一日三餐
                            Intent intent5 = new Intent(getActivity(), MBoxItem.class);
                            intent5.putExtra("flag","A2");
                            startActivity(intent5);
                            break;
                        case 6:
                            //需求池
                            Intent intent6 = new Intent(getActivity(), MBoxItem.class);
                            intent6.putExtra("flag","A8");
                            startActivity(intent6);
                            break;
                        case 7:
                            //工具推荐
                            Intent intent7 = new Intent(getActivity(), MBoxItem.class);
                            intent7.putExtra("flag","A5");
                            startActivity(intent7);
                            break;
                        case 8:
                            //考研党
                            Intent intent8 = new Intent(getActivity(), MBoxItem.class);
                            intent8.putExtra("flag","A9");
                            startActivity(intent8);
                            break;
                        case 9:
                            //周边推荐
                            Intent intent9 = new Intent(getActivity(), MBoxItem.class);
                            intent9.putExtra("flag","A10");
                            startActivity(intent9);
                            break;
                        case 10:
                            //每日一听
                            Intent intent10 = new Intent(getActivity(), MBoxItem.class);
                            intent10.putExtra("flag","A11");
                            startActivity(intent10);
                            break;
                        case 11:
                            //晨读打卡
                            Intent intent11 = new Intent(getActivity(), MBoxItem.class);
                            intent11.putExtra("flag","A12");
                            startActivity(intent11);
                            break;
                        case 12:
                            //谈天说地
                            Intent intent12 = new Intent(getActivity(), MBoxItem.class);
                            intent12.putExtra("flag","A13");
                            startActivity(intent12);
                            break;
                        default:
                            break;
                    }

                }
            });

            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Box fruit = mFruitList.get(position);
            holder.fruitImage.setImageResource(fruit.getImageId());
            holder.fruitName.setText(fruit.getName());

            ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
            layoutParams.height = RelativeLayout.LayoutParams.WRAP_CONTENT;
        }

        @Override
        public int getItemCount() {
            return mFruitList.size();
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
