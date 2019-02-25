package com.example.luhongcheng.Login;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.example.luhongcheng.R;
import java.util.ArrayList;

/**
 * Created by alex233 on 2018/4/21.
 */

public class LoginActivity extends AppCompatActivity {

    private ArrayList<Fragment> mFragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.login);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.login));//设置状态栏背景色
        }

        initFragment();
        initView();
        ImageView share = (ImageView) findViewById(R.id.shareapp) ;
        share.setOnClickListener(new ShareText());
    }


    //分享文字至所有第三方软件
    public class ShareText implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_TEXT, "SITschool上应大学生助手集成OA系统部分查询及资讯功能，可在Android端实现查询成绩，查询电费，查询第二课堂，查询考试安排等等一系列功能，目前在酷安已发布，快来下载吧：https://www.coolapk.com/apk/187672");
            intent.setType("text/plain");
            startActivity(Intent.createChooser(intent, "分享到"));
        }
    }


    private void initFragment() {
        mFragments = new ArrayList<>();

        login_one_fragment oneFragment =  login_one_fragment.newInstance();
        login_two_fragment twoFragment =  login_two_fragment.newInstance();

        mFragments.add(oneFragment);
        mFragments.add(twoFragment);
    }

    private static final String DOG_BREEDS[] = {"登录", "用户协议"};

    private void initView() {
        final TabLayout tabLayout = findViewById(R.id.login_tab);
        final ViewPager viewPager = findViewById(R.id.login_viewpager);

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