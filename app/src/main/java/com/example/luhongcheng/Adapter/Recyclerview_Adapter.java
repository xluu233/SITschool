package com.example.luhongcheng.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.luhongcheng.R;
import com.example.luhongcheng.bean.QA;
import java.util.List;



public class Recyclerview_Adapter extends RecyclerView.Adapter<Recyclerview_Adapter.ViewHolder> {

    private Context mContext;
    private List<QA> mList;
    protected LayoutInflater inflater;


    public Recyclerview_Adapter(Context context, List<QA> list) {
        mContext = context;
        inflater = LayoutInflater.from(context);
        mList = list;

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View convertView = LayoutInflater.from(mContext).inflate(R.layout.sq_qa_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(convertView);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.title.setText(mList.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;


        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);


        }
    }



}
