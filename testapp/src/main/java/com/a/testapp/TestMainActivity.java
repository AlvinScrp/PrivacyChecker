package com.a.testapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.app.AppOpsManager;
import android.app.usage.NetworkStats;
import android.app.usage.NetworkStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.RemoteException;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TestMainActivity extends AppCompatActivity {

    private Button btnByteUse;
    private Button btnClearLog;
    private TextView tvResult;
    private Context context;

    private List<String> logs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_main);
        context = this;
//        Log.d("alvin", Log.getStackTraceString(new Throwable()));
        btnByteUse = findViewById(R.id.btnByteUse);
        btnClearLog = findViewById(R.id.btnClearLog);
        tvResult = findViewById(R.id.tvResult);

        findViewById(R.id.btn_byteUse_detail)
                .setOnClickListener(v -> gotoAPPDetail("com.a.testapp"));

        findViewById(R.id.btn_nicomama_detail)
                .setOnClickListener(v -> gotoAPPDetail("com.nicomama.niangaomama"));

        btnByteUse.setOnClickListener(v -> {
            getByteUse();
        });

        btnClearLog.setOnClickListener(v -> {
            clearLogButLastOne();
        });

        hasPermissionToReadNetworkStats();
    }

    private void clearLogButLastOne() {
        if (logs.size() > 1) {
            String lastOne = logs.get(logs.size() - 1);
            logs.clear();
            logs.add(lastOne);
        }
        tvResult.setText(listToString(logs));
    }

    private void getByteUse() {
        String date = stampToDate(System.currentTimeMillis());
        String usage = getByteUseByUseId();
        logs.add(date + "--> " + usage);
        tvResult.setText(listToString(logs));
    }

    private String listToString(List<String> logs) {
        StringBuilder sb = new StringBuilder();
        for (String log : logs) {
            sb.append(log + "\n");
        }
        return sb.toString();
    }

    private void getWifiByteUse() {
        try {
            NetworkStatsManager networkStatsManager = (NetworkStatsManager) getSystemService(NETWORK_STATS_SERVICE);
            // 获取到目前为止设备的Wi-Fi流量统计
            NetworkStats.Bucket bucket = networkStatsManager.querySummaryForDevice(ConnectivityManager.TYPE_WIFI, "", 0, System.currentTimeMillis());
            Log.i("Info", "Total: " + (bucket.getRxBytes() + bucket.getTxBytes()));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * 参考文章： https://blog.csdn.net/w7849516230/article/details/71705835/
     * @return
     */
    private String getByteUseByUseId() {
        String result = "";

        try {
            int rxByte = 0;
            int txByte = 0;
            // 获取subscriberId
            TelephonyManager tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
//            String subId = tm.getSubscriberId();
            String subId = "";

            int uid = getUidByPackageName(context, "com.nicomama.niangaomama");


            NetworkStats summaryStats;
            NetworkStats.Bucket summaryBucket = new NetworkStats.Bucket();

            NetworkStatsManager networkStatsManager = (NetworkStatsManager) getSystemService(NETWORK_STATS_SERVICE);

            summaryStats = networkStatsManager.querySummary(ConnectivityManager.TYPE_WIFI, subId, 1641285089900L, System.currentTimeMillis());
            do {
                summaryStats.getNextBucket(summaryBucket);

//                String msg = "uid:" + summaryBucket.getUid() + "   rx:" + summaryBucket.getRxBytes() +
//                        "   tx:" + summaryBucket.getTxBytes();

                int summaryUid = summaryBucket.getUid();
                if (uid == summaryUid) {
                    rxByte += summaryBucket.getRxBytes();
                    txByte += summaryBucket.getTxBytes();
//                    result = msg;
                }

            } while (summaryStats.hasNextBucket());

            String msg = "rx:" + rxByte +
                    "   tx:" + txByte;
            Log.i(TestMainActivity.class.getSimpleName(), msg);
            result = msg;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public static int getUidByPackageName(Context context, String packageName) {
        int uid = -1;
        PackageManager packageManager = context.getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(packageName, PackageManager.GET_META_DATA);

            uid = packageInfo.applicationInfo.uid;
//            Log.i(TestMainActivity.class.getSimpleName(), packageInfo.packageName + " uid:" + uid);


        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return uid;
    }


    private boolean hasPermissionToReadNetworkStats() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        final AppOpsManager appOps = (AppOpsManager) getSystemService(Context.APP_OPS_SERVICE);
        int mode = appOps.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS,
                android.os.Process.myUid(), getPackageName());
        if (mode == AppOpsManager.MODE_ALLOWED) {
            return true;
        }

        requestReadNetworkStats();
        return false;
    }

    // 打开“有权查看使用情况的应用”页面
    private void requestReadNetworkStats() {
        Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
        startActivity(intent);
    }


    public void gotoAPPDetail(String packageName) {
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

    /*
     * 将时间戳转换为时间
     *
     * s就是时间戳
     */

    public static String stampToDate(long timeStamp) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(timeStamp);
        res = simpleDateFormat.format(date);
        return res;
    }

    public  void backgroundData(){
        Settings.System.getString(getContentResolver(),Settings.Secure.BACKGROUND_DATA);
//and
        Settings.Secure.getString(getContentResolver(),Settings.Secure.BACKGROUND_DATA);
    }


}