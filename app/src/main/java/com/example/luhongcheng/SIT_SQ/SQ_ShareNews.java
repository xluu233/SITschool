package com.example.luhongcheng.SIT_SQ;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.bumptech.glide.request.RequestOptions;
import com.example.luhongcheng.Adapter.ShareNews_Adapter;
import com.example.luhongcheng.R;
import com.example.luhongcheng.SIT_SQ_other.Share_News;
import com.example.luhongcheng.bean.HotNews;
import com.example.luhongcheng.zixun.zhuyeDisplayActvivity;
import com.github.clans.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;


public class SQ_ShareNews extends Fragment {

    public SQ_ShareNews(){
        Context mContext = getActivity();
    }
    public static SQ_ShareNews newInstance(Context context) {
        Context mContext = context;
        return new SQ_ShareNews();
    }

    FloatingActionButton choose_box;
    SwipeRefreshLayout refresh;
    RecyclerView recyclerView;
    List<HotNews> mlist = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View v = inflater.inflate(R.layout.sq_share_news, container, false);
        return v;
    }


    @SuppressLint("ResourceAsColor")
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView = getActivity().findViewById(R.id.my_news);
        refresh = getActivity().findViewById(R.id.news_refresh);
        choose_box = getActivity().findViewById(R.id.share_news);


        onClick();
        getArticle();
    }

    @SuppressLint("ResourceAsColor")
    private void onClick() {
        choose_box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Share_News.class);
                startActivity(intent);
            }
        });

/*        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
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
        });*/



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
        mlist.clear();
        Thread getnews = new Thread(new Runnable() {
            @Override
            public void run() {
                BmobQuery<com.example.luhongcheng.Bmob_bean.Share_News> query = new BmobQuery<com.example.luhongcheng.Bmob_bean.Share_News>();
                query.order("-createdAt");
                query.setLimit(20);
                query.findObjects(new FindListener<com.example.luhongcheng.Bmob_bean.Share_News>(){
                    @Override
                    public void done(final List<com.example.luhongcheng.Bmob_bean.Share_News> list, BmobException e) {
                        if (list != null) {
                            final String[] title  =  new String[list.size()];
                            final String[] image = new String[list.size()];
                            final String[] time = new String[list.size()];
                            final String[] url = new String[list.size()];

                            for(int i = 0;i<list.size();i++){
                                title[i] = list.get(i).getTitle();
                                image[i] = list.get(i).getimageUrl();
                                time[i] = list.get(i).getCreatedAt();
                                url[i] = list.get(i).getUrl();

                                Log.d("news:",title[i]);
                                Log.d("news:",image[i] );
                                Log.d("news:",time[i]);
                                Log.d("news:",url[i]);

                                mlist.add(new HotNews(title[i],image[i],time[i],url[i]));
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
/*                    listView.setAdapter(new newsAdaper(mlist));
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            HotNews xixi = mlist.get(position);
                            Intent intent = new Intent(getActivity(), zhuyeDisplayActvivity.class);
                            intent.putExtra("news_url", xixi.getUrl());
                            startActivity(intent);
                        }
                    });*/
                    recyclerView.setAdapter(new ShareNews_Adapter(getContext(),mlist));
                    break;
                default:
                    break;
            }
        }

    };


    public class newsAdaper extends BaseAdapter {

        List<HotNews> list;
        public newsAdaper(List<HotNews> list) {
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
            newsAdaper.ViewHolder holder = null;
            if(convertView==null){
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.sq_share_news_item, null);
                holder = new newsAdaper.ViewHolder();
                holder.time = (TextView)convertView.findViewById(R.id.article_time);
                holder.img = (ImageView) convertView.findViewById(R.id.article_image);
                holder.title = (TextView) convertView.findViewById(R.id.article_title);


                convertView.setTag(holder);
            }else{
                holder = (newsAdaper.ViewHolder) convertView.getTag();
            }
            final HotNews news = list.get(position);
            holder.title.setText(news.getTitle());
            holder.time.setText(news.getTime());

/*            AssetManager mgr = getActivity().getAssets();
            Typeface tf = Typeface.createFromAsset(mgr, "fonts/fangsong.TTF");//仿宋
            holder.time.setTypeface(tf);
            holder.title.setTypeface(tf);*/

            if (news.getImage().length()>0){
                Glide.with(getContext())
                        .load(news.getImage())
                        .apply(new RequestOptions().placeholder(R.drawable.loading))
                        .apply(new RequestOptions() .error(R.drawable.error))
                        .apply(new RequestOptions() .centerCrop())
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
