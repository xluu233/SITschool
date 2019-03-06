package com.example.luhongcheng.SIT_SQ;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.luhongcheng.Adapter.NineGridTest2Adapter;
import com.example.luhongcheng.Adapter.OAdapter;
import com.example.luhongcheng.Bmob_bean.QA;
import com.example.luhongcheng.Bmob_bean.news;
import com.example.luhongcheng.MainFragmentActivity;
import com.example.luhongcheng.MainFragment_One.OneFragment;
import com.example.luhongcheng.OA.OADisplayActvivity;
import com.example.luhongcheng.R;
import com.example.luhongcheng.SIT_SQ_other.Add_QA;
import com.example.luhongcheng.SQ.SSS;
import com.example.luhongcheng.bean.OA;
import com.example.luhongcheng.bean.SQ_QA;
import com.example.luhongcheng.utils.ItemClickSupport;
import com.github.clans.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;


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

    int the_load_num = 0; //加载次数


    private RecyclerView.LayoutManager mLayoutManager;
    private NineGridTest2Adapter mAdapter;
    private List<SQ_QA> mList = new ArrayList<>();
    private List<String> url = new ArrayList<>();

    LinearLayout status_layout;

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


    @SuppressLint({"ClickableViewAccessibility", "ResourceAsColor"})
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        button = getActivity().findViewById(R.id.qa_update);
        recyclerView = getActivity().findViewById(R.id.qa_recycler);
        refreshLayout = getActivity().findViewById(R.id.qa_refresh);
        refreshLayout.setColorSchemeColors(R.color.colorAccent);
        status_layout = getActivity().findViewById(R.id.status);

        onClick();
        getDate();




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

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        //加载数据
                        getDate();
                        //关闭刷新
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                refreshLayout.setRefreshing(false);
                            }
                        });
                    }
                }).start();


            }
        });
    }

    private void getDate() {
        mList.clear();
        Thread qa = new Thread(new Runnable() {
            @Override
            public void run() {
                BmobQuery<QA> query = new BmobQuery<QA>();
                query.order("-createdAt");
                query.setLimit(20);
                query.findObjects(new FindListener<QA>(){
                    @Override
                    public void done(final List<QA> list, BmobException e) {
                        if (list != null) {

                            String title;
                            String content;
                            String time;
                            String user_id;

                            for(int i = 0;i<list.size();i++){
                                title = list.get(i).getTitle();
                                content = list.get(i).getContent();
                                time = list.get(i).getCreatedAt();
                                user_id = list.get(i).getUser_id();

                                url = list.get(i).getImage();
                                mList.add(new SQ_QA(url,title,content,time));
                            }
                            Message msg = new Message();
                            msg.what = 1;
                            handler.sendMessage(msg);

                        }

                    }

                });


            }
        });
        qa.start();

    }

    @SuppressLint("HandlerLeak")
    private Handler handler= new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == 1){
                status_layout.setVisibility(View.INVISIBLE);
                mLayoutManager = new LinearLayoutManager(getActivity());
                recyclerView.setLayoutManager(mLayoutManager);

                mAdapter = new NineGridTest2Adapter(getContext(),mList);
                recyclerView.setAdapter(mAdapter);


                ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        Toast.makeText(getContext(),"短按了一下",Toast.LENGTH_SHORT).show();
                    }
                });

                ItemClickSupport.addTo(recyclerView).setOnItemLongClickListener(new ItemClickSupport.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClicked(RecyclerView recyclerView, int position, View v) {
                        Vibrator vibrator = (Vibrator)getActivity().getSystemService(getActivity().VIBRATOR_SERVICE);
                        vibrator.vibrate(50);

                        /*连续震动
                        Vibrator vibrator = (Vibrator)this.getSystemService(this.VIBRATOR_SERVICE);
                        long[] patter = {1000, 1000, 2000, 50};
                        vibrator.vibrate(patter, 0);
                        vibrator.cancel();*/

                        Toast.makeText(getContext(),"长按了一下",Toast.LENGTH_SHORT).show();
                        return false;
                    }
                });

                /*recyclerView.setOnClickListener(new AdapterView.OnItemClickListener());
                recyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        SQ_QA item = mList.get(position);
                        Intent intent = new Intent(getActivity(), OADisplayActvivity.class);
                        intent.putExtra("news_url",news.getA2());
                        startActivity(intent);
                        //Intent intent = new Intent(MainActivity.this,NewsDisplayActvivity.class);
                        //intent.putExtra("news_url",news.getNewsUrl());
                        //startActivity(intent);

                        //Intent intent2 = new Intent(MainActivity.this,NewsDisplayActvivity.class);
                        //intent2.putExtra("COOKIE",str);
                        //startActivity(intent2);
                        //此处不能传递COOKIE，可能会混淆
                    }
                });*/
            }
        }
    };

}
