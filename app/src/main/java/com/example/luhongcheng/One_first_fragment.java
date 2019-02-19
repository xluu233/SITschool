package com.example.luhongcheng;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.luhongcheng.Bmob.Tips;
import com.example.luhongcheng.MBox.MBoxItem;
import com.example.luhongcheng.SWZL.swzlmain;
import com.example.luhongcheng.WeiXin.Weixin_more;
import com.example.luhongcheng.zixun.zhuyeDisplayActvivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


@SuppressLint("ValidFragment")
public class One_first_fragment  extends Fragment {


    String souhu_url;
    //天气
    ImageButton weather_icon;
    TextView weather_t1,weather_t2,weather_t3,weather_t4;

    private List<Box> fruitList = new ArrayList<Box>();

    Context mContext;
    Button more,more2;

    ImageButton souhuiv;
    TextView souhutitle,souhusubtitle;

    ImageView swzl_iv;
    TextView swzl_title,swzl_subtitle,swzl_time;


    private OkHttpClient okHttpClient;
    private OkHttpClient.Builder builder;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View v = inflater.inflate(R.layout.a_one_viewpager, container, false);
        return v;
    }


    @Override
    public void onActivityCreated( Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext = getActivity();

        weather_icon = (ImageButton) getActivity().findViewById(R.id.weather_icon);
        weather_t1 = (TextView)getActivity().findViewById(R.id.weather_t1);
        weather_t2 = (TextView)getActivity().findViewById(R.id.weather_t2);
        weather_t3 = (TextView)getActivity().findViewById(R.id.weather_t3);
        weather_t4 = (TextView)getActivity().findViewById(R.id.weather_t4);
        more = (Button) getActivity().findViewById(R.id.more);
        more2 = (Button) getActivity().findViewById(R.id.more2);

        swzl_iv = (ImageView) getActivity().findViewById(R.id.swzl_iv);
        swzl_title = (TextView)getActivity().findViewById(R.id.swzl_title);
        swzl_subtitle = (TextView)getActivity().findViewById(R.id.swzl_subtitle);
        swzl_time = (TextView)getActivity().findViewById(R.id.swzl_time);


        souhuiv = (ImageButton) getActivity().findViewById(R.id.souhu_iv);
        souhutitle = (TextView)getActivity().findViewById(R.id.souhu_title);
        souhusubtitle =(TextView)getActivity().findViewById(R.id.souhu_subtitle);


        registerLoginBroadcast();//注册广播
        initOnClick();
        initSet();

    }
    private void initSet() {
        getsouhu();
        getWeather();
        getSwzl();
    }



    @SuppressLint("ResourceAsColor")
    private void initOnClick() {

        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),zhuyeDisplayActvivity.class);
                intent.putExtra("news_url","https://m.sm.cn/s?q=%E4%B8%8A%E6%B5%B7%E5%A5%89%E8%B4%A4%E5%A4%A9%E6%B0%94&by=submit&snum=6");
                startActivity(intent);
            }
        });
        more2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),Weixin_more.class);
                startActivity(intent);
            }
        });

        souhuiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),SouHuNews.class);
                startActivity(intent);
            }
        });

        souhutitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),SouHuNews.class);
                intent.putExtra("url",souhu_url);
                startActivity(intent);
            }
        });

        souhusubtitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),SouHuNews.class);
                intent.putExtra("url",souhu_url);
                startActivity(intent);
            }
        });

        swzl_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),swzlmain.class);
                startActivity(intent);
            }
        });
        swzl_subtitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),swzlmain.class);
                startActivity(intent);
            }
        });
        swzl_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),swzlmain.class);
                startActivity(intent);
            }
        });


    }


    Bitmap bitmap;
    private void getsouhu() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final OkHttpClient client = new OkHttpClient().newBuilder()
                            .followRedirects(false)//禁止重定向
                            .followSslRedirects(false)//哈哈哈哈哈哈哈好开心啊
                            .build();

                    Request request = new Request.Builder()
                            .url("http://m.sohu.com/media/694346?spm=smwp.content.author-info.1.1537437344995hk1YAuY")
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();

                    Document doc = Jsoup.parse(responseData);
                    Elements url = doc.select("ul.feed-list-area");
                    Element link =  url.select("li").get(0);


                    souhu_url = link.select("a.onePic").attr("href");
                    souhu_url = " http://m.sohu.com"+souhu_url+"&spm=smwp.media.fd-s.1.1537437360311dAYraYh";
                    //System.out.println("文章链接:"+souhu_url.toString());

                    String A2 = link.select("section.onePic__img-area").select("img").attr("original");
                    // System.out.println("图片链接:"+A2.toString());

                    /*
                    if (A2.length() != 0){
                        Glide.with(getContext())
                                .load(A2)
                                .placeholder(R.drawable.loading)
                                .error(R.drawable.error)
                                .fitCenter()
                                .into(souhuiv);
                    }*/

                    URL myFileURL;
                    if (A2.length() !=0){
                        try {
                            myFileURL = new URL(A2);
                            HttpURLConnection conn = (HttpURLConnection) myFileURL.openConnection();
                            conn.setConnectTimeout(3000);
                            conn.setDoInput(true);
                            conn.setUseCaches(false);
                            conn.connect();
                            InputStream is = conn.getInputStream();
                            bitmap = BitmapFactory.decodeStream(is);
                            is.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Message msg = handler.obtainMessage();
                        msg.obj = bitmap;
                        msg.what = 1;
                        handler.sendMessage(msg);
                    }else {
                        getsouhu2();
                    }

                    final String A3 = link.select("article.onePic__content").select("h4.feed__title").text();
                    // System.out.println("标题:"+A3.toString());

                    final String A4 = link.select("article.onePic__content").select("footer.feed__detail").select("span.time").text();
                    //System.out.println("时间:"+A4.toString());

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            souhutitle.setText(A3);
                            souhusubtitle.setText(A4);
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }



    private void getsouhu2() {
        new Thread(new Runnable() {
            @SuppressLint("NewApi")
            @Override
            public void run() {
                try {
                    final OkHttpClient client = new OkHttpClient().newBuilder()
                            .followRedirects(false)//禁止重定向
                            .followSslRedirects(false)//哈哈哈哈哈哈哈好开心啊
                            .build();

                    Request request = new Request.Builder()
                            .url("http://m.sohu.com/media/694346?spm=smwp.content.author-info.1.1537437344995hk1YAuY")
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();

                    Document doc = Jsoup.parse(responseData);
                    Elements url = doc.select("ul.feed-list-area");
                    Element link =  url.select("li").get(0);


                    souhu_url = link.select("a.plainText").attr("href");
                    souhu_url = " http://m.sohu.com"+souhu_url+"?spm=smwp.media.fd-s.1.1547014372361QHJEKjY";
                    //System.out.println("文章链接:"+souhu_url.toString());

                    //http://m.sohu.com/a/287399973_694346&spm=smwp.media.fd-s.1.1537437360311dAYraYh
                    //http://m.sohu.com/a/287399973_694346?spm=smwp.media.fd-s.1.1547014372361QHJEKjY
                    //链接经常会变化

                    final String A3 = link.select("a.plainText").select("h4.feed__title").text();
                    //System.out.println("标题:"+A3.toString());

                    final String A4 = link.select("a.plainText").select("footer.feed__detail").select("span.time").text();
                    //System.out.println("时间:"+A4.toString());

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            souhuiv.setVisibility(View.INVISIBLE);
                            souhutitle.setText(A3);
                            souhusubtitle.setText(A4);
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    souhuiv.setBackgroundResource(0);
                    souhuiv.setImageBitmap(bitmap);
                    break;
            }
        }
    };


    private void getWeather() {
        Thread threadx = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient().newBuilder()
                            .build();

                    Request request = new Request.Builder()
                            .url("https://www.tianqi.com/fengxian/")
                            .build();
                    Response response = client.newCall(request).execute();
                    Document doc = Jsoup.parse(response.body().string());

                    Elements url2 = doc.getElementsByClass("weather");
                    final String t1= url2.select("p").text();
                    final String t2 = url2.select("span").text();

                    getActivity().runOnUiThread(new Runnable() {
                        @SuppressLint("NewApi")
                        @Override
                        public void run() {
                            weather_t1.setText(t1);
                            weather_t2.setText(t2);
                            weather_icon.setBackground(getResources().getDrawable(R.drawable.b1));
                        }
                    });


                    Message message=new Message();
                    String tt = url2.select("span").select("b").text();
                    if (tt.contains("晴")){
                        message.what=0;
                        handler_weather.sendMessage(message);
                    }
                    if (tt.contains("多云")){
                        message.what=1;
                        handler_weather.sendMessage(message);
                    }
                    if (tt.contains("阴")){
                        message.what=2;
                        handler_weather.sendMessage(message);
                    }
                    if (tt.contains("小雨")){
                        message.what=9;
                        handler_weather.sendMessage(message);
                    }
                    if (tt.contains("中雨") || tt.contains("暴雨")){
                        message.what=8;
                        handler_weather.sendMessage(message);
                    }
                    if (tt.contains("雪")){
                        message.what=10;
                        handler_weather.sendMessage(message);
                    }

                    Elements url3 = doc.getElementsByClass("shidu");
                    String t3 = url3.get(0).text();
                    weather_t3.setText(t3);

                    Elements url4 = doc.getElementsByClass("kongqi").select("h5");
                    String t4= url4.text();
                    weather_t4.setText(t4);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        threadx.start();
    }


    @SuppressLint("HandlerLeak")
    Handler handler_weather = new Handler() {
        @SuppressLint("NewApi")
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    weather_icon.setBackgroundResource(0);
                    weather_icon.setBackground(getResources().getDrawable(R.drawable.b0));
                    break;
                case 1:
                    weather_icon.setBackgroundResource(0);
                    weather_icon.setBackground(getResources().getDrawable(R.drawable.b1));
                    break;
                case 2:
                    weather_icon.setBackgroundResource(0);
                    weather_icon.setBackground(getResources().getDrawable(R.drawable.b2));
                    break;
                case 9:
                    weather_icon.setBackgroundResource(0);
                    weather_icon.setBackground(getResources().getDrawable(R.drawable.b7));
                    break;
                case 8:
                    weather_icon.setBackgroundResource(0);
                    weather_icon.setBackground(getResources().getDrawable(R.drawable.b8));
                    break;
                case 10:
                    weather_icon.setBackgroundResource(0);
                    weather_icon.setBackground(getResources().getDrawable(R.drawable.b15));
                    break;
            }
        }
    };


    private void getSwzl() {
        Thread swzl = new Thread(new Runnable() {
            @Override
            public void run() {
                BmobQuery<com.example.luhongcheng.Bmob.SWZL> query = new BmobQuery<com.example.luhongcheng.Bmob.SWZL>();
                query.setLimit(1);
                query.order("-createdAt");//时间降序查询
                query.findObjects(new FindListener<com.example.luhongcheng.Bmob.SWZL>(){
                    @Override
                    public void done(List<com.example.luhongcheng.Bmob.SWZL> list, BmobException e) {
                        if(e==null){
                            String title = list.get(0).getTitle();
                            String subtitle = list.get(0).getContent();
                            String time = list.get(0).getCreatedAt();
                            String image = list.get(0).getimageUrl();

                            if (title.length() != 0){
                                swzl_title.setText(title);
                                swzl_subtitle.setText(subtitle);
                                swzl_time.setText(time);
                                Glide.with(getContext())
                                        .load(image)
                                        .placeholder(R.drawable.loading)
                                        .error(R.drawable.error)
                                        .fitCenter()
                                        .into(swzl_iv);
                            }


                        }else{
                            Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                        }

                    }
                });


            }
        });
        swzl.start();

    }

    /**
     * 自定义广播接受器,用来处理登录广播
     */
    private class LoginBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            //处理我们具体的逻辑,更新UI
            Toast.makeText(mContext,"xiix",Toast.LENGTH_SHORT).show();
            initSet();
        }

    }

    //广播接收器
    private LoginBroadcastReceiver mReceiver = new LoginBroadcastReceiver();

    //注册广播方法
    private void registerLoginBroadcast(){
        IntentFilter intentFilter = new IntentFilter(OneFragment.REFRESH_ACTION);
        LocalBroadcastManager.getInstance(mContext).registerReceiver(mReceiver,intentFilter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(mContext).unregisterReceiver(mReceiver);
    }
}
