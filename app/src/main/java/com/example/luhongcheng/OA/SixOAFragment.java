package com.example.luhongcheng.OA;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.luhongcheng.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;

public class SixOAFragment extends Fragment {

	private List<OA> newsList;
	private OAdapter adapter;
	private Handler handler;
	private ListView lv;

	private OkHttpClient okHttpClient;
	private OkHttpClient.Builder builder;
	List<String> cookies;
	private ProgressBar progressBar;
	String data;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.layout_oa_item, container, false);
		return v;
	}


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
							Intent intent = new Intent(getActivity(),OADisplayActvivity.class);
							intent.putExtra("news_url",news.getA2());
							startActivity(intent);
							//Intent intent = new Intent(MainActivity.this,NewsDisplayActvivity.class);
							//intent.putExtra("news_url",news.getNewsUrl());
							//startActivity(intent);

							//Intent intent2 = new Intent(MainActivity.this,NewsDisplayActvivity.class);
							//intent2.putExtra("COOKIE",str);
							//startActivity(intent2);
							//此处不能传递COOKIE，可能会混淆
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
					Element url = doc.getElementById("pf8275");   //依据ID取值
					Elements link =  url.getElementsByTag("li");

					for(int j = 0;j < link.size();j++){
						String A2 = link.get(j).select("a.rss-title").attr("href");
						A2 = "http://myportal.sit.edu.cn/"+A2;
					//	System.out.println(A2.toString());

						String A1 = link.get(j).select("a").attr("title");
						//System.out.println(A1.toString());

						String A3 = link.get(j).select("span").text();
					//	System.out.println(A3.toString());

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
