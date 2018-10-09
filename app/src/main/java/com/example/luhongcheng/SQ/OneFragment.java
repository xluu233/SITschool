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
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.luhongcheng.Bmob.SQVP;
import com.example.luhongcheng.Bmob.UserInfo;
import com.example.luhongcheng.ImageLunhuanAdapter;
import com.example.luhongcheng.ImageLunhuanAdapter2;
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



    private ListView lv;
    private List<SSS> slist;
    @SuppressLint("HandlerLeak")
    private Handler mHandler2 = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                   // SSAdaper adapter = new SSAdaper(slist);
                    lv.setAdapter(new SSAdaper(slist));
                    setListViewHeightBasedOnChildren(lv);

                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            SSS news = slist.get(position);
                            Intent intent = new Intent(getActivity(),ssDisPlay.class);
                            intent.putExtra("objectID",news.getSsID());
                            intent.putExtra("personID",news.getPersonID());

                            intent.putExtra("iconUrl",news.getIconUrl());
                            intent.putExtra("imageUrl",news.getImageUrl());
                            intent.putExtra("content",news.getTitle());
                            intent.putExtra("nickname",news.getNickname());
                            intent.putExtra("qm",news.getQm());
                            intent.putExtra("label",news.getLabel());
                            startActivity(intent);

                        }
                    });

                    break;

                default:
                    break;
            }
        }

    };

    /**
     * 动态设置ListView的高度
     * 因为Scrollview嵌套listview只显示一行
     * @param listView
     */
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        if(listView == null) return;
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }
        int totalHeight = 0;

        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

       // totalHeight  =listAdapter.getCount()*250;
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight +(listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }


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

    FloatingActionButton add ;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        vp=(ViewPager) getView().findViewById(R.id.vp);
        ll_tag=(LinearLayout) getView().findViewById(R.id.ll_tag);

        add = (FloatingActionButton) getActivity().findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),SS_ADD.class);
                startActivity(intent);
            }
        });
        lv = (ListView) getActivity().findViewById(R.id.ss_listView);
        initData();

        //将Scrollview置顶
        ScrollView sv = (ScrollView)getActivity().findViewById(R.id.scr);
        //sv.smoothScrollTo(0, 0);


        RefreshLayout refreshLayout = (RefreshLayout)getActivity().findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                Page =1;
                slist.clear();
                lv.setAdapter(new SSAdaper(slist));
                initData();
                refreshlayout.finishRefresh(1000/*,false*/);//传入false表示刷新失败
            }
        });


        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
             public void onLoadMore(RefreshLayout refreshlayout) {
                Page = Page +1;
              //  lv.setAdapter(new SSAdaper(slist));
                initData();

                refreshlayout.finishLoadMore(2000/*,false*/);//传入false表示加载失败
            }
        });


    }


    int i =0;
    String personID;
    String ssID2,label2;
    String time2;
    int Page = 1;//页数
    private void initData() {
        slist = new ArrayList<SSS>();
        /*
        slist.add(new SSS("http://c.hiphotos.baidu.com/baike/pic/item/8435e5dde71190efbcb4111ac41b9d16fcfa60ac.jpg", "caonima"));
        //slist.add(new SSS("http://image.jijidown.com/v1/image?av=5055716&url=http://i2.hdslb.com/bfs/archive/fbd625bacf24b8f0407bc7e93ef7817ff394ebd4.jpg&sign=E02494DD439DC72692D942CF0A0A3DBF", "车市"));
        slist.add(new SSS("http://img.mp.itc.cn/upload/20161026/4bceef7cfdff48e688a212e0a025d756_th.jpg", "奥术大师多"));
        slist.add(new SSS("http://06.imgmini.eastday.com/mobile/20180422/43a9a7a27dfe05aaf09157cba82a37f0.jpeg", "as大无"));
        mHandler2.obtainMessage(0).sendToTarget();
        */
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                BmobQuery<com.example.luhongcheng.Bmob.SS> query = new BmobQuery<com.example.luhongcheng.Bmob.SS>();
                query.order("createdAt");//时间降序查询
                query.setLimit(3*Page);//查询前20条数据
                //query.setSkip(3*(Page-1));//分页查询

                query.findObjects(new FindListener<com.example.luhongcheng.Bmob.SS>(){
                    @Override
                    public void done(final List<com.example.luhongcheng.Bmob.SS> list, BmobException e) {
                        List<com.example.luhongcheng.Bmob.SS> lists = new ArrayList<>();
                        if (list != null) {
                            final String[] content  =  new String[list.size()+1];
                            final String[] image = new String[list.size()+1];
                            String[] ID = new  String[list.size()+1];
                            final  String[]  ssID= new String[list.size()+1];
                            final  String[]  label= new String[list.size()+1];
                            final  String[] time = new String[list.size()+1];

                            int n = list.size()+1;

                            for(i = 0;i<list.size();i++){
                                content[i] = list.get(i).getContent();
                                image[i] = list.get(i).getimgUrl();
                                ID[i] = list.get(i).getID();
                                ssID[i] = list.get(i).getObjectId();
                                label[i] = list.get(i).getLabel();
                                time[i] = list.get(i).getCreatedAt();

                                String image2 = image[i];
                                String content2 = content[i];
                                personID = ID[i];
                                ssID2 = ssID[i];
                                label2 = label[i];
                                time2 = time[i];
                                getssuerinfo(n,image2,content2,personID,ssID2,label2,time2);

                                //slist.add(new SSS(image[i],content[i],icon,qm,nickname));
                            }

                            //mHandler2.obtainMessage(0).sendToTarget();
                        }

                    }


                });

            }
        });
        thread2.start();




    }

    private void getssuerinfo(final int n , final String img, final String content, final String personID, final String ssID2,final String label2,final String time2) {

        final String[] nickname = new String[n];
        final String[] qm = new String[n];
        final String[] icon = new String[n];

        BmobQuery<UserInfo> query = new BmobQuery<UserInfo>();
        query.getObject(personID, new QueryListener<UserInfo>() {
            @Override
            public void done(UserInfo object, BmobException e) {
                if(e==null){
                    // String nickname,qm,icon;
                    nickname[i] = object.getNickname();
                    qm[i] = object.getQM();
                    icon[i] = object.geticonUrl();
                    if (qm[i] == null){
                        qm[i] = "这个人很懒，什么都没有";
                    }

                    if (nickname[i] == null){
                        nickname[i] = "无名人";
                    }

                   // System.out.println("nickname:"+nickname[i]);
                   // System.out.println("icon:"+icon[i]);
                   // System.out.println("qm:"+qm[i]);
                    //System.out.println("image:"+image[i]);
                    //System.out.println("content:"+content[i]);

                }else{
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }


               // System.out.println("image:"+ img);
               // System.out.println("content:"+content);
                slist.add(new SSS(img,content,icon[i],qm[i],nickname[i],personID,ssID2,label2,time2));
                // System.out.print(slist);
                mHandler2.obtainMessage(0).sendToTarget();

            }
        });


    }

    public class SSAdaper extends BaseAdapter {

        private List<SSS> list;
        private ListView listview;

        public SSAdaper(List<SSS> list) {
            super();
            this.list = list;

        }

        @Override
        public int getCount() {
            /*
            if (list.size()>100){
                return 100;
            }else {

            }*/
            return list.size();
            //主页最大数量100
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if(convertView==null){
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.ss_listview_item, null);
                holder = new ViewHolder();
                holder.img = (ImageView) convertView.findViewById(R.id.img);
                holder.icon = (ImageView) convertView.findViewById(R.id.icon);
                holder.title = (TextView) convertView.findViewById(R.id.content);
                holder.nk = (TextView) convertView.findViewById(R.id.nickname);
                holder.qmm = (TextView) convertView.findViewById(R.id.qm);
                holder.tv_label = (TextView) convertView.findViewById(R.id.label);
                convertView.setTag(holder);
            }else{
                holder = (ViewHolder) convertView.getTag();
            }
            SSS news = list.get(position);
          //  holder.img.setTag(news.getImageUrl());
            holder.title.setText(news.getTitle());
            holder.nk.setText(news.getNickname());
            holder.qmm.setText(news.getQm());

            String label = news.getLabel();
           // System.out.println("label是什么"+label);
            if (label != null){

                if (label.equals("A1")){
                    holder.tv_label.setText("#今日最佳#");
                }else if (label.equals("A2")){
                    holder.tv_label.setText("#一日三餐#");
                }else if (label.equals("A3")){
                    holder.tv_label.setText("#表白墙#");
                } else if (label.equals("A4")){
                    holder.tv_label.setText("#众话说#");
                } else if (label.equals("A5")){
                    holder.tv_label.setText("#工具推荐#");
                } else if (label.equals("A6")){
                    holder.tv_label.setText("#学习交流#");
                } else if (label.equals("A7")){
                    holder.tv_label.setText("#安利#");
                } else if (label.equals("A8")){
                    holder.tv_label.setText("#需求池#");
                } else if (label.equals("A9")){
                    holder.tv_label.setText("#考研党#");
                } else if (label.equals("A10")){
                    holder.tv_label.setText("#周边推荐#");
                } else if (label.equals("A11")){
                    holder.tv_label.setText("#每日一听#");
                }else if (label.equals("A12")){
                    holder.tv_label.setText("#晨读打卡#");
                } else if (label.equals("A13")){
                    holder.tv_label.setText("#谈天说地#");
                }

            }

            Glide.with(getContext())
                    .load(news.getImageUrl())
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.error)
                    //  .dontTransform()//不进行图片变换
                    .fitCenter()
                   // .centerCrop()
                    //.override(Target.SIZE_ORIGINAL, 1000)
                    .into(holder.img);

            Glide.with(getContext())
                    .load(news.getIconUrl())
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.error)
                    //  .dontTransform()//不进行图片变换
                    .fitCenter()
                    // .centerCrop()
                    //.override(Target.SIZE_ORIGINAL, 1000)
                    .into(holder.icon);


            return convertView;

        }

        class ViewHolder {
            ImageView img,icon;
            TextView title,nk,qmm,tv_label;
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
