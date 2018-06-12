package com.example.luhongcheng;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.luhongcheng.about.about0;
import com.example.luhongcheng.about.about1;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;

/**
 * Created by Administrator on 2018/4/8.
 */

public class FourFragment extends Fragment {
    private String context;
    public FourFragment(String context){
        this.context = context;
    }



    private String[] data = {
            "功能介绍",
            "加入team",
            "检查更新",
            "反馈帮助",
            "捐赠开发者"
    };

    //打包问题，在这里加入无参构造函数

    public FourFragment() {
        Context mContext = getActivity();
    }

    public static FourFragment newInstance(Context context) {
        Context mContext = context;
        return new FourFragment ();
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.d_fragment,container,false);

        return view;
    }

    @Override
    public void onActivityCreated( Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,data);



        ListView listView = (ListView) getView().findViewById(R.id.list_view);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                switch (position) {
                    case 0:
                        //版本介绍
                        Intent intent=new Intent(getActivity(), about0.class);
                        startActivity(intent);
                        break;
                    case 1:
                        //转到QQ群
                        Intent intent1=new Intent(getActivity(),about1.class);
                        startActivity(intent1);
                        break;
                    case 2:
                        //检查更新
                        Intent intent2 = new Intent();
                        intent2.setData(Uri.parse("https://www.coolapk.com/apk/187672"));//Url 就是你要打开的网址
                        intent2.setAction(Intent.ACTION_VIEW);
                        startActivity(intent2); //启动浏览器
                        break;
                    case 3:
                       /* Intent intent3=new Intent(item13.this, about3.class);
                        startActivity(intent3);*/
                        Toast.makeText(getActivity(),"此功能未开放", Toast.LENGTH_SHORT).show();
                        break;
                    case 4:
                       /* Intent intent4=new Intent(item13.this, about4.class);
                        startActivity(intent4);   */
                        Toast.makeText(getActivity(),"赞赏未开放，可以到应用商店给个好评哟", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;


                }
            }
        });

        Button clear = (Button) getView().findViewById(R.id.clear) ;
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor=getActivity().getSharedPreferences("userid",0).edit();

                editor.clear().commit();

                Toast.makeText(getActivity(), "账号信息已清除", Toast.LENGTH_SHORT).show();
            }
        });
        bindView();

    }


    private void bindView() {
        ImageView share = (ImageView) getView().findViewById(R.id.shareapp) ;
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, "SITschool上应大学生助手集成OA系统部分查询及资讯功能，可在Android端实现查询成绩，查询电费，查询第二课堂，查询考试安排等等一系列功能，目前在酷安已发布，快来下载吧：https://www.coolapk.com/apk/187672");
                intent.setType("text/plain");
                startActivity(Intent.createChooser(intent, "分享到"));
            }
        });
    }


    //分享单张图片至所有第三方软件
    public class ShareSingleImage implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            String imagePath = Environment.getExternalStorageDirectory() + File.separator + "p2pviewcam/screenshot/NGLSPP-000000-MYEJY_2017_06_15_10_31_50_CH_4.jpg";
            //由文件得到uri
            Uri imageUri = Uri.fromFile(new File(imagePath));

            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
            shareIntent.setType("image/*");
            startActivity(Intent.createChooser(shareIntent, "分享到"));
        }
    }

    public class about {
        private String name;
        private int  imageId;
        public about(String name,int imageId){
            this.name = name;
            this.imageId = imageId;
        }
        public String getName(){
            return  name;
        }
        public int getImageId(){
            return imageId;
        }
    }



}
