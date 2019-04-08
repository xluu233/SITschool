package com.example.luhongcheng.SIT_SQ;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.luhongcheng.Adapter.ShareNews_Adapter;
import com.example.luhongcheng.Bmob_bean.Report;
import com.example.luhongcheng.R;
import com.example.luhongcheng.SIT_SQ_other.Share_News;
import com.example.luhongcheng.bean.HotNews;
import com.example.luhongcheng.utils.ItemClickSupport;
import com.example.luhongcheng.WebDisplay;
import com.github.clans.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;


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
    String person_id;


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
        SharedPreferences sp=getActivity().getSharedPreferences("personID",0);
        person_id =  sp.getString("ID","");

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
                BmobQuery<com.example.luhongcheng.Bmob_bean.Share_News> query = new BmobQuery<>();
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

                            String item_id;

                            for(int i = 0;i<list.size();i++){
                                title[i] = list.get(i).getTitle();
                                image[i] = list.get(i).getimageUrl();
                                time[i] = list.get(i).getCreatedAt();
                                url[i] = list.get(i).getUrl();
                                item_id = list.get(i).getObjectId();

                                Log.d("news:",title[i]);
                                Log.d("news:",image[i] );
                                Log.d("news:",time[i]);
                                Log.d("news:",url[i]);

                                mlist.add(new HotNews(title[i],image[i],time[i],url[i],item_id));
                            }
                            Message msg = new Message();
                            msg.what = 1;
                            mHandler.sendMessage(msg);

                            //mHandler2.obtainMessage(0).sendToTarget();
                        }
                    }

                });


            }
        });
        getnews.start();

    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler= new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == 1){
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setAdapter(new ShareNews_Adapter(getContext(),mlist));


                ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        Intent intent = new Intent(getActivity(), WebDisplay.class);
                        intent.putExtra("news_url",mlist.get(position).getUrl());
                        intent.putExtra("title","社区");
                        startActivity(intent);

                    }
                });


                ItemClickSupport.addTo(recyclerView).setOnItemLongClickListener(new ItemClickSupport.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClicked(RecyclerView recyclerView, final int position, View v) {
                        Vibrator vibrator = (Vibrator)getActivity().getSystemService(getActivity().VIBRATOR_SERVICE);
                        vibrator.vibrate(50);

                        final String[] items = {"举报"};
                        final AlertDialog.Builder listDialog = new AlertDialog.Builder(getContext());
                        listDialog.setItems(items, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which){
                                    case 0:
                                        report_item(mlist.get(position).getItem_id());
                                        break;
                                }
                            }
                        });
                        listDialog.show();
                        return false;
                    }
                });


            }
        }
    };


    private void report_item(final String id) {

        final EditText et = new EditText(getContext());
        new AlertDialog.Builder(getContext()).setTitle("举报")
                .setIcon(R.drawable.report)
                .setView(et)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String input = et.getText().toString();
                        if (input.equals("")) {
                            Toast.makeText(getContext(), "内容不能为空！" + input, Toast.LENGTH_LONG).show();
                        }
                        else {
                            Report report = new Report();
                            report.setItem_id(id);
                            report.setTitle(input);
                            report.setUser_id(person_id);
                            report.save(new SaveListener<String>() {
                                @Override
                                public void done(String s, BmobException e) {
                                    if (e==null){
                                        Toast.makeText(getActivity(),"举报成功",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }
                })
                .setNegativeButton("取消", null)
                .show();


    }








}
