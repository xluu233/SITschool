package com.example.luhongcheng.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.luhongcheng.Bmob_bean.UserInfo;
import com.example.luhongcheng.ImageFullDisplay;
import com.example.luhongcheng.R;
import com.example.luhongcheng.View.CircleImageView;
import com.example.luhongcheng.bean.PingLun;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;


public class CommentRecyAdapter extends RecyclerView.Adapter<CommentRecyAdapter.ViewHolder> {
    private Context mContext;
    private List<PingLun> list;

    public CommentRecyAdapter(Context context, List<PingLun> list){
        this.mContext = context;
        this.list = list;
    }


    @NonNull
    @Override
    public CommentRecyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View convertView = LayoutInflater.from(mContext).inflate(R.layout.pinglun_item, viewGroup, false);
        CommentRecyAdapter.ViewHolder viewHolder = new CommentRecyAdapter.ViewHolder(convertView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder,int position) {
        holder.content.setText(list.get(position).getContent());
        holder.time.setText(position+1+"楼"+" · "+list.get(position).getTime());

        BmobQuery<UserInfo> bmobQuery = new BmobQuery<>();

        if (list.get(position).getContent().contains("http://bmob")){
            final Matcher m = Pattern.compile("(?i)http://[^\u4e00-\u9fa5]+").matcher(list.get(position).getContent());
            while(m.find()){
                final String url = m.group();
                holder.content.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, ImageFullDisplay.class);
                        intent.putExtra("url2",url);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
                        mContext.startActivity(intent);
                    }
                });
                holder.content.setText(list.get(position).getContent().replace(m.group(),""));
            }
        }


/*

        final Matcher ms = Pattern.compile("(?i)https://[^\u4e00-\u9fa5]+").matcher(list.get(position).getContent());
        while(ms.find()){
            final String url = ms.group();
            holder.content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent8 = new Intent(mContext, WebDisplay.class);
                    intent8.putExtra("news_url", url);
                    intent8.putExtra("title","超链接");
                    intent8.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
                    mContext.startActivity(intent8);
                }
            });
        }
*/


        bmobQuery.getObject(list.get(position).getAuthor_id(), new QueryListener<UserInfo>() {
            @Override
            public void done(UserInfo userInfo, BmobException e) {
                if(e==null){
                    holder.nickname.setText(userInfo.getNickname());

                    Glide.with(mContext)
                            .load(userInfo.geticonUrl())
                            .apply(new RequestOptions().placeholder(R.drawable.loading2))
                            .apply(new RequestOptions() .error(R.drawable.error))
                           // .apply(new RequestOptions().fitCenter())
                            .into(holder.icon);

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
