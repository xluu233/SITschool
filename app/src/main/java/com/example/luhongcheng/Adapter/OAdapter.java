package com.example.luhongcheng.Adapter;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.luhongcheng.bean.OA;
import com.example.luhongcheng.R;

import java.util.List;

/**
 * Created by Administrator on 2017/1/21.
 */

public class OAdapter extends BaseAdapter {

    private List<OA> newsList;
    private View view;
    private Context mContext;
    private ViewHolder viewHolder;

    public OAdapter(Context mContext, List<OA> newsList) {
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
            view = LayoutInflater.from(mContext).inflate(R.layout.oa_item, null);
            viewHolder = new ViewHolder();
            viewHolder.A1 = (TextView) view.findViewById(R.id.A1);
            //viewHolder.A2 = (TextView) view.findViewById(R.id.A2);
            viewHolder.A3 = (TextView) view.findViewById(R.id.A3);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.A1.setText(newsList.get(position).getA1());
        //viewHolder.A2.setText(" "+newsList.get(position).getA2());
        viewHolder.A3.setText(newsList.get(position).getA3());

        AssetManager mgr = mContext.getAssets();
        Typeface tf = Typeface.createFromAsset(mgr, "fonts/fangsong.TTF");//仿宋
        viewHolder.A1.setTypeface(tf);
        viewHolder.A3.setTypeface(tf);

        return view;
    }

    class ViewHolder{
        TextView A1;
        //TextView A2;
        TextView A3;

    }

}
