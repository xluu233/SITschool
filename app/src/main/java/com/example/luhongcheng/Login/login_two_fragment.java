package com.example.luhongcheng.Login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.example.luhongcheng.R;


public class login_two_fragment extends Fragment {

    public static login_two_fragment newInstance() {
        return new login_two_fragment();
    }
    WebView webView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View v = inflater.inflate(R.layout.login_two, container, false);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        webView = (WebView) getActivity().findViewById(R.id.login_web);
        webView.loadUrl("file:///android_asset/User_agreement.html");
    //    webView.loadUrl("http://bmob-cdn-20204.b0.upaiyun.com/2019/01/08/95b26af7405b775780bee60ca5a10223.html");
    }


}
