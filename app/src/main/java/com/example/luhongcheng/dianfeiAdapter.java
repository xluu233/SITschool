package com.example.luhongcheng;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;



import java.util.List;

/**
 * Created by Administrator on 2017/1/21.
 */

public class dianfeiAdapter extends BaseAdapter {

    private List<dianfei> newsList;
    private View view;
    private Context mContext;
    private ViewHolder viewHolder;

    public dianfeiAdapter(Context mContext, List<dianfei> newsList) {
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
            view = LayoutInflater.from(mContext).inflate(R.layout.dianfei_item, null);
            viewHolder = new ViewHolder();
            viewHolder.name = (TextView) view.findViewById(R.id.name);
            viewHolder.a1 = (TextView)view.findViewById(R.id.a1);
            viewHolder.a2 = (TextView)view.findViewById(R.id.a2);
            viewHolder.a3 = (TextView)view.findViewById(R.id.a3);
            viewHolder.a4 = (TextView)view.findViewById(R.id.a4);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.name.setText("宿舍号："+newsList.get(position).getname());
        viewHolder.a1.setText("存款余额(元) : "+newsList.get(position).geta1());
        viewHolder.a2.setText("电补余额(元) : "+newsList.get(position).geta2());
        viewHolder.a3.setText("合计余额(元) : "+newsList.get(position).geta3());
        viewHolder.a4.setText("可用电量(度) : "+newsList.get(position).geta4());
        return view;
    }

    class ViewHolder{
        TextView name;
        TextView a1;
        TextView a2;
        TextView a3;
        TextView a4;
    }

}
