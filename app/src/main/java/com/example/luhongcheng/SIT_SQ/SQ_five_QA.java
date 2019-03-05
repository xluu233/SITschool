package com.example.luhongcheng.SIT_SQ;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.luhongcheng.MainFragmentActivity;
import com.example.luhongcheng.MainFragment_One.OneFragment;
import com.example.luhongcheng.R;
import com.example.luhongcheng.SIT_SQ_other.Add_QA;
import com.github.clans.fab.FloatingActionButton;


public class SQ_five_QA extends Fragment {

    public SQ_five_QA(){
        Context mContext = getActivity();
    }

    public static SQ_five_QA newInstance(Context context) {
        Context mContext = context;
        return new SQ_five_QA();
    }

    RecyclerView recyclerView;
    FloatingActionButton button;
    SwipeRefreshLayout refreshLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View v = inflater.inflate(R.layout.sq_five_qa, container, false);
        return v;
    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        button = getActivity().findViewById(R.id.qa_update);
        recyclerView = getActivity().findViewById(R.id.qa_recycler);
        refreshLayout = getActivity().findViewById(R.id.qa_refresh);



        onClick();


    }

    private void onClick() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sp=getActivity().getSharedPreferences("personID",0);
                String personID =  sp.getString("ID","");

                if (personID.length() == 0){
                    Toast.makeText(getActivity(),"没有获取到ID",Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent = new Intent(getContext(), Add_QA.class);
                    startActivity(intent);
                }

            }
        });
    }


}
