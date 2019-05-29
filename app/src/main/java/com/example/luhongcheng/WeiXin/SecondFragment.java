package com.example.luhongcheng.WeiXin;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.luhongcheng.R;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;


public class SecondFragment extends Fragment {

	private WebView webview;
	String URL1= "https://weixin.sogou.com/weixinwap?ie=utf8&s_from=input&type=1&t=1539699478603&pg=webSearchList&_sug_=n&_sug_type_=&query=SIT";
    private boolean isExit;

    public static SecondFragment newInstance() {
		return  new SecondFragment();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.weixin_2, container,false);
		return v;
	}

	@SuppressLint("NewApi")
	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		webview = (WebView) getActivity().findViewById(R.id.webview2);
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




		final FloatingActionMenu fab = (FloatingActionMenu) getActivity().findViewById(R.id.fab2);
		fab.setClosedOnTouchOutside(true);
		FloatingActionButton refresh = (FloatingActionButton)getActivity().findViewById(R.id.fab_preview2);
		FloatingActionButton zhuan = (FloatingActionButton)getActivity().findViewById(R.id.zhuan2);

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


    public void exit() {
        if(!isExit) {
            isExit = true;
            webview.goBack();
        } else {
            getActivity().finish();
        }
    }

}
