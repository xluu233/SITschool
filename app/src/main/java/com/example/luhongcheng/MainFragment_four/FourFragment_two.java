package com.example.luhongcheng.MainFragment_four;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.luhongcheng.R;
import com.example.luhongcheng.bean.Fruit;
import com.example.luhongcheng.Adapter.FruitAdapter;
import com.example.luhongcheng.Login.LoginActivity;
import com.example.luhongcheng.setting.about0;
import com.example.luhongcheng.setting.about1;
import com.example.luhongcheng.setting.about2;
import com.example.luhongcheng.setting.about3;
import com.example.luhongcheng.setting.about4;
import com.example.luhongcheng.utils.APKVersionCodeUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/4/8.
 */

public class FourFragment_two extends Fragment {
    private String context;
    TextView vv;
    ViewGroup container;

    private List<Fruit> fruitList = new ArrayList<Fruit>();

    @SuppressLint("ValidFragment")
    public FourFragment_two(String context){
        this.context = context;
    }

    //打包问题，在这里加入无参构造函数
    public FourFragment_two() {
        Context mContext = getActivity();
    }
    public static FourFragment_two newInstance(Context context) {
        Context mContext = context;
        return new FourFragment_two();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.d_two_fragment,container,false);
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
/*                        Intent intent1 = new Intent(getActivity(), about1.class);
                        startActivity(intent1);*/
                        joinQQGroup("ztsdUJHGWN-PjRprGf3p9HbWjQz2yAGo");
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
                    case 5:
                        good_zan();
                        //goToMarket(getContext(),"com.example.luhongcheng");
                        break;
                    case 6:
                        get_out();
                        break;
                    default:
                        break;


                }
            }
        });

        bindView();

    }

    private void good_zan() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        //intent.setClassName(PACKAGE_COOL_MARKET, "com.coolapk.market.activity.AppViewActivity");
        intent.setData(Uri.parse("market://details?id=" + getActivity().getPackageName()));
        startActivity(intent);
    }

    //酷市场 -- 酷安网
    public static final String PACKAGE_COOL_MARKET = "com.coolapk.market";
    //小米应用商店
    public static final String PACKAGE_MI_MARKET = "com.xiaomi.market";
    //豌豆荚
    public static final String PACKAGE_WANDOUJIA_MARKET = "com.wandoujia.phoenix2";


    public static void goToMarket(Context context, String packageName) {
        Uri uri = Uri.parse("market://details?id=" + packageName);
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        try {
            goToMarket.setClassName(PACKAGE_COOL_MARKET, "com.coolapk.market.activity.AppViewActivity");
            context.startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }



    private void get_out(){
        //学号姓名
        SharedPreferences.Editor editor = getActivity().getSharedPreferences("userid", 0).edit();
        editor.clear().commit();

        //Userinfo表
        SharedPreferences.Editor editor2 = getActivity().getSharedPreferences("personID", 0).edit();
        editor2.clear().commit();

        //_User表
        SharedPreferences.Editor editor3=getActivity().getSharedPreferences("User_ID",0).edit();
        editor3.clear().commit();

        //学院姓名
        SharedPreferences.Editor editor4=getActivity().getSharedPreferences("nameid",0).edit();
        editor4.clear().commit();

        Toast.makeText(getActivity(), "账号信息已清除", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(getActivity(),LoginActivity.class);
        startActivity(intent);
        getActivity().finish();
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
        Fruit zan = new Fruit("给个好评",R.drawable.good_zan);
        fruitList.add(zan);
        Fruit out = new Fruit("退出登录",R.drawable.get_out);
        fruitList.add(out);

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


    /****************
     *
     * 发起添加群流程。群号：SITschool学生助手(740771842) 的 key 为： ztsdUJHGWN-PjRprGf3p9HbWjQz2yAGo
     * 调用 joinQQGroup(ztsdUJHGWN-PjRprGf3p9HbWjQz2yAGo) 即可发起手Q客户端申请加群 SITschool学生助手(740771842)
     *
     * @param key 由官网生成的key
     * @return 返回true表示呼起手Q成功，返回fals表示呼起失败
     ******************/
    public boolean joinQQGroup(String key) {
        Intent intent = new Intent();
        intent.setData(Uri.parse("mqqopensdkapi://bizAgent/qm/qr?url=http%3A%2F%2Fqm.qq.com%2Fcgi-bin%2Fqm%2Fqr%3Ffrom%3Dapp%26p%3Dandroid%26k%3D" + key));
        // 此Flag可根据具体产品需要自定义，如设置，则在加群界面按返回，返回手Q主界面，不设置，按返回会返回到呼起产品界面    //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        try {
            startActivity(intent);
            return true;
        } catch (Exception e) {
            Toast.makeText(getActivity(),"no QQ",Toast.LENGTH_SHORT).show();
            return false;
        }
    }

}



