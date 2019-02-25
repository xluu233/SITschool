package com.example.luhongcheng;


import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.luhongcheng.Bmob.UserInfo;
import com.example.luhongcheng.DataBase.Person_Data;
import com.example.luhongcheng.FruitItem.Fruit;
import com.example.luhongcheng.FruitItem.FruitAdapter;
import com.example.luhongcheng.MySelf.MyLove;
import com.example.luhongcheng.MySelf.MySs;
import com.example.luhongcheng.OneSelf.Find;
import com.example.luhongcheng.OneSelf.setMy;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

import static cn.bmob.v3.Bmob.getApplicationContext;

public class FourFragment_one extends Fragment {

    TextView nickname,qianming,APPid,xueyuan;
    static ImageView icon;
    String username;
    private List<Fruit> fruitList = new ArrayList<Fruit>();

    TextView a1,a2,bianji;
    TextView find;
    String iconUrl;
    String name;
    String personID;

    List<String> guanzhu = new ArrayList<>();
    List<String> fensi = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.d_one_fragment,container,false);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        cn.bmob.v3.Bmob.initialize(getContext(), "69d2a14bfc1139c1e9af3a9678b0f1ed");

        bianji = (TextView)getActivity().findViewById(R.id.bianji);
        icon = (ImageView) getActivity().findViewById(R.id.myicon);
        nickname = (TextView)getActivity().findViewById(R.id.nickname);
        qianming = (TextView)getActivity().findViewById(R.id.qianming);
        APPid = (TextView)getActivity().findViewById(R.id.APPid);
        xueyuan = (TextView)getActivity().findViewById(R.id.xueyuan);
        a1 = (TextView)getActivity().findViewById(R.id.guanzhu);
        a2 = (TextView)getActivity().findViewById(R.id.fensi);
        find = (TextView)getActivity().findViewById(R.id.find);
        ListView one_list = (ListView) getActivity().findViewById(R.id.oneself_list);

        //getInfoFromDB();
        restoreInfo();
        initFruits();

        FruitAdapter adapter2 = new FruitAdapter(getActivity(), R.layout.fruit_item, fruitList);
        one_list.setAdapter(adapter2);
        one_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Fruit fruit = fruitList.get(position);
                switch (position) {
                    case 0:
                        Intent intent1 = new Intent(getActivity(),MySs.class);
                        intent1.putExtra("icon_url",iconUrl);
                        intent1.putExtra("nickname", String.valueOf(nickname));
                        intent1.putExtra("qm", String.valueOf(qianming));
                        startActivity(intent1);
                        break;
                    case 1:
                        Intent intent2 = new Intent(getActivity(),MyLove.class);
                        startActivity(intent2);
                        break;
                    case 2:
                        //Intent intent3 = new Intent(OneSelf.this,MySs.class);
                        //startActivity(intent3);
                        break;
                    case 3:
                        //Intent intent4 = new Intent(OneSelf.this,MySs.class);
                        //startActivity(intent4);
                        break;
                    case 4:
                        // Intent intent5 = new Intent(OneSelf.this,MySs.class);
                        //startActivity(intent5);
                        break;
                    default:
                        break;


                }
            }
        });


        bianji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),setMy.class);
                startActivity(intent);
            }
        });


        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Find.class);
                startActivity(intent);
            }
        });


    }


    //读取个人信息
    private void restoreInfo(){
        SharedPreferences sp=getActivity().getSharedPreferences("userid",0);
        username = sp.getString("username",""); //学号

        BmobQuery<UserInfo> query = new BmobQuery<UserInfo>();
        query.addWhereEqualTo("ID", username);
        query.findObjects(new FindListener<UserInfo>() {
            @Override
            public void done(List<UserInfo> object, BmobException e) {
                if(e==null){
                    for (UserInfo xixi : object) {

                        name =  xixi.getName();
                        personID = xixi.getObjectId();

                        if (personID != null){
                            APPid.setText(personID);
                        }

                        name = name.replace("姓名：","");

                        String nick_name = xixi.getNickname();
                        if (nick_name != null){
                            nickname.setText(nick_name);
                        }else {
                            nickname.setText("你好，"+name);
                        }

                        String qm = xixi.getQM();
                        if (qm != null){
                            qianming.setText(qm);
                        }


                        //保存用户的ID
                        SharedPreferences.Editor editor=getActivity().getSharedPreferences("personID",0).edit();
                        editor.putString("ID",personID);
                        editor.commit();

                        String xy = xixi.getXueyuan();
                        if (xy != null){
                            xueyuan.setText(xy);
                        }

                        iconUrl = xixi.geticonUrl();
                        if (iconUrl != null){
                            postUrl(iconUrl);
                        }



                    }
                }else{
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });


        BmobQuery<UserInfo> query2 = new BmobQuery<UserInfo>();
        query2.addWhereEqualTo("ID", username);
        query2.findObjects(new FindListener<UserInfo>() {
            @Override
            public void done(List<UserInfo> object, BmobException e) {
                if(e==null){
                    for (UserInfo xixi : object) {

                        guanzhu = xixi.getGuanzhu();
                        fensi = xixi.getFensi();

                        a1.setText("关注："+guanzhu.size());
                        a2.setText("粉丝："+fensi.size());
                        //Toast.makeText(getApplicationContext(),"关注："+guanzhu.size()+"粉丝："+fensi.size(),Toast.LENGTH_LONG).show();


                    }
                }else{
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });




    }



    private void postUrl(final String iconUrl) {
        new Thread() {
            public void run() {
                bitmap = getHttpBitmap(iconUrl);
                Message msg = handler.obtainMessage();
                msg.obj = bitmap;
                msg.what = 1;
                handler.sendMessage(msg);
                //SaveDataToDB();
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

    private static Bitmap bitmap;
    private static Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    icon.setBackgroundResource(0);//清楚imageview的背景，还有一种方法是setImageDrawable(null);
                    icon.setImageBitmap(bitmap);
                    icon.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    break;
            }
        }
    };

    private  void SaveDataToDB() {
        Person_Data person_data = new Person_Data();
        person_data.setNickname(String.valueOf(nickname));
        person_data.setPersonID(personID);
        person_data.setQianming(String.valueOf(qianming));
        person_data.setName(name);
        person_data.setXueyuan(String.valueOf(xueyuan));
        person_data.setIcon(bitmap);
        person_data.save();
    }

    private void getInfoFromDB() {
        Person_Data person_data2 = new Person_Data();
        String A1 = person_data2.getNickname();
        if (A1 !=null){
            nickname.setText(A1);
        }

        qianming.setText(person_data2.getQianming());
        APPid.setText(person_data2.getPersonID());
        xueyuan.setText(person_data2.getXueyuan());
        icon.setImageBitmap(person_data2.getIcon());
    }

    private void initFruits() {
        Fruit pear = new Fruit("我的说说", R.drawable.personcenter);
        fruitList.add(pear);
        Fruit apple = new Fruit("好友列表", R.drawable.like);
        fruitList.add(apple);

    }


}
