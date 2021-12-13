package com.lemin.commontools.base;

import android.app.Activity;
import android.content.ComponentCallbacks2;
import android.support.multidex.MultiDexApplication;


import com.lemin.commontools.helper.GlideHelper;
import com.lemin.commontools.helper.SPHelper;
import com.lemin.commontools.helper.StringHelper;
import com.lemin.commontools.helper.ToastHelper;
import com.lemin.commontools.httpapi.RxHttpUtils;
import com.lemin.commontools.utils.Constant;
import com.previewlibrary.ZoomMediaLoader;

import java.util.Iterator;
import java.util.Stack;



public abstract class BaseApplication extends MultiDexApplication {

    public static BaseApplication INSTANCE;
    public Stack<Activity> mActivitys;
    private static String token; // 缓存登陆成功返回的token
    private static AtyLiftCall atyLiftCall = new AtyLiftCall();

    public static boolean isFront() {
        return atyLiftCall.isFront();
    }

    public static BaseApplication instance() {
        return INSTANCE;
    }

    @Override
    public void onCreate() {
        super.onCreate();
//        if (BuildConfig.DEBUG) {
//            LeakCanary.install(this);
//        }
        registerActivityLifecycleCallbacks(atyLiftCall);
        INSTANCE = this;
        mActivitys = new Stack<>();
        ToastHelper.init(this);
        ZoomMediaLoader.getInstance().init(new GlideHelper());  //微信九宫格图片预览

        RxHttpUtils.init(this);
    }

    public static String getToken() {
        if (StringHelper.isEmpty(token)) {
            token = SPHelper.get(Constant.PREF_LOGIN_TOKEN, "");
        }
        return token;
    }

    public static void setToken(String token) {
        BaseApplication.token = token;
        // 同时保存到本地
        SPHelper.put(Constant.PREF_LOGIN_TOKEN, token);
    }

    /**
     * 添加Activity
     *
     * @param activity
     */
    public void addActivity(Activity activity) {
        if (null == activity) return;
        mActivitys.add(activity);
    }

    /**
     * 移除Activity
     *
     * @param activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            mActivitys.remove(activity);
            if (!activity.isFinishing())
                activity.finish();
        }
    }

    /**
     * 移除 除Activity之外的所有Activity
     *
     * @param activityName
     */
    public void onlyLiveActivity(String activityName) {
        Iterator<Activity> iterator = mActivitys.iterator();
        while (iterator.hasNext()) {
            Activity activity = iterator.next();
            if (!activity.getClass().getSimpleName().equals(activityName)) {
                iterator.remove();   //注意这个地方
                activity.finish();
            }
        }
        mActivitys.clear();
    }

    public void liveAty(String atyName) {
        Iterator<Activity> iterator = mActivitys.iterator();
        boolean isFind = false;
        while (iterator.hasNext()) {
            Activity activity = iterator.next();
            if (isFind) {
                iterator.remove();
                activity.finish();
            }
            if (activity.getClass().getSimpleName().equals(atyName)) {
                isFind = true;
            }
        }
    }

    /**
     * 移除Activity
     */
    public void removeAllActivity() {
        mActivitys.clear();
    }

    /**
     * 移除所有Activity
     */
    public void clearAll() {
        for (Activity activity : mActivitys) {
            if (!activity.isFinishing()) activity.finish();
        }
        mActivitys.clear();
    }

    /**
     * 移除Activity
     *
     * @param activity
     */
    public void removeActivity(Activity activity) {
        if (activity != null && mActivitys.contains(activity)) {
            mActivitys.remove(activity);
        }
    }

    /**
     * activity是否存在
     *
     * @param activity
     * @return
     */
    public boolean containActivity(Activity activity) {
        if (null == activity) return false;
        return mActivitys.contains(activity);
    }

    /**
     * activity是否存在
     *
     * @param clazz
     * @return
     */
    public boolean containActivity(Class<? extends Activity> clazz) {
        for (Activity activity : mActivitys) {
            if (activity.getClass().getName().equals(clazz.getName()))
                return true;
        }
        return false;
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        // clear Glide cache
        if (level == ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN) {
            GlideHelper.trimMemory(this);
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        // low memory clear Glide cache
        GlideHelper.lowMemory(this);
    }
}
