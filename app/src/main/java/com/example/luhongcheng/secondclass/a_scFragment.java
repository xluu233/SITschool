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

import java.io.IOException;
import java.util.Objects;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class a_scFragment extends Fragment {

	private OkHttpClient okHttpClient;
	private OkHttpClient.Builder builder;
	private Handler handler;
	String str;
	String B1;
	String B2;
	String C1;
	String C2;
	String C3;
	String C4;
	String C5;
	String C6;
	String C7;
	String C8;
	String C9;
	String C10;
	SwipeRefreshLayout refreshLayout;

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.layout_second, container, false);
		return v;
	}



	@SuppressLint("HandlerLeak")
	@Override
	public void onActivityCreated( Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		final TextView name = (TextView) Objects.requireNonNull(getView()).findViewById(R.id.id);
		final TextView xuefen1 = (TextView) getView().findViewById(R.id.xuefen1);
		final TextView c1 = (TextView) getView().findViewById(R.id.c1);
		final TextView c2 = (TextView) getView().findViewById(R.id.c2);
		final TextView c3 = (TextView) getView().findViewById(R.id.c3);
		final TextView c4 = (TextView) getView().findViewById(R.id.c4);
		final TextView c5 = (TextView) getView().findViewById(R.id.c5);
		final TextView c6 = (TextView) getView().findViewById(R.id.c6);
		final TextView c7 = (TextView) getView().findViewById(R.id.c7);
		final TextView c8 = (TextView) getView().findViewById(R.id.c8);
		final TextView c9 = (TextView) getView().findViewById(R.id.c9);
		final TextView c10 = (TextView) getView().findViewById(R.id.c10);
		refreshLayout = getView().findViewById(R.id.sc_a_refresh);
		getCookies();

		handler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				if(msg.what == 1){
					name.setText(getB1(B1));
					xuefen1.setText(getB2(B2));
					c1.setText(getC1(C1));
					c2.setText(getC2(C2));
					c3.setText(getC3(C3));
					c4.setText(getC4(C4));
					c5.setText(getC5(C5));
					c6.setText(getC6(C6));
					c7.setText(getC7(C7));
					c8.setText(getC8(C8));
					c9.setText(getC9(C9));
					c10.setText(getC10(C10));

				}
			}

			private String getB1(String B1) {
				return B1;
			}
			private String getB2(String B2) {
				return B2;
			}
			private String getC1(String C1) {
				return C1;
			}
			private String getC2(String C2) {
				return C2;
			}
			private String getC3(String C3) {
				return C3;
			}
			private String getC4(String C4) {
				return C4;
			}
			private String getC5(String C5) {
				return C5;
			}
			private String getC6(String C6) {
				return C6;
			}
			private String getC7(String C7) {
				return C7;
			}
			private String getC8(String C8) {
				return C8;
			}
			private String getC9(String C9) {
				return C9;
			}
			private String getC10(String C10) {
				return C10;
			}

		};


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
					Elements link =  doc.getElementsByClass("user-info");

					for(int j = 0;j < link.size();j++){
						B1 = link.get(j).select("div").get(2).text();
						System.out.println("B1"+B1.toString());

						B2 = link.get(j).select("span").text();
						System.out.println("B2"+B2.toString());
						B2 = B2.replaceAll("◆ ◆ ","");
						B2 = B2.replaceAll(" 进入明细 关闭","");
					}


					Message msg = new Message();
					msg.what = 1;
					handler.sendMessage(msg);
				}catch (Exception e){
					e.printStackTrace();
				}

			}
		}).start();

		new Thread(new Runnable() {
			@Override
			public void run() {
				try{
					Document doc = Jsoup.parse(responseData4);
					//Element url = doc.getElementById("pf8271");   //依据ID取值
					Elements link =  doc.getElementsByClass("table_style_4");

					for(int j = 0;j < link.size();j++){
						C1 = link.get(j).select("tr").get(1).text();
						//System.out.println("C1"+C1.toString());

						C2 = link.get(j).select("tr").get(2).text();
						//System.out.println("C2"+C2.toString());

						C3 = link.get(j).select("tr").get(3).text();
						//System.out.println("C3"+C3.toString());

						C4 = link.get(j).select("tr").get(4).text();
						//System.out.println("C4"+C4.toString());

						C5 = link.get(j).select("tr").get(5).text();
						//System.out.println("C5"+C5.toString());

						C6 = link.get(j).select("tr").get(6).text();
						//System.out.println("C6"+C6.toString());

						C7 = link.get(j).select("tr").get(7).text();
						//System.out.println("C7"+C7.toString());

						C8 = link.get(j).select("tr").get(8).text();
						//System.out.println("C8"+C8.toString());

						C9 = link.get(j).select("tr").get(9).text();
						//System.out.println("C9"+C9.toString());

						C10 = link.get(j).select("tr").get(10).text();
						//System.out.println("C10"+C10.toString());
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
