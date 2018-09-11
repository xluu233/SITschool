package com.example.luhongcheng.utils;

import android.util.Log;

import java.util.List;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class getLoginUtils {
    static List<String> cookies;
    static  int logincode;
    static  String str;

    public  static int getlgoin(final String xuehao, final String mima){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    RequestBody requestBody = new FormBody.Builder()
                            .add("goto", "http://myportal.sit.edu.cn/loginSuccess.portal")
                            .add("gotoOnFail", "http://myportal.sit.edu.cn/loginFailure.portal")
                            .add("Login.Token1",xuehao)
                            .add("Login.Token2",mima)
                            .build();
                    Request request1 = new Request.Builder()
                            .url("http://myportal.sit.edu.cn/userPasswordValidate.portal")
                            .post(requestBody)
                            .build();

                    Response response1 = client.newCall(request1).execute();
                    final Headers headers = response1.headers();
                    cookies = headers.values("Set-Cookie");
                    String[] strs = cookies.toArray(new String[cookies.size()]);
                    for (int i = 0; i < strs.length; ++i) {
                        str = strs[i];
                    }
                    Log.d("cookie信息", "onResponse-size: " + str);
                    if (str == null){
                        logincode = 0;
                    }
                    else{
                        logincode = 1;
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
        return logincode;
    }
}
