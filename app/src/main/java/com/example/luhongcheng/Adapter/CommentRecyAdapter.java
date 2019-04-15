package com.example.luhongcheng.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.luhongcheng.Bmob_bean.UserInfo;
import com.example.luhongcheng.R;
import com.example.luhongcheng.SIT_SQ_other.SQ_SecondLayout;
import com.example.luhongcheng.View.CircleImageView;
import com.example.luhongcheng.bean.PingLun;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

import static org.litepal.LitePalApplication.getContext;

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
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.content.setText(list.get(position).getContent());
        holder.time.setText(position+1+"楼"+" · "+list.get(position).getTime());

        BmobQuery<UserInfo> bmobQuery = new BmobQuery<>();

        bmobQuery.getObject(list.get(position).getAuthor_id(), new QueryListener<UserInfo>() {
            @Override
            public void done(UserInfo userInfo, BmobException e) {
                if(e==null){
                    holder.nickname.setText(userInfo.getNickname());

                    Glide.with(getContext())
                            .load(userInfo.geticonUrl())
                            .apply(new RequestOptions().placeholder(R.drawable.loading2))
                            .apply(new RequestOptions() .error(R.drawable.error))
                            .apply(new RequestOptions().fitCenter())
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
