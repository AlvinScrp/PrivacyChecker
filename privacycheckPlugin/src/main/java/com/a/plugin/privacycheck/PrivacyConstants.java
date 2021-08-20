package com.a.plugin.privacycheck;

import java.util.HashSet;
import java.util.Set;

public  class PrivacyConstants {
    public static Set<String> privacySet = new HashSet<>();
    public static String Privacy_RunningAppProcesses = "android.app.ActivityManager.getRunningAppProcesses";
    public static String Privacy_getIpAddress = "android.net.wifi.WifiInfo.getIpAddress";
    public static String Privacy_getSubscriberId ="android.telephony.TelephonyManager.getSubscriberId";
    public static String Privacy_getDeviceId ="android.telephony.TelephonyManager.getDeviceId";
    public static String Privacy_Secure_getString ="android.provider.Settings$Secure.getString";
    public static String Privacy_System_getString ="android.provider.Settings$System.getString";
    public static String Privacy_getSSID ="android.net.wifi.WifiInfo.getSSID";
    public static String Privacy_getMacAddress ="android.net.wifi.WifiInfo.getMacAddress";
    public static String Privacy_getNetworkInterfaces ="java.net.NetworkInterface.getNetworkInterfaces";

    static {
        privacySet.add(Privacy_getSubscriberId);
        privacySet.add(Privacy_getDeviceId);
        privacySet.add(Privacy_Secure_getString);
        privacySet.add(Privacy_System_getString);
        privacySet.add(Privacy_getSSID);
        privacySet.add(Privacy_getMacAddress);
        privacySet.add(Privacy_getNetworkInterfaces);
        privacySet.add(Privacy_getIpAddress);
        privacySet.add(Privacy_RunningAppProcesses);


    }
}
