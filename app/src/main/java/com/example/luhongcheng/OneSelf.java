package com.example.luhongcheng;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.luhongcheng.Bmob.Bmob;
import com.example.luhongcheng.Bmob.LOGO;
import com.example.luhongcheng.Bmob._User;
import com.example.luhongcheng.FruitItem.Fruit;
import com.example.luhongcheng.FruitItem.FruitAdapter;
import com.example.luhongcheng.FruitItem.OneselfListAdapter;
import com.example.luhongcheng.about.about0;
import com.example.luhongcheng.about.about1;
import com.example.luhongcheng.about.about2;
import com.example.luhongcheng.about.about3;
import com.example.luhongcheng.about.about4;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;

import static com.example.luhongcheng.StartFlash.getHttpBitmap;

public class OneSelf extends Activity{
    Button bianji;
    TextView nickname,qianming,APPid,xueyuan;
    static ImageButton icon;
    String username;
    private List<Fruit> fruitList = new ArrayList<Fruit>();

    String QQtext;
    String Weibotext;
    String Weixintext;
    String iconUrl;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.oneself);
        bianji = (Button)findViewById(R.id.bianji);
        cn.bmob.v3.Bmob.initialize(this, "69d2a14bfc1139c1e9af3a9678b0f1ed");
        icon = (ImageButton) findViewById(R.id.myicon);
        nickname = (TextView)findViewById(R.id.nickname);
        qianming = (TextView)findViewById(R.id.qianming);
        APPid = (TextView)findViewById(R.id.APPid);
        xueyuan = (TextView)findViewById(R.id.xueyuan);
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
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
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


    }


    public void close(View view){
        this.finish();
    }

    //读取个人信息
    private void restoreInfo(){
        SharedPreferences sp=getSharedPreferences("userid",0);
        username = sp.getString("username","");


        BmobQuery<_User> query = new BmobQuery<_User>();
        query.addWhereEqualTo("username", username);
        query.findObjects(new FindListener<_User>() {
            @Override
            public void done(List<_User> object, BmobException e) {
                if(e==null){
                    for (_User xixi : object) {
                        nickname.setText(xixi.getNickname());
                        qianming.setText(xixi.getQianming());
                        APPid.setText("ID:"+xixi.getObjectId());
                        //保存用户的ID
                        SharedPreferences.Editor editor=getSharedPreferences("personID",0).edit();
                        editor.putString("ID",xixi.getObjectId());
                        editor.commit();

                        xueyuan.setText(xixi.getXueyuan());
                        iconUrl = xixi.geticonUrl();
                        //System.out.println("头像链接"+iconUrl);
                        postUrl(iconUrl);


                    }
                }else{
                    //Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
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


    private void initFruits() {
        Fruit pear = new Fruit("个人中心", R.drawable.personcenter);
        fruitList.add(pear);
        Fruit apple = new Fruit("我喜欢的", R.drawable.like);
        fruitList.add(apple);
        Fruit banana = new Fruit("QQ", R.drawable.qq,QQtext);
        fruitList.add(banana);
        Fruit orange = new Fruit("微博", R.drawable.weibo,Weibotext);
        fruitList.add(orange);
        Fruit watermelon = new Fruit("微信", R.drawable.weixin,Weixintext);
        fruitList.add(watermelon);

    }





}
