package com.a.privacychecker;

import android.app.ActivityManager;
import android.app.Application;
import android.content.ContentResolver;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.provider.Settings;
import android.telecom.TelecomManager;
import android.telephony.TelephonyManager;

import com.a.mylibrary.PrivacyVisitor;

import java.sql.Ref;


public class MainApp extends Application {

    public static boolean allowVisit = false;

    private static Context context = null;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        context = this.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static Object privacyVisitProxy(String clzName, String methodName, Object obj, Class[] paramsClasses, Object[] paramsValues) {
//        Class[] paramsClasses = parseParamsClasses(paramsValues);
        if (allowVisit) {
            return obj == null ? RefInvoke.invokeStaticMethod(clzName, methodName, paramsClasses, paramsValues)
                    : RefInvoke.invokeInstanceMethod(obj, methodName, paramsClasses, paramsValues);
//            if (mLongName.equals(
//                    PrivacyConstants.Privacy_RunningAppProcesses)) {
////            return " $_ = new java.util.ArrayList();";
////            return " $_ = new java.util.ArrayList<android.app.ActivityManager$RunningAppProcessInfo>();";
//                return ((ActivityManager) obj).getRunningAppProcesses();
//
//            } else if (mLongName.equals(PrivacyConstants.Privacy_getIpAddress)) {
//                return ((WifiInfo) obj).getIpAddress();
//            } else if (mLongName.equals(PrivacyConstants.Privacy_getSubscriberId)) {
//                return ((TelephonyManager) obj).getSubscriberId();
//            } else if (mLongName.equals(PrivacyConstants.Privacy_getDeviceId)) {
//                return ((TelephonyManager) obj).getDeviceId();
//            } else if (mLongName.equals(PrivacyConstants.Privacy_Secure_getString)) {
//                return Settings.Secure.getString((ContentResolver) paramsValues[0], (String) paramsValues[1]);
//            } else if (mLongName.equals(PrivacyConstants.Privacy_System_getString)) {
//                return Settings.System.getString((ContentResolver) paramsValues[0], (String) paramsValues[1]);
//            } else if (mLongName.equals(PrivacyConstants.Privacy_getSSID)) {
//                return ((WifiInfo) obj).getSSID();
//            } else if (mLongName.equals(PrivacyConstants.Privacy_getMacAddress)) {
//                return ((WifiInfo) obj).getMacAddress();
//            } else {
//                return null;
//            }
        } else {
            String mLongName = clzName + "." + methodName;
            if (mLongName.equals(
                    PrivacyConstants.Privacy_RunningAppProcesses)) {
                return null;
            } else if (mLongName.equals(PrivacyConstants.Privacy_getIpAddress)) {
                return 0;
            } else if (mLongName.equals(PrivacyConstants.Privacy_getSubscriberId)) {
                return "invalid_SubscriberId";
            } else if (mLongName.equals(PrivacyConstants.Privacy_getDeviceId)) {
                return "invalid_deviceId";
            } else if (mLongName.equals(PrivacyConstants.Privacy_Secure_getString)
                    || mLongName.equals(PrivacyConstants.Privacy_System_getString)) {
                return (paramsValues[1]).equals("android_id") ? "3dsdssdsd88"
                        : RefInvoke.invokeStaticMethod(clzName, methodName, paramsClasses, paramsValues);
//            } else if (mLongName.equals(PrivacyConstants.Privacy_System_getString)) {
//                if (!((String) paramsValues[1]).equals("android_id")) {
//                    return Settings.System.getString((ContentResolver) paramsValues[0], (String) paramsValues[1]);
//                } else {
//                    return "3dsdssdsd88";
//                }
            } else if (mLongName.equals(PrivacyConstants.Privacy_getSSID)) {
                return "<unknown ssid>";
            } else if (mLongName.equals(PrivacyConstants.Privacy_getMacAddress)) {
                return "02:00:00:00:00:00";
            } else {
                return null;
            }
        }
    }

    private static Class[] parseParamsClasses(Object[] paramsValues) {
        if (paramsValues == null || paramsValues.length == 0) {
            return new Class[0];
        }
        Class[] paramsClasses = new Class[paramsValues.length];
        for (int i = 0; i < paramsValues.length; i++) {
            paramsClasses[i] = paramsValues[i].getClass();
        }
        return paramsClasses;
    }

}
