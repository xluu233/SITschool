package com.example.luhongcheng.about;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.luhongcheng.R;

import java.io.File;
import java.net.URLEncoder;

/**
 * Created by alex233 on 2018/5/9.
 */

public class about4  extends AppCompatActivity {

    ImageButton zhifubao;
    public static final String ALIPAY_PERSON = "HTTPS://QR.ALIPAY.COM/FKX044332HI2XXVCCFV48F";
    @Override
    protected  void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about4);

        zhifubao = (ImageButton) findViewById(R.id.zhifubao);
        zhifubao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAliPay2Pay(ALIPAY_PERSON);
            }
        });
        openAliPay2Pay(ALIPAY_PERSON);

    }

    private void openAliPay2Pay(String qrCode) {
        if (openAlipayPayPage(this, qrCode)) {
            Toast.makeText(this, "跳转成功", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "跳转失败", Toast.LENGTH_SHORT).show();
        }
    }

    public static boolean openAlipayPayPage(Context context, String qrcode) {
        try {
            qrcode = URLEncoder.encode(qrcode, "utf-8");
        } catch (Exception e) {
        }
        try {
            final String alipayqr = "alipayqr://platformapi/startapp?saId=10000007&clientVersion=3.7.0.0718&qrcode=" + qrcode;
            openUri(context, alipayqr + "%3F_s%3Dweb-other&_t=" + System.currentTimeMillis());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private static void openUri(Context context, String s) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(s));
        context.startActivity(intent);
    }


}
