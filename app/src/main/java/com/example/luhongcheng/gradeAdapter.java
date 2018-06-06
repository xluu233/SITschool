package com.example.luhongcheng;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Administrator on 2017/1/21.
 */

public class gradeAdapter extends BaseAdapter {

    private List<grade> newsList;
    private View view;
    private Context mContext;
    private ViewHolder viewHolder;

    public gradeAdapter(Context mContext, List<grade> newsList) {
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
            view = LayoutInflater.from(mContext).inflate(R.layout.grade_item, null);
            viewHolder = new ViewHolder();
            viewHolder.a1 = (TextView) view.findViewById(R.id.A1);
            viewHolder.a2 = (TextView)view.findViewById(R.id.A2);
            viewHolder.a3 = (TextView)view.findViewById(R.id.A3);
            viewHolder.a4 = (TextView)view.findViewById(R.id.A4);
            viewHolder.a5 = (TextView)view.findViewById(R.id.A5);
            viewHolder.a6 = (TextView)view.findViewById(R.id.A6);
            viewHolder.a7 = (TextView)view.findViewById(R.id.A7);
            viewHolder.a8 = (TextView)view.findViewById(R.id.A8);
            viewHolder.a9 = (TextView)view.findViewById(R.id.A9);


            viewHolder.a7.setTextColor(Color.RED);
            viewHolder.a2.setTextColor(Color.BLUE);
            //tv.setTextColor(0xff000000);

            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.a1.setText("课程代码："+newsList.get(position).geta1());
        viewHolder.a2.setText("名称："+newsList.get(position).geta2());
        viewHolder.a3.setText("学分："+newsList.get(position).geta3());
        viewHolder.a4.setText("平时分："+newsList.get(position).geta4());
        viewHolder.a5.setText("期中："+newsList.get(position).geta5());
        viewHolder.a6.setText("期末："+newsList.get(position).geta6());
        viewHolder.a7.setText("总评："+newsList.get(position).geta7());
        viewHolder.a8.setText("二考成绩："+newsList.get(position).geta8());
        viewHolder.a9.setText("二考总评："+newsList.get(position).geta9());
        return view;

    }

    class ViewHolder{
        TextView a1;
        TextView a2;
        TextView a3;
        TextView a4;
        TextView a5;
        TextView a6;
        TextView a7;
        TextView a8;
        TextView a9;
    }

}
