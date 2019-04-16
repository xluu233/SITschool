package com.example.luhongcheng.secondclass;

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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.luhongcheng.Adapter.SecondClassAdapter;
import com.example.luhongcheng.LazyLoadFragment;
import com.example.luhongcheng.R;
import com.example.luhongcheng.bean.SecondClass;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class f_scFragment extends LazyLoadFragment {

	private List<SecondClass> newsList = new ArrayList<>();
	private SecondClassAdapter adapter;
	private ListView lv;
	private String str;
	private boolean layoutInit = false;
	private boolean canfresh = true;


	@Override
	protected int setContentView() {
		return R.layout.layout_class_item;
	}

	@RequiresApi(api = Build.VERSION_CODES.KITKAT)
	@Override
	protected void lazyLoad() {
		if (newsList.size() == 0){
			SharedPreferences spCount = Objects.requireNonNull(getActivity()).getSharedPreferences("SecondCookie", 0);
			str = spCount.getString("cookie", "");
			postdata();
		}
	}


	@SuppressLint("HandlerLeak")
	@Override
	public void onActivityCreated( Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		lv = Objects.requireNonNull(getView()).findViewById(R.id.news_lv);
		layoutInit = true;

		SmartRefreshLayout refreshLayout = getView().findViewById(R.id.secondclass_refresh);
		refreshLayout.setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh(@NonNull final RefreshLayout refreshlayout) {
				if (canfresh){
					new Thread(new Runnable() {
						@Override
						public void run() {
							postdata();
							canfresh = false;
							try {
								refreshlayout.finishRefresh(2000/*,false*/);
								Thread.sleep(10000);
								canfresh = true;
							} catch (InterruptedException e) {
								e.printStackTrace();
							}

						}
					}).start();
				}else {
					Toast.makeText(getContext(),"太快了~10s后再试",Toast.LENGTH_SHORT).show();
					refreshlayout.finishRefresh(2000/*,false*/);
				}
			}
		});

	}


	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			if(msg.what == 1 ){
				adapter = new SecondClassAdapter(getActivity(),newsList);
				lv.setAdapter(adapter);
				lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
						SecondClass news = newsList.get(position);
						Intent intent = new Intent(getActivity(),SecondClassDisplayActvivity.class);
						intent.putExtra("news_url",news.getA2());
						startActivity(intent);
					}
				});
			}
		}
	};



	public void postdata() {
		// 开启线程来发起网络请求
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					final OkHttpClient client = new OkHttpClient().newBuilder()
							.followRedirects(false)//禁止重定向
							.followSslRedirects(false)//哈哈哈哈哈哈哈好开心啊
							.build();


					Request request4 = new Request.Builder()
							// 社团活动
							.url("http://sc.sit.edu.cn/public/activity/activityList.action?categoryId=8ab17f543fe626a8013fe6278a880001")
							.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
							.header("Accept-Language", "zh-CN,zh;q=0.9")
							.header("Connection", "Keep-Alive")
							.header("Cookie", str)
							.header("Host", "sc.sit.edu.cn")
							.header("Upgrade-Insecure-Requests","1")
							// .header("Referer", "http://myportal.sit.edu.cn/userPasswordValidate.portal")
							.header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3423.2 Safari/537.36")
							.build();
					Response response4 = client.newCall(request4).execute();
					String responseData4 = response4.body().string();
					getNews(responseData4);



				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}


	private void getNews(final String responseData4){
		new Thread(new Runnable() {
			@Override
			public void run() {
				try{
					Document doc = Jsoup.parse(responseData4);
					//Element url = doc.getElementById("pf8271");   //依据ID取值
					Elements link =  doc.getElementsByTag("li");

					for(int j = 2;j < link.size();j++){
						String A1 = link.get(j).select("a").text();
						A1 = A1.replace("·","");
						//System.out.println("A1"+A1.toString());

						String A2 = link.get(j).select("a").attr("href");
						A2 = "http://sc.sit.edu.cn"+A2;
						//System.out.println("A2"+A1.toString());

						String A3 = link.get(j).select("span").text();
						//System.out.println("A3"+A1.toString());

						SecondClass news = new SecondClass(A1,A2,A3);
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
