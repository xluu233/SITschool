package com.example.luhongcheng;


import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
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

import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


//OA主页

public class item3 extends FragmentActivity implements OnClickListener, OnPageChangeListener {
	Fragment fragment;
	private ViewPager myviewpager;
	//fragment的集合，对应每个子页面
	private ArrayList<Fragment> fragments;
	//选项卡中的按钮
	private Button btn_third;
	private Button btn_four;
	private Button btn_fifth;
	private Button btn_six;
	private Button btn_seven;
	private Button btn_eight;
	//作为指示标签的按钮
	private ImageView cursor;
	//标志指示标签的横坐标
	float cursorX = 0;
	//所有按钮的宽度的集合
	private int[] widthArgs;
	//所有按钮的集合
	private Button[] btnArgs;


	String xuehao;
	String mima;

	String str;
	List<String> cookies;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.item3);
		initView();
		getID();
		getCookies();

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			getWindow().setStatusBarColor(getResources().getColor(R.color.OA));//设置状态栏背景色
		}

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

	public void initView(){
		myviewpager = (ViewPager)this.findViewById(R.id.myviewpager);

		btn_third = (Button)this.findViewById(R.id.btn_third);
		btn_four = (Button)this.findViewById(R.id.btn_four);
		btn_fifth = (Button)this.findViewById(R.id.btn_fifth);
		btn_six = (Button)this.findViewById(R.id.btn_six);
		btn_seven = (Button)this.findViewById(R.id.btn_seven);
		btn_eight = (Button)this.findViewById(R.id.btn_eight);
		btnArgs = new Button[]{btn_third,btn_four,btn_fifth,btn_six,btn_seven,btn_eight};
		
		cursor = (ImageView)this.findViewById(R.id.cursor_btn);
		cursor.setBackgroundColor(Color.RED);
		//通过此方法设置指示器的初始大小和位置
		btn_third.post(new Runnable(){
			   @Override
			   public void run() {
				   LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams)cursor.getLayoutParams();
					//减去边距*2，以对齐标题栏文字
				   lp.width = btn_third.getWidth()-btn_third.getPaddingLeft()*2;
				   cursor.setLayoutParams(lp);  
				   cursor.setX(btn_third.getPaddingLeft());
			  }
		});
		
		myviewpager.setOnPageChangeListener(this);
		btn_third.setOnClickListener(this);
		btn_four.setOnClickListener(this);
		btn_fifth.setOnClickListener(this);
		btn_six.setOnClickListener(this);
		btn_seven.setOnClickListener(this);
		btn_eight.setOnClickListener(this);
		
		fragments = new ArrayList<Fragment>();
		fragments.add(new ThirdOAFragment());
		fragments.add(new FourOAFragment());
		fragments.add(new FifthOAFragment());
		fragments.add(new SixOAFragment());
		fragments.add(new SevenOAFragment());
		fragments.add(new EightOAFragment());
		
		OAFragmentPagerAdapter adapter = new OAFragmentPagerAdapter(getSupportFragmentManager(),fragments);
		myviewpager.setAdapter(adapter);
		
		resetButtonColor();
		btn_third.setTextColor(Color.RED);
		
	}
	
	//重置所有按钮的颜色
	public void resetButtonColor(){
		btn_third.setBackgroundColor(Color.parseColor("#FFCC99"));
		btn_four.setBackgroundColor(Color.parseColor("#FFCC99"));
		btn_fifth.setBackgroundColor(Color.parseColor("#FFCC99"));
		btn_six.setBackgroundColor(Color.parseColor("#FFCC99"));
		btn_seven.setBackgroundColor(Color.parseColor("#FFCC99"));
		btn_eight.setBackgroundColor(Color.parseColor("#FFCC99"));

		btn_third.setTextColor(Color.BLACK);
		btn_four.setTextColor(Color.BLACK);
		btn_fifth.setTextColor(Color.BLACK);
		btn_six.setTextColor(Color.BLACK);
		btn_seven.setTextColor(Color.BLACK);
		btn_eight.setTextColor(Color.BLACK);
	}

	@Override
	public void onClick(View whichbtn) {
		// TODO Auto-generated method stub
		
		switch (whichbtn.getId()) {
			case R.id.btn_third:
				myviewpager.setCurrentItem(1);
				cursorAnim(1);
				break;

			case R.id.btn_four:
				myviewpager.setCurrentItem(2);
				cursorAnim(2);
				break;

			case R.id.btn_fifth:
				myviewpager.setCurrentItem(3);
				cursorAnim(3);
				break;

			case R.id.btn_six:
				myviewpager.setCurrentItem(4);
				cursorAnim(4);
				break;

			case R.id.btn_seven:
				myviewpager.setCurrentItem(5);
				cursorAnim(5);
				break;

			case R.id.btn_eight:
				myviewpager.setCurrentItem(6);
				cursorAnim(6);
				break;


		}
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageSelected(int arg0) {
		// TODO Auto-generated method stub
		if(widthArgs==null){
			widthArgs = new int[]{
					btn_third.getWidth(),
					btn_four.getWidth(),
					btn_fifth.getWidth(),
					btn_six.getWidth(),
					btn_seven.getWidth(),
					btn_eight.getWidth(),};
		}
		//每次滑动首先重置所有按钮的颜色
		resetButtonColor();
		//将滑动到的当前按钮颜色设置为红色
		btnArgs[arg0].setTextColor(Color.RED);
		cursorAnim(arg0);
	}
	
	//指示器的跳转，传入当前所处的页面的下标
	public void cursorAnim(int curItem){
		//每次调用，就将指示器的横坐标设置为0，即开始的位置
		cursorX = 0;
		//再根据当前的curItem来设置指示器的宽度
		LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams)cursor.getLayoutParams();
		//减去边距*2，以对齐标题栏文字
		lp.width = widthArgs[curItem]-btnArgs[0].getPaddingLeft()*2;  
		cursor.setLayoutParams(lp);  
		//循环获取当前页之前的所有页面的宽度
		for(int i=0; i<curItem; i++){
			cursorX = cursorX + btnArgs[i].getWidth();
		}
		//再加上当前页面的左边距，即为指示器当前应处的位置
		cursor.setX(cursorX+btnArgs[curItem].getPaddingLeft());	
	}

}
