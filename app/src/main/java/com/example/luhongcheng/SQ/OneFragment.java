package com.example.luhongcheng.SQ;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.luhongcheng.Bmob_bean.news;
import com.example.luhongcheng.R;
import com.example.luhongcheng.zixun.zhuyeDisplayActvivity;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;


public class OneFragment extends Fragment {
    public static OneFragment newInstance() {
        return new OneFragment();
    }

    //准备好三张网络图片的地址
    String imageUrl[]=new String[4];
    String[] clickUrl = new String[4];

    FloatingActionButton choose_box;
    SwipeRefreshLayout refresh;
    ListView listView;
    List<SSS> mlist;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        choose_box = (FloatingActionButton)getActivity().findViewById(R.id.choose_box);
        Bmob.initialize(getActivity(), "69d2a14bfc1139c1e9af3a9678b0f1ed");
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
                Intent intent = new Intent(getActivity(),Article_Add.class);
                startActivity(intent);
            }
        });

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {
                boolean enable = false;
                if (listView != null && listView.getChildCount() > 0) {
                    boolean firstItemVisible = listView.getFirstVisiblePosition() == 0;
                    boolean topOfFirstItemVisible = listView.getChildAt(0).getTop() == 0;
                    enable = firstItemVisible && topOfFirstItemVisible;
                }
                refresh.setEnabled(enable);
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
                query.order("-createdAt");
                query.setLimit(100);
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

            AssetManager mgr = getActivity().getAssets();
            Typeface tf = Typeface.createFromAsset(mgr, "fonts/fangsong.TTF");//仿宋
            holder.time.setTypeface(tf);
            holder.title.setTypeface(tf);

            if (news.getImageUrl().length()>0){
                Glide.with(getContext())
                        .load(news.getImageUrl())
                        .centerCrop()
                        //.fitCenter()
                        .placeholder(R.drawable.loading)
                        .error(R.drawable.error)
                        .into(holder.img);
            }


            return convertView;
        }

        class ViewHolder {
            ImageView img;
            TextView title,time;
        }

    }







}
