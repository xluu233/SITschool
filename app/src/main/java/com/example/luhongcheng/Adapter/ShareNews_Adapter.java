package com.example.luhongcheng.Adapter;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.luhongcheng.R;
import com.example.luhongcheng.bean.HotNews;
import com.example.luhongcheng.bean.QA;

import java.util.List;


public class ShareNews_Adapter extends RecyclerView.Adapter<ShareNews_Adapter.ViewHolder> {

    private Context mContext;
    private List<HotNews> mList;
    protected LayoutInflater inflater;

    public ShareNews_Adapter(Context context, List<HotNews> list) {
        mContext = context;
        inflater = LayoutInflater.from(context);
        mList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View convertView = LayoutInflater.from(mContext).inflate(R.layout.sq_share_news_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(convertView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        //从asset 读取字体
        AssetManager mgr = mContext.getAssets();
        //根据路径得到Typeface
        Typeface tf = Typeface.createFromAsset(mgr, "fonts/xihei.ttf");//细黑体
        holder.title.setTypeface(tf,Typeface.BOLD);
        holder.time.setTypeface(tf,Typeface.BOLD);

        holder.title.setText(mList.get(position).getTitle());
        holder.time.setText(mList.get(position).getTime());

        Glide.with(mContext)
                .load(mList.get(position).getImage())
                .apply(new RequestOptions().placeholder(R.drawable.loading))
                .apply(new RequestOptions() .error(R.drawable.error))
                .apply(new RequestOptions() .centerCrop())
                .into(holder.img);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView title,time;

        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.article_title);
            time = itemView.findViewById(R.id.article_time);
            img = itemView.findViewById(R.id.article_image);
        }
    }



}
