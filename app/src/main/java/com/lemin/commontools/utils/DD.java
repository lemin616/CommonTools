package com.lemin.commontools.utils;

import android.app.Application;
import android.util.Log;

import com.lemin.commontools.BuildConfig;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.LogcatLogStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;




public class DD {
    public static void dd(String tag, String info) {
        if (BuildConfig.BUILD_TYPE.equals("debug")) {
            Log.e("==" + tag + "==", "666Msg:" + info);
        }
    }
    public static void dd( String info) {
        dd("DD",info);
    }

    public static void json(String json){
        Logger.t("JSON").json(json);
    }

    // init it in ur application
    public static void initALog(Application app) {
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(true)  // (Optional) Whether to show thread info or not. Default true
                .methodCount(2)         // (Optional) How many method line to show. Default 2
                .methodOffset(7)        // (Optional) Hides internal method calls up to offset. Default 5
                .logStrategy(new LogcatLogStrategy()) // (Optional) Changes the log strategy to print out. Default LogCat
                .tag("Alog")   // (Optional) Global tag for every log. Default PRETTY_LOGGER
                .build();

        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));
    }
}
