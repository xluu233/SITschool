package com.example.luhongcheng.SIT_SQ;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.luhongcheng.Adapter.NineGridTest2Adapter;
import com.example.luhongcheng.Bmob_bean.QA;
import com.example.luhongcheng.Bmob_bean.Report;
import com.example.luhongcheng.Bmob_bean.UserInfo;
import com.example.luhongcheng.R;
import com.example.luhongcheng.SIT_SQ_other.Add_QA;
import com.example.luhongcheng.SIT_SQ_other.SQ_SecondLayout;
import com.example.luhongcheng.bean.SQ_QA;
import com.example.luhongcheng.utils.ItemClickSupport;
import com.github.clans.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;


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

    private List<String> my_collection = new ArrayList<>();//我的收藏集合
    private List<String> my_Likes = new ArrayList<>(); //我的喜欢合集

    LinearLayout status_layout;
    String person_id;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sp=getActivity().getSharedPreferences("personID",0);
        person_id =  sp.getString("ID","");
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
        //initRefresh();//刷新事件
        get_MyCollection();
    }

    private void initRefresh() {
        getDate();

    }

    private void get_MyCollection() {
        if (person_id.length() == 0){
            Toast.makeText(getActivity(),"没有获取到ID",Toast.LENGTH_SHORT).show();
        }else {
            Thread collection = new Thread(new Runnable() {
                @Override
                public void run() {
                    BmobQuery<UserInfo> query2 = new BmobQuery<UserInfo>();
                    query2.getObject(person_id, new QueryListener<UserInfo>() {
                        @Override
                        public void done(UserInfo object, BmobException e) {
                            if (e == null) {

                                my_collection.addAll(object.getMy_Collection());
                                my_Likes = object.getMy_Likes();
                                getDate();
                            } else {
                                //Log.i("bmob图片", "失败：" + e.getMessage() + "," + e.getErrorCode());
                            }
                        }
                    });
                }
            });
            collection.start();
        }
    }


    private void onClick() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (person_id.length() == 0){
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
                        get_MyCollection();
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
                            String id;

                            for(int i = 0;i<list.size();i++){
                                title = list.get(i).getTitle();
                                content = list.get(i).getContent();
                                time = list.get(i).getCreatedAt();
                                id = list.get(i).getObjectId();

                                url = list.get(i).getImage();
                                mList.add(new SQ_QA(url,title,content,time,id,my_Likes));
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
                        //Toast.makeText(getContext(),"短按了一下",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getContext(), SQ_SecondLayout.class);
                        intent.putExtra("item_id",mList.get(position).getId());
                        intent.putExtra("user_id",person_id);
                        intent.putExtra("title",mList.get(position).getTitle());
                        intent.putExtra("content",mList.get(position).getContent());
                        startActivity(intent);

                    }
                });

                ItemClickSupport.addTo(recyclerView).setOnItemLongClickListener(new ItemClickSupport.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClicked(RecyclerView recyclerView, final int position, View v) {
                        Vibrator vibrator = (Vibrator)getActivity().getSystemService(getActivity().VIBRATOR_SERVICE);
                        vibrator.vibrate(50);

                        /*连续震动
                        Vibrator vibrator = (Vibrator)this.getSystemService(this.VIBRATOR_SERVICE);
                        long[] patter = {1000, 1000, 2000, 50};
                        vibrator.vibrate(patter, 0);
                        vibrator.cancel();*/

                        final String[] items = {"收藏","举报"};

                        final AlertDialog.Builder listDialog = new AlertDialog.Builder(getContext());
                        listDialog.setItems(items, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which){
                                    case 0:
                                        //收藏
                                        collection_item(mList.get(position).getId());
                                        break;
                                    case 1:
                                        //举报
                                        report_item(mList.get(position).getId());
                                        break;
                                }
                            }
                        });
                        listDialog.show();

                        //Toast.makeText(getContext(),"长按了一下",Toast.LENGTH_SHORT).show();
                        return false;
                    }
                });


            }
        }
    };

    private void report_item(final String id) {

        final EditText et = new EditText(getContext());
        new AlertDialog.Builder(getContext()).setTitle("举报")
                .setIcon(R.drawable.report)
                .setView(et)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String input = et.getText().toString();
                        if (input.equals("")) {
                            Toast.makeText(getContext(), "内容不能为空！" + input, Toast.LENGTH_LONG).show();
                        }
                        else {
                            Report report = new Report();
                            report.setItem_id(id);
                            report.setTitle(input);
                            report.setUser_id(person_id);
                            report.save(new SaveListener<String>() {
                                @Override
                                public void done(String s, BmobException e) {
                                    if (e==null){
                                        Toast.makeText(getActivity(),"举报成功",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }
                })
                .setNegativeButton("取消", null)
                .show();


    }

    private void collection_item(String id) {
        if (!my_collection.contains(id)){
            my_collection.add(id);

            UserInfo p2 = new UserInfo();
            p2.setValue("My_Collection",my_collection);
            p2.update(person_id, new UpdateListener() {
                @Override
                public void done(BmobException e) {
                    if(e==null){
                        Toast.makeText(getContext(),"收藏成功",Toast.LENGTH_SHORT).show();
                    }else{
                        // Toast.makeText(mContext,"error"+e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }else {
            Toast.makeText(getContext(),"你已经收藏过了",Toast.LENGTH_SHORT).show();
        }



    }

}
