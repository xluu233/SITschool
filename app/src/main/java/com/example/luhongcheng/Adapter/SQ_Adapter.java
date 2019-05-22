package com.example.luhongcheng.Adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Looper;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.luhongcheng.Bmob_bean.Report;
import com.example.luhongcheng.Bmob_bean.UserInfo;
import com.example.luhongcheng.R;
import com.example.luhongcheng.SIT_SQ_other.SQ_SecondLayout;
import com.example.luhongcheng.View.CircleImageView;
import com.example.luhongcheng.View.NineGridTestLayout;
import com.example.luhongcheng.View.PopupWindowList;
import com.example.luhongcheng.WebDisplay;
import com.example.luhongcheng.bean.SQ;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

import static org.litepal.LitePalApplication.getContext;


public class SQ_Adapter extends RecyclerView.Adapter<SQ_Adapter.ViewHolder> {

    private Context mContext;
    private List<SQ> mList;
    protected LayoutInflater inflater;
    private String personID; //用户ID
    private String author_id; //作者
    private String item_id; //说说


    private List<String> my_guanzhu;
    private List<String> my_Likes = new ArrayList<>();
    private List<String> my_collection;
    private boolean hadZan;
    private String url;


    public SQ_Adapter(Context context, List<com.example.luhongcheng.bean.SQ> mList) {
        mContext = context;
        inflater = LayoutInflater.from(context);
        this.mList = mList;

        SharedPreferences sp=mContext.getSharedPreferences("personID",0);
        personID =  sp.getString("ID","");
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View convertView = LayoutInflater.from(mContext).inflate(R.layout.sq_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(convertView);
        return viewHolder;

    }

    @SuppressLint("ResourceType")
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.gridview.setUrlList(mList.get(position).getUrl());
        holder.content.setText(mList.get(position).getContent());
        holder.time.setText(mList.get(position).getTime());

        //从asset 读取字体
        AssetManager mgr = mContext.getAssets();
        //根据路径得到Typeface
        Typeface tf = Typeface.createFromAsset(mgr, "fonts/simhei.ttf");//仿宋
        holder.content.setTypeface(tf);

        if (mList.get(position).getContent().length() == 0){
            holder.content.setHeight(0);
        }

        final Matcher m = Pattern.compile("(?i)http://[^\u4e00-\u9fa5]+").matcher(mList.get(position).getContent());
        final Matcher ms = Pattern.compile("(?i)https://[^\u4e00-\u9fa5]+").matcher(mList.get(position).getContent());

        while(m.find()){
            holder.content.setText(mList.get(position).getContent().replace(m.group(),"(@超链接)"));
        }
        while(ms.find()){
            holder.content.setText(mList.get(position).getContent().replace(ms.group(),"(@超链接)"));
        }


        Log.d("ss-time:",mList.get(position).getTime());
        my_Likes = mList.get(position).getMy_likes();
        author_id = mList.get(position).getAuthor_id();

        if (my_Likes.contains(mList.get(position).getItem_id())){
            hadZan = true;
            holder.zan.setBackgroundResource(R.drawable.sq_zan_2);
        }else {
            hadZan = false;
        }

        final String[] nickname = new String[1];
        final String[] qm = new String[1];
        final String[] icon_url = new String[1];

        BmobQuery<UserInfo> query = new BmobQuery<UserInfo>();
        query.getObject(author_id, new QueryListener<UserInfo>() {
            @Override
            public void done(UserInfo userInfo, BmobException e) {
                if (e==null){
                    if (userInfo.getNickname() != null){
                        nickname[0] = userInfo.getNickname();
                        holder.nickname.setText(nickname[0]);
                    }
                    if (userInfo.getQM() != null){
                        qm[0] = userInfo.getQM();
                        holder.qm.setText(qm[0]);
                    }
                    if (userInfo.geticonUrl() != null){
                        icon_url[0] = userInfo.geticonUrl();
                        if (icon_url[0].length() != 0){
                            Glide.with(getContext())
                                    .load(icon_url[0])
                                    .apply(new RequestOptions().placeholder(R.drawable.loading))
                                    .apply(new RequestOptions() .error(R.drawable.error))
                                    .apply(new RequestOptions().fitCenter())
                                    .into(holder.icon);
                        }
                    }
                }
            }
        });


        holder.zan_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!my_Likes.contains(mList.get(position).getItem_id())){
                    UserInfo xixi = new UserInfo();
                    xixi.setObjectId(personID);

                    com.example.luhongcheng.Bmob_bean.SQ post = new com.example.luhongcheng.Bmob_bean.SQ();
                    post.setObjectId(mList.get(position).getItem_id());
                    //将当前用户添加到Post表中的likes字段值中，表明当前用户喜欢该帖子
                    BmobRelation relation = new BmobRelation();
                    //将当前用户添加到多对多关联中
                    relation.add(xixi);
                    //多对多关联指向`post`的`likes`字段
                    post.setLikes(relation);
                    post.update(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if(e==null){
                                Toast.makeText(mContext,"赞",Toast.LENGTH_SHORT).show();
                                holder.zan.setBackgroundResource(R.drawable.sq_zan_2);
                                my_Likes.add(mList.get(position).getItem_id());
                                addZan();
                                Log.i("bmob","多对多关联添加成功");
                            }else{
                                Log.i("bmob","失败："+e.getMessage());
                            }
                        }

                    });
                }

            }
        });


        holder.more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                BmobQuery<UserInfo> query = new BmobQuery<UserInfo>();
                query.getObject(personID, new QueryListener<UserInfo>() {
                    @Override
                    public void done(UserInfo userInfo, BmobException e) {
                        if (e==null){
                            my_guanzhu = userInfo.getGuanzhu();
                            my_collection = userInfo.getMy_Collection();
                            showPopWindows(v,mList.get(position).getAuthor_id(),mList.get(position).getItem_id());
                        }
                    }
                });

/*                my_guanzhu = mList.get(position).getMy_guanzhu();
                my_collection = mList.get(position).getMy_collection();
                showPopWindows(v,mList.get(position).getAuthor_id(),mList.get(position).getItem_id());*/

            }
        });

        holder.comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, SQ_SecondLayout.class);
                intent.putExtra("from","SQ");
                intent.putExtra("item_id",mList.get(position).getItem_id());
                intent.putExtra("author_id",mList.get(position).getAuthor_id());
                mContext.startActivity(intent);

            }
        });



    }


    private PopupWindowList mPopupWindowList;
    private void showPopWindows(View view, final String author_id, final String item_id){
        Log.d("more:","showPopWindows");
        Log.d("more:",personID);
        Log.d("more:",item_id);
        Log.d("more:", String.valueOf(my_collection));
        Log.d("more:", String.valueOf(my_guanzhu));
        List<String> dataList = new ArrayList<>();
        if (personID.equals(author_id)){
            dataList.add("删除");
        }else {
            if (my_guanzhu == null){
                dataList.add("关注");
            }else {
                if (my_guanzhu.contains(author_id)){
                    dataList.add("已关注");
                }else {
                    dataList.add("关注");
                }
            }

        }

        if (my_collection == null){
            dataList.add("收藏");
        }else {
            if (my_collection.contains(item_id)){
                dataList.add("已收藏");
            }else {
                dataList.add("收藏");
            }
        }

        dataList.add("举报");

        
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
                        //["b15aa78885","ffd69ef865","b15aa78885","b15aa78885","b15aa78885
                        if (personID.equals(author_id)){
                            //Toast.makeText(mContext,"---",Toast.LENGTH_SHORT).show();
                            DeleteItem(item_id);
                        }else {
                            if (my_guanzhu == null){
                                my_guanzhu = new ArrayList<>();
                                my_guanzhu.add(author_id);
                                UserInfo object = new  UserInfo();
                                object.setGuanzhu(my_guanzhu);
                                object.update(personID, new UpdateListener() {
                                    @Override
                                    public void done(BmobException e1) {
                                        if(e1==null){
                                            Toast.makeText(mContext, "关注成功", Toast.LENGTH_SHORT).show();
                                        }else{
                                            Toast.makeText(mContext, "关注失败", Toast.LENGTH_SHORT).show();
                                            Log.i("bmob","更新失败："+e1.getMessage()+","+e1.getErrorCode());
                                        }
                                    }
                                });
                            }else {
                                if (my_guanzhu.contains(author_id)){
                                    Toast.makeText(mContext,"已关注",Toast.LENGTH_SHORT).show();
                                }else {
                                    my_guanzhu.add(author_id);
                                    UserInfo object = new  UserInfo();
                                    object.setGuanzhu(my_guanzhu);
                                    object.update(personID, new UpdateListener() {
                                        @Override
                                        public void done(BmobException e1) {
                                            if(e1==null){
                                                Toast.makeText(mContext, "关注成功", Toast.LENGTH_SHORT).show();
                                            }else{
                                                Toast.makeText(mContext, "关注失败", Toast.LENGTH_SHORT).show();
                                                Log.i("bmob","更新失败："+e1.getMessage()+","+e1.getErrorCode());
                                            }
                                        }
                                    });
                                }
                            }
                        }
                        break;
                    case 1:
                        if (my_collection == null){
                            my_collection = new ArrayList<>();
                            my_collection.add(item_id);
                            UserInfo object = new  UserInfo();
                            object.setMy_Collection(my_collection);
                            object.update(personID, new UpdateListener() {
                                @Override
                                public void done(BmobException e1) {
                                    if(e1==null){
                                        Toast.makeText(mContext, "收藏成功", Toast.LENGTH_SHORT).show();
                                    }else{
                                        Toast.makeText(mContext, "失败", Toast.LENGTH_SHORT).show();
                                        Log.i("bmob","更新失败："+e1.getMessage()+","+e1.getErrorCode());
                                    }
                                }
                            });
                        }else {
                            if (my_collection.contains(item_id)){
                                Toast.makeText(mContext,"已收藏",Toast.LENGTH_SHORT).show();
                            }else {
                                my_collection.add(item_id);
                                UserInfo object = new  UserInfo();
                                object.setMy_Collection(my_collection);
                                object.update(personID, new UpdateListener() {
                                    @Override
                                    public void done(BmobException e1) {
                                        if(e1==null){
                                            Toast.makeText(mContext, "收藏成功", Toast.LENGTH_SHORT).show();
                                        }else{
                                            Toast.makeText(mContext, "失败", Toast.LENGTH_SHORT).show();
                                            Log.i("bmob","更新失败："+e1.getMessage()+","+e1.getErrorCode());
                                        }
                                    }
                                });
                            }
                        }

                        break;
                    case 2:
                        report_item(item_id);
                        break;
                    default:
                        break;
                }
                mPopupWindowList.hide();
            }
        });
    }

    private void DeleteItem(String item_id) {
        com.example.luhongcheng.Bmob_bean.SQ sq = new com.example.luhongcheng.Bmob_bean.SQ();
        sq.setObjectId(item_id);
        sq.delete(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    Toast.makeText(mContext, "删除成功！" , Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(mContext, "失败！", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void report_item(final String id) {

        final EditText et = new EditText(mContext);
        new AlertDialog.Builder(mContext).setTitle("举报")
                .setIcon(R.drawable.report)
                .setView(et)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String input = et.getText().toString();
                        if (input.equals("")) {
                            Toast.makeText(mContext, "内容不能为空！" + input, Toast.LENGTH_LONG).show();
                        }
                        else {
                            Report report = new Report();
                            report.setItem_id(id);
                            report.setTitle(input);
                            report.setUser_id(personID);
                            report.save(new SaveListener<String>() {
                                @Override
                                public void done(String s, BmobException e) {
                                    if (e==null){
                                        Toast.makeText(mContext,"举报成功",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }
                })
                .setNegativeButton("取消", null)
                .show();


    }

    private void addZan() {
        UserInfo p2 = new UserInfo();
        p2.setValue("My_Likes",my_Likes);
        p2.update(personID, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){

                }else{
                    //Toast.makeText(mContext,"error"+e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private String queryZanNums(String objectId) {
        final int[] zan = new int[1];
        // 查询喜欢这个帖子的所有用户，因此查询的是用户表
        BmobQuery<UserInfo> query = new BmobQuery<UserInfo>();
        com.example.luhongcheng.Bmob_bean.SQ post = new com.example.luhongcheng.Bmob_bean.SQ();
        post.setObjectId(objectId);
        //likes是Post表中的字段，用来存储所有喜欢该帖子的用户
        query.addWhereRelatedTo("likes", new BmobPointer(post));
        query.findObjects(new FindListener<UserInfo>() {
            @Override
            public void done(List<UserInfo> object,BmobException e) {
                if(e==null){
                    Log.i("bmob","查询个数："+object.size());
                    zan[0] = object.size();

                }else{
                    Log.i("bmob","失败："+e.getMessage());
                }
            }

        });
        return String.valueOf(zan[0]);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        NineGridTestLayout gridview;
        TextView time;
        TextView content;
        TextView nickname,qm;
        CircleImageView icon;

        ImageView zan,comment,share;
        ImageView more;
        TextView zan_nums,comment_nums;

        LinearLayout zan_layout;

        public ViewHolder(final View itemView) {
            super(itemView);

            gridview = itemView.findViewById(R.id.layout_nine_grid);
            content = itemView.findViewById(R.id.content);
            time = itemView.findViewById(R.id.sq_time);
            icon = itemView.findViewById(R.id.sq_icon);
            nickname = itemView.findViewById(R.id.sq_nickname);
            qm = itemView.findViewById(R.id.sq_qm);
            zan = itemView.findViewById(R.id.zan);
            zan_layout = itemView.findViewById(R.id.zan_layout);
            zan_nums = itemView.findViewById(R.id.zan_nums);
            comment = itemView.findViewById(R.id.comment);
            comment_nums = itemView.findViewById(R.id.comment_nums);
            more = itemView.findViewById(R.id.sq_collection);
            share = itemView.findViewById(R.id.sq_share);


            share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Snackbar.make(itemView,"暂不可用,请等待后续开发",Toast.LENGTH_SHORT).show();
                }
            });
        }


    }






}
