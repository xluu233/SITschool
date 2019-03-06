package com.example.luhongcheng.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.luhongcheng.R;
import com.example.luhongcheng.View.NineGridTestLayout;
import com.example.luhongcheng.bean.Fruit;
import com.example.luhongcheng.bean.SQ_QA;

import java.util.List;

import static org.litepal.LitePalApplication.getContext;

/**
 * Created by HMY on 2016/8/6
 */
public class NineGridTest2Adapter extends RecyclerView.Adapter<NineGridTest2Adapter.ViewHolder> {

    private Context mContext;
    private List<SQ_QA> mList;
    protected LayoutInflater inflater;

    public NineGridTest2Adapter(Context context, List<SQ_QA> list) {
        mContext = context;
        inflater = LayoutInflater.from(context);
        mList = list;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View convertView = LayoutInflater.from(mContext).inflate(R.layout.item_nine_sqview, parent, false);
        ViewHolder viewHolder = new ViewHolder(convertView);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.gridview.setUrlList(mList.get(position).getUrl());
        holder.title.setText(mList.get(position).getTitle());
        holder.content.setText(mList.get(position).getContent());
        holder.time.setText(mList.get(position).getTime());

        holder.zan_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,"你赞了一下",Toast.LENGTH_SHORT).show();
            }
        });

        holder.collection_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,"你收藏了",Toast.LENGTH_SHORT).show();
            }
        });

        holder.discuss_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,"你点击了评论",Toast.LENGTH_SHORT).show();
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
