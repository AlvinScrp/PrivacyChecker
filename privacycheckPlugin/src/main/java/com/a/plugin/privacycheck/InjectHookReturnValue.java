package com.a.plugin.privacycheck;

import javassist.CannotCompileException;
import javassist.expr.MethodCall;

public class InjectHookReturnValue {

    public static void execute(MethodCall m) throws CannotCompileException {
        String mLongName = m.getClassName() + "." + m.getMethodName();
        //simple inject log
//        String replace = "{  android.util.Log.d(\"alvin\",android.util.Log.getStackTraceString(new Throwable(\""+mLongName+"\"))); $_ = $proceed($$); }";
//        m.replace(replace);

        StringBuilder sb = new StringBuilder();
        sb.append("{ ");
        sb.append("     if(com.a.privacychecker.MainApp.allowVisit){");
        sb.append("         {  android.util.Log.d(\"alvin\",android.util.Log.getStackTraceString(new Throwable(\"" + mLongName + "\"))); $_ = $proceed($$); }");
        sb.append("     }else {");
        sb.append(getInvalidReturnText(m, mLongName));
        sb.append("     }");
        sb.append("}");
        String replace = sb.toString();
        System.out.println(sb.toString());
        m.replace(replace);
    }

    public static String getInvalidReturnText(MethodCall m, String mLongName) {

        if (mLongName.equals(
                PrivacyConstants.Privacy_RunningAppProcesses)) {
//            return " $_ = new java.util.ArrayList();";
//            return " $_ = new java.util.ArrayList<android.app.ActivityManager$RunningAppProcessInfo>();";
            return " $_ =  null;";
        } else if (mLongName.equals(PrivacyConstants.Privacy_getIpAddress)) {
            return " $_ = 0 ;";
        } else if (mLongName.equals(PrivacyConstants.Privacy_getSubscriberId)) {
            return " $_ = \"invalid_SubscriberId\";";
        } else if (mLongName.equals(PrivacyConstants.Privacy_getDeviceId)) {
            return " $_ = \"invalid_deviceId\" ;";
        } else if (mLongName.equals(PrivacyConstants.Privacy_Secure_getString)) {
            return " $_ = \"sds3dw2sde5f\" ;";
        } else if (mLongName.equals(PrivacyConstants.Privacy_System_getString)) {
            return " $_ = \"sds3dw2sde5f\";";
        } else if (mLongName.equals(PrivacyConstants.Privacy_getSSID)) {
            return " $_ = \"<unknown ssid>\";";
        } else if (mLongName.equals(PrivacyConstants.Privacy_getMacAddress)) {
            return " $_ = \"02:00:00:00:00:00\" ;";
        } else {
            return " $_ =  null;";
        }
    }



}
