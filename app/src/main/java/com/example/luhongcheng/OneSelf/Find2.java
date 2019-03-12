package com.example.luhongcheng.OneSelf;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.luhongcheng.Bmob_bean.UserInfo;
import com.example.luhongcheng.View.CircleImageView;
import com.example.luhongcheng.R;
import com.example.luhongcheng.bean.Friends;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

public class Find2 extends Activity {

    ImageView back;
    ImageView search;
    EditText text;
    String mText;
    private ListView mlistview;
    private List<Friends> mlist = new ArrayList<>();
    private Handler handler;
    private SwipeRefreshLayout refreshLayout;
    private ProgressBar progressBar;

    List<String> you_guanzhu = new ArrayList<>();
    List<String> you_fensi = new ArrayList<>();

    List<String> he_guanzhu = new ArrayList<>();
    List<String> he_fensi = new ArrayList<>();

    private String you_id;

    String GZ_Status = "0";//关注状态
    String You_Statue;//你的状态，代表是否获取到关注和粉丝列表

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find);
        back = (ImageView)findViewById(R.id.back);
        search = (ImageView)findViewById(R.id.search);
        text = (EditText)findViewById(R.id.text);
        mlistview = (ListView)findViewById(R.id.search_friends);
        refreshLayout  =(SwipeRefreshLayout)findViewById(R.id.refresh);
        progressBar = (ProgressBar)findViewById(R.id.ProgressBar);
        cn.bmob.v3.Bmob.initialize(this, "69d2a14bfc1139c1e9af3a9678b0f1ed");

        //状态栏颜色
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.teal_300));
        }



        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if(msg.what == 1){
                    progressBar.setVisibility(View.GONE);
                    FriendAdaper adapter = new FriendAdaper(mlist,getApplicationContext());
                    mlistview.setAdapter(adapter);
                    mlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Friends news = mlist.get(position);

                            Intent intent = new Intent(getApplicationContext(),ShowOnePerson.class);
                            intent.putExtra("person_id",news.getPerson_id());
                            startActivity(intent);
                        }
                    });
                }
            }
        };

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
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

                        getOneInfo();
                        //关闭刷新
                        refreshLayout.setRefreshing(false);

                    }
                }).start();
            }
        });

        onClick();
    }

    private void getOneInfo() {
        you_fensi.clear();
        you_guanzhu.clear();
        SharedPreferences sp=getSharedPreferences("userid",0);
        final String username = sp.getString("username","");

        SharedPreferences sp2=getSharedPreferences("personID",0);
        you_id = sp2.getString("ID","");

        if (username != null){
            BmobQuery<UserInfo> query2 = new BmobQuery<UserInfo>();
            query2.addWhereEqualTo("ID", username);
            query2.findObjects(new FindListener<UserInfo>() {
                @Override
                public void done(List<UserInfo> object, BmobException e) {
                    if(e==null){

                        for (UserInfo xixi : object) {
                            you_guanzhu = xixi.getGuanzhu();
                            you_fensi = xixi.getFensi();
                           // Toast.makeText(getApplicationContext(),"你的关注："+you_guanzhu.size()+"你的粉丝："+you_fensi.size(),Toast.LENGTH_LONG).show();
                        }
                        Start_Search();

                    }else{
                        Toast.makeText(getApplicationContext(),"查询你的粉丝列表失败",Toast.LENGTH_SHORT).show();
                        //Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                    }
                }
            });
        }


    }

    private void onClick() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String output = text.getText().toString();
                if (output.length() == 10){
                    progressBar.setVisibility(View.VISIBLE);
                    getOneInfo();
                }else {
                    Toast.makeText(getApplicationContext(),"请输入正确学号",Toast.LENGTH_SHORT).show();
                }
            }
        });

        text.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                    String output = text.getText().toString();
                    if (output.length() == 10){
                        progressBar.setVisibility(View.VISIBLE);
                        Start_Search();
                    }else {
                        Toast.makeText(getApplicationContext(),"请输入正确学号",Toast.LENGTH_SHORT).show();
                    }


                }
                return false;
            }
        });

        mlistview.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {
                boolean enable = false;
                if (mlistview != null &&mlistview.getChildCount() > 0) {
                    boolean firstItemVisible = mlistview.getFirstVisiblePosition() == 0;
                    boolean topOfFirstItemVisible = mlistview.getChildAt(0).getTop() == 0;
                    enable = firstItemVisible && topOfFirstItemVisible;
                }
                refreshLayout.setEnabled(enable);
            }
        });

    }

    private void Start_Search() {
        mText = text.getText().toString();
        mlist.clear();

        he_fensi.clear();
        he_guanzhu.clear();
        Thread getnews = new Thread(new Runnable() {
            @Override
            public void run() {
                BmobQuery<UserInfo> query = new BmobQuery<UserInfo>();
                query.addWhereEqualTo("ID", mText);
                query.findObjects(new FindListener<UserInfo>() {
                    @Override
                    public void done(List<UserInfo> list, BmobException e) {
                        if (e == null){
                            for (UserInfo userInfo : list){
                                String name,qm,id,xueyuan;

                                /*Log.d("信息-学号",userInfo.getID());
                                Log.d("信息-关注",userInfo.getGuanzhu().toString());
                                Log.d("信息-粉丝",userInfo.getFensi().toString());
                                Log.d("信息-昵称",userInfo.getNickname());
                                Log.d("信息-头像连接",userInfo.geticonUrl());
                                Log.d("信息-签名",userInfo.getQM());*/

                                id = userInfo.getObjectId();

                                name = userInfo.getName().replace("姓名：","");

                                xueyuan = userInfo.getXueyuan().replace("院系：","");


                                if (userInfo.getQM() != null){
                                    qm = userInfo.getQM();
                                }else {
                                    qm = "这个人很懒，什么都没有留下";
                                }

                                if(userInfo.getGuanzhu() != null){
                                    he_guanzhu = userInfo.getGuanzhu();
                                }

                                if (userInfo.getFensi() != null){
                                    he_fensi = userInfo.getFensi();
                                }

                                mlist.add(new Friends(name,xueyuan,qm,id));

                                Message msg = new Message();
                                msg.what = 1;
                                handler.sendMessage(msg);
                            }
                        }
                    }
                });


            }
        });
        getnews.start();


    }


    public class FriendAdaper extends BaseAdapter {

        List<Friends> list;
        Context mContext;

        public FriendAdaper(List<Friends> list,Context mContext) {
            super();
            this.mContext = mContext;
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
            FriendAdaper.ViewHolder holder = null;
            if(convertView==null){
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.find_item, null);
                holder = new FriendAdaper.ViewHolder();
                holder.name = (TextView)convertView.findViewById(R.id.nickname);
                holder.icon = (CircleImageView) convertView.findViewById(R.id.icon);
                holder.qm= (TextView) convertView.findViewById(R.id.qm);
                holder.xueyuan = (TextView) convertView.findViewById(R.id.fensi);
                holder.check = (TextView)convertView.findViewById(R.id.check);


                convertView.setTag(holder);
            }else{
                holder = (FriendAdaper.ViewHolder) convertView.getTag();
            }
            final Friends news = list.get(position);
            holder.name.setText(news.getName());
            holder.qm.setText(news.getQm());
            holder.xueyuan.setText(news.getXueyuan());

            String id = news.getPerson_id();
            final String[] icon_url = new String[1];

            BmobQuery<UserInfo> query = new BmobQuery<UserInfo>();
            query.addWhereEqualTo("ID", mText);
            final ViewHolder finalHolder1 = holder;
            query.findObjects(new FindListener<UserInfo>() {
                @Override
                public void done(List<UserInfo> list, BmobException e) {
                    if (e == null){
                        for (UserInfo userInfo : list){

                            icon_url[0] = userInfo.geticonUrl();
                            //Log.d("头像",icon_url[0]);
                        }

                        Glide.with(mContext)
                                .load(icon_url[0])
                                .apply(new RequestOptions().placeholder(R.drawable.loading))
                                .apply(new RequestOptions() .error(R.drawable.error))
                                .into(finalHolder1.icon);
                    }
                }
            });


            if (he_guanzhu == null  && you_guanzhu ==null){
                GZ_Status = "0";    //互不关注
                holder.check.setText("关注");
            }
            if (you_guanzhu.contains(news.getPerson_id()) && !he_fensi.contains(you_id)){
                GZ_Status = "1";  //你关注了它,他没关注你
                holder.check.setText("已关注");
            }
            if (he_fensi.contains(you_id) && !you_guanzhu.contains(news.getPerson_id())){
                GZ_Status = "2"; //它关注了你，你没关注他
                //holder.check.setText("");
            }
            if (he_fensi.contains(you_id) && you_guanzhu.contains(news.getPerson_id())){
                GZ_Status = "3"; //互相关注
                holder.check.setText("互相关注");
            }
            if (you_id == news.getPerson_id()){
                GZ_Status = "4";
                holder.check.setVisibility(View.INVISIBLE);
            }

            final ViewHolder finalHolder = holder;
            holder.check.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if (GZ_Status == "0"){
                        progressBar.setVisibility(View.VISIBLE);
                        if (you_guanzhu != null){
                            you_guanzhu.add(news.getPerson_id());

                            UserInfo object = new  UserInfo();
                            object.setGuanzhu(you_guanzhu);
                            object.update(you_id, new UpdateListener() {
                                @Override
                                public void done(BmobException e1) {
                                    if(e1==null){
                                        Toast.makeText(getApplicationContext(), "关注成功", Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.INVISIBLE);
                                        finalHolder.check.setText("已关注");
                                        GZ_Status = "1";
                                    }else{
                                        //Toast.makeText(setMy.this, "你已经设置过了", Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.INVISIBLE);
                                        Toast.makeText(getApplicationContext(), "关注失败", Toast.LENGTH_SHORT).show();
                                        Log.i("bmob","更新失败："+e1.getMessage()+","+e1.getErrorCode());
                                    }
                                }
                            });

                        }else {
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(getApplicationContext(), "关注列表为空", Toast.LENGTH_SHORT).show();
                        }

                    }

                    if (GZ_Status == "1"){
                        //Toast.makeText(getApplicationContext(), "暂不支持取消关注", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.VISIBLE);
                        if (you_guanzhu != null){
                            you_guanzhu.remove(news.getPerson_id());

                            UserInfo object = new  UserInfo();
                            object.setGuanzhu(you_guanzhu);
                            object.update(you_id, new UpdateListener() {
                                @Override
                                public void done(BmobException e1) {
                                    if(e1==null){
                                        Toast.makeText(getApplicationContext(), "取消关注成功", Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.INVISIBLE);
                                        finalHolder.check.setText("未关注");
                                        GZ_Status = "0";
                                    }else{
                                        progressBar.setVisibility(View.INVISIBLE);
                                        Toast.makeText(getApplicationContext(), "失败", Toast.LENGTH_SHORT).show();
                                        Log.i("bmob","更新失败："+e1.getMessage()+","+e1.getErrorCode());
                                    }
                                }
                            });

                        }else {
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(getApplicationContext(), "关注列表为空", Toast.LENGTH_SHORT).show();
                        }

                    }

                    if (GZ_Status =="2"){
                        progressBar.setVisibility(View.VISIBLE);
                        if (you_guanzhu != null){
                            you_guanzhu.add(news.getPerson_id());

                            UserInfo object = new  UserInfo();
                            object.setGuanzhu(you_guanzhu);
                            object.update(you_id, new UpdateListener() {
                                @Override
                                public void done(BmobException e1) {
                                    if(e1==null){
                                        Toast.makeText(getApplicationContext(), "关注成功", Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.INVISIBLE);
                                        finalHolder.check.setText("已关注");
                                        GZ_Status = "3";
                                    }else{
                                        progressBar.setVisibility(View.INVISIBLE);
                                        //Toast.makeText(setMy.this, "你已经设置过了", Toast.LENGTH_SHORT).show();
                                        Toast.makeText(getApplicationContext(), "关注失败", Toast.LENGTH_SHORT).show();
                                        Log.i("bmob","更新失败："+e1.getMessage()+","+e1.getErrorCode());
                                    }
                                }
                            });

                        }else {
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(getApplicationContext(), "关注列表为空", Toast.LENGTH_SHORT).show();
                        }

                    }


                    if (GZ_Status == "3"){
                        //Toast.makeText(getApplicationContext(), "暂不支持取消关注", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.VISIBLE);
                        if (you_guanzhu != null){
                            you_guanzhu.remove(news.getPerson_id());

                            UserInfo object = new  UserInfo();
                            object.setGuanzhu(you_guanzhu);
                            object.update(you_id, new UpdateListener() {
                                @Override
                                public void done(BmobException e1) {
                                    if(e1==null){
                                        Toast.makeText(getApplicationContext(), "取消关注成功", Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.INVISIBLE);
                                        finalHolder.check.setText("未关注");
                                        GZ_Status = "2";
                                    }else{
                                        progressBar.setVisibility(View.INVISIBLE);
                                        Toast.makeText(getApplicationContext(), "失败", Toast.LENGTH_SHORT).show();
                                        Log.i("bmob","更新失败："+e1.getMessage()+","+e1.getErrorCode());
                                    }
                                }
                            });

                        }else {
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(getApplicationContext(), "关注列表为空", Toast.LENGTH_SHORT).show();
                        }

                    }

                    if (GZ_Status == "4"){
                        Toast.makeText(getApplicationContext(), "你还想关注自己咋地？", Toast.LENGTH_SHORT).show();
                    }

                }
            });

            return convertView;
        }

        class ViewHolder {
            CircleImageView icon;
            TextView name,qm,xueyuan;
            TextView check;
        }

    }





}
