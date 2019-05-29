package com.example.luhongcheng.WeiXin;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.example.luhongcheng.R;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.Timer;
import java.util.TimerTask;

import static android.view.KeyEvent.KEYCODE_BACK;



public class FirstFragment extends Fragment {

	private WebView webview;
	String URL1= "http://weixin.sogou.com/weixinwap?ie=utf8&s_from=input&type=1&t=1537445736414&pg=webSearchList&_sug_=y&_sug_type_=&query=%E4%B8%8A%E6%B5%B7%E5%BA%94%E7%94%A8%E6%8A%80%E6%9C%AF%E5%A4%A7%E5%AD%A6";
    private boolean isExit;

    public static FirstFragment newInstance() {
		return  new  FirstFragment();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.weixin_1, container,false);
		return v;
	}

	@SuppressLint("NewApi")
	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		webview = (WebView) getActivity().findViewById(R.id.webview1);
		webview.getSettings().setAllowFileAccessFromFileURLs(true);
		webview.loadUrl(URL1);
		webview.getSettings().setJavaScriptEnabled(true);
		webview.setWebViewClient(new WebViewClient(){
			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				super.onPageStarted(view, url, favicon);
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
				String javascript =  "javascript:function hideOther() {" +
						// 第一个页面
						"document.getElementsByClassName('tab-top')[0].remove();" +
						"document.getElementsByClassName('hhy-navcontainer')[0].remove();+" +
						//"document.getElementsByClassName('header')[0].remove();+" +
						"document.getElementsByClassName('account-txt')[0].remove();" +
						"document.getElementsByClassName('footer')[0].remove();" +
						"document.getElementsByClassName('back-top')[0].remove(); }";

				view.loadUrl(javascript);
				view.loadUrl("javascript:hideOther();");
			}

		});

		WebSettings webSettings=webview.getSettings();
		webSettings.setUseWideViewPort(true);
		webSettings.setLoadWithOverviewMode(true);




		final FloatingActionMenu fab = (FloatingActionMenu) getActivity().findViewById(R.id.fab1);
		fab.setClosedOnTouchOutside(true);
		FloatingActionButton refresh = (FloatingActionButton)getActivity().findViewById(R.id.fab_preview1);
		FloatingActionButton zhuan = (FloatingActionButton)getActivity().findViewById(R.id.zhuan1);

		refresh.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				webview.reload();
			}
		});
		zhuan.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String nowURL = webview.getUrl();

				Intent intent= new Intent();
				intent.setAction("android.intent.action.VIEW");
				intent.setData(Uri.parse(nowURL));
				startActivity(intent);

			}
		});

	}

	/*
	activity中监听返回键的方法在fragment中不适用
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KEYCODE_BACK) && webview.canGoBack()) {
			webview.goBack();
			return true;
		}
		return this.onKeyDown(keyCode, event);
	}
	*/


	//fragment中监听返回键要在activity中设置
    public void exit() {
        if(!isExit) {
            isExit = true;
            webview.goBack();
        } else {
            getActivity().finish();
        }
    }

}
