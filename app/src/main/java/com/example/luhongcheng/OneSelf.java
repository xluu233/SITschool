package com.example.luhongcheng;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.luhongcheng.Bmob.UserInfo;
import com.example.luhongcheng.DataBase.Person_Data;
import com.example.luhongcheng.FruitItem.Fruit;
import com.example.luhongcheng.FruitItem.OneselfListAdapter;
import com.example.luhongcheng.MySelf.MyLove;
import com.example.luhongcheng.MySelf.MySs;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class OneSelf extends AppCompatActivity {
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

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.oneself);

        bianji = (TextView)findViewById(R.id.bianji);
        cn.bmob.v3.Bmob.initialize(this, "69d2a14bfc1139c1e9af3a9678b0f1ed");
        icon = (ImageView) findViewById(R.id.myicon);
        nickname = (TextView)findViewById(R.id.nickname);
        qianming = (TextView)findViewById(R.id.qianming);
        APPid = (TextView)findViewById(R.id.APPid);
        xueyuan = (TextView)findViewById(R.id.xueyuan);
        a1 = (TextView)findViewById(R.id.guanzhu);
        a2 = (TextView)findViewById(R.id.fensi);
        find = (TextView)findViewById(R.id.find);
        getInfoFromDB();
        restoreInfo();
        initFruits();
        ListView listView = (ListView) findViewById(R.id.list_view);
        OneselfListAdapter adapter2 = new OneselfListAdapter(getApplicationContext(), R.layout.oneself_list_item, fruitList);
        listView.setAdapter(adapter2);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Fruit fruit = fruitList.get(position);
                //Toast.makeText(getActivity(), fruit.getImageId(), Toast.LENGTH_SHORT).show();
                switch (position) {
                    case 0:
                        Intent intent1 = new Intent(OneSelf.this,MySs.class);
                        intent1.putExtra("icon_url",iconUrl);
                        intent1.putExtra("nickname", String.valueOf(nickname));
                        intent1.putExtra("qm", String.valueOf(qianming));
                        startActivity(intent1);
                        break;
                    case 1:
                        Intent intent2 = new Intent(OneSelf.this,MyLove.class);
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

        icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        bianji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OneSelf.this,setMy.class);
                startActivity(intent);
            }
        });

        TopToBottomFinishLayout bottomFinishLayout = (TopToBottomFinishLayout) findViewById(R.id.OneSelf);
        bottomFinishLayout.setOnFinishListener(new TopToBottomFinishLayout.OnFinishListener() {
            @Override
            public void onFinish() {
                finish();
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        /* //toolbar设置返回键
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.teal_300));//设置状态栏背景色
        }

        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Find.class);
                startActivity(intent);
            }
        });

    }

    public void CloseOneSelf(View view){
        finish();
    }



    private void getInfoFromDB() {
        Person_Data person_data2 = new Person_Data();
        String A1 = person_data2.getNickname();
        String A2 = person_data2.getName();
        if (A1 ==null){
            nickname.setText(A2);
        }else {
            nickname.setText(A1);
        }

        qianming.setText(person_data2.getQianming());
        APPid.setText(person_data2.getPersonID());
        xueyuan.setText(person_data2.getXueyuan());
        icon.setImageBitmap(person_data2.getIcon());
    }


    //读取个人信息
    private void restoreInfo(){
        SharedPreferences sp=getSharedPreferences("userid",0);
        username = sp.getString("username","");

        BmobQuery<UserInfo> query = new BmobQuery<UserInfo>();
        query.addWhereEqualTo("ID", username);
        query.findObjects(new FindListener<UserInfo>() {
            @Override
            public void done(List<UserInfo> object, BmobException e) {
                if(e==null){
                    for (UserInfo xixi : object) {

                        name =  xixi.getName();
                        personID = xixi.getObjectId();

                        name = name.replace("姓名：","");

                        nickname.setText(xixi.getNickname());
                        if (nickname.length() == 0){
                            nickname.setText("你好，"+name);
                        }

                        qianming.setText(xixi.getQM());
                        APPid.setText("ID:"+xixi.getObjectId());


                        //保存用户的ID
                        SharedPreferences.Editor editor=getSharedPreferences("personID",0).edit();
                        editor.putString("ID",xixi.getObjectId());
                        editor.commit();

                        xueyuan.setText(xixi.getXueyuan());
                        iconUrl = xixi.geticonUrl();
                        //System.out.println("头像链接"+iconUrl);

                        /*
                        if (iconUrl.length() == 0){
                            iconUrl = xixi.getLink();
                        }
                        */

                        postUrl(iconUrl);


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
                SaveDataToDB();
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


    private void initFruits() {
        Fruit pear = new Fruit("我的说说", R.drawable.personcenter);
        fruitList.add(pear);
        Fruit apple = new Fruit("好友列表", R.drawable.like);
        fruitList.add(apple);

    }





}
