package com.example.luhongcheng;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.luhongcheng.UI.JellyInterpolator;

/**
 * Created by alex233 on 2018/4/21.
 */



public class startactivity extends AppCompatActivity implements View.OnClickListener {

    private EditText username,password;
    private TextView main_btn_login,main_btn_nologin;
    private View progress;
    private View mInputLayout;
    private float mWidth, mHeight;
    private LinearLayout mName, mPsw;
    String usernameid;
    String passwordid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main_startflash);
        main_btn_login = (TextView) findViewById(R.id.main_btn_login);
        main_btn_nologin=(TextView) findViewById(R.id.main_btn_nologin);
        progress = findViewById(R.id.layout_progress);
        mInputLayout = findViewById(R.id.input_layout);
        username=(EditText)findViewById(R.id.username);
        password=(EditText)findViewById(R.id.password);
        //initView();

        restoreInfo();
        test();

        //为login设置点击事件
        //这里需要改进，login导致动画没了
        main_btn_login.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View v) {
               //Intent intent = new Intent(startactivity.this, JellyInterpolator.class);
                usernameid = username.getText().toString();
                passwordid = password.getText().toString();
                if(usernameid.length()==0 & passwordid.length()==0){
                    //判断账号密码长度
                    Toast.makeText(startactivity.this,"请输入学号和密码",Toast.LENGTH_SHORT).show();
                }
                else if (usernameid.length()==10 & passwordid.length()>=4){
                    memInfo(usernameid,passwordid);
                    Intent intent = new Intent(startactivity.this, MainActivity.class);
                    //设置startactivity.java为第一启动项，点击login传入mainactivity.java
                    startActivity(intent);
                }
                else{
                    Toast.makeText(startactivity.this,"格式错误，请重试",Toast.LENGTH_SHORT).show();
                }
            }
        });
        //无密码登录
        main_btn_nologin.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(startactivity.this, JellyInterpolator.class);
                Intent intent2 = new Intent(startactivity.this, MainActivity.class);

                //设置startactivity.java为第一启动项，点击login传入mainactivity.java
                startActivity(intent2);
            }
        });
    }

    private void test() {
        SharedPreferences sp=getSharedPreferences("userid",0);
        username.setText(sp.getString("username",""));
        password.setText(sp.getString("password",""));

        if (username.length()==10 && password.length()>=4){
            Intent intent3 = new Intent(startactivity.this,MainActivity.class);
            startActivity(intent3);
        }
    }


    /*保存密码-嘻嘻*/
    private void memInfo(String usernameid,String passwordid){
        SharedPreferences.Editor editor=getSharedPreferences("userid",0).edit();
        editor.putString("username",usernameid);
        editor.putString("password",passwordid);
        editor.commit();
    }

    private void restoreInfo(){
        SharedPreferences sp=getSharedPreferences("userid",0);
        username.setText(sp.getString("username",""));
        password.setText(sp.getString("password",""));
    }
    /**保存密码就到这里了*/

    @Override
    public void onClick(View view) {

        mWidth = main_btn_login.getMeasuredWidth();
        mHeight = main_btn_login.getMeasuredHeight();

        mName.setVisibility(View.INVISIBLE);
        mPsw.setVisibility(View.INVISIBLE);

        inputAnimator(mInputLayout, mWidth, mHeight);

    }



    private void inputAnimator(final View view, float w, float h) {
        ValueAnimator animator = ValueAnimator.ofFloat(0, w);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (Float) animation.getAnimatedValue();
                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
                params.leftMargin = (int) value;
                params.rightMargin = (int) value;
                view.setLayoutParams(params);
            }
        });
        AnimatorSet set = new AnimatorSet();
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(mInputLayout, "scaleX", 1f, 0.5f);
        set.setDuration(1000);
        set.setInterpolator(new AccelerateDecelerateInterpolator());
        set.playTogether(animator, animator2);
        set.start();
        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                progress.setVisibility(View.VISIBLE);
                progressAnimator(progress);
                mInputLayout.setVisibility(View.INVISIBLE);

                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        recovery();
                    }
                }, 2000);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                // TODO Auto-generated method stub
            }
        });

    }

    private void progressAnimator(final View view) {
        PropertyValuesHolder animator = PropertyValuesHolder.ofFloat("scaleX", 0.5f, 1f);
        PropertyValuesHolder animator2 = PropertyValuesHolder.ofFloat("scaleY", 0.5f, 1f);
        ObjectAnimator animator3 = ObjectAnimator.ofPropertyValuesHolder(view, animator, animator2);
        animator3.setDuration(1000);
        animator3.setInterpolator(new JellyInterpolator());
        animator3.start();
    }


    /*如果登录失败就恢复原来的样子*/
    private void recovery() {
        progress.setVisibility(View.GONE);
        mInputLayout.setVisibility(View.VISIBLE);
        mName.setVisibility(View.VISIBLE);
        mPsw.setVisibility(View.VISIBLE);

        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) mInputLayout.getLayoutParams();
        params.leftMargin = 0;
        params.rightMargin = 0;
        mInputLayout.setLayoutParams(params);

        ObjectAnimator animator2 = ObjectAnimator.ofFloat(mInputLayout, "scaleX", 0.5f,1f );
        animator2.setDuration(500);
        animator2.setInterpolator(new AccelerateDecelerateInterpolator());
        animator2.start();
    }



}