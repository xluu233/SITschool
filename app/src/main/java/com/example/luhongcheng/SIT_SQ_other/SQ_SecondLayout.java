package com.example.luhongcheng.SIT_SQ_other;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.luhongcheng.Bmob_bean.QA;
import com.example.luhongcheng.Bmob_bean.QA_Comment;
import com.example.luhongcheng.Bmob_bean.UserInfo;
import com.example.luhongcheng.View.CircleImageView;
import com.example.luhongcheng.R;
import com.example.luhongcheng.View.NineGridTestLayout;
import com.example.luhongcheng.bean.PingLun;
import com.example.luhongcheng.utils.BaseStatusBarActivity;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

import static org.litepal.LitePalApplication.getContext;

public class SQ_SecondLayout extends BaseStatusBarActivity {
    String item_id;
    String user_id;
    String my_id;


    private List<String> urllist = new ArrayList<>(); //图片地址合集
    private List<String> my_Likes = new ArrayList<>(); //我的喜欢合集
    private List<String> my_guanzhu = new ArrayList<>(); //我的关注合集

    CircleImageView icon;
    TextView nickname,qm;

    TextView ss_title,ss_content,ss_time,guanzhu;
    NineGridTestLayout gridview;
    ListView listView;
    EditText msg;
    ImageView send;

    List<PingLun> comment_list = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.sq_second_layout);
        super.onCreate(savedInstanceState);
        initView();
        item_id = getIntent().getStringExtra("item_id");
        user_id = getIntent().getStringExtra("user_id");

        get_UserInfo();


        SharedPreferences sp=getSharedPreferences("personID",0);
        my_id =  sp.getString("ID","");


        onClick();
    }

    private void onClick() {
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        listView = findViewById(R.id.comment_listview);
        msg = findViewById(R.id.msg);
        send = findViewById(R.id.send_msg);

        if (user_id == my_id){
            guanzhu.setVisibility(View.GONE);
        }

    }

    //当前作者的信息
    private void get_UserInfo() {
        if (user_id.length() != 0 || item_id.length() !=0){
            Thread collection = new Thread(new Runnable() {
                @Override
                public void run() {
                    BmobQuery<UserInfo> query2 = new BmobQuery<UserInfo>();
                    query2.getObject(user_id, new QueryListener<UserInfo>() {
                        @Override
                        public void done(UserInfo userInfo, BmobException e) {
                            if (e == null) {


                                String icon_url = userInfo.geticonUrl();
                                String qm2 = userInfo.getQM();
                                String nickname2 = userInfo.getNickname();
                                if (nickname2 == null){
                                    nickname2 = userInfo.getName().replace("你好：","");
                                }

                                getDate(); //说说的信息
                                get_MyInfo(); //我的信息
                                get_Comment();

                                Glide.with(getApplicationContext())
                                        .load(icon_url)
                                        .apply(new RequestOptions().placeholder(R.drawable.loading))
                                        .apply(new RequestOptions() .error(R.drawable.error))
                                        .into(icon);
                                nickname.setText(nickname2);
                                qm.setText(qm2);



                            } else {
                                //Log.i("bmob图片", "失败：" + e.getMessage() + "," + e.getErrorCode());
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

    private void get_Comment() {
        BmobQuery<QA_Comment> query = new BmobQuery<QA_Comment>();
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

    @SuppressLint("HandlerLeak")
    private Handler handler  = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == 1){
                CommentAdaper adapter = new CommentAdaper(comment_list);
                listView.setAdapter(adapter);
                setListViewHeightBasedOnChildren(listView);
            }
        }
    };

    public class CommentAdaper extends BaseAdapter {
        private List<PingLun> list;
        public CommentAdaper(List<PingLun> list) {
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

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if(convertView==null){
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.pinglun_item, null);
                holder = new ViewHolder();
                holder.content =(TextView) convertView.findViewById(R.id.content);
                holder.nickname =(TextView)convertView.findViewById(R.id.nickname);
                holder.icon = convertView.findViewById(R.id.comment_icon);
                holder.time = convertView.findViewById(R.id.time);

                convertView.setTag(holder);
            }else{
                holder = (ViewHolder) convertView.getTag();
            }
            holder.content.setText(list.get(position).getContent());
            holder.time.setText(position+1+"楼"+" · "+list.get(position).getTime());


            BmobQuery<UserInfo> bmobQuery = new BmobQuery<UserInfo>();
            final ViewHolder finalHolder = holder;
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

                    }else{

                    }
                }
            });



/*            Glide.with(getContext())
                    .load(list.get(position).getIcon_url())
                    .apply(new RequestOptions().placeholder(R.drawable.loading))
                    .apply(new RequestOptions() .error(R.drawable.error))
                    .apply(new RequestOptions().fitCenter())
                    .into(holder.icon);*/


            return convertView;
        }

        class ViewHolder {
            CircleImageView icon;
            TextView nickname,content,time;

        }

    }

    private void get_MyInfo() {
        if (my_id.length() !=0){
            Thread collection_info = new Thread(new Runnable() {
                @Override
                public void run() {
                    BmobQuery<UserInfo> query2 = new BmobQuery<UserInfo>();
                    query2.getObject(my_id, new QueryListener<UserInfo>() {
                        @Override
                        public void done(UserInfo userInfo, BmobException e) {
                            if (e == null) {

                                my_guanzhu = userInfo.getGuanzhu();
                                my_Likes = userInfo.getMy_Likes();

                                if (my_guanzhu.contains(user_id)){
                                    guanzhu.setText("已关注");
                                    guanzhu.setClickable(false);
                                }





                            } else {
                                //Log.i("bmob图片", "失败：" + e.getMessage() + "," + e.getErrorCode());
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
                if (!my_guanzhu.contains(user_id)){
                    my_guanzhu.add(user_id);
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


    @Override
    protected int getStatusBarColor() {
        return getResources().getColor(R.color.white);
    }


    private void getDate() {
        Thread qa_second = new Thread(new Runnable() {
            @Override
            public void run() {
                BmobQuery<QA> query = new BmobQuery<QA>();
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


                        if (urllist != null){
                            gridview.setUrlList(urllist);
                        }

                    }
                });


            }
        });
        qa_second.start();


    }

    public void setListViewHeightBasedOnChildren(ListView list) {
        // 获取ListView对应的Adapter
        CommentAdaper commentAdaper = (CommentAdaper) list.getAdapter();
        if (commentAdaper == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0, len = commentAdaper.getCount(); i < len; i++) {
            // listAdapter.getCount()返回数据项的数目
            View listItem = commentAdaper.getView(i, null, listView);
            // 计算子项View 的宽高
            listItem.measure(0, 0);
            // 统计所有子项的总高度
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight+ (listView.getDividerHeight() * (commentAdaper.getCount() - 1));
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
    }

}
