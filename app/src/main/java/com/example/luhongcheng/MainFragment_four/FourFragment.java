package com.example.luhongcheng.MainFragment_four;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.luhongcheng.R;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/4/8.
 */

public class FourFragment extends Fragment {

    private ArrayList<Fragment> mFragments;
    @SuppressLint("ValidFragment")

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

/*        if (Build.VERSION.SDK_INT >= 21){
            View decorView = getActivity().getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getActivity().getWindow().setStatusBarColor(Color.TRANSPARENT);
        }*/

/*        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WindowManager.LayoutParams localLayoutParams = getActivity().getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }*/

        View statusBar = getView().findViewById(R.id.statusBarView);
        ViewGroup.LayoutParams layoutParams = statusBar.getLayoutParams();
        layoutParams.height = getStatusBarHeight(getActivity());

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
        SlidingTabLayout tabLayout = getActivity().findViewById(R.id.d_tab);
        ViewPager viewPager = getActivity().findViewById(R.id.d_viewpager);

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

        tabLayout.setViewPager(viewPager);
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



