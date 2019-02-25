package com.example.luhongcheng.OneSelf;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
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
import com.example.luhongcheng.Bmob.UserInfo;
import com.example.luhongcheng.ImageView.CircleImageView;
import com.example.luhongcheng.R;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

public class Find extends Activity {

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
                            //Friends news = mlist.get(position);

                            //Intent intent = new Intent(getApplicationContext(),ShowOnePerson.class);
                            //intent.putExtra("person_id",news.getPerson_id());
                            //startActivity(intent);
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

                        Start_Search();
                        //关闭刷新
                        refreshLayout.setRefreshing(false);

                    }
                }).start();
            }
        });

        onClick();
        getOneInfo();
    }

    private void getOneInfo() {
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
                    Start_Search();
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

        Thread getnews = new Thread(new Runnable() {
            @Override
            public void run() {
                BmobQuery<UserInfo> query = new BmobQuery<UserInfo>();
                query.addWhereEqualTo("ID", mText);
                //query.addWhereContains("ID",mText);
                query.order("-createdAt");
                query.setLimit(10);
                query.findObjects(new FindListener<UserInfo>(){
                    @Override
                    public void done(final List<UserInfo> object, BmobException e) {
                        if(e==null){

                            if (object.size() != 0){
                                String[] id = new String[object.size()];
                                String[] nickname = new String[object.size()];
                                String[] qm = new String[object.size()];
                                String[] fs = new String[object.size()];
                                String[] icon = new String[object.size()];

                                for (UserInfo xixi : object) {
                                    he_guanzhu = xixi.getGuanzhu();
                                    he_fensi = xixi.getFensi();
                                    // Toast.makeText(getApplicationContext(),"他的关注："+he_guanzhu.size()+"他的粉丝："+he_fensi.size(),Toast.LENGTH_SHORT).show();
                                }

                                for (int i=0;i<object.size();i++){
                                    id[i] = object.get(i).getObjectId();

                                    if (object.get(i).getNickname() != null){
                                        nickname[i] = object.get(i).getNickname();
                                    }else {
                                        nickname[i] = "  ";
                                    }

                                    if (object.get(i).getQM() != null){
                                        qm[i] = object.get(i).getQM();
                                    }else {
                                        qm[i] = "这个人很懒，什么都木有";
                                    }

                                    if (object.get(i).getFensi() != null){
                                        fs[i] = String.valueOf(object.get(i).getFensi().size());
                                    }else {
                                        fs[i] = "0";
                                    }

                                    if (object.get(i).geticonUrl() != null){
                                        icon[i] = object.get(i).geticonUrl();
                                    }else {
                                        icon[i] = "https://gss3.bdstatic.com/7Po3dSag_xI4khGkpoWK1HF6hhy/baike/w%3D268%3Bg%3D0/sign=4a48b3ac4f10b912bfc1f1f8fbc69b3e/500fd9f9d72a6059a274150d2034349b033bba45.jpg";
                                    }

                                    mlist.add(new Friends(nickname[i],qm[i],fs[i],icon[i],id[i]));

                                    //Toast.makeText(getApplicationContext(),"查询成功",Toast.LENGTH_SHORT).show();


                                    Log.e("获得信息：", String.valueOf(mlist));
                                }

                                Message msg = new Message();
                                msg.what = 1;
                                handler.sendMessage(msg);
                            }

                        }else{
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(getApplicationContext(),"没有找到这位同学哦，快把App分享给她吧",Toast.LENGTH_SHORT).show();
                           // Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                        }

                    }

                });


            }
        });
        getnews.start();



        /*
        BmobQuery<UserInfo> query3 = new BmobQuery<UserInfo>();
        query3.addWhereEqualTo("ID", mText);
        query3.findObjects(new FindListener<UserInfo>() {
            @Override
            public void done(List<UserInfo> object, BmobException e) {
                if(e==null){
                    for (UserInfo xixi : object) {
                        he_guanzhu = xixi.getGuanzhu();
                        he_fensi = xixi.getFensi();
                       // Toast.makeText(getApplicationContext(),"他的关注："+he_guanzhu.size()+"他的粉丝："+he_fensi.size(),Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(),"查询它的粉丝列表失败",Toast.LENGTH_LONG).show();
                    //he_fensi = null;
                    //he_guanzhu = null;
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });
        */

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
                holder.nickname = (TextView)convertView.findViewById(R.id.nickname);
                holder.icon = (CircleImageView) convertView.findViewById(R.id.icon);
                holder.qm= (TextView) convertView.findViewById(R.id.qm);
                holder.fensi= (TextView) convertView.findViewById(R.id.fensi);
                holder.check = (TextView)convertView.findViewById(R.id.check);


                convertView.setTag(holder);
            }else{
                holder = (FriendAdaper.ViewHolder) convertView.getTag();
            }
            final Friends news = list.get(position);
            holder.nickname.setText(news.getNickname());
            holder.qm.setText(news.getQm());
            holder.fensi.setText(news.getFensi()+ "  粉丝");


            if (news.getIcon_url().length()>0){
                Glide.with(mContext)
                        .load(news.getIcon_url())
                        .error(R.drawable.error)
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
            if (you_id.equals(news.getPerson_id())){
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
                                        Toast.makeText(getApplicationContext(), "关注失败", Toast.LENGTH_SHORT).show();
                                        Log.i("bmob","更新失败："+e1.getMessage()+","+e1.getErrorCode());
                                    }
                                }
                            });

                        }else {
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
                                        Toast.makeText(getApplicationContext(), "失败", Toast.LENGTH_SHORT).show();
                                        Log.i("bmob","更新失败："+e1.getMessage()+","+e1.getErrorCode());
                                    }
                                }
                            });

                        }else {
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
                                        //Toast.makeText(setMy.this, "你已经设置过了", Toast.LENGTH_SHORT).show();
                                        Toast.makeText(getApplicationContext(), "关注失败", Toast.LENGTH_SHORT).show();
                                        Log.i("bmob","更新失败："+e1.getMessage()+","+e1.getErrorCode());
                                    }
                                }
                            });

                        }else {
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
                                        Toast.makeText(getApplicationContext(), "失败", Toast.LENGTH_SHORT).show();
                                        Log.i("bmob","更新失败："+e1.getMessage()+","+e1.getErrorCode());
                                    }
                                }
                            });

                        }else {
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
            TextView nickname,qm,fensi;
            TextView check;
        }

    }





}
