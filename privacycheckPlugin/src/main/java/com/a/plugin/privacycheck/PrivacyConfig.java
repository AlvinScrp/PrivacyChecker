package com.a.plugin.privacycheck;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * 参考
 * https://github.com/yanerchuang/PrivacyPolicyComplianceCheck/blob/master/app/src/main/java/com/ywj/xposeddemo/MainActivity.java
 * TelephonyManager信息获取：https://www.cnblogs.com/weixing/p/3253479.html
 */
public class PrivacyConfig {

    //    public static String Statement_Allow_bool = "com.webuy.hyk.privacychecker.PrivacyProxy.isAllowVisit()";
    //    public static String Log_tag_Allow ="alvinAllow222";
    //    public static String Log_tag_Reject = "alvinReject222";
    public static boolean isAllow = false;
    public static String Statement_Reject_Method = "com.webuy.hyk.privacychecker.PrivacyProxy.privacyRejectMethod";
    public static String Statement_Reject_Field = "com.webuy.hyk.privacychecker.PrivacyProxy.privacyRejectField";
    public static String Statement_log = "com.webuy.hyk.privacychecker.PrivacyProxy.privacyLog";

    public static String IgnoreClass_MainApp = "cn.com.haoyiku.HykApp";
    public static String IgnoreClass_PrivacyProxy = "com.webuy.hyk.privacychecker.PrivacyProxy";

    public static Set<String> ignoreClasses = new HashSet<>();
    public static Set<String> methodSet = new HashSet<>();
    public static Set<String> fieldSet = new HashSet<>();
    public static Set<String> methodHookValueSet = new HashSet<>();
    public static Set<String> fieldHookValueSet = new HashSet<>();

    //不可变设备信息 IMSI、IMEI，MEID ,SimSerialNumber,设备手机号，MAC地址
    public static String getSerial = "android.os.Build.getSerial"; //设备序列号
    public static String getSubscriberId = "android.telephony.TelephonyManager.getSubscriberId"; //IMSI
    public static String getSubscriberIdGemini = "android.telephony.TelephonyManager.getSubscriberIdGemini"; //IMSI
    public static String getDeviceId = "android.telephony.TelephonyManager.getDeviceId";//IMEI
    public static String getImei = "android.telephony.TelephonyManager.getImei";//IMEI
    public static String getNai = "android.telephony.TelephonyManager.getNai";//IMEI
    public static String getMeid = "android.telephony.TelephonyManager.getMeid";//CDMA手机的 MEID
    public static String getSimSerialNumber = "android.telephony.TelephonyManager.getSimSerialNumber";//SIM卡的序列号
    public static String getLine1Number = "android.telephony.TelephonyManager.getLine1Number";//手机号
    public static String getCellLocation = "android.telephony.TelephonyManager.getCellLocation"; //电话方位
    public static String getNetworkOperatorName = "android.telephony.TelephonyManager.getNetworkOperatorName";//运营商名称,注意：仅当用户已在网络注册时有效,在CDMA网络中结果也许不可靠
    public static String getNetworkOperator = "android.telephony.TelephonyManager.getNetworkOperator";//运营商名称,注意：仅当用户已在网络注册时有效,在CDMA网络中结果也许不可靠
    public static String getNetworkType = "android.telephony.TelephonyManager.getNetworkType";//当前使用的网络类型
    public static String getNetworkCountryIso = "android.telephony.TelephonyManager.getNetworkCountryIso";//获取ISO标准的国家码，即国际长途区号。
    public static String getPhoneType = "android.telephony.TelephonyManager.getPhoneType";//手机类型 NONE GSM CDMA
    public static String getVoiceMailNumber = "android.telephony.TelephonyManager.getVoiceMailNumber";//获取语音邮件号码
    public static String getVoiceMailAlphaTag = "android.telephony.TelephonyManager.getVoiceMailAlphaTag";//获取语音邮件号码
    public static String getSimCountryIso = "android.telephony.TelephonyManager.getSimCountryIso";//获取ISO国家码，相当于提供SIM卡的国家码
    public static String getSimOperator = "android.telephony.TelephonyManager.getSimOperator";//获取SIM卡提供的移动国家码和移动网络码.5或6位的十进制数字.
    public static String getSimOperatorName = "android.telephony.TelephonyManager.getSimOperatorName";//
    public static String getSimState = "android.telephony.TelephonyManager.getSimState";//
    public static String getCallState = "android.telephony.TelephonyManager.getCallState";//电话状态 0-IDLE无活动 1-RINGING响铃 2-OFFHOOK摘机
    public static String getDeviceSoftwareVersion = "android.telephony.TelephonyManager.getDeviceSoftwareVersion";//设备的软件版本号 例如：the IMEI/SV(software version) for GSM phones.
    public static String getDataActivity = "android.telephony.TelephonyManager.getDataActivity";//获取数据连接状态
    public static String getAllCellInfo = "android.telephony.TelephonyManager.getAllCellInfo";//附近的电话信息
    public static String isNetworkRoaming = "android.telephony.TelephonyManager.isNetworkRoaming";//是否漫游
    public static String hasIccCard = "android.telephony.TelephonyManager.hasIccCard";//ICC卡是否存在
    //WLAN接入点，IP地址,MAC地址
    public static String getConnectionInfo = "android.net.wifi.WifiManager.getConnectionInfo"; //getConnectionInfo
    public static String getActiveNetworkInfo = "android.net.ConnectivityManager.getActiveNetworkInfo"; //getActiveNetworkInfo
    public static String getMacAddress = "android.net.wifi.WifiInfo.getMacAddress"; //mac 地址
    public static String getSSID = "android.net.wifi.WifiInfo.getSSID";//WLAN接入点
    public static String getIpAddressByWifiInfo = "android.net.wifi.WifiInfo.getIpAddress"; //ip
    public static String getNetworkInterfaces = "java.net.NetworkInterface.getNetworkInterfaces"; //ip
    public static String getHardwareAddress = "java.net.NetworkInterface.getHardwareAddress"; //mac地址
    public static String getMacRuntimeExec = " java.lang.Runtime.exec"; //mac地址

    //软件列表
    public static String getRunningAppProcesses = "android.app.ActivityManager.getRunningAppProcesses";//运行进程
    public static String getAppTasks = "android.app.ActivityManager.getAppTasks";//运行进程
    public static String getRunningTasks = "android.app.ActivityManager.getRunningTasks";//运行进程
    public static String getInstalledPackages = "android.content.pm.PackageManager.getInstalledPackages"; //安装应用列表
    public static String getInstalledApplications = "android.content.pm.PackageManager.getInstalledApplications"; //安装应用列表
    //Android ID
    public static String Secure_getString = "android.provider.Settings$Secure.getString"; //Android Id?
    public static String System_getString = "android.provider.Settings$System.getString"; //Android Id?

    public static String getPrimaryClip = "android.content.ClipboardManager.getPrimaryClip"; //剪切板

    public static String Field_Serial = "android.os.Build.SERIAL";

    static {

        Collections.addAll(methodSet,
                getSerial,
                getSubscriberId, getDeviceId, getSubscriberIdGemini, getImei, getNai, getMeid, getSimSerialNumber, getLine1Number,
                getNetworkOperatorName, getNetworkOperator, getNetworkType, getNetworkCountryIso,
                getSimOperator, getSimOperatorName, getSimCountryIso, getSimState,
                getVoiceMailNumber, getVoiceMailAlphaTag, getAllCellInfo, getCellLocation,


                getMacAddress, getSSID, getIpAddressByWifiInfo, getNetworkInterfaces, getHardwareAddress, getMacRuntimeExec,

                getRunningAppProcesses, getAppTasks, getRunningTasks, getInstalledPackages, getInstalledApplications,

                Secure_getString, System_getString,
                getPrimaryClip
        );

        Collections.addAll(fieldSet, Field_Serial);


        Collections.addAll(methodHookValueSet,
                getSerial,
                getSubscriberId, getDeviceId, getSubscriberIdGemini, getImei, getNai, getMeid, getSimSerialNumber,
                getMacAddress, getHardwareAddress,
                Secure_getString, System_getString
        );

        Collections.addAll(fieldHookValueSet, Field_Serial);

        Collections.addAll(ignoreClasses, IgnoreClass_MainApp, IgnoreClass_PrivacyProxy);

    }
}
