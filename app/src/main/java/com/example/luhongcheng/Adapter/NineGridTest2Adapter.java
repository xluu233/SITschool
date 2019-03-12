package com.example.luhongcheng.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.luhongcheng.Bmob_bean.QA;
import com.example.luhongcheng.Bmob_bean.UserInfo;
import com.example.luhongcheng.R;
import com.example.luhongcheng.SIT_SQ_other.SQ_SecondLayout;
import com.example.luhongcheng.View.NineGridTestLayout;
import com.example.luhongcheng.bean.SQ_QA;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by HMY on 2016/8/6
 */
public class NineGridTest2Adapter extends RecyclerView.Adapter<NineGridTest2Adapter.ViewHolder> {

    private Context mContext;
    private List<SQ_QA> mList;
    protected LayoutInflater inflater;
    private String personID; //用户ID

    List<String> user_Likes = new ArrayList<>();
    boolean hadzan;

    public NineGridTest2Adapter(Context context, List<SQ_QA> list) {
        mContext = context;
        inflater = LayoutInflater.from(context);
        mList = list;

        SharedPreferences sp=mContext.getSharedPreferences("personID",0);
        personID =  sp.getString("ID","");

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View convertView = LayoutInflater.from(mContext).inflate(R.layout.item_nine_sqview, parent, false);
        ViewHolder viewHolder = new ViewHolder(convertView);
        return viewHolder;

    }

    @SuppressLint("ResourceType")
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.gridview.setUrlList(mList.get(position).getUrl());
        holder.title.setText(mList.get(position).getTitle());
        holder.content.setText(mList.get(position).getContent());
        holder.time.setText(mList.get(position).getTime());


        user_Likes = mList.get(position).getMy_likes();

        final String objectId = mList.get(position).getId();

        if (user_Likes != null){
            if (user_Likes.contains(objectId)){
                hadzan = true;
                holder.zan.setBackgroundResource(R.drawable.sq_zan_2);
            }
        }


        holder.zan_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread qa_update_zan = new Thread(new Runnable() {
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
                qa_update_zan.start();


            }
        });


        holder.discuss_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, SQ_SecondLayout.class);
                intent.putExtra("item_id",mList.get(position).getId());
                intent.putExtra("user_id",personID);
                intent.putExtra("title",mList.get(position).getTitle());
                intent.putExtra("content",mList.get(position).getContent());
                mContext.startActivity(intent);

               /* QA post = new QA();
                post.setObjectId(mList.get(position).getId());

                UserInfo user = new UserInfo();
                user.setObjectId(personID);

                QA_Comment comment = new QA_Comment();
                comment.setContent("评论测试");
                comment.setAuthor(user);
                comment.setPost(post);
                comment.save(new SaveListener<String>() {

                    @Override
                    public void done(String objectId, BmobException e) {
                        if(e==null){
                            Toast.makeText(mContext,"评论成功",Toast.LENGTH_SHORT).show();
                        }else{
                            Log.i("bmob","失败："+e.getMessage());
                        }
                    }

                });*/

            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        NineGridTestLayout gridview;
        TextView title;
        TextView content;
        TextView time;
        ImageView icon;

        ImageView zan,discuss;

        TextView pinlun; //选取一条评论

        TextView zan_nums;
        LinearLayout zan_layout,discuss_layout;

        public ViewHolder(View itemView) {
            super(itemView);
            gridview = (NineGridTestLayout) itemView.findViewById(R.id.layout_nine_grid);
            title = itemView.findViewById(R.id.title);
            content = itemView.findViewById(R.id.content);
            time = itemView.findViewById(R.id.time);
            icon = itemView.findViewById(R.id.icon);

            zan = itemView.findViewById(R.id.qa_zan);

            discuss = itemView.findViewById(R.id.qa_discuss);

            pinlun = itemView.findViewById(R.id.qa_pinglun);

            zan_layout = itemView.findViewById(R.id.zan);
            discuss_layout = itemView.findViewById(R.id.discuss);
            zan_nums = itemView.findViewById(R.id.qa_zan_nums);


        }
    }





}
