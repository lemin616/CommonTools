package com.lemin.commontools.base;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;


import com.lemin.commontools.helper.LoadingHelper;
import com.lemin.commontools.helper.ToastHelper;
import com.lemin.commontools.helper.picture.permission.DefaultRationale;
import com.lemin.commontools.helper.picture.permission.PermissionSetting;
import com.lemin.commontools.listener.IBaseView;
import com.lemin.commontools.utils.DD;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Rationale;
import com.gyf.barlibrary.ImmersionBar;

import java.util.List;



public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity implements IBaseView, BaseView {
    /**
     * 当前 Activity 渲染的视图 View
     */
    protected View mContentView;

    protected Activity mActivity;

    protected ImmersionBar immersionBar;

    protected T mPresenter;

    protected Rationale mRationale;
    protected PermissionSetting mSetting;
    private AlertDialog loading;

    public static int anInt=0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DD.dd("ActivityName", getClass().getName());
        mActivity = this;
        initData(getIntent().getExtras());
        setBaseView(bindLayout());
        initImmersionBar();
        initView(savedInstanceState, mContentView);
        initPresenter();
        if (mPresenter != null) {
            mPresenter.attachView(this);
            mPresenter.attachContext(this);
        }
        BaseApplication.instance().addActivity(this);
        doBusiness();
    }

    private void setBaseView(@LayoutRes int layoutId) {
        mContentView = LayoutInflater.from(this).inflate(layoutId, null);
        setContentView(mContentView);
    }

    protected void initImmersionBar() {
        immersionBar = ImmersionBar.with(this);
        immersionBar.init();
    }

    @Override
    public void showToast(String msg) {
        ToastHelper.show(msg);
    }

    /**
     * 权限申请
     *
     * @param permissions
     */
    protected void requestPermission(String[]... permissions) {
        mRationale = new DefaultRationale();
        mSetting = new PermissionSetting(this);
        AndPermission.with(this)
                .runtime()
                .permission(permissions)
                .rationale(mRationale)
                .onGranted(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> permissions) {
                        applyPermissionResult(true, permissions);
                    }
                })
                .onDenied(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> permissions) {
                        applyPermissionResult(false, permissions);
                        if (AndPermission.hasAlwaysDeniedPermission(BaseActivity.this, permissions)) {
                            mSetting.showSetting(permissions);
                        }
                    }
                }).start();
    }

    /**
     * 权限申请
     *
     * @param isSuccess
     */
    protected void applyPermissionResult(boolean isSuccess, List<String> deniedPermissions) {
    }

    @Override
    public void onBackPressed() {
        BaseApplication.instance().removeActivity(this);
        super.onBackPressed();
    }

    @Override
    public void onDestroy() {
        if (immersionBar != null) {
            immersionBar.destroy();
        }  //必须调用该方法，防止内存泄漏，不调用该方法，如果界面bar发生改变，在不关闭app的情况下，退出此界面再进入将记忆最后一次bar改变的状态
        if (mPresenter != null)
            mPresenter.detachView();
        BaseApplication.instance().removeActivity(this);
        super.onDestroy();
    }

    protected void showLoading() {
        LoadingHelper.dismiss(loading);
        loading = LoadingHelper.show(this);
    }

    protected void hideLoading() {
        LoadingHelper.dismiss(loading);
    }

    public void hideInput() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        anInt++;
    }

    @Override
    protected void onPause() {
        super.onPause();
        anInt--;
    }
    @Override
    protected void onStart() {
        super.onStart();
        anInt++;
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
