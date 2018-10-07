package com.example.luhongcheng.OA;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.luhongcheng.R;

import java.util.List;

/**
 * Created by Administrator on 2017/1/21.
 */

public class Downloadadapter extends BaseAdapter {

    private List<xiazai> newsList;
    private View view;
    private Context mContext;
    private ViewHolder viewHolder;

    public Downloadadapter(Context mContext, List<xiazai> newsList) {
        this.newsList = newsList;
        this.mContext= mContext;
    }

    @Override
    public int getCount() {
        return newsList.size();
    }

    @Override
    public Object getItem(int position) {
        return newsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.xiazai_listview_item, null);
            viewHolder = new ViewHolder();
            viewHolder.A1 = (TextView) view.findViewById(R.id.title);

            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.A1.setText(newsList.get(position).getTitle());


        return view;
    }

    class ViewHolder{
        TextView A1;

    }

}
