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
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.luhongcheng.Bmob_bean.QA;
import com.example.luhongcheng.Bmob_bean.QA_Comment;
import com.example.luhongcheng.Bmob_bean.Report;
import com.example.luhongcheng.Bmob_bean.SQ;
import com.example.luhongcheng.Bmob_bean.SQ_Comment;
import com.example.luhongcheng.Bmob_bean.UserInfo;
import com.example.luhongcheng.MainFragmentActivity;
import com.example.luhongcheng.View.CircleImageView;
import com.example.luhongcheng.R;
import com.example.luhongcheng.View.NineGridTestLayout;
import com.example.luhongcheng.bean.PingLun;
import com.example.luhongcheng.utils.BaseStatusBarActivity;
import com.example.luhongcheng.utils.ItemClickSupport;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    CircleImageView icon;
    TextView nickname,qm;

    TextView ss_title,ss_content,ss_time,guanzhu;
    NineGridTestLayout gridview;
    RecyclerView recyclerView;
    SmartRefreshLayout refreshLayout;
    EditText msg;
    ImageView send;
    Toolbar toolbar;

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
        msg = findViewById(R.id.msg);
        send = findViewById(R.id.send_msg);
        toolbar = findViewById(R.id.toolbar);
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

                    String content = msg.getText().toString();
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

                    String content = msg.getText().toString();
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


        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull final RefreshLayout refreshlayout) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //getDate();
                        try {
                            Thread.sleep(1000);
                            refreshlayout.finishRefresh(2000/*,false*/);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }


                    }
                }).start();


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

                        Log.d("getDate", String.valueOf(urllist));
                        Log.d("getDate",time);
                        Log.d("getDate",title);
                        Log.d("getDate",content);

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

                    Log.d("评论",userInfo.getObjectId());
                    Log.d("评论",content);

                    PingLun pingLun = new PingLun(content,userInfo.getObjectId(),time);
                    //PingLun pingLun = new PingLun(userInfo.geticonUrl(),userInfo.getNickname(),content,userInfo.getObjectId());
                    comment_list.add(pingLun);
                }

                Message msg = new Message();
                msg.what = 1;
                handler.sendMessage(msg);

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

                    Log.d("评论",userInfo.getObjectId());
                    Log.d("评论",content);

                    PingLun pingLun = new PingLun(content,userInfo.getObjectId(),time);
                    //PingLun pingLun = new PingLun(userInfo.geticonUrl(),userInfo.getNickname(),content,userInfo.getObjectId());
                    comment_list.add(pingLun);
                }

                Message msg = new Message();
                msg.what = 1;
                handler.sendMessage(msg);

            }
        });

    }

    @SuppressLint("HandlerLeak")
    private Handler handler  = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == 1){
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(mLayoutManager);
                CommentRecyAdapter adapter = new CommentRecyAdapter(getApplicationContext(),comment_list);
                recyclerView.setAdapter(adapter);

                ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        //回复
                    }
                });

                ItemClickSupport.addTo(recyclerView).setOnItemLongClickListener(new ItemClickSupport.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClicked(RecyclerView recyclerView, final int position, View v) {
                        Vibrator vibrator = (Vibrator)getSystemService(VIBRATOR_SERVICE);
                        vibrator.vibrate(50);
                        final String[] items = {"举报"};
                        final AlertDialog.Builder listDialog = new AlertDialog.Builder(getContext());
                        listDialog.setItems(items, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which){
                                    case 0:
                                        report_item(comment_list.get(position).getContent());
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

    private class CommentRecyAdapter extends RecyclerView.Adapter<CommentRecyAdapter.ViewHolder> {

        private Context mContext;
        private List<PingLun> list;

        private CommentRecyAdapter(Context context, List<PingLun> list){
            this.mContext = context;
            this.list = list;
        }


        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View convertView = LayoutInflater.from(mContext).inflate(R.layout.pinglun_item, viewGroup, false);
            CommentRecyAdapter.ViewHolder viewHolder = new CommentRecyAdapter.ViewHolder(convertView);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.content.setText(list.get(position).getContent());
            holder.time.setText(position+1+"楼"+" · "+list.get(position).getTime());

            BmobQuery<UserInfo> bmobQuery = new BmobQuery<>();
            final CommentRecyAdapter.ViewHolder finalHolder = holder;
            bmobQuery.getObject(list.get(position).getAuthor_id(), new QueryListener<UserInfo>() {
                @Override
                public void done(UserInfo userInfo, BmobException e) {
                    if(e==null){
                        finalHolder.nickname.setText(userInfo.getNickname());

                        Glide.with(getContext())
                                .load(userInfo.geticonUrl())
                                .apply(new RequestOptions().placeholder(R.drawable.loading))
                                .apply(new RequestOptions() .error(R.drawable.error))
                                .apply(new RequestOptions().fitCenter())
                                .into(finalHolder.icon);

                    }
                }
            });

        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            CircleImageView icon;
            TextView nickname,content,time;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                icon = itemView.findViewById(R.id.comment_icon);
                nickname = itemView.findViewById(R.id.nickname);
                content = itemView.findViewById(R.id.content);
                time = itemView.findViewById(R.id.time);
            }
        }
    }



    @Override
    protected int getStatusBarColor() {
        return getResources().getColor(R.color.colorAccent);
    }


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
                            report.setUser_id(my_id);
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


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            this.finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}
