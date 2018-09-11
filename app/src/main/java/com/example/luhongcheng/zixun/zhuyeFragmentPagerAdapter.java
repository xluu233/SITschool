package com.example.luhongcheng.zixun;

import android.app.Fragment;
import android.app.FragmentManager;

import java.util.ArrayList;
import java.util.List;

public class zhuyeFragmentPagerAdapter extends FragmentPagerAdapter {
	//存储所有的fragment
	private List<Fragment> list;
	
	public zhuyeFragmentPagerAdapter(FragmentManager fm, ArrayList<Fragment> list){
		super(fm);
		this.list = list;
	}

	@Override
	public Fragment getItem(int arg0) {
		// TODO Auto-generated method stub
		return list.get(arg0);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

}
