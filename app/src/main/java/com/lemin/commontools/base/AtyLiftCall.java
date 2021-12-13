package com.lemin.commontools.base;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

public class AtyLiftCall implements Application.ActivityLifecycleCallbacks {
    public int count;


    public boolean isFront() {
        return count > 0;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(Activity activity) {
        count++;
    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {
        count--;
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }
}
