package com.example.luhongcheng.secondclass;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.luhongcheng.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.Objects;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class my_scFragment extends Fragment {

	private OkHttpClient okHttpClient;
	private OkHttpClient.Builder builder;

	String str;

	SwipeRefreshLayout refreshLayout;

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.mine_second, container, false);
		return v;
	}



	@SuppressLint("HandlerLeak")
	@Override
	public void onActivityCreated( Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		final TextView name = (TextView) Objects.requireNonNull(getView()).findViewById(R.id.id);
		final TextView xuefen1 = (TextView) getView().findViewById(R.id.xuefen1);

		refreshLayout = getView().findViewById(R.id.sc_a_refresh);
		getCookies();



		refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				new Thread(new Runnable() {
					@Override
					public void run() {
						getCookies();
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
							@Override
							public void run() {
								refreshLayout.setRefreshing(false);
							}
						});

					}
				}).start();
			}
		});
	}

	private void getCookies() {
		SharedPreferences spCount = Objects.requireNonNull(getActivity()).getSharedPreferences("SecondCookie", 0);
		str = spCount.getString("cookie", "");
		getmessage();
	}

	private void getmessage() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					final OkHttpClient client = new OkHttpClient().newBuilder()
							.followRedirects(false)//禁止重定向
							.followSslRedirects(false)//哈哈哈哈哈哈哈好开心啊
							.build();

					Request request4 = new Request.Builder()
							.url("http://sc.sit.edu.cn/public/pcenter/activityOrderList.action")
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



				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}).start();
	}

}
