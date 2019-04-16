package com.example.luhongcheng.OA;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.luhongcheng.Adapter.OAdapter;
import com.example.luhongcheng.LazyLoadFragment;
import com.example.luhongcheng.R;
import com.example.luhongcheng.bean.OA;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;

public class b_oaFragment extends LazyLoadFragment {

	private List<OA> newsList = new ArrayList<>();
	private OAdapter adapter;
	private ListView lv;
	private String data;
	private boolean layoutInit = false;

	@Override
	protected int setContentView() {
		return R.layout.layout_oa_item;
	}

	@RequiresApi(api = Build.VERSION_CODES.KITKAT)
	@Override
	protected void lazyLoad() {
		if (newsList.size() == 0){
			SharedPreferences spCount = getActivity().getSharedPreferences("OAData", 0);
			data = spCount.getString("data", "");
			getNews(data);
		}
	}


	@Override
	public void onActivityCreated( Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		lv = (ListView) getView().findViewById(R.id.oa_lv);
		layoutInit = true;
		SmartRefreshLayout refreshLayout = getView().findViewById(R.id.oa_refresh);
		refreshLayout.setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh(@NonNull final RefreshLayout refreshlayout) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						refreshlayout.finishRefresh(2000/*,false*/);
					}
				}).run();
			}
		});

	}

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			if(msg.what == 1){
				adapter = new OAdapter(getActivity(),newsList);
				lv.setAdapter(adapter);
				lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
						OA news = newsList.get(position);
						Intent intent = new Intent(getActivity(),OADisplayActvivity.class);
						intent.putExtra("news_url",news.getA2());
						startActivity(intent);
					}
				});
			}
		}
	};


	private void getNews(final String data){
		new Thread(new Runnable() {
			@Override
			public void run() {
				try{
					Document doc = Jsoup.parse(data);
					Element url = doc.getElementById("pf8272");
					Elements link =  url.getElementsByTag("li");

					for(int j = 0;j < link.size();j++){
						String A2 = link.get(j).select("a.rss-title").attr("href");
						A2 = "http://myportal.sit.edu.cn/"+A2;
						//System.out.println(A2.toString());

						String A1 = link.get(j).select("a").attr("title");
						//System.out.println(A1.toString());

						String A3 = link.get(j).select("span").text();
						//System.out.println(A3.toString());

						OA news = new OA(A1,A2,A3);
						newsList.add(news);
					}


					if (layoutInit){
						Message msg = new Message();
						msg.what = 1;
						handler.sendMessage(msg);
					}
				}catch (Exception e){
					e.printStackTrace();
				}
			}
		}).start();
	}


}
