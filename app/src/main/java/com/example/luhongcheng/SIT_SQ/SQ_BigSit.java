package com.example.luhongcheng.SIT_SQ;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.luhongcheng.Adapter.SQ_Adapter;
import com.example.luhongcheng.Bmob_bean.Report;
import com.example.luhongcheng.Bmob_bean.SQ;
import com.example.luhongcheng.Bmob_bean.UserInfo;
import com.example.luhongcheng.LazyLoadFragment;
import com.example.luhongcheng.R;
import com.example.luhongcheng.SIT_SQ_other.Add_SQ;
import com.example.luhongcheng.SIT_SQ_other.SQ_SecondLayout;
import com.example.luhongcheng.utils.ItemClickSupport;
import com.example.luhongcheng.utils.getRAM;
import com.github.clans.fab.FloatingActionButton;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;



public class SQ_BigSit extends LazyLoadFragment {
    public SQ_BigSit(){}
    public static SQ_BigSit newInstance() {
        return new SQ_BigSit();
    }


    RecyclerView recyclerView;
    FloatingActionButton button;
    SmartRefreshLayout refreshLayout;
    LinearLayout loading;
    ImageView image_load;


    private List<com.example.luhongcheng.bean.SQ> mList = new ArrayList<>();
    private List<String> url = new ArrayList<>();

    private List<String> my_collection = new ArrayList<>();//我的收藏集合
    private List<String> my_Likes = new ArrayList<>(); //我的喜欢合集
    //private List<String> my_GuanZhu = new ArrayList<>();//我的关注

    String person_id;
    String author_id;
    //String nickname,qm,icon_url;

    String content;
    String time;
    String item_id;

    boolean layoutInit = false;
    boolean canfresh = true;

/*
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.sq_bigsit, container, false);
        return v;
    }
*/

    @Override
    protected int setContentView() {
        return R.layout.sq_bigsit;
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void lazyLoad() {
        String message = "FragmentSQ" + (isInit ? "已经初始并已经显示给用户可以加载数据" : "没有初始化不能加载数据")+">>>>>>>>>>>>>>>>>>>";
        Log.d(TAG, message);

        SharedPreferences sp= Objects.requireNonNull(getActivity()).getSharedPreferences("personID",0);
        person_id =  sp.getString("ID","");
        if (mList.size() == 0){
            get_MyCollection();
        }
    }



    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @SuppressLint({"ClickableViewAccessibility", "ResourceAsColor"})
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        button = Objects.requireNonNull(getActivity()).findViewById(R.id.sit_add_news);
        recyclerView = getActivity().findViewById(R.id.sit_recycler);
        refreshLayout = getActivity().findViewById(R.id.sit_refresh);
        loading = getActivity().findViewById(R.id.bigsit_loading);
        image_load = getActivity().findViewById(R.id.bigsit_image_loading);
        RequestOptions options = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        Glide.with(getContext()).load(R.drawable.loading2).apply(options).into(image_load);


        layoutInit = true;
        onClick();
        //get_MyCollection();

    }

    private void onClick() {
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull final RefreshLayout refreshlayout) {
                if (canfresh){
                    new Thread(new Runnable() {
                        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                        @Override
                        public void run() {
                            getDate();
                            canfresh = false;
                            try {
                                Thread.sleep(1000);
                                refreshlayout.finishRefresh(2000/*,false*/);
                                Thread.sleep(15000);
                                canfresh = true;
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }


                        }
                    }).start();
                }else {
                    Toast.makeText(getContext(),"太快了~10s后再试",Toast.LENGTH_SHORT).show();
                    refreshlayout.finishRefresh(2000/*,false*/);
                }


            }
        });

/*        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                //getDate2();
                refreshlayout.finishLoadMore(2000*//*,false*//*);//传入false表示加载失败
                Snackbar.make(getView(),"只支持加载20条数据",Toast.LENGTH_SHORT).show();
            }
        });*/


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (person_id.length() == 0){
                    Toast.makeText(getActivity(),"没有获取到ID,请前往个人中心",Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent = new Intent(getContext(), Add_SQ.class);
                    startActivity(intent);
                }

            }
        });

    /*    refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
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
        });*/
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void get_MyCollection() {
        if (person_id.length() == 0){
            //Toast.makeText(getActivity(),"没有获取到ID",Toast.LENGTH_SHORT).show();
            SharedPreferences sp= Objects.requireNonNull(getActivity()).getSharedPreferences("userid",0);
            final String username = sp.getString("username","");

            Thread collection = new Thread(new Runnable() {
                @Override
                public void run() {
                    BmobQuery<UserInfo> query2 = new BmobQuery<>();
                    if (username != null) {
                        query2.addWhereContains("ID",username);
                    }
                    query2.findObjects(new FindListener<UserInfo>() {
                        @Override
                        public void done(List<UserInfo> list, BmobException e) {
                            if (e == null) {
                                person_id = list.get(0).getObjectId();

                                SharedPreferences.Editor editor= Objects.requireNonNull(getActivity()).getSharedPreferences("personID",0).edit();
                                editor.putString("ID",person_id);
                                editor.apply();

/*                                if (list.get(0).getMy_Collection() != null){
                                    my_collection = list.get(0).getMy_Collection();
                                }

                                if (list.get(0).getMy_Likes() != null){
                                    my_Likes = list.get(0).getMy_Likes();
                                }*/

                                my_collection = list.get(0).getMy_Collection();
                                my_Likes = list.get(0).getMy_Likes();
                                //my_GuanZhu = list.get(0).getGuanzhu();

                                getDate();

                                Log.d("QA：","get_MyCollection()");
                            } else {
                                Log.i("bmob图片", "失败：" + e.getMessage() + "," + e.getErrorCode());
                            }
                        }
                    });

                }
            });
            collection.start();

        }else {
            Thread collection = new Thread(new Runnable() {
                @Override
                public void run() {
                    BmobQuery<UserInfo> query2 = new BmobQuery<>();
                    query2.getObject(person_id, new QueryListener<UserInfo>() {
                        @Override
                        public void done(UserInfo object, BmobException e) {
                            if (e == null) {

                                if (object.getMy_Collection() != null){
                                    my_collection.addAll(object.getMy_Collection());
                                }

                                if (object.getMy_Likes() != null){
                                    my_Likes = object.getMy_Likes();
                                }

                                getDate();

                                Log.d("QA：","get_MyCollection()");
                            } else {
                                Log.i("bmob图片", "失败：" + e.getMessage() + "," + e.getErrorCode());
                            }
                        }
                    });
                }
            });
            collection.start();
        }
    }


    //加载条数
    int loadnum = 10;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void getDate() {
        mList.clear();
        String ram = getRAM.showRAMInfo(Objects.requireNonNull(getContext())).replace(",","");
        if (ram.length() != 0){
            if (Integer.valueOf(ram)>5000){
                loadnum = 20;
            }else if (Integer.valueOf(ram)>7000){
                loadnum = 30;
            }
        }
        Thread qa = new Thread(new Runnable() {
            @Override
            public void run() {
                BmobQuery<SQ> query = new BmobQuery<>();
                query.order("-createdAt");
                //query.setSkip(10);
                query.setLimit(loadnum);
                query.findObjects(new FindListener<SQ>(){
                    @Override
                    public void done(final List<SQ> list, BmobException e) {
                        if (list != null) {

                            for(int i = 0;i<list.size();i++){
                                content = list.get(i).getContent();
                                time = list.get(i).getCreatedAt();
                                item_id = list.get(i).getObjectId();
                                url = list.get(i).getImage();
                                UserInfo userInfo = list.get(i).getAuthor();
                                author_id = userInfo.getObjectId();

                                mList.add(new com.example.luhongcheng.bean.SQ(author_id,url,content,time,item_id,my_Likes,null,null));
                            }
                            if  (layoutInit){
                                loading.setVisibility(View.INVISIBLE);
                                Message msg = new Message();
                                msg.what = 1;
                                handler.sendMessage(msg);
                            }

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
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                recyclerView.setLayoutManager(mLayoutManager);

                SQ_Adapter mAdapter = new SQ_Adapter(getContext(),mList);
                recyclerView.setAdapter(mAdapter);


                ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        //Toast.makeText(getContext(),"短按了一下",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getContext(), SQ_SecondLayout.class);
                        intent.putExtra("from","SQ");
                        intent.putExtra("item_id",mList.get(position).getItem_id());
                        intent.putExtra("author_id",mList.get(position).getAuthor_id());
                        startActivity(intent);

                    }
                });


                ItemClickSupport.addTo(recyclerView).setOnItemLongClickListener(new ItemClickSupport.OnItemLongClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public boolean onItemLongClicked(RecyclerView recyclerView, final int position, View v) {
                        Vibrator vibrator = (Vibrator) Objects.requireNonNull(getActivity()).getSystemService(Context.VIBRATOR_SERVICE);
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
                                        collection_item(mList.get(position).getItem_id());
                                        break;
                                    case 1:
                                        //举报
                                        report_item(mList.get(position).getItem_id());
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
                    }
                }
            });
        }else {
            Toast.makeText(getContext(),"你已经收藏过了",Toast.LENGTH_SHORT).show();
        }



    }




}
