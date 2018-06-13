package com.example.luhongcheng.userCard;

import android.content.Context;
import android.graphics.Color;
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

public class userCardAdapter extends BaseAdapter {

    private List<userCard> newsList;
    private View view;
    private Context mContext;
    private ViewHolder viewHolder;

    public userCardAdapter(Context mContext, List<userCard> newsList) {
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
            view = LayoutInflater.from(mContext).inflate(R.layout.usercardinfo_item, null);
            viewHolder = new ViewHolder();
            viewHolder.a1 = (TextView) view.findViewById(R.id.A1);
            viewHolder.a2 = (TextView)view.findViewById(R.id.A2);
            viewHolder.a3 = (TextView)view.findViewById(R.id.A3);




            viewHolder.a2.setTextColor(Color.RED);
            //tv.setTextColor(0xff000000);

            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.a1.setText("时间："+newsList.get(position).geta1());
        viewHolder.a2.setText("金额："+newsList.get(position).geta2());
        viewHolder.a3.setText("地点："+newsList.get(position).geta3());
        return view;

    }

    class ViewHolder{
        TextView a1;
        TextView a2;
        TextView a3;


    }

}
