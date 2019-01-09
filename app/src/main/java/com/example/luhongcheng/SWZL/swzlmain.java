package com.example.luhongcheng.SWZL;


import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;


import com.example.luhongcheng.R;

import java.util.ArrayList;

public class swzlmain extends FragmentActivity {

    private ViewPager myviewpager;
    private ArrayList<Fragment> mFragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.swzl);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }
        initFragment();
        initView();
    }
    private void initFragment() {
        mFragments = new ArrayList<>();

        FirstFragment Fragment1 =  FirstFragment.newInstance();
        SecondFragment Fragment2 =  SecondFragment.newInstance();

        mFragments.add(Fragment1);
        mFragments.add(Fragment2);
    }

    private static final String DOG_BREEDS[] = {"丢失物件","寻找物件"};

    private void initView() {
        final TabLayout tabLayout = findViewById(R.id.swzl_tab);
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




}

