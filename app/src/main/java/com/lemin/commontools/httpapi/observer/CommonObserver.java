package com.lemin.commontools.httpapi.observer;


import android.app.Dialog;

import com.lemin.commontools.httpapi.RxHttpUtils;
import com.lemin.commontools.httpapi.base.BaseObserver;

import io.reactivex.disposables.Disposable;

/**
 *         通用的Observer
 *         用户可以根据自己需求自定义自己的类继承BaseObserver<T>即可
 */

public abstract class CommonObserver<T> extends BaseObserver<T> {


    private Dialog mProgressDialog;

    public CommonObserver() {
    }

    public CommonObserver(Dialog progressDialog) {
        mProgressDialog = progressDialog;
    }

    /**
     * 失败回调
     * @param errorCode 错误码
     * @param errorMsg 错误信息
     */
    protected abstract void onError(int errorCode,String errorMsg);

    /**
     * 成功回调
     *
     * @param t
     */
    protected abstract void onSuccess(T t);


    @Override
    public void doOnSubscribe(Disposable d) {
        RxHttpUtils.addDisposable(d);
    }

    @Override
    public void doOnError(int errorCode, String errorMsg) {
        dismissLoading();
        onError(errorCode,errorMsg);
    }

    @Override
    public void doOnNext(T t) {
        onSuccess(t);
    }

    @Override
    public void doOnCompleted() {
        dismissLoading();
    }

    /**
     * 隐藏loading对话框
     */
    private void dismissLoading() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }
}
