package com.example.luhongcheng.SIT_SQ_other;

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
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.luhongcheng.Adapter.CommentRecyAdapter;
import com.example.luhongcheng.Bmob_bean.QA;
import com.example.luhongcheng.Bmob_bean.QA_Comment;
import com.example.luhongcheng.Bmob_bean.Report;
import com.example.luhongcheng.Bmob_bean.SQ;
import com.example.luhongcheng.Bmob_bean.SQ_Comment;
import com.example.luhongcheng.Bmob_bean.UserInfo;
import com.example.luhongcheng.View.CircleImageView;
import com.example.luhongcheng.R;
import com.example.luhongcheng.View.NineGridTestLayout;
import com.example.luhongcheng.View.PopupWindowList;
import com.example.luhongcheng.WebDisplay;
import com.example.luhongcheng.bean.PingLun;
import com.example.luhongcheng.utils.BaseStatusBarActivity;
import com.example.luhongcheng.utils.ItemClickSupport;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

import static org.litepal.LitePalApplication.getContext;

public class SQ_SecondLayout extends BaseStatusBarActivity {
    String item_id; //说说id
    String author_id; //作者id
    String my_id; //我的id
    String From_TAG;


    private List<String> urllist = new ArrayList<>(); //图片地址合集
    //private List<String> my_Likes = new ArrayList<>(); //我的喜欢合集
    private List<String> my_guanzhu = new ArrayList<>(); //我的关注合集
    private List<String> my_collection = new ArrayList<>();//我的收藏


    CircleImageView icon;
    TextView nickname,qm;
    TextView ss_title,ss_content,ss_time,guanzhu;
    NineGridTestLayout gridview;
    RecyclerView recyclerView;
    //SmartRefreshLayout refreshLayout;
    SwipeRefreshLayout refreshLayout;
    EditText editText;
    ImageView send,more_item;
    Toolbar toolbar;
    TextView no_comments;
    List<PingLun> comment_list = new ArrayList<>();


    @SuppressLint("NewApi")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.sq_second_layout);
        super.onCreate(savedInstanceState);
        initView();
        onClick();
        SharedPreferences sp=getSharedPreferences("personID",0);
        my_id =  sp.getString("ID","");
        From_TAG = getIntent().getStringExtra("from");
        item_id = getIntent().getStringExtra("item_id");
        author_id = getIntent().getStringExtra("author_id");

        get_UserInfo();
        if (From_TAG.equals("QA")){
            getDateFromQA(); //QA的信息
            //get_QAComment();
        }else if (From_TAG.equals("SQ")){
            ss_title.setVisibility(View.GONE);
            getDateFromSQ(); //SQ的信息
            //get_SQComment();
        }

        if (author_id.equals(my_id)){
            guanzhu.setVisibility(View.GONE);
        }

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    private void initView() {
        icon = findViewById(R.id.icon);
        nickname = findViewById(R.id.nickname);
        qm = findViewById(R.id.qm);
        ss_title = findViewById(R.id.title);
        ss_content = findViewById(R.id.content);
        ss_time = findViewById(R.id.time);
        guanzhu = findViewById(R.id.secondlayout_guanzhu);
        gridview = findViewById(R.id.layout_nine_grid);
        recyclerView = findViewById(R.id.comment_recy);
        refreshLayout = findViewById(R.id.item_refresh);
        editText = findViewById(R.id.msg);
        send = findViewById(R.id.send_msg);
        toolbar = findViewById(R.id.toolbar);
        no_comments = findViewById(R.id.no_comment);
        more_item = findViewById(R.id.more_item);
    }


    private void onClick() {
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (From_TAG.equals("QA")){
                    QA post = new QA();
                    post.setObjectId(item_id);

                    UserInfo user = new UserInfo();
                    user.setObjectId(my_id);

                    String content = editText.getText().toString();
                    if (content.length() != 0){
                        QA_Comment comment = new QA_Comment();
                        comment.setContent(content);
                        comment.setAuthor(user);
                        comment.setPost(post);
                        comment.save(new SaveListener<String>() {

                            @Override
                            public void done(String objectId, BmobException e) {
                                if(e==null){
                                    Toast.makeText(getApplicationContext(),"评论成功",Toast.LENGTH_SHORT).show();
                                }else{
                                    Log.i("bmob","失败："+e.getMessage());
                                }
                            }

                        });
                    }

                }else if (From_TAG.equals("SQ")){
                    SQ post1 = new SQ();
                    post1.setObjectId(item_id);

                    UserInfo user = new UserInfo();
                    user.setObjectId(my_id);

                    String content = editText.getText().toString();
                    if (content.length() != 0){
                        SQ_Comment comment = new SQ_Comment();
                        comment.setContent(content);
                        comment.setAuthor(user);
                        comment.setPost(post1);
                        comment.save(new SaveListener<String>() {
                            @Override
                            public void done(String objectId, BmobException e) {
                                if(e==null){
                                    Toast.makeText(getApplicationContext(),"评论成功",Toast.LENGTH_SHORT).show();
                                }else{
                                    Log.i("bmob","失败："+e.getMessage());
                                }
                            }

                        });
                    }
                }


            }
        });


        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        comment_list.clear();
                        get_UserInfo();
                        if (From_TAG.equals("QA")){
                            getDateFromQA(); //QA的信息
                            //get_QAComment();
                        }else if (From_TAG.equals("SQ")){
                            ss_title.setVisibility(View.GONE);
                            getDateFromSQ(); //SQ的信息
                            //get_SQComment();
                        }

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
                }).start();

            }
        });

        more_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopWindows(v);
            }
        });
    }


    //当前作者的信息
    private void get_UserInfo() {
        if (author_id.length() != 0 || item_id.length() !=0){
            Thread collection = new Thread(new Runnable() {
                @Override
                public void run() {
                    BmobQuery<UserInfo> query2 = new BmobQuery<>();
                    query2.getObject(author_id, new QueryListener<UserInfo>() {
                        @Override
                        public void done(UserInfo userInfo, BmobException e) {
                            if (e == null) {

                                String icon_url = userInfo.geticonUrl();
                                String qm2 = userInfo.getQM();
                                String nickname2 = userInfo.getNickname();
                                if (nickname2 == null){
                                    nickname2 = userInfo.getName().replace("你好：","");
                                }

                                Glide.with(getApplicationContext())
                                        .load(icon_url)
                                        .apply(new RequestOptions().placeholder(R.drawable.loading))
                                        .apply(new RequestOptions() .error(R.drawable.error))
                                        .into(icon);
                                nickname.setText(nickname2);
                                qm.setText(qm2);

                                get_MyInfo(); //我的信息

                            }
                        }
                    });
                }
            });
            collection.start();
        }else {
            Toast.makeText(this,"没有获取到ID",Toast.LENGTH_SHORT).show();
        }
    }

    private void get_MyInfo() {
        if (my_id.length() !=0){
            Thread collection_info = new Thread(new Runnable() {
                @Override
                public void run() {
                    BmobQuery<UserInfo> query2 = new BmobQuery<>();
                    query2.getObject(my_id, new QueryListener<UserInfo>() {
                        @Override
                        public void done(UserInfo userInfo, BmobException e) {
                            if (e == null) {

                                my_guanzhu = userInfo.getGuanzhu();
                                //my_Likes = userInfo.getMy_Likes();
                                my_collection = userInfo.getMy_Collection();

                                if (my_guanzhu.contains(author_id)){
                                    guanzhu.setText("已关注");
                                    guanzhu.setClickable(false);
                                }

                            }
                        }
                    });
                }
            });
            collection_info.start();
        }else {
            Toast.makeText(this,"没有获取到ID",Toast.LENGTH_SHORT).show();
        }

        guanzhu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!my_guanzhu.contains(author_id)){
                    my_guanzhu.add(author_id);
                    UserInfo object = new  UserInfo();
                    object.setGuanzhu(my_guanzhu);
                    object.update(my_id, new UpdateListener() {
                        @Override
                        public void done(BmobException e1) {
                            if(e1==null){
                                Toast.makeText(getApplicationContext(), "关注成功", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(getApplicationContext(), "关注失败", Toast.LENGTH_SHORT).show();
                                Log.i("bmob","更新失败："+e1.getMessage()+","+e1.getErrorCode());
                            }
                        }
                    });
                }

            }
        });
    }

    private void getDateFromSQ() {
        Log.d("getDate","getDateFromSQ");
        Thread qa_second = new Thread(new Runnable() {
            @Override
            public void run() {
                BmobQuery<SQ> query = new BmobQuery<>();
                query.getObject(item_id, new QueryListener<SQ>() {
                    @Override
                    public void done(SQ qa, BmobException e) {
                        String content = qa.getContent();
                        String time = qa.getCreatedAt();

                        urllist = qa.getImage();
                        ss_content.setText(content);
                        ss_time.setText(time);

                        final Matcher m = Pattern.compile("(?i)http://[^\u4e00-\u9fa5]+").matcher(content);
                        final Matcher ms = Pattern.compile("(?i)https://[^\u4e00-\u9fa5]+").matcher(content);
                        while(m.find()){
                            final String url = m.group();
                            ss_content.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent8 = new Intent(getApplicationContext(), WebDisplay.class);
                                    intent8.putExtra("news_url", url);
                                    intent8.putExtra("title","超链接");
                                    startActivity(intent8);
                                }
                            });
                        }
                        while(ms.find()){
                            final String url = ms.group();
                            ss_content.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent8 = new Intent(getApplicationContext(), WebDisplay.class);
                                    intent8.putExtra("news_url", url);
                                    intent8.putExtra("title","超链接");
                                    startActivity(intent8);
                                }
                            });
                        }

                        if (content.length() ==0){
                            ss_content.setVisibility(View.GONE);
                        }

                        Log.d("getDate", String.valueOf(urllist));
                        Log.d("getDate",content);
                        Log.d("getDate",time);

                        if (urllist != null){
                            gridview.setUrlList(urllist);
                        }

                        get_SQComment();
                    }
                });
            }
        });
        qa_second.start();
    }

    private void getDateFromQA() {
        Log.d("getDate","getDateFromQA");
        Thread qa_second = new Thread(new Runnable() {
            @Override
            public void run() {
                BmobQuery<QA> query = new BmobQuery<>();
                query.getObject(item_id, new QueryListener<QA>() {
                    @Override
                    public void done(QA qa, BmobException e) {
                        String title = qa.getTitle();
                        String content = qa.getContent();
                        String time = qa.getCreatedAt();

                        urllist = qa.getImage();

                        ss_title.setText(title);
                        ss_content.setText(content);
                        ss_time.setText(time);

                        if (content.length() ==0){
                            ss_content.setVisibility(View.GONE);
                        }

                        final Matcher m = Pattern.compile("(?i)http://[^\u4e00-\u9fa5]+").matcher(content);
                        final Matcher ms = Pattern.compile("(?i)https://[^\u4e00-\u9fa5]+").matcher(content);
                        while(m.find()){
                            final String url = m.group();
                            ss_content.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent8 = new Intent(getApplicationContext(), WebDisplay.class);
                                    intent8.putExtra("news_url", url);
                                    intent8.putExtra("title","超链接");
                                    startActivity(intent8);
                                }
                            });
                        }
                        while(ms.find()){
                            final String url = ms.group();
                            ss_content.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent8 = new Intent(getApplicationContext(), WebDisplay.class);
                                    intent8.putExtra("news_url", url);
                                    intent8.putExtra("title","超链接");
                                    startActivity(intent8);
                                }
                            });
                        }


//                        Log.d("getDate", String.valueOf(urllist));
//                        Log.d("getDate",time);
//                        Log.d("getDate",title);
//                        Log.d("getDate",content);

                        if (urllist != null){
                            gridview.setUrlList(urllist);
                        }

                        get_QAComment();
                    }
                });
            }
        });
        qa_second.start();
    }


    private void get_QAComment() {
        BmobQuery<QA_Comment> query = new BmobQuery<>();
        //用此方式可以构造一个BmobPointer对象。只需要设置objectId就行
        QA post = new QA();
        post.setObjectId(item_id);
        query.addWhereEqualTo("post",new BmobPointer(post));
        //希望同时查询该评论的发布者的信息，以及该帖子的作者的信息，这里用到上面`include`的并列对象查询和内嵌对象的查询
        query.include("user,post.author");
        query.findObjects(new FindListener<QA_Comment>() {
            @Override
            public void done(List<QA_Comment> objects,BmobException e) {

                for (int i= 0;i<objects.size();i++){
                    UserInfo userInfo = objects.get(i).getAuthor();
                    String content = objects.get(i).getContent();
                    String time = objects.get(i).getCreatedAt();
                    String item_id = objects.get(i).getObjectId();

/*                    Log.d("评论",userInfo.getObjectId());
                    Log.d("评论",content);*/

                    PingLun pingLun = new PingLun(content,item_id,userInfo.getObjectId(),time);
                    //PingLun pingLun = new PingLun(userInfo.geticonUrl(),userInfo.getNickname(),content,userInfo.getObjectId());
                    comment_list.add(pingLun);
                }

                if (comment_list.size() != 0){
                    Message msg = new Message();
                    msg.what = 1;
                    handler.sendMessage(msg);
                }else {
                    recyclerView.setVisibility(View.GONE);
                }
            }
        });
    }

    private void get_SQComment() {
        BmobQuery<SQ_Comment> query = new BmobQuery<>();
        //用此方式可以构造一个BmobPointer对象。只需要设置objectId就行
        SQ post = new SQ();
        post.setObjectId(item_id);
        query.addWhereEqualTo("post",new BmobPointer(post));
        //希望同时查询该评论的发布者的信息，以及该帖子的作者的信息，这里用到上面`include`的并列对象查询和内嵌对象的查询
        query.include("user,post.author");
        query.findObjects(new FindListener<SQ_Comment>() {
            @Override
            public void done(List<SQ_Comment> objects,BmobException e) {
                for (int i= 0;i<objects.size();i++){
                    UserInfo userInfo = objects.get(i).getAuthor();
                    String content = objects.get(i).getContent();
                    String time = objects.get(i).getCreatedAt();
                    String item_id = objects.get(i).getObjectId();

/*                    Log.d("评论",userInfo.getObjectId());
                    Log.d("评论",content);*/

                    PingLun pingLun = new PingLun(content,item_id,userInfo.getObjectId(),time);
                    //PingLun pingLun = new PingLun(userInfo.geticonUrl(),userInfo.getNickname(),content,userInfo.getObjectId());
                    comment_list.add(pingLun);
                }
                if (comment_list.size() != 0){
                    Message msg = new Message();
                    msg.what = 1;
                    handler.sendMessage(msg);
                }else {
                    recyclerView.setVisibility(View.GONE);
                }

            }
        });

    }


    String huifu;
    @SuppressLint("HandlerLeak")
    private Handler handler  = new Handler(){
        @Override
        public void handleMessage(Message msg) {

            if(msg.what == 1){
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setNestedScrollingEnabled(false);
                no_comments.setVisibility(View.GONE);

                CommentRecyAdapter adapter = new CommentRecyAdapter(getApplicationContext(),comment_list);
                //recyclerView.setMinimumHeight(comment_list.size()*80);
                recyclerView.setAdapter(adapter);

                ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        if (!my_id.equals(comment_list.get(position).getAuthor_id())){
                            BmobQuery<UserInfo> bmobQuery = new BmobQuery<>();
                            bmobQuery.getObject(comment_list.get(position).getAuthor_id(), new QueryListener<UserInfo>() {
                                @Override
                                public void done(UserInfo userInfo, BmobException e) {
                                    if(e==null){
                                        huifu = userInfo.getNickname();
                                        Message msg = new Message();
                                        msg.what = 2;
                                        handler.sendMessage(msg);
                                    }
                                }
                            });
                        }
                    }
                });
            }
            if (msg.what == 2){
                editText.setText("回复@"+huifu+"：");
            }
        }
    };

    @Override
    protected int getStatusBarColor() {
        return  getResources().getColor(R.color.colorAccent);
    }


    private PopupWindowList mPopupWindowList;
    private void showPopWindows(View view){

        List<String> dataList = new ArrayList<>();
        dataList.add("收藏");

        if (mPopupWindowList == null){
            mPopupWindowList = new PopupWindowList(view.getContext());
        }
        mPopupWindowList.setAnchorView(view);
        mPopupWindowList.setItemData(dataList);
        mPopupWindowList.setModal(true);
        mPopupWindowList.show();
        mPopupWindowList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        collection_item(item_id);
                        break;
                    default:
                        break;
                }
                mPopupWindowList.hide();
            }
        });
    }



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            this.finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    private void collection_item(String id) {
        if (my_collection.size() == 0 ){
            BmobQuery<UserInfo> query2 = new BmobQuery<>();
            query2.getObject(my_id, new QueryListener<UserInfo>() {
                @Override
                public void done(UserInfo userInfo, BmobException e) {
                    if (e == null) {

                        my_guanzhu = userInfo.getGuanzhu();
                        //my_Likes = userInfo.getMy_Likes();
                        my_collection = userInfo.getMy_Collection();

                        if (my_guanzhu.contains(author_id)){
                            guanzhu.setText("已关注");
                            guanzhu.setClickable(false);
                        }

                    }
                }
            });
        }else if (!my_collection.contains(id)){
            my_collection.add(id);
            UserInfo p2 = new UserInfo();
            p2.setValue("My_Collection",my_collection);
            p2.update(my_id, new UpdateListener() {
                @Override
                public void done(BmobException e) {
                    if(e==null){
                        Toast.makeText(getContext(),"收藏成功",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }else {
            Toast.makeText(getContext(),"你已经收藏过了",Toast.LENGTH_SHORT).show();
        }
    }


}
