package com.a.privacychecker;

import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.annotation.RequiresApi;

public  class NotificationUtil {

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void disableAppNotification(Context context){

//
//        // Only the owner can see all apps.
//        /*int adminRetrieveFlags = PackageManager.MATCH_ANY_USER |
//                PackageManager.MATCH_DISABLED_COMPONENTS |
//                PackageManager.MATCH_DISABLED_UNTIL_USED_COMPONENTS;*/
//        try {
//            String packagename = context.getPackageName();
//            NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//            PackageManager mPm = context.getPackageManager();
//            ApplicationInfo info = mPm.getApplicationInfo(context.getPackageName(),0);
//            int uid = mPm.getPackageUid(packagename, 0);
//            INotificationManager mNotificationManagerP = INotificationManager.Stub.asInterface( mNotificationManager);
//
//
//            mNotificationManager.setNotificationsEnabledForPackage(packagename, uid, false);
//        } catch (Exception e) {
//            Log.w("Tim_A", "Error calling NoMan", e);
//        }
    }

}
