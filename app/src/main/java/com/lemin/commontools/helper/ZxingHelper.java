package com.lemin.commontools.helper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

public class ZxingHelper {
    private static boolean inited = false;
    public static final int REQUEST_CODE = 12345;

    public static void scan(Activity aty) {
        if (aty == null)
            return;
        initZxingLib(aty);
        Intent intent = new Intent(aty, CaptureActivity.class);
        aty.startActivityForResult(intent, REQUEST_CODE);
    }


    private static void initZxingLib(Activity aty) {
        if (inited)
            return;
        inited = true;
        ZXingLibrary.initDisplayOpinion(aty.getApplication());
    }

    public static String getResult(int reqcode, Intent data) {
        if (reqcode == REQUEST_CODE && null != data && data.getExtras() != null) {
            //处理扫描结果（在界面上显示）
            Bundle bundle = data.getExtras();
            if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS)
                return bundle.getString(CodeUtils.RESULT_STRING);
        }
        return "";
    }
}
