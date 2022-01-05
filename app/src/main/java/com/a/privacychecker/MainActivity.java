package com.a.privacychecker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.a.mylibrary.PrivacyVisitor;
//import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory;
import com.netease.nimlib.sdk.util.NIMUtil;
import com.qiyukf.unicorn.api.Unicorn;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.UUID;


public class MainActivity extends AppCompatActivity {

    private Context context = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        {context = this;}

        {findViewById(R.id.btn_privacychecker_detail)
                    .setOnClickListener(v -> gotoAPPDetail("com.a.privacychecker")); }
        findViewById(R.id.btn_nicomama_detail)
                .setOnClickListener(v->gotoAPPDetail("com.nicomama.niangaomama"));
        findViewById(R.id.btn_visit).setOnClickListener(v -> visitPrivacy());

    }

    private void visitPrivacy() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n\nPrivacyVisitor.getIMSI_SubscriberId: " + PrivacyVisitor.getIMSI_SubscriberId(context));
        sb.append("\n\nPrivacyVisitor.getIMEI_DeviceId: " + PrivacyVisitor.getIMEI_DeviceId(context));
        sb.append("\n\nPrivacyVisitor.getIpAddress: " + PrivacyVisitor.getIpAddress(context));
        sb.append("\n\nPrivacyVisitor.getMacAddress1: " + PrivacyVisitor.getMacAddress(context));
        sb.append("\n\nPrivacyVisitor.getMacAddress2: " + PrivacyVisitor.getNewMac());
        sb.append("\n\nPrivacyVisitor.getSSID: " + PrivacyVisitor.getSSID(context));
        sb.append("\n\nPrivacyVisitor.getAndroidID: " + PrivacyVisitor.getAndroidID(context));
        sb.append("\n\nBuild.Serial By Filed: " + PrivacyVisitor.getSerialByFiled());
        sb.append("\n\nBuild.Serial By Method: " + PrivacyVisitor.getSerialByMethod());
        sb.append("\n\nPrivacyVisitor.getRunningAppProcesses: " + PrivacyVisitor.getRunningAppProcesses(context));
        sb.append("\n\nNIMUtil.getProcessFromFile: " + PrivacyRefInvoke.invokeStaticMethod(NIMUtil.class, "getProcessFromFile"));
        sb.append("\n\nNIMUtil.isMainProcessLive: " + NIMUtil.isMainProcessLive(context));
        sb.append("\n\ngetHostAddress: " + PrivacyVisitor.getHostAddress());

        TextView tvResult = findViewById(R.id.tvResult);
        tvResult.setText(sb.toString());

    }

    public  void gotoAPPDetail(String packageName) {
        try {
            Intent intent = new Intent();
            //下面这种方案是直接跳转到当前应用的设置界面。
            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", packageName, null);
            intent.setData(uri);
          startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}