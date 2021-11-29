package com.a.mylibrary;

import android.app.ActivityManager;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

public class PrivacyVisitor {

    public static String getIMSI_SubscriberId(Context context) {
        StringBuilder sb = new StringBuilder();
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context
                    .TELEPHONY_SERVICE);
            if (telephonyManager != null) {
                return telephonyManager.getSubscriberId();
//                sb.append("\n IMSI_SubscriberId:" + operatorString);
            }
        } catch (Exception e) {
            e.printStackTrace();
            sb.append(e.getMessage());
        }
        return sb.toString();
    }

    public static String getAndroidID(Context mContext) {

        String androidID = "";
        try {
            return Settings.Secure.getString(mContext.getContentResolver(), "android_id");
        } catch (Exception e) {
            e.printStackTrace();
            return androidID;
        }
    }

    public static String getIMEI_DeviceId(Context context) {
        StringBuilder sb = new StringBuilder();
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context
                    .TELEPHONY_SERVICE);
            if (telephonyManager != null) {
                String operatorString = telephonyManager.getDeviceId();
                sb.append(operatorString);
            }
        } catch (Exception e) {
            e.printStackTrace();
            sb.append(e.getMessage());
        }
        return sb.toString();
    }

    public static String getIpAddress(Context context) {
        try {
            WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            int ipAddress = wifiInfo.getIpAddress();
            String ip = (ipAddress & 0xff) + "." + (ipAddress >> 8 & 0xff) + "." + (ipAddress >> 16 & 0xff) + "." + (ipAddress >> 24 & 0xff);
            return ip;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "0.0.0.0";

    }

    public static String getSSID(Context context) {
        try {
            WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            String ssid = wifiInfo.getSSID();
            return ssid;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "<unknown ssid>";

    }

    public static String getMacAddress(Context context) {
        try {
            WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            String macAddress = wifiInfo.getMacAddress();
            return macAddress;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "02:00:00:00:00:00";

    }



    /**
     * 通过网络接口取
     * @return
     */
    public static String getNewMac() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;
                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return null;
                }
                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(String.format("%02X:", b));
                }
                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static String getHostAddress() {
        try {
            Enumeration var0 = NetworkInterface.getNetworkInterfaces();

            while(var0.hasMoreElements()) {
                NetworkInterface var1 = (NetworkInterface)var0.nextElement();
                Enumeration var2 = var1.getInetAddresses();

                while(var2.hasMoreElements()) {
                    InetAddress var3 = (InetAddress)var2.nextElement();
                    if (!var3.isLoopbackAddress() && var3 instanceof Inet4Address) {
                        return var3.getHostAddress();
                    }
                }
            }
        } catch (SocketException var4) {
//            com.qiyukf.nimlib.k.b.e(a, "get ip address socket exception");
        }

        return "";
    }

    public static String getRunningAppProcesses(Context context) {
        try {
//            new ArrayList();
            new java.util.ArrayList<android.app.ActivityManager.RunningAppProcessInfo>();
            android.app.ActivityManager.RunningAppProcessInfo info
                    = new android.app.ActivityManager.RunningAppProcessInfo();
            ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            String processName = activityManager.getRunningAppProcesses().get(0).processName;
            return processName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "<unknown process name>";
    }
}
