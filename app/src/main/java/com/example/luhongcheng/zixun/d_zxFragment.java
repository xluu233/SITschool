package com.example.luhongcheng.zixun;

import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.luhongcheng.R;
import com.example.luhongcheng.WebDisplay;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;

public class d_zxFragment extends Fragment{
	private List<OA> newsList;
	private OAdapter adapter;
	private Handler handler;
	private ListView lv;

	private OkHttpClient okHttpClient;
	private OkHttpClient.Builder builder;
	private ProgressBar progressBar;
	String data;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.layout_zhuye_item, container, false);
		return v;
	}


	@SuppressLint("HandlerLeak")
	@Override
	public void onActivityCreated( Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		newsList = new ArrayList<>();
		lv = (ListView) getView().findViewById(R.id.news_lv);


		builder = new OkHttpClient.Builder();
		okHttpClient = builder.build();
		progressBar = (ProgressBar) getView().findViewById(R.id.progressBarNormal) ;



		handler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				if(msg.what == 1){
					progressBar.setVisibility(View.GONE);
					adapter = new OAdapter(getActivity(),newsList);
					lv.setAdapter(adapter);
					lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
						@Override
						public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
							OA news = newsList.get(position);
							Intent intent = new Intent(getActivity(), WebDisplay.class);
							intent.putExtra("news_url",news.getA2());
							intent.putExtra("title","官网资讯");
							startActivity(intent);
						}
					});
				}
			}
		};
		getData();
	}

	private void getData() {
		SharedPreferences spCount = getActivity().getSharedPreferences("OAData", 0);
		//在fragment中用share方法要getActivity（）
		data = spCount.getString("data", "");
		getNews(data);
	}



	private void getNews(final String data){
		new Thread(new Runnable() {
			@Override
			public void run() {
				try{
					Document doc = Jsoup.parse(data);
					Elements url = doc.getElementsByClass("post post4");   //依据ID取值
					Elements link =  url.select("tbody");

					for(int j = 1;j < link.size();j++){
						String A2 = link.get(j).select("a").attr("href");
						A2 = "http://www.sit.edu.cn"+A2;
						//System.out.println(A2.toString());

						String A1 = link.get(j).select("td").get(0).text();
						//System.out.println(A1.toString());

						String A3 = link.get(j).select("td").get(1).text();
						//System.out.println(A3.toString());

						OA news = new OA(A1,A2,A3);
						newsList.add(news);
					}


					Message msg = new Message();
					msg.what = 1;
					handler.sendMessage(msg);
				}catch (Exception e){
					e.printStackTrace();
				}
			}
		}).start();
	}


}
