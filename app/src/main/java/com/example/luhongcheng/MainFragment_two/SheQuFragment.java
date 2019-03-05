package com.example.luhongcheng.MainFragment_two;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.luhongcheng.MainFragment_four.FourFragment;
import com.example.luhongcheng.R;
import com.example.luhongcheng.SIT_SQ.SQ_five_QA;
import com.example.luhongcheng.SIT_SQ.SQ_four_vedio;
import com.example.luhongcheng.SIT_SQ.SQ_one_my_attention;
import com.example.luhongcheng.SIT_SQ.SQ_three_big_sit;
import com.example.luhongcheng.SIT_SQ.SQ_two_top_message;
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

        SQ_one_my_attention one = new SQ_one_my_attention();
        SQ_two_top_message two = new SQ_two_top_message();
        SQ_three_big_sit three = new SQ_three_big_sit();
        SQ_four_vedio four = new SQ_four_vedio();
        SQ_five_QA five = new SQ_five_QA();


        mFragments.add(one);
        mFragments.add(two);
        mFragments.add(three);
        mFragments.add(four);
        mFragments.add(five);


    }

    private static final String DOG_BREEDS[] = {"关注","头条", "社区", "视频","问答"};

    private void initView() {
        SlidingTabLayout tabLayout = getActivity().findViewById(R.id.sq_tab);
        ViewPager viewPager = getActivity().findViewById(R.id.sq_vp);

        //tabLayout.setupWithViewPager(viewPager);


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
        viewPager.setCurrentItem(1);
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
