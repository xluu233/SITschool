package com.example.luhongcheng.zixun;



import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.luhongcheng.R;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;



public class news extends AppCompatActivity {

	private ArrayList<Fragment> mFragments;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.item1);
		getCookies();

		initFragment();
		initView();
	}


	private void getCookies() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					OkHttpClient client = new OkHttpClient();
					Request request1 = new Request.Builder()
							.url("http://www.sit.edu.cn/")
							.build();

					Response response1 = client.newCall(request1).execute();
					String responseData = response1.body().string();
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

	private void initFragment() {
		mFragments = new ArrayList<>();

		ThirdzhuyeFragment one = new ThirdzhuyeFragment();
		FourzhuyeFragment two = new FourzhuyeFragment();
		FifthzhuyeFragment three = new FifthzhuyeFragment();
		SixzhuyeFragment four = new SixzhuyeFragment();
		mFragments.add(one);
		mFragments.add(two);
		mFragments.add(three);
		mFragments.add(four);
	}

	private static final String DOG_BREEDS[] = {"学校新闻","校园快讯","学术文化","通知公告"};

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
				viewPager.setCurrentItem(tabLayout.getSelectedTabPosition(), true);
			}

			@Override
			public void onTabUnselected(TabLayout.Tab tab) {
			}

			@Override
			public void onTabReselected(TabLayout.Tab tab) {
			}
		});
	}



}
