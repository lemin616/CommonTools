package com.lemin.commontools.helper;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;



public class VersionHelper {

    public static String getVersionName(Context mContext) {
        try {
            // 获取packagemanager的实例
            PackageManager packageManager = mContext.getPackageManager();
            // getPackageName()是你当前类的包名，0代表是获取版本信息
            PackageInfo packInfo = packageManager.getPackageInfo(mContext.getPackageName(), 0);
            String version = packInfo.versionName;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "1.0.0";
    }

    public static int getVersionCode(Context mContext) throws Exception {
        try {
            // 获取packagemanager的实例
            PackageManager packageManager = mContext.getPackageManager();
            // getPackageName()是你当前类的包名，0代表是获取版本信息
            PackageInfo packInfo = packageManager.getPackageInfo(mContext.getPackageName(), 0);
            int version = packInfo.versionCode;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
