package com.example.luhongcheng;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class SheQuFragment extends Fragment {
    private static final String TAG = SheQuFragment.class.getSimpleName();
    private ArrayList<Fragment> mFragments;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.shequ,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initFragment();
        initView();

    }

    private void initFragment() {
        mFragments = new ArrayList<>();

        com.example.luhongcheng.SQ.OneFragment oneFragment =  com.example.luhongcheng.SQ.OneFragment.newInstance();
        com.example.luhongcheng.SQ.TwoFragment twoFragment =  com.example.luhongcheng.SQ.TwoFragment.newInstance();
        com.example.luhongcheng.SQ.ThreeFragment threeFragment =  com.example.luhongcheng.SQ.ThreeFragment.newInstance();
        com.example.luhongcheng.SQ.FourFragment fourFragment =  com.example.luhongcheng.SQ.FourFragment.newInstance();

        mFragments.add(oneFragment);
        mFragments.add(twoFragment);
        mFragments.add(threeFragment);
        mFragments.add(fourFragment);
    }

    private static final String DOG_BREEDS[] = {"精选", "广场", "Box", "收藏"};

    private void initView() {
        final TabLayout tabLayout = getActivity().findViewById(R.id.tab_layout);
        final ViewPager viewPager = getActivity().findViewById(R.id.viewpager);

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
                Log.d(TAG, "onTabSelected: ");
                viewPager.setCurrentItem(tabLayout.getSelectedTabPosition(), true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                Log.d(TAG, "onTabUnselected: ");
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                Log.d(TAG, "onTabReselected: ");
            }
        });
    }



}
