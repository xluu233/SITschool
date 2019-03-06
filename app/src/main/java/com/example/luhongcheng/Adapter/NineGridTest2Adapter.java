package com.example.luhongcheng.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.luhongcheng.Bmob_bean.QA_Collection;
import com.example.luhongcheng.Bmob_bean.QA_Comment;
import com.example.luhongcheng.Bmob_bean.QA;
import com.example.luhongcheng.Bmob_bean.QA_Likes;
import com.example.luhongcheng.Bmob_bean.UserInfo;
import com.example.luhongcheng.Bmob_bean._User;
import com.example.luhongcheng.R;
import com.example.luhongcheng.View.NineGridTestLayout;
import com.example.luhongcheng.bean.SQ_QA;
import com.example.luhongcheng.bean.SecondClass;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by HMY on 2016/8/6
 */
public class NineGridTest2Adapter extends RecyclerView.Adapter<NineGridTest2Adapter.ViewHolder> {

    private Context mContext;
    private List<SQ_QA> mList;
    protected LayoutInflater inflater;
    private String personID; //用户ID



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

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.gridview.setUrlList(mList.get(position).getUrl());
        holder.title.setText(mList.get(position).getTitle());
        holder.content.setText(mList.get(position).getContent());
        holder.time.setText(mList.get(position).getTime());

        final List<String> zan = new ArrayList<>();
        List<String> collection = new ArrayList<>();
        List<String> pinglin = new ArrayList<>();

        BmobQuery<QA_Likes> query = new BmobQuery<QA_Likes>();
        //用此方式可以构造一个BmobPointer对象。只需要设置objectId就行
        QA post = new QA();
        post.setObjectId(mList.get(position).getId());
        query.addWhereEqualTo("post",new BmobPointer(post));
        //希望同时查询该评论的发布者的信息，以及该帖子的作者的信息，这里用到上面`include`的并列对象查询和内嵌对象的查询
        //query.include("user,post.author");
        query.findObjects(new FindListener<QA_Likes>() {
            @Override
            public void done(List<QA_Likes> list, BmobException e) {

                if(e==null){
                    holder.zan_num.setText("xixi"+list.get(0).getAuthor());
                }else{
                    Log.i("bmob","失败："+e.getMessage());
                }


            }
        });


        holder.zan_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                QA post = new QA();
                post.setObjectId(mList.get(position).getId());

                UserInfo user = new UserInfo();
                user.setObjectId(personID);

                QA_Likes likes = new QA_Likes();
                likes.setAuthor(user);
                likes.setPost(post);
                likes.save(new SaveListener<String>() {

                    @Override
                    public void done(String objectId, BmobException e) {
                        if(e==null){
                            Toast.makeText(mContext,"赞", Toast.LENGTH_SHORT).show();
                        }else{
                            Log.i("bmob","失败："+e.getMessage());
                        }
                    }

                });
            }
        });

        holder.collection_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                QA post = new QA();
                post.setObjectId(mList.get(position).getId());

                UserInfo user = new UserInfo();
                user.setObjectId(personID);

                QA_Collection collection = new QA_Collection();
                collection.setAuthor(user);
                collection.setPost(post);
                collection.save(new SaveListener<String>() {
                    @Override
                    public void done(String s, BmobException e) {
                        if(e==null){
                            Toast.makeText(mContext,"收藏成功",Toast.LENGTH_SHORT).show();
                        }else{
                            Log.i("bmob","失败："+e.getMessage());
                        }
                    }
                });


            }
        });

        holder.discuss_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                QA post = new QA();
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

                });

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

        ImageView zan,discuss,collection;
        TextView zan_num,discuss_num;

        TextView pinlun; //选取一条评论

        LinearLayout zan_layout,discuss_layout,collection_layout;

        public ViewHolder(View itemView) {
            super(itemView);
            gridview = (NineGridTestLayout) itemView.findViewById(R.id.layout_nine_grid);
            title = itemView.findViewById(R.id.title);
            content = itemView.findViewById(R.id.content);
            time = itemView.findViewById(R.id.time);
            icon = itemView.findViewById(R.id.icon);

            zan = itemView.findViewById(R.id.qa_zan);
            zan_num = itemView.findViewById(R.id.zan_nums);

            discuss = itemView.findViewById(R.id.qa_discuss);
            discuss_num = itemView.findViewById(R.id.qa_discuss_num);

            collection = itemView.findViewById(R.id.qa_collection);

            pinlun = itemView.findViewById(R.id.qa_pinglun);

            zan_layout = itemView.findViewById(R.id.zan);
            discuss_layout = itemView.findViewById(R.id.discuss);
            collection_layout = itemView.findViewById(R.id.collection);

        }
    }





}
