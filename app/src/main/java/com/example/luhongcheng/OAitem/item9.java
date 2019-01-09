package com.example.luhongcheng.OAitem;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import com.example.luhongcheng.R;
import com.example.luhongcheng.WeiXin.FirstFragment;
import com.example.luhongcheng.WeiXin.SecondFragment;
import com.example.luhongcheng.WeiXin.ThirdFragment;
import java.util.ArrayList;




public class item9 extends AppCompatActivity {

    FirstFragment oneFragment;
    SecondFragment twoFragment;
    ThirdFragment threeFragment;
    private ArrayList<Fragment> mFragments;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weixin);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        initFragment();
        initView();

    }


    private void initFragment() {
        mFragments = new ArrayList<>();

        oneFragment =  FirstFragment.newInstance();
        twoFragment =  SecondFragment.newInstance();
        threeFragment = ThirdFragment.newInstance();

        mFragments.add(oneFragment);
        mFragments.add(twoFragment);
        mFragments.add(threeFragment);
    }

    private static final String DOG_BREEDS[] = {"SIT1", "SIT2", "SIT3"};

    private void initView() {
        final TabLayout tabLayout = findViewById(R.id.weixin_tab);
        final ViewPager viewPager = findViewById(R.id.viewpager);

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


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                oneFragment.exit();//这里我们调用的meFrament中的exit
                twoFragment.exit();
                threeFragment.exit();
               return true;    //已处理
            }
        }
        return super.onKeyDown(keyCode, event);
    }


}
