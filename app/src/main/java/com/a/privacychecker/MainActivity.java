package com.a.privacychecker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.widget.TextView;

import com.a.mylibrary.PrivacyVisitor;
import com.netease.nimlib.sdk.util.NIMUtil;
import com.qiyukf.unicorn.api.Unicorn;
//import com.sensorsdata.analytics.android.sdk.SensorsDataAPI;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.NetworkInterface;
import java.net.SocketException;

public class MainActivity extends AppCompatActivity {

    private Context context = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        findViewById(R.id.btn_allow).setOnClickListener(v -> updateAllowFlag(true));
        findViewById(R.id.btn_reject).setOnClickListener(v -> updateAllowFlag(false));
        updateAllowFlag(false);

    }

    private void updateAllowFlag(boolean allow) {
        MainApp.allowVisit = allow;
        ((TextView) findViewById(R.id.tvStatus)).setText("当前状态：" + (allow ? "允许" : "拒绝"));
        visitPrivacy();
    }

    private void visitPrivacy() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n\nMainActivity.getIMSI_SubscriberId: " + getIMSI_SubscriberId(context));
//        sb.append("\n\nSensorsDataAPI.isSDKDisabled: " + SensorsDataAPI.isSDKDisabled());
        sb.append("\n\nUnicorn.isInit: " + Unicorn.isInit());
        sb.append("\n\nPrivacyVisitor.getIMSI_SubscriberId: " + PrivacyVisitor.getIMSI_SubscriberId(context));
        sb.append("\n\nPrivacyVisitor.getIMEI_DeviceId: " + PrivacyVisitor.getIMEI_DeviceId(context));
        sb.append("\n\nPrivacyVisitor.getIpAddress: " + PrivacyVisitor.getIpAddress(context));
        sb.append("\n\nPrivacyVisitor.getMacAddress: " + PrivacyVisitor.getMacAddress(context));
        sb.append("\n\nPrivacyVisitor.getSSID: " + PrivacyVisitor.getSSID(context));
        sb.append("\n\nPrivacyVisitor.getAndroidID: " + PrivacyVisitor.getAndroidID(context));
        sb.append("\n\nPrivacyVisitor.getRunningAppProcesses: " + PrivacyVisitor.getRunningAppProcesses(context));
        sb.append("\n\nNIMUtil.getProcessFromFile: " + RefInvoke.invokeStaticMethod(NIMUtil.class,"getProcessFromFile"));
        sb.append("\n\nNIMUtil.isMainProcessLive: " + NIMUtil.isMainProcessLive(context));
        sb.append("\n\nNetworkInterface.getNetworkInterfaces: " + PrivacyVisitor.getNewMac());

        ((TextView) findViewById(R.id.tv)).setText(sb.toString());

//        NIMUtil.isMainProcessLive(context);

    }

    public static String getIMSI_SubscriberId(Context context) {
        StringBuilder sb = new StringBuilder();
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context
                    .TELEPHONY_SERVICE);
            if (telephonyManager != null) {
                return telephonyManager.getSubscriberId();
//                sb.append("\n\n IMSI_SubscriberId:" + operatorString);
            }
        } catch (Exception e) {
            e.printStackTrace();
            sb.append(e.getMessage());
        }
        return sb.toString();
    }


}