package com.example.luhongcheng.SIT_SQ_other;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.luhongcheng.Adapter.QA_Adapter;
import com.example.luhongcheng.Bmob_bean.QA;
import com.example.luhongcheng.Bmob_bean.Report;
import com.example.luhongcheng.Bmob_bean.UserInfo;
import com.example.luhongcheng.R;
import com.example.luhongcheng.utils.ItemClickSupport;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;


public class QA_More extends Activity {

    SwipeRefreshLayout refreshLayout;
    RecyclerView recyclerView;
    Toolbar toolbar;

    String person_id;
    String fenqu=null;
    private List<String> my_collection = new ArrayList<>();//我的收藏集合
    private List<String> my_Likes = new ArrayList<>(); //我的喜欢合集
    private List<com.example.luhongcheng.bean.QA> mList = new ArrayList<>();
    private List<String> url = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sq_qa_more);
        toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.qa_recycler);
        refreshLayout = findViewById(R.id.refresh);
        onClick();
        SharedPreferences sp = getSharedPreferences("personID",0);
        person_id =  sp.getString("ID","");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorAccent));//设置状态栏背景色
        }

        String tag = getIntent().getStringExtra("from");
        if (tag.equals("life")){
            toolbar.setTitle("问答—life");
            fenqu = "0";
            get_MyCollection();
        }
        else if (tag.equals("study")){
            toolbar.setTitle("问答-Study");
            fenqu = "1";
            get_MyCollection();
        }
    }

    private void onClick() {
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        getDate();
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                refreshLayout.setRefreshing(false);
                            }
                        });
                    }
                }).run();
            }
        });
    }


    private void get_MyCollection() {
        if (person_id.length() == 0){
            //Toast.makeText(getActivity(),"没有获取到ID",Toast.LENGTH_SHORT).show();
            SharedPreferences sp=getSharedPreferences("userid",0);
            final String username = sp.getString("username","");

            Thread collection = new Thread(new Runnable() {
                @Override
                public void run() {
                    BmobQuery<UserInfo> query2 = new BmobQuery<UserInfo>();
                    query2.addWhereContains("ID",username);
                    query2.findObjects(new FindListener<UserInfo>() {
                        @Override
                        public void done(List<UserInfo> list, BmobException e) {
                            if (e == null) {
                                person_id = list.get(0).getObjectId();

                                SharedPreferences.Editor editor=getSharedPreferences("personID",0).edit();
                                editor.putString("ID",person_id);
                                editor.commit();

                                if (list.get(0).getMy_Collection() != null){
                                    my_collection = list.get(0).getMy_Collection();
                                }

                                if (list.get(0).getMy_Likes() != null){
                                    my_Likes = list.get(0).getMy_Likes();
                                }

                                getDate();

                                Log.d("QA：","get_MyCollection()");
                            } else {
                                Log.i("bmob图片", "失败：" + e.getMessage() + "," + e.getErrorCode());
                            }
                        }
                    });

                }
            });
            collection.start();

        }else {
            Thread collection = new Thread(new Runnable() {
                @Override
                public void run() {
                    BmobQuery<UserInfo> query2 = new BmobQuery<UserInfo>();
                    query2.getObject(person_id, new QueryListener<UserInfo>() {
                        @Override
                        public void done(UserInfo object, BmobException e) {
                            if (e == null) {

                                if (object.getMy_Collection() != null){
                                    my_collection.addAll(object.getMy_Collection());
                                }

                                if (object.getMy_Likes() != null){
                                    my_Likes = object.getMy_Likes();
                                }

                                getDate();

                                Log.d("QA：","get_MyCollection()");
                            } else {
                                Log.i("bmob图片", "失败：" + e.getMessage() + "," + e.getErrorCode());
                            }
                        }
                    });
                }
            });
            collection.start();
        }
    }

    private void getDate() {
        mList.clear();
        Thread qa = new Thread(new Runnable() {
            @Override
            public void run() {
                BmobQuery<QA> query = new BmobQuery<QA>();
                query.order("-createdAt");
                query.setLimit(20);
                query.addWhereEqualTo("fenqu",fenqu);
                query.findObjects(new FindListener<QA>(){
                    @Override
                    public void done(final List<QA> list, BmobException e) {
                        if (list != null) {

                            String title;
                            String content;
                            String time;
                            String item_id;
                            String author_id;
                            String tag;

                            for(int i = 0;i<list.size();i++){
                                title = list.get(i).getTitle();
                                content = list.get(i).getContent();
                                time = list.get(i).getCreatedAt();
                                item_id = list.get(i).getObjectId();
                                author_id = list.get(i).getAuthor().getObjectId();
                                tag = list.get(i).getFenqu();


                                url = list.get(i).getImage();
                                mList.add(new com.example.luhongcheng.bean.QA(url,title,content,time,item_id,my_Likes,author_id,tag));
                            }
                            Message msg = new Message();
                            msg.what = 1;
                            handler.sendMessage(msg);

                        }

                    }

                });


            }
        });
        qa.start();

    }

    @SuppressLint("HandlerLeak")
    private Handler handler= new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == 1){
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(mLayoutManager);
                QA_Adapter mAdapter = new QA_Adapter(getApplicationContext(),mList);
                recyclerView.setAdapter(mAdapter);

                ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        //Toast.makeText(getContext(),"短按了一下",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), SQ_SecondLayout.class);
                        intent.putExtra("from","QA");
                        intent.putExtra("item_id",mList.get(position).getItem_id());
                        intent.putExtra("author_id",mList.get(position).getAuthor_id());
                        startActivity(intent);

                    }
                });

                ItemClickSupport.addTo(recyclerView).setOnItemLongClickListener(new ItemClickSupport.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClicked(RecyclerView recyclerView, final int position, View v) {
                        Vibrator vibrator = (Vibrator)getSystemService(VIBRATOR_SERVICE);
                        vibrator.vibrate(50);

                        /*连续震动
                        Vibrator vibrator = (Vibrator)this.getSystemService(this.VIBRATOR_SERVICE);
                        long[] patter = {1000, 1000, 2000, 50};
                        vibrator.vibrate(patter, 0);
                        vibrator.cancel();*/

                        final String[] items = {"收藏","举报"};

                        final AlertDialog.Builder listDialog = new AlertDialog.Builder(getApplicationContext());
                        listDialog.setItems(items, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which){
                                    case 0:
                                        //收藏
                                        collection_item(mList.get(position).getItem_id());
                                        break;
                                    case 1:
                                        //举报
                                        report_item(mList.get(position).getItem_id());
                                        break;
                                }
                            }
                        });
                        listDialog.show();

                        //Toast.makeText(getContext(),"长按了一下",Toast.LENGTH_SHORT).show();
                        return false;
                    }
                });


            }
        }
    };

    private void report_item(final String id) {
        final EditText et = new EditText(getApplicationContext());
        new AlertDialog.Builder(getApplicationContext()).setTitle("举报")
                .setIcon(R.drawable.report)
                .setView(et)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String input = et.getText().toString();
                        if (input.equals("")) {
                            Toast.makeText(getApplicationContext(), "内容不能为空！" + input, Toast.LENGTH_LONG).show();
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
                                        Toast.makeText(getApplicationContext(),"举报成功",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }
                })
                .setNegativeButton("取消", null)
                .show();


    }

    private void collection_item(String id) {
        if (!my_collection.contains(id)){
            my_collection.add(id);

            UserInfo p2 = new UserInfo();
            p2.setValue("My_Collection",my_collection);
            p2.update(person_id, new UpdateListener() {
                @Override
                public void done(BmobException e) {
                    if(e==null){
                        Toast.makeText(getApplicationContext(),"收藏成功",Toast.LENGTH_SHORT).show();
                    }else{
                        // Toast.makeText(mContext,"error"+e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }else {
            Toast.makeText(getApplicationContext(),"你已经收藏过了",Toast.LENGTH_SHORT).show();
        }



    }



}
