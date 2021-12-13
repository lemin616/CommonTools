package com.lemin.commontools.httpapi.observer;

import android.app.Dialog;

import com.lemin.commontools.httpapi.RxHttpUtils;
import com.lemin.commontools.httpapi.base.BaseDataObserver;
import com.lemin.commontools.httpapi.bean.BaseData;
import com.lemin.commontools.utils.DD;

import io.reactivex.disposables.Disposable;

/**
 * 针对特定格式的时候设置的通用的Observer
 * 用户可以根据自己需求自定义自己的类继承BaseDataObserver<T>即可
 */

public abstract class DataObserver<T extends BaseData> extends BaseDataObserver<T> {

    private Dialog mProgressDialog;

    public DataObserver() {
    }

    public DataObserver(Dialog progressDialog) {
        mProgressDialog = progressDialog;
    }

    /**
     * 失败回调
     *
     * @param errorCode 错误码
     * @param errorMsg  错误信息
     */
    protected abstract void onError(int errorCode, String errorMsg);

    /**
     * 成功回调
     *
     * @param data 结果
     */
    protected abstract void onSuccess(T data);


    @Override
    public void doOnSubscribe(Disposable d) {
        RxHttpUtils.addDisposable(d);
    }

    @Override
    public void doOnError(int errorCode, String errorMsg) {
        dismissLoading();
        onError(errorCode, errorMsg);
    }

    @Override
    public void doOnNext(T data) {
//        onSuccess(data.getData());
        //可以根据需求对code统一处理
        DD.dd("doOnNext", data.getCode() + " " + data.getMsg());
        switch (data.getCode()) {
            case 0:
                onSuccess(data);
                break;
            default:
                onError(data.getCode(), data.getMsg());
                break;
        }
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
