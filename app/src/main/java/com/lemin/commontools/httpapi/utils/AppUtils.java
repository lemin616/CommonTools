package com.lemin.commontools.httpapi.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.lemin.commontools.helper.SPHelper;
import com.lemin.commontools.httpapi.RxHttpUtils;

import java.util.UUID;

/**
 * 关于应用的工具类
 */

public class AppUtils {

    /**
     * 获取手机版本号
     *
     * @return 返回版本号
     */
    public static String getAppVersion() {
        PackageInfo pi;
        String versionNum;
        try {
            PackageManager pm = RxHttpUtils.getContext().getPackageManager();
            pi = pm.getPackageInfo(RxHttpUtils.getContext().getPackageName(), PackageManager.GET_CONFIGURATIONS);
            versionNum = pi.versionName;
        } catch (Exception e) {
            versionNum = "0";
        }
        return versionNum;
    }

    /**
     * 获取手机唯一标识码UUID
     *
     * @return 返回UUID
     * <p>
     * 记得添加相应权限
     * android.permission.READ_PHONE_STATE
     */
    public static String getUUID() {

        Context context = RxHttpUtils.getContext();

        String uuid = SPHelper.get("PHONE_UUID", "");

        if (TextUtils.isEmpty(uuid)) {

            try {
                TelephonyManager telephonyManager = (TelephonyManager) context
                        .getSystemService(Context.TELEPHONY_SERVICE);
                @SuppressLint({"MissingPermission", "HardwareIds"}) String tmDevice = telephonyManager.getDeviceId();
                @SuppressLint({"MissingPermission", "HardwareIds"}) String tmSerial = telephonyManager.getSimSerialNumber();

                @SuppressLint("HardwareIds") String androidId = android.provider.Settings.Secure.getString(context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
                UUID deviceUuid = new UUID(androidId.hashCode(), ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
                String uniqueId = deviceUuid.toString();
                uuid = uniqueId;
                SPHelper.put("PHONE_UUID", uuid);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        return uuid;

    }
}
