# PrivacyChecker
javassist实现hook隐私权限api访问

随着对用户个人信息保护的愈发重视，相关政策也呼之欲出。例如 “禁止在用户同意隐私政策前，访问用户个人信息”。
目前应用商店通过在系统层，监控app运行过程中对api的访问。我们的APP，对于应用商店来说是黑盒，所以在系统层监控是恰当的。

而我们的APP对我们来说是白盒，我们可以有更多方式实现监控，甚至改写。


插件已经上传到maven，使用方式如下：

## 集成
```java
//build.gradle(根目录)文件
buildscript {
    repositories {
        //插件库放到了jitpack上，需要翻墙访问。
         maven { url 'https://jitpack.io' }
    }
    dependencies {
        classpath 'com.github.AlvinScrp:PrivacyChecker:0.0.2'
    }
}

allprojects {
    repositories {
         maven { url 'https://jitpack.io' }
    }
}
-----------------------------------
//build.gradle (app modlue)文件
//插件顺序运行,放其他plugin后面.
apply plugin: 'privacycheck-plugin'

```
## 编译
```java
#编译注入
Log.d("alvin",Log.getStackTraceString(new Throwable("TelephonyManager.getSubscriberId")));

#编译过程中，输出日志
      ...
> Task :app:transformClassesWithPrivacyCheckTransformRobForNgmm_testDebug
----------Privacy check transform start----------
========
call: android.telephony.TelephonyManager.getSubscriberId
  at: com.cmic.sso.sdk.utils.o.a(boolean)(o.java:213)
      ...
insertCode cost 35.242 second
----------Privacy check transform end----------
      ...
```
## 运行
日志过滤查看(Tag：alvin)
```java
2021-01-26 15:33:00.613 2380-2380/com.nicomama.niangaomama D/alvin: java.lang.Throwable: android.telephony.TelephonyManager.getDeviceId
        at com.ngmm365.base_lib.utils.DeviceUtils.getDeviceId(DeviceUtils.java:90)
        at com.ngmm365.base_lib.utils.DeviceUtils.getIMEI(DeviceUtils.java:68)
        at com.ngmm365.base_lib.tracker.sensordata.SensorsDataMgr.trackInstallation(SensorsDataMgr.java:82)
        at com.nicomama.niangaomama.MainHomeActivity.onCreate(MainHomeActivity.java:115)
        at android.app.Activity.performCreate(Activity.java:8085)
        at android.app.Activity.performCreate(Activity.java:8073)
        at android.app.Instrumentation.callActivityOnCreate(Instrumentation.java:1320)
        at com.qiyukf.unicorn.m.a.callActivityOnCreate(QiyuInstrumentation.java:258)
        at android.app.ActivityThread.performLaunchActivity(ActivityThread.java:3868)
        at android.app.ActivityThread.handleLaunchActivity(ActivityThread.java:4074)
        at android.app.servertransaction.LaunchActivityItem.execute(LaunchActivityItem.java:91)
        at android.app.servertransaction.TransactionExecutor.executeCallbacks(TransactionExecutor.java:149)
        at android.app.servertransaction.TransactionExecutor.execute(TransactionExecutor.java:103)
        at android.app.ActivityThread$H.handleMessage(ActivityThread.java:2473)
        at android.os.Handler.dispatchMessage(Handler.java:110)
        at android.os.Looper.loop(Looper.java:219)
        at android.app.ActivityThread.main(ActivityThread.java:8347)
        at java.lang.reflect.Method.invoke(Native Method)
        at com.android.internal.os.RuntimeInit$MethodAndArgsCaller.run(RuntimeInit.java:513)
        at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:1055)

```
