package com.example.luhongcheng.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.luhongcheng.Bmob_bean.UserInfo;
import com.example.luhongcheng.R;
import com.example.luhongcheng.SIT_SQ_other.SQ_SecondLayout;
import com.example.luhongcheng.WebDisplay;
import com.example.luhongcheng.bean.QA;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

import static org.litepal.LitePalApplication.getContext;


public class QA_Adapter extends RecyclerView.Adapter<QA_Adapter.ViewHolder> {

    private Context mContext;
    private List<QA> mList;
    protected LayoutInflater inflater;
    private String personID; //用户ID
    private List<String> user_Likes = new ArrayList<>();


    public QA_Adapter(Context context, List<QA> list) {
        mContext = context;
        inflater = LayoutInflater.from(context);
        mList = list;

        SharedPreferences sp=mContext.getSharedPreferences("personID",0);
        personID =  sp.getString("ID","");

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View convertView = LayoutInflater.from(mContext).inflate(R.layout.sq_qa_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(convertView);
        return viewHolder;

    }

    @SuppressLint("ResourceType")
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        //holder.gridview.setUrlList(mList.get(position).getUrl());
        holder.title.setText(mList.get(position).getTitle());
        holder.content.setText(mList.get(position).getContent());
        holder.time.setText(mList.get(position).getTime());

        user_Likes = mList.get(position).getMy_likes();

        final String objectId = mList.get(position).getItem_id();

        if (user_Likes != null){
            if (user_Likes.contains(objectId)){
                holder.zan.setBackgroundResource(R.drawable.sq_zan_2);
            }
        }

        final Matcher m = Pattern.compile("(?i)http://[^\u4e00-\u9fa5]+").matcher(mList.get(position).getContent());
        final Matcher ms = Pattern.compile("(?i)https://[^\u4e00-\u9fa5]+").matcher(mList.get(position).getContent());
        while(m.find()){
            holder.content.setText(mList.get(position).getContent().replace(m.group(),"(@超链接)"));
        }
        while(ms.find()){
            holder.content.setText(mList.get(position).getContent().replace(ms.group(),"(@超链接)"));
        }


        holder.zan_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!user_Likes.contains(mList.get(position).getItem_id())){
                    UserInfo xixi = new UserInfo();
                    xixi.setObjectId(personID);

                    com.example.luhongcheng.Bmob_bean.QA post = new com.example.luhongcheng.Bmob_bean.QA();
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
                                user_Likes.add(mList.get(position).getItem_id());
                                addZan();
                                Log.i("bmob","多对多关联添加成功");
                            }else{
                                Log.i("bmob","失败："+e.getMessage());
                            }
                        }

                    });
                }

/*                Thread qa_update_zan = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (hadzan){
                            user_Likes.remove(objectId);
                            UserInfo p2 = new UserInfo();
                            p2.setValue("My_Likes",user_Likes);
                            p2.update(personID, new UpdateListener() {
                                @Override
                                public void done(BmobException e) {
                                    if(e==null){
                                        hadzan = false;
                                        holder.zan.setBackgroundResource(R.drawable.sq_zan_1);
                                        Toast.makeText(mContext,"取消成功",Toast.LENGTH_SHORT).show();

                                    }else{
                                        //Toast.makeText(mContext,"error"+e.getMessage(),Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                        }else{
                            user_Likes.add(objectId);
                            if (user_Likes != null){
                                UserInfo p2 = new UserInfo();
                                p2.setValue("My_Likes",user_Likes);
                                p2.update(personID, new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {
                                        if(e==null){
                                            hadzan = true;
                                            holder.zan.setBackgroundResource(R.drawable.sq_zan_2);
                                            Toast.makeText(mContext,"赞!",Toast.LENGTH_SHORT).show();

                                        }else{
                                           // Toast.makeText(mContext,"error"+e.getMessage(),Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                        }
                    }
                });
                qa_update_zan.start();*/
            }
        });


        BmobQuery<UserInfo> query = new BmobQuery<>();
        query.getObject(mList.get(position).getAuthor_id(), new QueryListener<UserInfo>() {
            @Override
            public void done(UserInfo userInfo, BmobException e) {
                if (e==null){
                    if (userInfo.getNickname() != null){
                        holder.nickname.setText(userInfo.getNickname());
                    }
                    if (userInfo.geticonUrl() != null){
                        if (userInfo.geticonUrl().length() != 0){
                            Glide.with(getContext())
                                    .load(userInfo.geticonUrl())
                                    .apply(new RequestOptions().placeholder(R.drawable.loading))
                                    .apply(new RequestOptions() .error(R.drawable.error))
                                    .apply(new RequestOptions().fitCenter())
                                    .into(holder.icon);
                        }
                    }
                }
            }
        });


        holder.discuss_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, SQ_SecondLayout.class);
                intent.putExtra("from","QA");
                intent.putExtra("item_id",mList.get(position).getItem_id());
                intent.putExtra("author_id",mList.get(position).getAuthor_id());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //NineGridTestLayout gridview;
        TextView title,content,time,nickname;
        ImageView icon,zan,discuss;
        LinearLayout zan_layout,discuss_layout;


        public ViewHolder(View itemView) {
            super(itemView);
           // gridview = (NineGridTestLayout) itemView.findViewById(R.id.layout_nine_grid);
            title = itemView.findViewById(R.id.title);
            content = itemView.findViewById(R.id.content);
            time = itemView.findViewById(R.id.time);
            zan = itemView.findViewById(R.id.qa_zan);
            discuss = itemView.findViewById(R.id.qa_discuss);
            zan_layout = itemView.findViewById(R.id.zan);
            discuss_layout = itemView.findViewById(R.id.discuss);

            icon = itemView.findViewById(R.id.qa_icon);
            nickname = itemView.findViewById(R.id.qa_nickname);

        }
    }


    private void addZan() {
        UserInfo p2 = new UserInfo();
        p2.setValue("My_Likes",user_Likes);
        p2.update(personID, new UpdateListener() {
            @Override
            public void done(BmobException e) {

            }
        });
    }



}
