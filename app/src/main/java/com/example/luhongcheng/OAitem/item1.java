package com.example.luhongcheng.OAitem;


import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.luhongcheng.R;
import com.example.luhongcheng.secondclass.g_scFragment;
import com.example.luhongcheng.secondclass.d_scFragment;
import com.example.luhongcheng.secondclass.c_scFragment;
import com.example.luhongcheng.secondclass.a_scFragment;
import com.example.luhongcheng.secondclass.f_scFragment;
import com.example.luhongcheng.secondclass.e_scFragment;
import com.example.luhongcheng.secondclass.b_scFragment;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

//第二课堂

public class item1 extends AppCompatActivity {

	String xuehao;
	String mima;
	List<String> cookies;
	String URL1 ;
	private ArrayList<Fragment> mFragments;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.item1);
		getID();
		getCookies();

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			getWindow().setStatusBarColor(getResources().getColor(R.color.colorAccent));//设置状态栏背景色
		}

		initFragment();
		initView();

	}

	private void getID() {
		SharedPreferences spCount = getSharedPreferences("userid", 0);
		xuehao= spCount.getString("username", "");
		mima= spCount.getString("password", "");

		if(xuehao.length()==0){
			Toast.makeText(item1.this,"你还没有输入账号", Toast.LENGTH_SHORT).show();
		}else {
			URL1 = "http://sc.sit.edu.cn/j_spring_security_check?j_username="+xuehao+"&returnUrl=";
		}

	}

	private void getCookies() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					final OkHttpClient client = new OkHttpClient().newBuilder()
							.followRedirects(false)//禁止重定向
							.followSslRedirects(false)//哈哈哈哈哈哈哈好开心啊
							.build();


					RequestBody requestBody1 = new FormBody.Builder()
							.add("goto", "http://myportal.sit.edu.cn/loginSuccess.portal")
							.add("gotoOnFail", "http://myportal.sit.edu.cn/loginFailure.portal")
							.add("Login.Token1",xuehao)
							.add("Login.Token2",mima)
							.build();
					Request request1 = new Request.Builder()
							.url("http://myportal.sit.edu.cn/userPasswordValidate.portal")
							.post(requestBody1)
							.build();
					Response response1 = client.newCall(request1).execute();

					final Headers headers1 = response1.headers();
					cookies = headers1.values("Set-Cookie");
					Log.d("cookie信息A", "onResponse-size: " + cookies);
					String[] iPlanetDirectoryPro = cookies.toArray(new String[cookies.size()]);
					String str1 = null;
					for (int i = 0; i < iPlanetDirectoryPro.length; ++i) {
						str1 = iPlanetDirectoryPro[i];
					}
					//System.out.println("iPlanetDirectoryPro:"+str1.toString());
					//str1是iPlanetDirectoryPro的值


					Request request2 = new Request.Builder()
							.url("http://sc.sit.edu.cn/login.jsp")
							.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
							.header("Accept-Language", "zh-CN,zh;q=0.9")
							.header("Connection", "Keep-Alive")
							.header("Cookie", String.valueOf(cookies))
							.header("Host", "sc.sit.edu.cn")
							.header("Upgrade-Insecure-Requests","1")
							.header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3423.2 Safari/537.36")
							.build();
					Response response2 = client.newCall(request2).execute();

					final Headers headers2 = response2.headers();
					cookies = headers2.values("Set-Cookie");
					String[] as = cookies.toArray(new String[cookies.size()]);
					String str2 = null;
					for (int i = 0; i < as.length; ++i) {
						str2 = as[i];
					}
					//System.out.println("假的JSESSIONID:"+str2.toString());
					//str2是第二课堂返回的第一个session
					String str = str1+";"+str2;
					//System.out.println("set_cookie:"+str.toString());
					str = str.replaceAll("Path=/; HttpOnly","");
					str = str.replaceAll("Path=/; Domain=.sit.edu.cn;","");
					//System.out.println("str:"+str.toString());


					Request request3 = new Request.Builder()
							.url(URL1)
							.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
							.header("Accept-Language", "zh-CN,zh;q=0.9")
							.header("Connection", "Keep-Alive")
							.header("Cookie", String.valueOf(cookies))
							.header("Host", "sc.sit.edu.cn")
							.header("Upgrade-Insecure-Requests","1")
							.header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3423.2 Safari/537.36")
							.build();
					Response response3 = client.newCall(request3).execute();
					final Headers headers3 = response3.headers();
					//Log.d("头信息", "header " + headers3);
					cookies = headers3.values("Set-Cookie");
					String[] ad = cookies.toArray(new String[cookies.size()]);
					String str3 = null;
					for (int i = 0; i < ad.length; ++i) {
						str3 = ad[i];
					}
					//System.out.println("真的JSESSIONID:"+str3.toString());
					str = str1+";"+str3;
					//System.out.println("str:"+str.toString());
					save(str);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	private void save(String str) {
		SharedPreferences.Editor editor=getSharedPreferences("SecondCookie",0).edit();
		editor.putString("cookie",str);
		editor.commit();
	}


	private void initFragment() {
		mFragments = new ArrayList<>();

		a_scFragment one =new a_scFragment();
		b_scFragment two = new b_scFragment();
		c_scFragment three = new c_scFragment();
		d_scFragment four = new d_scFragment();
		e_scFragment five = new e_scFragment();
		f_scFragment six = new f_scFragment();
		g_scFragment seven = new g_scFragment();

		mFragments.add(one);
		mFragments.add(two);
		mFragments.add(three);
		mFragments.add(four);
		mFragments.add(five);
		mFragments.add(six);
		mFragments.add(seven);
	}

	private static final String DOG_BREEDS[] = {"我的", "讲座","公益","三创","校园文化","社团","社会实践"};

	private void initView() {
		SlidingTabLayout tabLayout = findViewById(R.id.secondclass_tab);
		ViewPager viewPager = findViewById(R.id.secondclass_viewpager);
		viewPager.setOffscreenPageLimit(7);

		viewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
			@Override
			public Fragment getItem(int position) {
				return mFragments.get(position);
			}

			@Override
			public int getCount() {
				return mFragments.size();
			}

			@Override
			public CharSequence getPageTitle(int position) {
				return DOG_BREEDS[position];
			}

		});

		tabLayout.setViewPager(viewPager);
	}


}
