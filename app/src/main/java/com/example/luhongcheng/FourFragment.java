package com.example.luhongcheng;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.support.v4.app.Fragment;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.luhongcheng.FruitItem.Fruit;
import com.example.luhongcheng.FruitItem.FruitAdapter;
import com.example.luhongcheng.about.about0;
import com.example.luhongcheng.about.about1;
import com.example.luhongcheng.about.about2;
import com.example.luhongcheng.about.about3;
import com.example.luhongcheng.about.about4;
import com.example.luhongcheng.utils.APKVersionCodeUtils;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import static cn.bmob.v3.Bmob.getApplicationContext;

/**
 * Created by Administrator on 2018/4/8.
 */

public class FourFragment extends Fragment {
    private String context;
    TextView vv;
    ViewGroup container;
    Button RedBag;

    private List<Fruit> fruitList = new ArrayList<Fruit>();

    @SuppressLint("ValidFragment")
    public FourFragment(String context){
        this.context = context;
    }


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

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onActivityCreated( Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        container = (ViewGroup) getActivity().findViewById(R.id.container);
        //设置版本号
        vv = (TextView) getActivity().findViewById(R.id.version);
        //@SuppressLint({"NewApi", "LocalSuppress"}) String versionName = APKVersionCodeUtils.getVerName(this.getContext());
        String versionName = APKVersionCodeUtils.getVerName(getActivity());
        vv.setText("V" + versionName);
        RedBag = (Button)getActivity().findViewById(R.id.RedBag);
        RedBag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager cmb = (ClipboardManager)getApplicationContext().getSystemService(Context.CLIPBOARD_SERVICE);
                cmb.setText("540942228");
                Toast.makeText(getApplicationContext(),"已复制到剪切板,在搜索框中搜索即可",Toast.LENGTH_LONG).show();
                Intent intent = getApplicationContext().getPackageManager() .getLaunchIntentForPackage("com.eg.android.AlipayGphone");
                startActivity(intent);
            }
        });

        initFruits(); // 初始化水果数据
        ListView listView = (ListView) getView().findViewById(R.id.list_view);
        FruitAdapter adapter2 = new FruitAdapter(getActivity(), R.layout.fruit_item, fruitList);
        listView.setAdapter(adapter2);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Fruit fruit = fruitList.get(position);
                //Toast.makeText(getActivity(), fruit.getImageId(), Toast.LENGTH_SHORT).show();
                switch (position) {
                    case 0:
                        //版本介绍
                        Intent intent = new Intent(getActivity(), about0.class);
                        startActivity(intent);
                        break;
                    case 1:
                        //转到QQ群
                        Intent intent1 = new Intent(getActivity(), about1.class);
                        startActivity(intent1);
                        break;
                    case 2:
                        //检查更新
                        Intent intent2 = new Intent(getActivity(), about2.class);
                        startActivity(intent2);
                        break;
                    case 3:
                        Intent intent3 = new Intent(getActivity(), about3.class);
                        startActivity(intent3);
                        break;
                    case 4:
                        Intent intent4 = new Intent(getActivity(), about4.class);
                        startActivity(intent4);
                        break;
                    default:
                        break;


                }
            }
        });

        Button clear = (Button) getView().findViewById(R.id.clear);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = getActivity().getSharedPreferences("userid", 0).edit();
                editor.clear().commit();

                SharedPreferences.Editor editor2 = getActivity().getSharedPreferences("personID", 0).edit();
                editor2.clear().commit();

                Toast.makeText(getActivity(), "账号信息已清除", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getActivity(),LoginActivity.class);
                startActivity(intent);
            }
        });
        bindView();

    }

    private void initFruits() {
        Fruit apple = new Fruit("功能介绍", R.drawable.jieshao);
        fruitList.add(apple);
        Fruit banana = new Fruit("加入team", R.drawable.team);
        fruitList.add(banana);
        Fruit orange = new Fruit("检查更新", R.drawable.update);
        fruitList.add(orange);
        Fruit watermelon = new Fruit("反馈帮助", R.drawable.fankui);
        fruitList.add(watermelon);
        Fruit pear = new Fruit("捐赠开发者", R.drawable.juanzeng);
        fruitList.add(pear);
    }


    private void bindView() {
        final ImageView share = (ImageView) getView().findViewById(R.id.shareapp) ;
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, "SITschool上应大学生助手集成OA系统部分查询及资讯功能，可在Android端实现查询成绩，查询电费，查询第二课堂，查询考试安排等等一系列功能，快来下载吧：https://www.coolapk.com/apk/187672");
                intent.setType("text/plain");
                startActivity(Intent.createChooser(intent, "分享到"));

            }
        });
    }




}



