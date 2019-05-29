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
import android.support.v7.widget.RecyclerView;
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
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

public class Find2 extends Activity {

    ImageView back;
    ImageView search;
    EditText text;

    private ListView listView;
    private List<Friends> mlist = new ArrayList<>();
    private Handler handler;
    private SwipeRefreshLayout refreshLayout;
    private ProgressBar progressBar;

    List<String> you_guanzhu = new ArrayList<>();
    List<String> you_fensi = new ArrayList<>();

    List<String> he_guanzhu = new ArrayList<>();
    List<String> he_fensi = new ArrayList<>();

    String you_id;//我的person id;
    String GZ_Status = "0";//关注状态

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find);
        back = findViewById(R.id.back);
        search = findViewById(R.id.search);
        text = findViewById(R.id.text);
        listView = findViewById(R.id.search_friends);
        refreshLayout =findViewById(R.id.refresh);
        progressBar = findViewById(R.id.ProgressBar);
        //状态栏颜色
        getWindow().setStatusBarColor(getResources().getColor(R.color.colorAccent));

        SharedPreferences sp2=getSharedPreferences("personID",0);
        you_id = sp2.getString("ID","");

        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if(msg.what == 1){
                    hide_progressbar();
                    FriendAdaper adapter = new FriendAdaper(mlist,getApplicationContext());
                    listView.setAdapter(adapter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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



        onClick();
    }

    private void hide_progressbar() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.GONE);
            }
        });
    }


    private void show_progressbar() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.VISIBLE);
            }
        });
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
                    show_progressbar();
                    getOneInfo();
                }else {
                    Toast.makeText(getApplicationContext(),"请输入正确学号",Toast.LENGTH_SHORT).show();
                }

                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    public void run() {
                        hide_progressbar();
                    }
                }, 5000);

            }
        });

        text.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                    String output = text.getText().toString();
                    if (output.length() == 10){
                        show_progressbar();
                        getOneInfo();
                    }else {
                        Toast.makeText(getApplicationContext(),"请输入正确学号",Toast.LENGTH_SHORT).show();
                    }


                }
                return false;
            }
        });


        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String output = text.getText().toString();
                        if (output.length() == 10){
                            getOneInfo();
                        }else {
                            Toast.makeText(getApplicationContext(),"请输入正确学号",Toast.LENGTH_SHORT).show();
                        }

                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        //关闭刷新
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                refreshLayout.setRefreshing(false);
                            }
                        });

                    }
                }).start();
            }
        });

    }


    private void getOneInfo() {
        you_fensi.clear();
        you_guanzhu.clear();
        SharedPreferences sp=getSharedPreferences("userid",0);
        String username = sp.getString("username","");

        final String id = text.getText().toString();
        if(id.equals(username)){
            hide_progressbar();
            Toast.makeText(getApplicationContext(),"不能查询自己",Toast.LENGTH_SHORT).show();
        }else {
            //获取我的关注信息
            BmobQuery<UserInfo> query = new BmobQuery<>();
            query.getObject(you_id, new QueryListener<UserInfo>() {
                @Override
                public void done(UserInfo userInfo, BmobException e) {
                    if (e==null){
                        you_guanzhu = userInfo.getGuanzhu();
                        you_fensi = userInfo.getFensi();
                        // Toast.makeText(getApplicationContext(),"你的关注："+you_guanzhu.size()+"你的粉丝："+you_fensi.size(),Toast.LENGTH_LONG).show();
                        Start_Search(id);
                    }else {
                        hide_progressbar();
                        Toast.makeText(getApplicationContext(),"查询你的粉丝列表失败",Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }


    }


    private void Start_Search(final String id) {
        mlist.clear();

        he_fensi.clear();
        he_guanzhu.clear();
        new Thread(new Runnable() {
            @Override
            public void run() {
                BmobQuery<UserInfo> query = new BmobQuery<>();
                query.addWhereEqualTo("ID", id);
                query.findObjects(new FindListener<UserInfo>() {
                    @Override
                    public void done(List<UserInfo> list, BmobException e) {
                        if (e == null){
                            for (UserInfo userInfo : list){

                                /*Log.d("信息-学号",userInfo.getID());
                                Log.d("信息-关注",userInfo.getGuanzhu().toString());
                                Log.d("信息-粉丝",userInfo.getFensi().toString());
                                Log.d("信息-昵称",userInfo.getNickname());
                                Log.d("信息-头像连接",userInfo.geticonUrl());
                                Log.d("信息-签名",userInfo.getQM());*/

                                String id = userInfo.getObjectId();
                                String name = userInfo.getName().replace("姓名：","");
                                String xueyuan = userInfo.getXueyuan();

                                String qm = null,icon = null;
                                if (userInfo.getQM()!=null){
                                    qm = userInfo.getQM();
                                }

                                if (userInfo.geticonUrl()!=null){
                                    icon = userInfo.geticonUrl();
                                }

                                if(userInfo.getGuanzhu() != null){
                                    he_guanzhu = userInfo.getGuanzhu();
                                }

                                if (userInfo.getFensi() != null){
                                    he_fensi = userInfo.getFensi();
                                }

                                mlist.add(new Friends(name,xueyuan,qm,id,icon));
                            }

                            Message msg = new Message();
                            msg.what = 1;
                            handler.sendMessage(msg);

                        }else {
                            Toast.makeText(getApplicationContext(),"查询失败",Toast.LENGTH_SHORT).show();
                            hide_progressbar();
                        }
                    }
                });


            }
        }).start();

    }


    public class FriendAdaper extends BaseAdapter {

        List<Friends> list;
        Context mContext;

        FriendAdaper(List<Friends> list, Context mContext) {
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

        @SuppressLint({"NewApi", "InflateParams"})
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final FriendAdaper.ViewHolder holder;
            if(convertView==null){
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.find_item, null);
                holder = new FriendAdaper.ViewHolder();
                holder.name = convertView.findViewById(R.id.nickname);
                holder.icon = convertView.findViewById(R.id.icon);
                holder.qm= convertView.findViewById(R.id.qm);
                holder.xueyuan = convertView.findViewById(R.id.fensi);
                holder.check = convertView.findViewById(R.id.check);


                convertView.setTag(holder);
            }else{
                holder = (FriendAdaper.ViewHolder) convertView.getTag();
            }
            final Friends news = list.get(position);
            holder.name.setText(news.getName());
            holder.xueyuan.setText(news.getXueyuan());

            if (news.getQm()!=null){
                holder.qm.setText(news.getQm());
            }

            if (news.getIcon_url()!=null){
                Glide.with(mContext)
                        .load(news.getIcon_url())
                        .apply(new RequestOptions().placeholder(R.drawable.loading))
                        .apply(new RequestOptions() .error(R.drawable.error))
                        .into(holder.icon);
            }



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


            final ViewHolder finalHolder = holder;
            holder.check.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if (GZ_Status.equals("0")){
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

                    if (GZ_Status.equals("1")){
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

                    if (GZ_Status.equals("2")){
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


                    if (GZ_Status.equals("3")){
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
