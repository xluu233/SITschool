package com.example.luhongcheng.OneSelf;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.luhongcheng.Adapter.QA_Adapter;
import com.example.luhongcheng.Bmob_bean.SQ;
import com.example.luhongcheng.Bmob_bean.UserInfo;
import com.example.luhongcheng.R;
import com.example.luhongcheng.SIT_SQ_other.SQ_SecondLayout;
import com.example.luhongcheng.bean.QA;
import com.example.luhongcheng.utils.ItemClickSupport;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;

import static org.litepal.LitePalApplication.getContext;

public class MyQA extends Activity {
    RecyclerView recyclerView;
    private List<QA> mList = new ArrayList<>();
    private List<String> my_collection = new ArrayList<>();//我的收藏集合
    private List<String> my_Likes = new ArrayList<>(); //我的喜欢合集
    String personID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.my_qa);
        super.onCreate(savedInstanceState);
        recyclerView = findViewById(R.id.my_qa);

        SharedPreferences sp=getSharedPreferences("personID",0);
        personID =  sp.getString("ID","");
        if (personID!=null){
            get_MyCollection();
        }else {
            Toast.makeText(getContext(),"no personID",Toast.LENGTH_SHORT).show();
        }


    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void get_MyCollection() {
        Thread collection = new Thread(new Runnable() {
            @Override
            public void run() {
                BmobQuery<UserInfo> query2 = new BmobQuery<>();
                query2.getObject(personID, new QueryListener<UserInfo>() {
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

                        }
                    }
                });
            }
        });
        collection.start();
    }





    private void getDate() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                UserInfo xixi = new UserInfo();
                xixi.setObjectId(personID);

                BmobQuery<com.example.luhongcheng.Bmob_bean.QA> query = new BmobQuery<>();
                query.addWhereEqualTo("author", xixi);
                query.order("-createdAt");
                query.setLimit(30);
                query.findObjects(new FindListener<com.example.luhongcheng.Bmob_bean.QA>(){
                    @Override
                    public void done(final List<com.example.luhongcheng.Bmob_bean.QA> list, BmobException e) {
                        if (list != null) {

                            String title;
                            String content;
                            String time;
                            String item_id;
                            String author_id;
                            String tag;
                            List<String> url;

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
        }).start();

    }


    @SuppressLint("HandlerLeak")
    private Handler handler= new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == 1){
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(mLayoutManager);

                QA_Adapter mAdapter = new QA_Adapter(getContext(),mList);
                recyclerView.setAdapter(mAdapter);

                ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        //Toast.makeText(getContext(),"短按了一下",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getContext(), SQ_SecondLayout.class);
                        intent.putExtra("from","QA");
                        intent.putExtra("item_id",mList.get(position).getItem_id());
                        intent.putExtra("author_id",mList.get(position).getAuthor_id());
                        startActivity(intent);

                    }
                });


            }
        }
    };



}
