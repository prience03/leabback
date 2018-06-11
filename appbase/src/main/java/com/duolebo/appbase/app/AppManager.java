/**
 *
 */
package com.duolebo.appbase.app;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;

import com.duolebo.appbase.utils.Log;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/*

add following lines to your app's manifest file.

<receiver
android:name="com.duolebo.appbase.app.AppManager"
android:exported="false" >
<intent-filter>
    <action android:name="android.intent.action.PACKAGE_ADDED" />
    <action android:name="android.intent.action.PACKAGE_REMOVED" />
    <action android:name="android.intent.action.PACKAGE_REPLACED" />
    <data android:scheme="package" />
</intent-filter>
</receiver>
*/

/**
 * @author zlhl
 * @date 2014年5月12日
 */
public class AppManager extends BroadcastReceiver {

    private static ArrayList<IAppObserver> observers = new ArrayList<IAppObserver>();

    public static void addObserver(IAppObserver obs) {
        if (null != obs && observers.indexOf(obs) < 0) {
            observers.add(obs);
        }
    }

    public static void removeObserver(IAppObserver obs) {
        if (null != obs) {
            observers.remove(obs);
        }
    }

    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    public static List<ApplicationInfo> getAllAppInfo(Context context, boolean includeSystemApp) {
        PackageManager packageManager = context.getPackageManager();
        List<ApplicationInfo> allAppInfo = packageManager
                .getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES);
        ArrayList<ApplicationInfo> ret = new ArrayList<ApplicationInfo>();
        for (ApplicationInfo appInfo : allAppInfo) {
            if (includeSystemApp) {
                if (appInfo.flags != 0) {
                    if (!context.getPackageName().equals(appInfo.packageName))
                        ret.add(appInfo);
                }
            } else {
                if (((appInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) && (appInfo.flags != 0)) {
                    if (!context.getPackageName().equals(appInfo.packageName))
                        ret.add(appInfo);
                }
            }
        }
        return ret;
    }

    @SuppressLint("NewApi")
    static List<String> getRunningPackages(Context context) {
        ArrayList<String> pkgs = new ArrayList<String>();
        ActivityManager actvityManager = (ActivityManager)
                context.getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningAppProcessInfo> procInfos = actvityManager.getRunningAppProcesses();

        for (RunningAppProcessInfo pInfo : procInfos) {
            for (int i = 0; i < pInfo.pkgList.length; i++) {
                if (!pkgs.contains(pInfo.pkgList[i]))
                    pkgs.add(pInfo.pkgList[i]);
            }
        }
        return pkgs;
    }

    public static List<ApplicationInfo> getRunningAppInfo(Context context) {
        List<String> pkgs = getRunningPackages(context);
        List<ApplicationInfo> apps = new ArrayList<ApplicationInfo>();
        PackageManager pm = context.getPackageManager();
        for (String pkg : pkgs) {
            try {
                apps.add(pm.getApplicationInfo(pkg, 0));
            } catch (NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        return apps;
    }

    @SuppressLint("NewApi")
    public static List<ResolveInfo> getAllAppLauncher(final Context context) {
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        PackageManager pm = context.getPackageManager();
        List<ResolveInfo> apps = pm.queryIntentActivities(intent, 0);
        for (int i = 0; i < apps.size(); i++) {
            ResolveInfo ri = apps.get(i);
            if (context.getPackageName().equalsIgnoreCase(ri.activityInfo.packageName)) {
                apps.remove(i--);
            }
        }

        Collections.sort(apps, new Comparator<ResolveInfo>() {
            @Override
            public int compare(ResolveInfo lhs, ResolveInfo rhs) {
                PackageManager pm = context.getPackageManager();
                try {
                    PackageInfo lpi = pm.getPackageInfo(lhs.activityInfo.packageName, 0);
                    PackageInfo rpi = pm.getPackageInfo(rhs.activityInfo.packageName, 0);
                    if (lpi.firstInstallTime > rpi.firstInstallTime)
                        return 1;
                    if (lpi.firstInstallTime < rpi.firstInstallTime)
                        return -1;

                } catch (NameNotFoundException e) {
                    e.printStackTrace();
                }
                return 0;
            }
        });

        return apps;
    }

    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    public static void launch(Context context, ApplicationInfo appInfo) {
        PackageManager pm = context.getPackageManager();
        Intent i = pm.getLaunchIntentForPackage(appInfo.packageName);
        if (null != i)
            context.startActivity(i);
    }

    public static void launch(Context context, ResolveInfo appInfo) {
        Intent i = new Intent();
        i.addCategory(Intent.CATEGORY_LAUNCHER);
        i.setClassName(appInfo.activityInfo.packageName, appInfo.activityInfo.name);
        context.startActivity(i);
    }

    public static void uninstall(Context context, ApplicationInfo appInfo) {
        Uri packageURI = Uri.parse("package:" + appInfo.packageName);
        Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageURI);
        context.startActivity(uninstallIntent);
    }

    public static long getPublicSourceSize(Context context, ApplicationInfo appInfo) {
        return new File(appInfo.publicSourceDir).length();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        String data = intent.getData().getSchemeSpecificPart();
        boolean replacing = intent.getBooleanExtra(Intent.EXTRA_REPLACING, false);
        Log.i("AppManager", "getAction(): " + action + ", data: " + data + ", replacing:" + replacing);
        if (Intent.ACTION_PACKAGE_ADDED.equals(action) && !replacing) {
            for (IAppObserver obs : observers) {
                obs.onApplicationInstalled(intent, data);
            }
        } else if (Intent.ACTION_PACKAGE_REMOVED.equals(action) && !replacing) {
            for (IAppObserver obs : observers) {
                obs.onApplicationUninstalled(intent, data);
            }
        } else if (Intent.ACTION_PACKAGE_REPLACED.equals(action)) {
            for (IAppObserver cb : observers) {
                cb.onApplicationUpdated(intent, data);
            }
        } else if (Intent.ACTION_PACKAGE_CHANGED.equals(action)) {
            //TODO:
        } else if (Intent.ACTION_EXTERNAL_APPLICATIONS_AVAILABLE.equals(action)) {
            //TODO:
        } else if (Intent.ACTION_EXTERNAL_APPLICATIONS_UNAVAILABLE.equals(action)) {
            //TODO:
        } else if (Intent.ACTION_LOCALE_CHANGED.equals(action)) {
            //TODO:
        } else if (Intent.ACTION_CONFIGURATION_CHANGED.equals(action)) {
            //TODO:
        }
    }

    public static boolean launchSetting(Context context) {
        Intent intent = new Intent();
        if (0 <= Build.MODEL.indexOf("sw-")) {
            intent.setAction("net.sunniwell.action.SWSETTINGS");
            intent.putExtra("index", 0);
        } else if (0 <= Build.ID.indexOf("BesTV")) {
            intent.setClassName("com.bestv.setting", "com.bestv.setting.SettingActivity");
        } else if (0 <= Build.MODEL.indexOf("CoolTV")) {
            intent.setClassName("com.android.settings", "com.android.settings.yulong.cooltv.NormalSetting");
        } else if (0 <= Build.BRAND.indexOf("MStar")) {
            intent.setClassName("com.tcl.mstar.settings", "com.tcl.mstar.settings.TclSettings");
        } else if (0 <= Build.BRAND.indexOf("Hisense")) {
            intent.setClassName("com.hisense.settings", "com.hisense.settings.SettingActivity");
        } else if (!TextUtils.isEmpty(getSystemProperty("letv.hw.ver"))) {
            intent.setClassName("com.letv.t2.globalsetting", "com.letv.t2.globalsetting.GlobalSettingActivity");
        } else if (!TextUtils.isEmpty(getSystemProperty("ro.product.name"))
                && 0 <= getSystemProperty("ro.product.name").indexOf("SkyV60064bit")) {
            intent.setClassName("com.tianci.setting", "com.tianci.setting.TianciSetting");
        }

        try {
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            return tryLaunchSetting(context);
        }
        return true;
    }

    public static String getSystemProperty(String propKey) {
        String propValue = null;
        try {
            Class getSysProp = Class.forName("android.os.SystemProperties");
            Method method = getSysProp.getDeclaredMethod("get", String.class);
            propValue = (String) method.invoke(null, propKey);
        } catch (ClassNotFoundException e) {
        } catch (NoSuchMethodException e) {
        } catch (IllegalArgumentException e) {
        } catch (IllegalAccessException e) {
        } catch (InvocationTargetException e) {
        }
        return propValue;
    }

    public static boolean tryLaunchSetting(Context context) {
        if (launchSettingBaidu(context)) return true;
        if (launchSettingDefault(context)) return true;
        return false;
    }

    private static boolean launchSettingBaidu(Context context) {
        try {
            Intent intent = new Intent();
            intent.setClassName("com.baidu.tv.settings", "com.baidu.tv.settings.MainActivity");
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private static boolean launchSettingDefault(Context context) {
        try {
            Intent intent = new Intent();
            intent.setAction("android.settings.SETTINGS");
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private static final String TAG = "AppManager";
    
    public static boolean launchNetworkSetting(Context context) {
        Intent intent = new Intent();
        if (0 == Build.MODEL.indexOf("sw-")) {
            android.util.Log.d(TAG, "launchNetworkSetting: 11111");
            intent.setAction("android.settings.SETTINGS");
            intent.setAction("net.sunniwell.action.SWSETTINGS");
            intent.putExtra("index", 1);
        } else if (0 == Build.ID.indexOf("BesTV")) {
            android.util.Log.d(TAG, "launchNetworkSetting: 22222");
            intent.setAction("com.bestv.setting.netSetting");
            intent.addCategory("android.intent.category.DEFAULT");
            intent.putExtra("setting_type", 1);
        } else if (isYunOS(context)) {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = cm.getActiveNetworkInfo();
            if (null == info || info.getType() == ConnectivityManager.TYPE_WIFI) {
                intent.setClassName("com.android.settings", "com.android.settings.network.wifi.WifiSettingActivity");
            } else if (info.getType() == ConnectivityManager.TYPE_ETHERNET) {
                intent.setClassName("com.android.settings", "com.android.settings.network.ethernet.EthernetSettingActivity");
            }
        } else if (!TextUtils.isEmpty(getSystemProperty("ro.product.name"))
                && 0 <= getSystemProperty("ro.product.name").indexOf("SkyV60064bit")) {
            android.util.Log.d(TAG, "launchNetworkSetting: 3333");
            intent.setClassName("com.tianci.setting", "com.tianci.setting.TianciSetting");
        }else if(Build.MODEL.equals("VIDAA_TV")){
            intent.setClassName("com.android.tv.settings","com.hisense.systemsettings.ConnectitySettingsActivity");
        }else if(Build.MODEL.equals("konka KKHi3751AV500")) {
            intent.setClassName("com.konka.systemsetting", "com.konka.systemsetting.MainActivity");
        }else if(Build.MODEL.equals("H2903")){
            intent.setClassName("com.skyworthdigital.settings", "com.skyworthdigital.settings.MainActivity");
        }else if(0 == Build.MODEL.indexOf("AOSP")){
            android.util.Log.d(TAG, "tcl: 3333");
            intent.setClassName("com.tcl.settings","com.tcl.settings.MainActivity");
        }else {
            android.util.Log.d(TAG, "launchNetworkSetting: 44444");
            intent.setAction("android.settings.WIFI_SETTINGS");
        }
       //com.tianci.setting.activity.ConnectSetting
        try {
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public static boolean isYunOS(Context context) {
        PackageManager manager = context.getPackageManager();
        try {
            manager.getApplicationInfo("com.yunos.juhuasuan", 0);
            return true;
        } catch (NameNotFoundException e) { }
        try {
            manager.getApplicationInfo("com.yunos.chaoshi", 0);
            return true;
        } catch (NameNotFoundException e) { }
        try {
            manager.getApplicationInfo("com.yunos.account", 0);
            return true;
        } catch (NameNotFoundException e) { }

        return false;
    }

    @SuppressLint("NewApi")
    public static boolean isSony() {
        return (!TextUtils.isEmpty(Build.MANUFACTURER)
                && !TextUtils.isEmpty(Build.DEVICE)
                && 0 <= Build.MANUFACTURER.indexOf("Sony")
                && (0 <= Build.DEVICE.indexOf("SVP-DTV15")
                || 0 <= Build.DEVICE.indexOf("Sakura_CHD")));
    }

    public static void install(Context context, String apkPath) {
        String path = apkPath;
        if (apkPath.indexOf("file://") < 0)
            path = "file://" + apkPath;
        Uri uri = Uri.parse(path);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, "application/vnd.android.package-archive");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
