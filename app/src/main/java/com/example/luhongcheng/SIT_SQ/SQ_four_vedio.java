package com.example.luhongcheng.SIT_SQ;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.luhongcheng.MainFragment_One.OneFragment;
import com.example.luhongcheng.R;


public class SQ_four_vedio extends Fragment {
    public  SQ_four_vedio(){
        Context mContext = getActivity();
    }

    public static  SQ_four_vedio newInstance(Context context) {
        Context mContext = context;
        return new  SQ_four_vedio();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View v = inflater.inflate(R.layout.sq_four_vedio, container, false);
        return v;
    }


    @SuppressLint("ResourceAsColor")
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }


}
