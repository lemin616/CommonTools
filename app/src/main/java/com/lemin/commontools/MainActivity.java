package com.lemin.commontools;



import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.lemin.commontools.base.BaseActivity;

public class MainActivity extends BaseActivity {

    @Override
    public void showError(String msg) {

    }

    @Override
    public void initData(Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        immersionBar.fitsSystemWindows(true)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();
    }

    @Override
    public void initView(Bundle savedInstanceState, View view) {

    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void doBusiness() {

    }
}