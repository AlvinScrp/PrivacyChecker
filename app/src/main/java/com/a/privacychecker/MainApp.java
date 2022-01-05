package com.a.privacychecker;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.multidex.MultiDex;


public class MainApp extends Application {

    public static boolean allowVisit = true;

    private static Context context = null;


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);

    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this.getApplicationContext();
    }



    public static Object privacyVisitProxy(String clzName, String methodName, Object obj, Class[] paramsClasses, Object[] paramsValues) {
//        Class[] paramsClasses = parseParamsClasses(paramsValues);
        if (allowVisit) {
            Log.d("alvin" ,"privacyVisitProxy:"+clzName+"."+methodName);
            return obj == null ? PrivacyRefInvoke.invokeStaticMethod(clzName, methodName, paramsClasses, paramsValues)
                    : PrivacyRefInvoke.invokeInstanceMethod(obj, methodName, paramsClasses, paramsValues);
        } else {
            String mLongName = clzName + "." + methodName;
            if (mLongName.equals(
                    PrivacyConfigInApp.getRunningAppProcesses)) {
                return null;
            } else if (mLongName.equals(PrivacyConfigInApp.getIpAddressByWifiInfo)) {
                return 0;
            } else if (mLongName.equals(PrivacyConfigInApp.getSubscriberId)) {
                return "invalid_SubscriberId";
            } else if (mLongName.equals(PrivacyConfigInApp.getDeviceId)) {
                return "invalid_deviceId";
            } else if (mLongName.equals(PrivacyConfigInApp.Secure_getString)
                    || mLongName.equals(PrivacyConfigInApp.System_getString)) {
                return (paramsValues[1]).equals("android_id") ? "3dsdssdsd88"
                        : PrivacyRefInvoke.invokeStaticMethod(clzName, methodName, paramsClasses, paramsValues);
//            } else if (mLongName.equals(PrivacyConstants.Privacy_System_getString)) {
//                if (!((String) paramsValues[1]).equals("android_id")) {
//                    return Settings.System.getString((ContentResolver) paramsValues[0], (String) paramsValues[1]);
//                } else {
//                    return "3dsdssdsd88";
//                }
            } else if (mLongName.equals(PrivacyConfigInApp.getSSID)) {
                return "<unknown ssid>";
            } else if (mLongName.equals(PrivacyConfigInApp.getMacAddress)) {
                return "02:00:00:00:00:00";
            } else {
                return null;
            }
        }
    }
    public static Object privacyRejectMethod(String clzName, String methodName, Object obj, Class[] paramsClasses, Object[] paramsValues) {

        Object result = null;
        String mLongName = clzName + "." + methodName;

        try {
//            if (mLongName.equals(PrivacyConfigInApp.getRunningAppProcesses)) {
//
//            } else if (mLongName.equals(PrivacyConfigInApp.getIpAddressByWifiInfo)) {
//                result = Integer.valueOf(0);
//            } else
            if (mLongName.equals(PrivacyConfigInApp.getSubscriberId)
                    || mLongName.equals(PrivacyConfigInApp.getSubscriberIdGemini)) {
                result = "invalid_SubscriberId";
            } else if (mLongName.equals(PrivacyConfigInApp.getDeviceId)) {
                result = "invalid_deviceId";
            } else if (mLongName.equals(PrivacyConfigInApp.getImei)) {
                result = "invalid_imei";
            } else if (mLongName.equals(PrivacyConfigInApp.getNai)) {
                result = "invalid_nai";
            } else if (mLongName.equals(PrivacyConfigInApp.getMeid)) {
                result = "invalid_meid";
            } else if (mLongName.equals(PrivacyConfigInApp.Secure_getString)
                    || mLongName.equals(PrivacyConfigInApp.System_getString)) {
                if (paramsValues != null && paramsValues.length == 2 && (paramsValues[1]).equals("android_id")) {
                    result = "3dsdssdsd88";
                } else {
                    result = PrivacyRefInvoke.invokeStaticMethod(clzName, methodName, paramsClasses, paramsValues);
                }

                Object param1 = "unknown";
                if (paramsValues != null && paramsValues.length == 2) {
                    param1 = paramsValues[1];
                }
                Log.d("alvin", "SettingGetString param1:(" + param1 + ") result:" + result);


//            } else if (mLongName.equals(PrivacyConfigInApp.getSSID)) {
//                result = "<unknown ssid>";
            } else if (mLongName.equals(PrivacyConfigInApp.getMacAddress)) {
                result = "02:00:00:00:00:00";
            } else if (mLongName.equals(PrivacyConfigInApp.getHardwareAddress)) {
                result = null;
            } else if (mLongName.equals(PrivacyConfigInApp.getSerial)) {
//                result = "unknown1serial";
                result = "";
            }
            Log.d("alvin", "privacyInvalidValue:" + mLongName + " \nresult:" + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static Object privacyRejectField(String longName) {

        Object result = null;
        try {
            if (longName.equals(PrivacyConfigInApp.Field_Serial)) {
//                result = "unknown2serial";
                result = "";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;


    }

    public static void privacyLog(boolean isAllow, String longName) {
        String tag = (isAllow ? "alvinPrivacyAllow" : "alvinPrivacyReject");
        Log.d(tag, Log.getStackTraceString(new Throwable(longName)));
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
