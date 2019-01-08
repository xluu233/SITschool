package com.example.luhongcheng.OAitem;


import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.luhongcheng.OA.EightOAFragment;
import com.example.luhongcheng.OA.FifthOAFragment;
import com.example.luhongcheng.OA.FourOAFragment;
import com.example.luhongcheng.OA.OAFragmentPagerAdapter;
import com.example.luhongcheng.OA.SevenOAFragment;
import com.example.luhongcheng.OA.SixOAFragment;
import com.example.luhongcheng.OA.ThirdOAFragment;
import com.example.luhongcheng.R;
import com.example.luhongcheng.secondclass.EightFragment;
import com.example.luhongcheng.secondclass.FifthFragment;
import com.example.luhongcheng.secondclass.FourFragment;
import com.example.luhongcheng.secondclass.SecondFragment;
import com.example.luhongcheng.secondclass.SevenFragment;
import com.example.luhongcheng.secondclass.SixFragment;
import com.example.luhongcheng.secondclass.ThridFragment;

import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


//OA主页

public class item3 extends AppCompatActivity {


	String xuehao;
	String mima;
	String str;
	List<String> cookies;
	private ArrayList<Fragment> mFragments;





	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.item1);
		getID();
		getCookies();

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));//设置状态栏背景色
		}
		initFragment();
		initView();
	}

	private void getID() {
		SharedPreferences spCount = getSharedPreferences("userid", 0);
		xuehao= spCount.getString("username", "");
		mima= spCount.getString("password", "");

		if(xuehao.length()==0){
			Toast.makeText(item3.this,"你还没有输入账号", Toast.LENGTH_SHORT).show();
		}

	}


	private void getCookies() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					OkHttpClient client = new OkHttpClient();
					RequestBody requestBody = new FormBody.Builder()
							.add("goto", "http://myportal.sit.edu.cn/loginSuccess.portal")
							.add("gotoOnFail", "http://myportal.sit.edu.cn/loginFailure.portal")
							.add("Login.Token1",xuehao)
							.add("Login.Token2",mima)
							.build();
					Request request1 = new Request.Builder()
							.url("http://myportal.sit.edu.cn/userPasswordValidate.portal")
							.post(requestBody)
							.build();

					Response response1 = client.newCall(request1).execute();
					final Headers headers = response1.headers();

					cookies = headers.values("Set-Cookie");
					//Log.d("cookie信息", "onResponse-size: " + cookies);

					String[] strs = cookies.toArray(new String[cookies.size()]);
					for (int i = 0; i < strs.length; ++i) {
						str = strs[i];
					}
					save(str);

					Request request = new Request.Builder()
							.url("http://myportal.sit.edu.cn/index.portal")
							.header("Accept", "text/html, application/xhtml+xml, image/jxr, */*")
							.header("Accept-Language", "zh-Hans-CN,zh-Hans;q=0.5")
							.header("Connection", "Keep-Alive")
							.header("Cookie", str)
							.header("Host", "myportal.sit.edu.cn")
							.header("Referer", "http://myportal.sit.edu.cn/userPasswordValidate.portal")
							.header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; Trident/7.0; rv:11.0) like Gecko")
							.build();
					Response response = client.newCall(request).execute();
					String responseData = response.body().string();
					getData(responseData);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	private void getData(String responseData) {
		SharedPreferences.Editor editor=getSharedPreferences("OAData",0).edit();
		editor.putString("data",responseData);
		editor.commit();
	}

	private void save(String str) {
		SharedPreferences.Editor editor=getSharedPreferences("OACookie",0).edit();
		editor.putString("cookie",str);
		editor.commit();
	}


	private void initFragment() {
		mFragments = new ArrayList<>();

		ThirdOAFragment one = new ThirdOAFragment();
		FourOAFragment two = new FourOAFragment();
		FifthOAFragment three = new FifthOAFragment();
		SixOAFragment four = new SixOAFragment();
		SevenOAFragment five = new SevenOAFragment();
		EightOAFragment six = new EightOAFragment();

		mFragments.add(one);
		mFragments.add(two);
		mFragments.add(three);
		mFragments.add(four);
		mFragments.add(five);
		mFragments.add(six);
	}

	private static final String DOG_BREEDS[] = {"学生事务", "学习课堂","校园文化","公告信息","学院通知","文件下载"};

	private void initView() {
		final TabLayout tabLayout = findViewById(R.id.secondclass_tab);
		final ViewPager viewPager = findViewById(R.id.secondclass_viewpager);

		tabLayout.setupWithViewPager(viewPager);

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

		viewPager.setOffscreenPageLimit(mFragments.size());
		viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
		tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
			@Override
			public void onTabSelected(TabLayout.Tab tab) {
				// Log.d(TAG, "onTabSelected: ");
				viewPager.setCurrentItem(tabLayout.getSelectedTabPosition(), true);
			}

			@Override
			public void onTabUnselected(TabLayout.Tab tab) {
				// Log.d(TAG, "onTabUnselected: ");
			}

			@Override
			public void onTabReselected(TabLayout.Tab tab) {
				// Log.d(TAG, "onTabReselected: ");
			}
		});
	}




}
