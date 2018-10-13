package com.example.luhongcheng.SQ;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.example.luhongcheng.Bmob.SS;
import com.example.luhongcheng.Bmob.UserInfo;
import com.example.luhongcheng.Box;
import com.example.luhongcheng.ImageFullDisplay;
import com.example.luhongcheng.MBox.MBoxItem;
import com.example.luhongcheng.MoreBox;
import com.example.luhongcheng.R;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;

public class TwoFragment extends Fragment {
    public static TwoFragment newInstance() {
        return new TwoFragment();
    }
    private ListView lv;
    private List<SSS> slist;
    @SuppressLint("HandlerLeak")
    private Handler mHandler2 = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    // SSAdaper adapter = new SSAdaper(slist);
                    lv.setAdapter(new TwoFragment.SSAdaper(slist));
                    //setListViewHeightBasedOnChildren(lv);

                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            SSS news = slist.get(position);
                            Intent intent = new Intent(getActivity(),ssDisPlay.class);
                            intent.putExtra("objectID",news.getSsID());
                            intent.putExtra("personID",news.getPersonID());

                            intent.putExtra("iconUrl",news.getIconUrl());
                            intent.putExtra("imageUrl",news.getImageUrl());
                            intent.putExtra("content",news.getTitle());
                            intent.putExtra("nickname",news.getNickname());
                            intent.putExtra("qm",news.getQm());
                            intent.putExtra("label",news.getLabel());
                            startActivity(intent);

                        }
                    });
                    break;
                default:
                    break;
            }
        }

    };


    public static void setListViewHeightBasedOnChildren(ListView listView) {
        if(listView == null) return;
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        // totalHeight  =listAdapter.getCount()*250;
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight +(listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View v = inflater.inflate(R.layout.sq_fragment_two, container, false);
        return v;
    }

    FloatingActionButton add ;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        add = (FloatingActionButton) getActivity().findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),SS_ADD.class);
                startActivity(intent);
            }
        });
        lv = (ListView) getActivity().findViewById(R.id.ss_listView);
        initData();

        RefreshLayout refreshLayout = (RefreshLayout)getActivity().findViewById(R.id.refreshLayout2);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                Page =1;
                slist.clear();
                lv.setAdapter(new TwoFragment.SSAdaper(slist));
                initData();
                refreshlayout.finishRefresh(1000/*,false*/);//传入false表示刷新失败
            }
        });

        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                Page = Page +1;
                //  lv.setAdapter(new SSAdaper(slist));
                initData();

                refreshlayout.finishLoadMore(2000/*,false*/);//传入false表示加载失败
            }
        });

    }


    int i =0;
    String personID;
    String ssID2,label2;
    String time2;
    int Page = 1;//页数
    private void initData() {
        slist = new ArrayList<SSS>();

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                BmobQuery<SS> query = new BmobQuery<com.example.luhongcheng.Bmob.SS>();
                query.order("-createdAt");//时间降序查询
                query.setLimit(15*Page);//查询前20条数据
                query.setSkip(15*(Page-1));//分页查询

                query.findObjects(new FindListener<SS>(){
                    @Override
                    public void done(final List<com.example.luhongcheng.Bmob.SS> list, BmobException e) {
                        List<com.example.luhongcheng.Bmob.SS> lists = new ArrayList<>();
                        if (list != null) {
                            final String[] content  =  new String[list.size()];
                            final String[] image = new String[list.size()];
                            String[] ID = new  String[list.size()];
                            final  String[]  ssID= new String[list.size()];
                            final  String[]  label= new String[list.size()];
                            final  String[] time = new String[list.size()];

                            int n = list.size()+1;

                            for(i = 0;i<list.size();i++){
                                content[i] = list.get(i).getContent();
                                image[i] = list.get(i).getimgUrl();
                                ID[i] = list.get(i).getID();
                                ssID[i] = list.get(i).getObjectId();
                                label[i] = list.get(i).getLabel();
                                time[i] = list.get(i).getCreatedAt();

                                String image2 = image[i];
                                String content2 = content[i];
                                personID = ID[i];
                                ssID2 = ssID[i];
                                label2 = label[i];
                                time2 = time[i];
                                getssuerinfo(n,image2,content2,personID,ssID2,label2,time2);

                            }

                        }

                    }


                });

            }
        });
        thread2.start();




    }

    private void getssuerinfo(final int n , final String img, final String content, final String personID, final String ssID2,final String label2,final String time2) {

        final String[] nickname = new String[n];
        final String[] qm = new String[n];
        final String[] icon = new String[n];

        BmobQuery<UserInfo> query = new BmobQuery<UserInfo>();
        query.getObject(personID, new QueryListener<UserInfo>() {
            @Override
            public void done(UserInfo object, BmobException e) {
                if(e==null){
                    nickname[i] = object.getNickname();
                    qm[i] = object.getQM();
                    icon[i] = object.geticonUrl();
                    if (qm[i] == null){
                        qm[i] = "这个人很懒，什么都没有";
                    }

                    if (nickname[i] == null){
                        nickname[i] = "无名人";
                    }

                }else{
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }

                slist.add(new SSS(img,content,icon[i],qm[i],nickname[i],personID,ssID2,label2,time2));
                mHandler2.obtainMessage(0).sendToTarget();

            }
        });


    }

    public class SSAdaper extends BaseAdapter {
        private List<SSS> list;
        public SSAdaper(List<SSS> list) {
            super();
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if(convertView==null){
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.ss_listview_item, null);
                holder = new ViewHolder();
                holder.img = (ImageView) convertView.findViewById(R.id.img);
                holder.icon = (ImageView) convertView.findViewById(R.id.icon);
                holder.title = (TextView) convertView.findViewById(R.id.content);
                holder.nk = (TextView) convertView.findViewById(R.id.nickname);
                holder.qmm = (TextView) convertView.findViewById(R.id.qm);
                holder.tv_label = (TextView) convertView.findViewById(R.id.label);
                convertView.setTag(holder);
            }else{
                holder = (ViewHolder) convertView.getTag();
            }
            final SSS news = list.get(position);
            holder.title.setText(news.getTitle());
            holder.nk.setText(news.getNickname());
            holder.qmm.setText(news.getQm());

            holder.img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(),ImageFullDisplay.class);
                    intent.putExtra("url2",news.getImageUrl());
                    startActivity(intent);
                }
            });

            holder.icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(),ImageFullDisplay.class);
                    intent.putExtra("url2",news.getIconUrl());
                    startActivity(intent);
                }
            });


            String label = news.getLabel();
            if (label != null){
                if (label.equals("A1")){
                    holder.tv_label.setText("#今日最佳#");
                }else if (label.equals("A2")){
                    holder.tv_label.setText("#一日三餐#");
                }else if (label.equals("A3")){
                    holder.tv_label.setText("#表白墙#");
                } else if (label.equals("A4")){
                    holder.tv_label.setText("#众话说#");
                } else if (label.equals("A5")){
                    holder.tv_label.setText("#工具推荐#");
                } else if (label.equals("A6")){
                    holder.tv_label.setText("#学习交流#");
                } else if (label.equals("A7")){
                    holder.tv_label.setText("#安利#");
                } else if (label.equals("A8")){
                    holder.tv_label.setText("#需求池#");
                } else if (label.equals("A9")){
                    holder.tv_label.setText("#考研党#");
                } else if (label.equals("A10")){
                    holder.tv_label.setText("#周边推荐#");
                } else if (label.equals("A11")){
                    holder.tv_label.setText("#每日一听#");
                }else if (label.equals("A12")){
                    holder.tv_label.setText("#晨读打卡#");
                } else if (label.equals("A13")){
                    holder.tv_label.setText("#谈天说地#");
                }
            }

            Glide.with(getContext())
                    .load(news.getImageUrl())
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.error)
                    .override(600, 200)
                    .into(holder.img);


            Glide.with(getContext())
                    .load(news.getIconUrl())
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.error)
                    .fitCenter()
                    .into(holder.icon);

            return convertView;
        }

        class ViewHolder {
            ImageView img,icon;
            TextView title,nk,qmm,tv_label;
        }

    }






}
