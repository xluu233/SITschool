package com.example.luhongcheng.SIT_SQ;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.luhongcheng.R;
import com.scwang.smartrefresh.layout.api.RefreshLayout;


public class SQ_Vedio extends Fragment {
    public SQ_Vedio(){
        Context mContext = getActivity();
    }
    public static SQ_Vedio newInstance(Context context) {
        Context mContext = context;
        return new SQ_Vedio();
    }


    RefreshLayout refreshLayout;
    WebView webView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View v = inflater.inflate(R.layout.sq_vedio, container, false);
        return v;
    }


    @SuppressLint("ResourceAsColor")
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //initView();
    }
/*

    private void initView() {
        refreshLayout = getActivity().findViewById(R.id.vedio_refresh);
        webView = getActivity().findViewById(R.id.vedio_web);
        webViewSetting();
    }

    private void webViewSetting() {
        webView.setWebViewClient(webViewClient);
        webView.setWebChromeClient(webChromeClient);
        //webView.loadUrl(url);


        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);//支持js
        //设置自适应屏幕
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        //webSettings.setSupportZoom(true);  //支持缩放，默认为true。是下面那个的前提。
        // webSettings.setBuiltInZoomControls(true); //设置可以缩放
        //webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件
//若上面是false，则该WebView不可缩放，这个不管设置什么都不能缩放。
        //webSettings.setTextZoom(2);//设置文本的缩放倍数，默认为 100
        //webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN); //支持内容重新布局
        // webSettings.supportMultipleWindows();  //多窗口
        //webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);  //关闭webview中缓存
        //webSettings.setAllowFileAccess(true);  //设置可以访问文件
        //webSettings.setNeedInitialFocus(true); //当webview调用requestFocus时为webview设置节点
        //webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true);  //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式
        //webSettings.setStandardFontFamily("");//设置 WebView 的字体，默认字体为 "sans-serif"
        webSettings.setDefaultFontSize(14);//设置 WebView 字体的大小，默认大小为 16
        //webSettings.setMinimumFontSize(12);//设置 WebView 支持的最小字体大小，默认为 8
        webSettings.setDomStorageEnabled(true);// 开启 DOM storage API 功能
        webSettings.setDatabaseEnabled(true);//开启 database storage API 功能
        webSettings.setAppCacheEnabled(true);//开启 Application Caches 功能
        moreWin(webSettings);
    }

    WebViewClient webViewClient = new WebViewClient() {
        */
/**
         * 多页面在同一个WebView中打开，就是不新建activity或者调用系统浏览器打开
         *//*

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            //view.loadUrl(url);
            //Toast.makeText(WebViewActivity.this, "没有权限的数据", Toast.LENGTH_SHORT).show();
            return false;
        }
    };
    WebChromeClient webChromeClient = new WebChromeClient() {
        @Override
        public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
            WebView.WebViewTransport transport = (WebView.WebViewTransport) resultMsg.obj;
            transport.setWebView(view);
            resultMsg.sendToTarget();
            return true;
        }

        @Override
        public void onReceivedIcon(WebView view, Bitmap icon) {
            super.onReceivedIcon(view, icon);
        }

        @Override
        public void onGeolocationPermissionsHidePrompt() {
            super.onGeolocationPermissionsHidePrompt();
        }

        @Override
        public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
            callback.invoke(origin, true, false);
            super.onGeolocationPermissionsShowPrompt(origin, callback);
        }

    };

    */
/**
     * 多窗口问题
     *//*

    private void moreWin(WebSettings webSettings) {
        //html中的_bank标签就是新建窗口打开，有时会打不开，需要加以下
        //然后 复写 WebChromeClient的onCreateWindow方法
        webSettings.setSupportMultipleWindows(false);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
    }

    @Override
    public void onPause() {
        super.onPause();
        webView.onPause();
        webView.pauseTimers();
    }

    @Override
    public void onResume() {
        super.onResume();
        webView.onResume();
        webView.resumeTimers();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (webView != null) {
            webView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            webView.clearHistory();
            ((ViewGroup) webView.getParent()).removeView(webView);
            webView.destroy();
            webView = null;
        }
    }
*/

}
