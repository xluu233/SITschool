package com.example.luhongcheng.Adapter;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.luhongcheng.R;
import com.example.luhongcheng.bean.Test;

import java.util.List;

/**
 * Created by Administrator on 2017/1/21.
 */

public class TestAdapter extends BaseAdapter {

    private List<Test> newsList;
    private View view;
    private Context mContext;
    private ViewHolder viewHolder;

    public TestAdapter(Context mContext, List<Test> newsList) {
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
            view = LayoutInflater.from(mContext).inflate(R.layout.test_item, null);
            viewHolder = new ViewHolder();
            viewHolder.a1 = (TextView) view.findViewById(R.id.A1);
            viewHolder.a2 = (TextView)view.findViewById(R.id.A2);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.a1.setText(" "+newsList.get(position).geta1());
        viewHolder.a2.setText(" "+newsList.get(position).geta2());

        AssetManager mgr = mContext.getAssets();
        Typeface tf = Typeface.createFromAsset(mgr, "fonts/fangsong.TTF");//仿宋
        viewHolder.a2.setTypeface(tf);

        return view;
    }

    class ViewHolder{
        TextView a1;
        TextView a2;
    }

}
