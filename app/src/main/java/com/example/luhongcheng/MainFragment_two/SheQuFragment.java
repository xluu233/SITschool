package com.example.luhongcheng.MainFragment_two;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.luhongcheng.R;
import com.example.luhongcheng.SIT_SQ.SQ_QA;
import com.example.luhongcheng.SIT_SQ.SQ_ShareNews;
import com.example.luhongcheng.SIT_SQ.SQ_Vedio;
import com.example.luhongcheng.SIT_SQ.SQ_BigSit;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;

public class SheQuFragment extends Fragment {
    private static final String TAG = SheQuFragment.class.getSimpleName();
    private ArrayList<Fragment> mFragments;

    public SheQuFragment() {
        Context mContext = getActivity();
    }
    public static SheQuFragment newInstance(Context context) {
        Context mContext = context;
        return new SheQuFragment();
    }

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

        View statusBar = getView().findViewById(R.id.statusBarView);
        ViewGroup.LayoutParams layoutParams = statusBar.getLayoutParams();
        layoutParams.height = getStatusBarHeight(getActivity());


    }

    private void initFragment() {
        mFragments = new ArrayList<>();


        SQ_BigSit one = new SQ_BigSit();
        SQ_ShareNews two = new SQ_ShareNews();
        SQ_QA three = new SQ_QA();
        SQ_Vedio four = new SQ_Vedio();


        mFragments.add(one);
        mFragments.add(two);
        mFragments.add(three);
        mFragments.add(four);



    }

    private static final String DOG_BREEDS[] = {"社区","热点","问答", "视频"};

    private void initView() {
        SlidingTabLayout tabLayout = getActivity().findViewById(R.id.sq_tab);
        ViewPager viewPager = getActivity().findViewById(R.id.sq_vp);

        //tabLayout.setupWithViewPager(viewPager);
        viewPager.setOffscreenPageLimit(4);//记数从0开始!!!


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

        //tablayout设置与viewpager同步 必须在viewpager设置adapter之后
        tabLayout.setViewPager(viewPager);
        //默认在第二个打开
        viewPager.setCurrentItem(0);
    }

    /**
     * 利用反射获取状态栏高度
     * @return
     * @param activity
     */
    public int getStatusBarHeight(Activity activity) {
        int result = 0;
        //获取状态栏高度的资源id
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

}
