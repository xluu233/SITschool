package com.example.luhongcheng.SWZL;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.luhongcheng.Bmob.SWZL;
import com.example.luhongcheng.Bmob.diudiu;
import com.example.luhongcheng.ImageFullDisplay;
import com.example.luhongcheng.R;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class SecondFragment extends Fragment {


	ListView listView2;

	public static SecondFragment newInstance() {
		return new SecondFragment();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.swzl_second, container, false);
		return v;
	}



	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		listView2 = (ListView) getActivity().findViewById(R.id.listView2);
		Bmob.initialize(getActivity(), "69d2a14bfc1139c1e9af3a9678b0f1ed");
		FloatingActionButton refresh = (FloatingActionButton)getActivity().findViewById(R.id.refresh);
		refresh.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				get();
			}
		});

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
							final String[] image = new String[list.size()];

							for(int i = 0;i<list.size();i++){
								title[i] = list.get(i).getTitle();
								content[i] = list.get(i).getContent();
								adress[i] = list.get(i).getQQ();
								image[i] = list.get(i).getimageUrl();


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
								public View getView(final int position, View convertView, ViewGroup parent) {
									ViewHolder viewHolder;
									if (convertView == null){
										LayoutInflater inflater = LayoutInflater.from(getContext());
										convertView = inflater.inflate(R.layout.swzl_second_item, null);//实例化一个布局文件
										viewHolder = new ViewHolder();
										viewHolder.tv_title = (TextView)convertView.findViewById(R.id.tv_title);
										viewHolder.tv_content = (TextView)convertView.findViewById(R.id.tv_content);
										viewHolder.tv_adress = (TextView)convertView.findViewById(R.id.qq);
										viewHolder.img = (ImageView)convertView.findViewById(R.id.iv);

										convertView.setTag(viewHolder);
									}else {
										viewHolder = (ViewHolder) convertView.getTag();
									}
									viewHolder.tv_title.setText(title[position]);
									viewHolder.tv_content.setText("内容："+content[position]);
									viewHolder.tv_adress.setText("联系方式："+adress[position]);

									viewHolder.img.setOnClickListener(new View.OnClickListener() {
										@Override
										public void onClick(View v) {
											Intent intent = new Intent(getActivity(),ImageFullDisplay.class);
											intent.putExtra("url2",image[position]);
											startActivity(intent);
										}
									});

									Glide.with(getContext())
											.load(image[position])
											.placeholder(R.drawable.loading)
											.error(R.drawable.error)
											//  .dontTransform()//不进行图片变换
											.fitCenter()
											// .centerCrop()
											//.override(Target.SIZE_ORIGINAL, 1000)
											.into(viewHolder.img);

									return convertView;
								}
								class ViewHolder{
									TextView tv_title;
									TextView tv_content;
									TextView tv_adress;
									ImageView img;
								}
							}
							listView2.setAdapter(new MyAdapter2(this));
						}


					}
				});
			}
		}); //声明一个子线程
		thread.start();


	}

}