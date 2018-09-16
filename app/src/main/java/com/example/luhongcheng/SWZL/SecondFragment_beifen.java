package com.example.luhongcheng.SWZL;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.luhongcheng.Bmob.SWZL;
import com.example.luhongcheng.Bmob.diudiu;
import com.example.luhongcheng.R;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class SecondFragment_beifen extends Fragment {
	ListView listView;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.swzl_second, container, false);
		return v;
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		listView = (ListView) getActivity().findViewById(R.id.listView);
		Bmob.initialize(getActivity(), "69d2a14bfc1139c1e9af3a9678b0f1ed");
		get();
	}

	public void get(){
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				BmobQuery<diudiu> query = new BmobQuery<diudiu>();
				query.findObjects(new FindListener<diudiu>(){
					@Override
					public void done(List<diudiu> list, BmobException e) {
						List<diudiu> lists = new ArrayList<>();
						if (list != null) {
							final String[] title  =  new String[list.size()];
							final String[] content  =  new String[list.size()];
							final String[] adress  =  new String[list.size()];

							for(int i = 0;i<list.size();i++){
								title[i] = list.get(i).getTitle();
								content[i] = list.get(i).getContent();
								adress[i] = list.get(i).getQQ();


							}


							class MyAdapter2 extends BaseAdapter {
								private FindListener<diudiu> context ;
								public MyAdapter2(FindListener<diudiu> context){
									this.context = context;
								}

								@Override
								public int getCount() {
									return title.length;
								}

								@Override
								public Object getItem(int position) {
									return title[position];
								}

								@Override
								public long getItemId(int position) {
									return position;
								}

								@Override
								public View getView(int position, View convertView, ViewGroup parent) {
									ViewHolder viewHolder;
									if (convertView == null){
										LayoutInflater inflater = LayoutInflater.from(getContext());
										convertView = inflater.inflate(R.layout.swzl_second_item, null);//实例化一个布局文件
										viewHolder = new ViewHolder();
										viewHolder.tv_title = (TextView)convertView.findViewById(R.id.tv_title);
										viewHolder.tv_content = (TextView)convertView.findViewById(R.id.tv_content);
										viewHolder.tv_adress = (TextView)convertView.findViewById(R.id.qq);

										convertView.setTag(viewHolder);
									}else {
										viewHolder = (ViewHolder) convertView.getTag();
									}
									viewHolder.tv_title.setText(title[position]);
									viewHolder.tv_content.setText("内容："+content[position]);
									viewHolder.tv_adress.setText("联系方式："+adress[position]);

									return convertView;
								}
								class ViewHolder{
									TextView tv_title;
									TextView tv_content;
									TextView tv_adress;
								}
							}
							listView.setAdapter(new MyAdapter2(this));
						}


					}
				});
			}
		}); //声明一个子线程
		thread.start();


	}

}
