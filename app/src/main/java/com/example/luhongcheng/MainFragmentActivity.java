package com.example.luhongcheng;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;


public class MainFragmentActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView A;
    private ImageView B;
    private ImageView C;
    private ImageView D;

    private FragmentManager fragmentManager;
    private OneFragment f1;
    private TwoFragment f2;
    private ThreeFragment f3;
    private FourFragment f4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main_fragment_activity);
        bindView();


    }



    //UI组件初始化与事件绑定
    private void bindView() {
        A = (ImageView) this.findViewById(R.id.A);
        B = (ImageView) this.findViewById(R.id.B);
        C = (ImageView) this.findViewById(R.id.C);
        D = (ImageView) this.findViewById(R.id.D);
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


    @Override
    public void onClick(View v) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        hideAllFragment(transaction);
        switch(v.getId()){
            case R.id.A:
                selected();
                A.setSelected(true);
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


    /*
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




    @Override
    public void onClick(View v) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        hideAllFragment(transaction);
        switch(v.getId()){
            case R.id.A:
                selected();
                A.setSelected(true);
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

*/


}
