package com.example.luhongcheng;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.luhongcheng.Bmob.update;
import com.example.luhongcheng.utils.APKVersionCodeUtils;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;


public class MainFragmentActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView A;
    private ImageView B;
    private ImageView C;
    private ImageView D;
    TextView A1;
    TextView B1;
    TextView C1;
    TextView D1;

    private FragmentManager fragmentManager;
    private OneFragment f1;
    private TwoFragment f2;
    private ThreeFragment f3;
    private FourFragment f4;


    private String url;
    private String code1;
    private String text;

    public int code;
    FragmentTransaction transaction;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main_fragment_activity);
        bindView();
        Bmob.initialize(this, "69d2a14bfc1139c1e9af3a9678b0f1ed");
        querySingleData();


        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_PHONE_STATE, Manifest.permission.INTERNET}, 0);
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.red_300));
        }

        startOne();
    }



    //查询单条数据
    public void querySingleData() {
        BmobQuery<update> bmobQuery = new BmobQuery<update>();
        bmobQuery.getObject("MF4j000B", new QueryListener<update>() {
            @Override
            public void done(update object, BmobException e) {
                if (e == null) {
                    url = object.getapkUrl();
                    code1 = object.getCode();
                    text = object.getText();
                    //System.out.println("APK更新地址：" + url);
                    //System.out.println("版本号：" + code1);
                    //System.out.println("更新内容" + text);
                    check(code1);
                } else {
                    //Log.i("bmob图片", "失败：" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }

    //判断版本大小
    public void check(String code1) {
        code = APKVersionCodeUtils.getVersionCode(this);
        int i = Integer.valueOf(this.code1).intValue();
        if (i > code) {
            showDialog();
        }
    }

    private void showDialog() {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setIcon(R.drawable.ic_launcher)//设置标题的图片
                .setTitle("检查到新版本")//设置对话框的标题
                .setMessage(text)//设置对话框的内容
                //设置对话框的按钮
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Toast.makeText(MainActivity.this, "点击了取消按钮", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("更新", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent();
                        intent.setData(Uri.parse(url));//Url 就是你要打开的网址
                        intent.setAction(Intent.ACTION_VIEW);
                        startActivity(intent); //启动浏览器
                        dialog.dismiss();
                    }
                }).create();
        dialog.show();
    }


    //UI组件初始化与事件绑定
    private void bindView() {
        A = (ImageView) this.findViewById(R.id.A);
        B = (ImageView) this.findViewById(R.id.B);
        C = (ImageView) this.findViewById(R.id.C);
        D = (ImageView) this.findViewById(R.id.D);
        A1 = (TextView) this.findViewById(R.id.A1);
        B1 = (TextView) this.findViewById(R.id.B1);
        C1 = (TextView) this.findViewById(R.id.C1);
        D1 = (TextView) this.findViewById(R.id.D1);

        A.setOnClickListener(this);
        B.setOnClickListener(this);
        C.setOnClickListener(this);
        D.setOnClickListener(this);
    }

    //重置所有文本的选中状态
    public void selected(){
        A.setSelected(false);
        B.setSelected(false);
        C.setSelected(false);
        D.setSelected(false);

        A1.setTextColor(getResources().getColor(R.color.black_primary));
        B1.setTextColor(getResources().getColor(R.color.black_primary));
        C1.setTextColor(getResources().getColor(R.color.black_primary));
        D1.setTextColor(getResources().getColor(R.color.black_primary));
    }


    //隐藏所有Fragment
    public void hideAllFragment(FragmentTransaction transaction){
        if(f1!=null){
            transaction.hide(f1);
        }
        if(f2!=null){
            transaction.hide(f2);
        }
        if(f3!=null){
            transaction.hide(f3);
        }
        if(f4!=null){
            transaction.hide(f4);
        }
    }

    private void startOne() {
        transaction = getFragmentManager().beginTransaction();
        hideAllFragment(transaction);
        selected();
        A.setSelected(true);
        A1.setTextColor(getResources().getColor(R.color.red_300));
        if(f1==null){
            f1 = new OneFragment("第一个Fragment");
            transaction.add(R.id.fragment_container,f1);
        }else{
            transaction.show(f1);
        }
        transaction.commit();
    }


    @Override
    public void onClick(View v) {
        transaction = getFragmentManager().beginTransaction();
        hideAllFragment(transaction);
        switch(v.getId()){
            case R.id.A:
                selected();
                A.setSelected(true);
                A1.setTextColor(getResources().getColor(R.color.red_300));
                if(f1==null){
                    f1 = new OneFragment("第一个Fragment");
                    transaction.add(R.id.fragment_container,f1);
                }else{
                    transaction.show(f1);
                }
                break;

            case R.id.B:
                selected();
                B.setSelected(true);
                B1.setTextColor(getResources().getColor(R.color.red_300));
                if(f2==null){
                    f2 = new TwoFragment("第二个Fragment");
                    transaction.add(R.id.fragment_container,f2);
                }else{
                    transaction.show(f2);
                }
                break;

            case R.id.C:
                selected();
                C.setSelected(true);
                C1.setTextColor(getResources().getColor(R.color.red_300));
                if(f3==null){
                    f3 = new ThreeFragment("第三个Fragment");
                    transaction.add(R.id.fragment_container,f3);
                }else{
                    transaction.show(f3);
                }
                break;

            case R.id.D:
                selected();
                D.setSelected(true);
                D1.setTextColor(getResources().getColor(R.color.red_300));
                if(f4==null){
                    f4 = new FourFragment("第四个Fragment");
                    transaction.add(R.id.fragment_container,f4);
                }else{
                    transaction.show(f4);
                }
                break;
        }
        transaction.commit();
    }

    // 用来计算返回键的点击间隔时间
    private long exitTime = 0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                //弹出提示，可以有多种方式
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}
