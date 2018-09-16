package com.example.luhongcheng.SWZL;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.luhongcheng.R;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.miui.zeus.mimo.sdk.ad.AdWorkerFactory;
import com.miui.zeus.mimo.sdk.ad.IAdWorker;
import com.miui.zeus.mimo.sdk.listener.MimoAdListener;
import com.xiaomi.ad.common.pojo.AdType;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;


public class FirstFragment extends Fragment {
    ListView listView;
    public static final String TAG = "AD-StandardNewsFeed";
    private static final String[] POSITION_ID = {"2cae1a1f63f60185630f78a1d63923b0","0c220d9bf7029e71461f247485696d07", "b38f454156852941f3883c736c79e7e1"};
    private IAdWorker mAdWorker;
    ViewGroup container;

    private static Bitmap bitmap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View v = inflater.inflate(R.layout.swzl_first, container,false);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        listView = (ListView) getActivity().findViewById(R.id.listView);
        Bmob.initialize(getActivity(), "69d2a14bfc1139c1e9af3a9678b0f1ed");
        get();

        final FloatingActionMenu fab = (FloatingActionMenu) getActivity().findViewById(R.id.fab);
        fab.setClosedOnTouchOutside(true);

        container = (ViewGroup)getActivity().findViewById(R.id.container);


        FloatingActionButton add = (FloatingActionButton) getActivity().findViewById(R.id.fab_share);
        FloatingActionButton refresh = (FloatingActionButton)getActivity(). findViewById(R.id.fab_preview);
        FloatingActionButton diudiu = (FloatingActionButton)getActivity(). findViewById(R.id.diudiu);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(getActivity(),send.class);
                startActivity(intent1);
            }
        });

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get();
            }
        });
        diudiu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(getActivity(),send2.class);
                startActivity(intent1);
            }
        });


        //xiaomiSDK
        try {
            mAdWorker = AdWorkerFactory.getAdWorker(getActivity(), container, new MimoAdListener() {
                @Override
                public void onAdPresent() {
                    Log.e(TAG, "onAdPresent");
                }

                @Override
                public void onAdClick() {
                    Log.e(TAG, "onAdClick");
                }

                @Override
                public void onAdDismissed() {
                    Log.e(TAG, "onAdDismissed");
                }

                @Override
                public void onAdFailed(String s) {
                    Log.e(TAG, "onAdFailed");
                }

                @Override
                public void onAdLoaded(int size) {
                    showAD();
                }

                @Override
                public void onStimulateSuccess() {
                }
            }, AdType.AD_STANDARD_NEWSFEED);

        } catch (Exception e) {
            e.printStackTrace();
        }
        loadAD();
        //广告完了


    }

    public void get(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                BmobQuery<com.example.luhongcheng.Bmob.SWZL> query = new BmobQuery<com.example.luhongcheng.Bmob.SWZL>();
                query.findObjects(new FindListener<com.example.luhongcheng.Bmob.SWZL>(){
                    @Override
                    public void done(List<com.example.luhongcheng.Bmob.SWZL> list, BmobException e) {
                        List<com.example.luhongcheng.Bmob.SWZL> lists = new ArrayList<>();
                        if (list != null) {
                            //System.out.println("查询成功"+list.get(0).getTitle()+list.get(0).getContent()+list.get(0).getTime()+list.get(0).getAdress()+list.get(0).getIconUrl());
                            final String[] title  =  new String[list.size()];
                            final String[] content  =  new String[list.size()];
                            final String[] time  =  new String[list.size()];
                            final String[] adress  =  new String[list.size()];
                            final String[] createtime = new String[list.size()];
                            final String[] image = new String[list.size()];
                            for(int i = 0;i<list.size();i++){
                                title[i] = list.get(i).getTitle();
                                content[i] = list.get(i).getContent();
                                time[i] = list.get(i).getTime();
                                adress[i] = list.get(i).getAdress();
                                createtime[i] = list.get(i).getUpdatedAt();
                                image[i] = list.get(i).getimageUrl();
                                Log.d("imageURL",list.get(i).getimageUrl());

                            }


                            class MyAdapter extends BaseAdapter {
                                private FindListener<com.example.luhongcheng.Bmob.SWZL> context ;
                                public MyAdapter(FindListener<com.example.luhongcheng.Bmob.SWZL> context){
                                    this.context = context;
                                }

                                @Override
                                public int getCount() {
                                    return title.length;
                                }

                                @Override
                                public Object getItem(int position) {
                                    return title[position];
                                }

                                @Override
                                public long getItemId(int position) {
                                    return position;
                                }

                                @Override
                                public View getView(int position, View convertView, ViewGroup parent) {
                                    ViewHolder viewHolder;
                                    if (convertView == null){
                                        LayoutInflater inflater = LayoutInflater.from(getContext());
                                        convertView = inflater.inflate(R.layout.swzl_first_item, null);//实例化一个布局文件
                                        viewHolder = new ViewHolder();
                                        viewHolder.tv_title = (TextView)convertView.findViewById(R.id.tv_title);
                                        viewHolder.tv_content = (TextView)convertView.findViewById(R.id.tv_content);
                                        viewHolder.tv_time = (TextView)convertView.findViewById(R.id.tv_time);
                                        viewHolder.tv_adress = (TextView)convertView.findViewById(R.id.tv_adress);
                                        viewHolder.create_time = (TextView) convertView.findViewById(R.id.create_time);

                                        convertView.setTag(viewHolder);
                                    }else {
                                        viewHolder = (ViewHolder) convertView.getTag();
                                    }
                                    viewHolder.tv_title.setText(title[position]);
                                    viewHolder.tv_content.setText("内容："+content[position]);
                                    viewHolder.tv_time.setText("时间地点："+time[position]);
                                    viewHolder.tv_adress.setText("联系方式："+adress[position]);
                                    viewHolder.create_time.setText("发布时间："+createtime[position]);

                                    return convertView;
                                }
                                class ViewHolder{
                                    TextView tv_title;
                                    TextView tv_content;
                                    TextView tv_time;
                                    TextView tv_adress;
                                    TextView create_time;
                                }
                            }
                            listView.setAdapter(new MyAdapter(this));
                        }


                    }
                });
            }
        }); //声明一个子线程
        thread.start();


    }


    private void loadAD() {
        try {
            mAdWorker.recycle();
            mAdWorker.load("b38f454156852941f3883c736c79e7e1");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showAD() {
        try{
            container.addView(mAdWorker.updateAdView(null, 0));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            mAdWorker.recycle();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //根据图片的url地址得到图片
    public void getImage(final String path){
        new Thread() {
            public void run() {
                bitmap = getHttpBitmap(path);
                Message msg = handler.obtainMessage();
                msg.obj = bitmap;
                msg.what = 1;
                handler.sendMessage(msg);
            };
        }.start();
    }

    public static Bitmap getHttpBitmap(String url) {
        URL myFileURL;
        try {
            myFileURL = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) myFileURL.openConnection();
            conn.setConnectTimeout(6000);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    private static Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    break;
            }
        }
    };


}
