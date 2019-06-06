package com.example.luhongcheng;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.luhongcheng.Bmob_bean.LoginTimes;
import com.example.luhongcheng.Bmob_bean.update;
import com.example.luhongcheng.MainFragment_One.OneFragment;
import com.example.luhongcheng.MainFragment_four.FourFragment;
import com.example.luhongcheng.MainFragment_three.TwoFragment;
import com.example.luhongcheng.MainFragment_two.SheQuFragment;
import com.example.luhongcheng.utils.APKVersionCodeUtils;
import com.example.luhongcheng.utils.DeviceUtil;

import java.util.Objects;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;

public class MainFragmentActivity extends AppCompatActivity{


    private String url;
    private String code1;
    private String text;
    public int code;

    private OneFragment fragment1;
    private TwoFragment fragment2;
    private FourFragment fragment3;
    private SheQuFragment fragment4;
    private Fragment[]      fragments;
    private int             lastShowFragment = 0;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.navigation_home:
                   // Toast.makeText(MainFragmentActivity.this,"第一个",Toast.LENGTH_SHORT).show();
                    if (lastShowFragment != 0) {
                        switchFrament(lastShowFragment, 0);
                        lastShowFragment = 0;
                    }

                    return true;
                case R.id.navigation_dashboard:
                   // Toast.makeText(MainFragmentActivity.this,"第二个",Toast.LENGTH_SHORT).show();
                    if (lastShowFragment != 1) {
                        switchFrament(lastShowFragment, 1);
                        lastShowFragment = 1;
                    }
                    return true;
                case R.id.navigation_notifications:
                   // Toast.makeText(MainFragmentActivity.this,"第三个",Toast.LENGTH_SHORT).show();
                    if (lastShowFragment != 2) {
                        switchFrament(lastShowFragment,2);
                        lastShowFragment = 2;
                    }
                    return true;
                case R.id.navigation_shequ:
                    // Toast.makeText(MainFragmentActivity.this,"第四个",Toast.LENGTH_SHORT).show();
                    if (lastShowFragment != 3) {
                        switchFrament(lastShowFragment,3);
                        lastShowFragment = 3;
                    }
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main_fragment_activity);
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
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorAccent));
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        initFragments();

        android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragment_container, fragments[0]);
        transaction.show(fragments[0]).commitAllowingStateLoss();
        //switchFrament(lastShowFragment,0);

        putLoginInfo();
    }

    private void putLoginInfo() {
        //将用户信息及设备信息上传
        SharedPreferences sp= getSharedPreferences("userid",0);
        String username = sp.getString("username","");

        if (username.length()!=0){
            LoginTimes loginTimes = new LoginTimes();
            loginTimes.setUser(username);
            loginTimes.setAndroidVersion(DeviceUtil.getBuildVersion());
            loginTimes.setAPI(String.valueOf(DeviceUtil.getBuildLevel()));
            loginTimes.setDevices_id(DeviceUtil.getDeviceId(getApplicationContext()));
            loginTimes.setPhone(DeviceUtil.getPhoneBrand());
            loginTimes.setPhoneModel(DeviceUtil.getPhoneModel());
            loginTimes.setAppVersion(DeviceUtil.getVersionName(getApplicationContext()));
            loginTimes.save(new SaveListener<String>() {
                @Override
                public void done(String s, BmobException e) {
                    if (e==null){
                        System.out.println("用户登录信息上传成功");
                    }
                }
            });
        }
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public void switchFrament(int lastIndex, int index) {
        android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.hide(fragments[lastIndex]);
        if (!fragments[index].isAdded()) {
            transaction.add(R.id.fragment_container, fragments[index]);
        }
        transaction.show(fragments[index]).commitAllowingStateLoss();
    }

    private void initFragments() {
        fragment1 = new OneFragment();
        fragment2 = new TwoFragment();
        fragment3 = new FourFragment();
        fragment4 = new SheQuFragment();
        fragments = new Fragment[]{fragment1, fragment2, fragment3,fragment4};
        lastShowFragment = 0;
      //  getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, fragment1).show(fragment1).commit();
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
        code = APKVersionCodeUtils.getVersionCode(this);    //当前版本号
        int i = Integer.valueOf(this.code1).intValue();     //最新版本
        if (i>code){
            showDialog();
        }else {
            shareAPP();
        }
        /*if (i >code && i-code<=20) {
            showDialog();
        }else if (i-code >=30){
            showDialog2();
        }else {
            shareAPP();
        }*/
    }

    private void shareAPP() {
        SharedPreferences sp=getSharedPreferences("share",0);
        String id = sp.getString("id","");
        if (id.length() == 0){
            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setIcon(R.drawable.ic_launcher)//设置标题的图片
                    .setTitle("分享")//设置对话框的标题
                    .setMessage("开发不易，分享给更多人吧!")//设置对话框的内容
                    //设置对话框的按钮
                    .setNegativeButton("不愿意", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //Toast.makeText(MainActivity.this, "点击了取消按钮", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    })
                    .setPositiveButton("分享", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent();
                            intent.setAction(Intent.ACTION_SEND);
                            intent.putExtra(Intent.EXTRA_TEXT, "SITschool上应大学生助手集成OA系统部分查询及资讯功能，可在Android端实现查询成绩，查询电费，查询第二课堂，查询考试安排等等一系列功能，快来下载吧：https://www.coolapk.com/apk/187672");
                            intent.setType("text/plain");
                            startActivity(Intent.createChooser(intent, "分享到"));

                            SharedPreferences.Editor editor=getSharedPreferences("share",0).edit();
                            editor.putString("id","11");
                            editor.commit();

                            dialog.dismiss();
                        }
                    }).create();
            dialog.setCancelable(false);
            dialog.show();
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
                        finish();
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
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

   /* private void showDialog2() {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setIcon(R.drawable.ic_launcher)//设置标题的图片
                .setTitle("你已经太久没更新了哦")//设置对话框的标题
                .setMessage(text)//设置对话框的内容
                //设置对话框的按钮
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Toast.makeText(MainActivity.this, "点击了取消按钮", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        finish();
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
*/


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
