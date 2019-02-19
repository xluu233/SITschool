package com.example.luhongcheng;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.luhongcheng.FruitItem.Fruit;
import com.example.luhongcheng.FruitItem.FruitAdapter;
import com.example.luhongcheng.Login.LoginActivity;
import com.example.luhongcheng.about.about0;
import com.example.luhongcheng.about.about1;
import com.example.luhongcheng.about.about2;
import com.example.luhongcheng.about.about3;
import com.example.luhongcheng.about.about4;
import com.example.luhongcheng.utils.APKVersionCodeUtils;

import java.util.ArrayList;
import java.util.List;

import static cn.bmob.v3.Bmob.getApplicationContext;

/**
 * Created by Administrator on 2018/4/8.
 */

public class FourFragment extends Fragment {
    private String context;
    private ArrayList<Fragment> mFragments;
    @SuppressLint("ValidFragment")
    public FourFragment(String context){
        this.context = context;
    }

    //打包问题，在这里加入无参构造函数
    public FourFragment() {
        Context mContext = getActivity();
    }
    public static FourFragment newInstance(Context context) {
        Context mContext = context;
        return new FourFragment ();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.d_fragment,container,false);
        return view;
    }

    @Override
    public void onActivityCreated( Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initFragment();
        initView();

        if (Build.VERSION.SDK_INT >= 21){
            View decorView = getActivity().getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getActivity().getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

    }

    private void initFragment() {
        mFragments = new ArrayList<>();

        FourFragment_one one = new FourFragment_one();
        FourFragment_two two = new FourFragment_two();

        mFragments.add(one);
        mFragments.add(two);
    }

    private static final String DOG_BREEDS[] = {"   我的   ", "   设置   "};

    private void initView() {
        final TabLayout tabLayout = getActivity().findViewById(R.id.d_tab);
        final ViewPager viewPager = getActivity().findViewById(R.id.d_viewpager);

        tabLayout.setupWithViewPager(viewPager);

        viewPager.setAdapter(new FragmentStatePagerAdapter(getChildFragmentManager()) {
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



