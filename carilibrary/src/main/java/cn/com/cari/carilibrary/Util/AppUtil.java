/**
 *
 */
package cn.com.cari.carilibrary.Util;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;

import java.util.List;

/**
 * @author zhouzhou
 * @Title: Ch4Util
 * @Description:程序系统相关配置
 * @Company: www.cari.com.cn
 * @date 2015-9-24 下午4:07:39
 */
public class AppUtil {
    public static String USER_ID;
    public static boolean isDebuge;

    /**
     * 桌面创建快捷方式
     **/
    public static void createLauncherIcon(Context context, Class NextActivity, String app_name, int ic_launcher) {

        if (!hasShortcut(context)) {
            Intent shortcut = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
            // 不允许重建
            shortcut.putExtra("duplicate", false);
            // 设置名字
            shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, app_name);
            // 设置图标
            shortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, Intent.ShortcutIconResource.fromContext(context, ic_launcher));
            // 设置意图和快捷方式关联程序
            // 设置关联程序
            Intent launcherIntent = new Intent(Intent.ACTION_MAIN);
            launcherIntent.setClass(context, NextActivity);
            launcherIntent.addCategory(Intent.CATEGORY_LAUNCHER);
            shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, launcherIntent);
            // 发送广播
            context.sendBroadcast(shortcut);
        }
    }

    /**
     * 是否存在桌面创建快捷方式
     **/
    public static boolean hasShortcut(Context context) {
        boolean result = false;
        String title = null;
        try {
            final PackageManager pm = context.getPackageManager();
            title = pm.getApplicationLabel(pm.getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA)).toString();
        } catch (Exception e) {
        }

        final String uriStr;
        if (android.os.Build.VERSION.SDK_INT < 8) {
            uriStr = "content://com.android.launcher.settings/favorites?notify=true";
        } else {
            uriStr = "content://com.android.launcher2.settings/favorites?notify=true";
        }
        final Uri CONTENT_URI = Uri.parse(uriStr);
        final Cursor c = context.getContentResolver().query(CONTENT_URI, null, "title=?", new String[]{title}, null);
        if (c != null && c.getCount() > 0) {
            result = true;
        }
        return result;
    }

    /**
     * 获取系统版本名称
     *
     * @param cx
     * @return
     */
    public static String getVersionName(Context cx)  {
        String versionName = null;
        PackageManager packageManager = cx.getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = null;
        try {
            packInfo = packageManager.getPackageInfo(cx.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        versionName = packInfo.versionName;
        String packageName = packInfo.packageName;
        int versionCode = packInfo.versionCode;
        ApplicationInfo appInfo = packInfo.applicationInfo;
        Drawable icon = packageManager.getApplicationIcon(appInfo);
        return versionName;
    }


    /**
     * 获取版本号
     * @param cx
     * @return
     * @throws PackageManager.NameNotFoundException
     */
    public static int getVersion(Context cx) {
        PackageManager packageManager = cx.getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = null;
        try {
            packInfo = packageManager.getPackageInfo(cx.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return packInfo.versionCode;
    }

    /**
     * 获取程序包名
     *
     * @param context
     * @return
     * @throws PackageManager.NameNotFoundException
     */
    public static String packageName(Context context){
        PackageManager packageManager = context.getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = null;
        try {
            packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return packInfo.packageName;
    }

    /**
     * 判断当前页面是否为顶部页面
     *
     * @param context
     * @return
     */
    private boolean isTopActivity(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
        Log.d("", "pkg:" + cn.getPackageName());
        Log.d("", "cls:" + cn.getClassName());
        return true;
    }

    /**
     * 判断当前程序是否在后台运行
     *
     * @param context
     * @return
     */
    public static boolean isBackground(Context context) {
        boolean result = false;
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(context.getPackageName())) {
                if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_BACKGROUND) {
                    Log.i("后台", appProcess.processName);
//                    return true;
                } else {
                    Log.i("前台", appProcess.processName);
//                    return false;
                    result = true;
                }
            }
        }
        return result;
    }

    /**
     * 判断当前程序是否在运行
     *
     * @param context
     * @return
     */
    public static boolean isRunning(Context context) {
        boolean result = false;
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(context.getPackageName())) {
                result = true;
            }
        }
        return result;
    }
}
