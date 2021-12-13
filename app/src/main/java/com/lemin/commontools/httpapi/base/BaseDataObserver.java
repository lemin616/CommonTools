package com.lemin.commontools.httpapi.base;

import com.lemin.commontools.httpapi.bean.BaseData;
import com.lemin.commontools.httpapi.exception.ApiException;
import com.lemin.commontools.httpapi.interfaces.IDataSubscriber;
import com.lemin.commontools.utils.DD;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 基类BaseObserver使用BaseData
 */

public abstract class BaseDataObserver<T extends BaseData> implements Observer<T>, IDataSubscriber<T> {

    @Override
    public void onSubscribe(Disposable d) {
        doOnSubscribe(d);
    }

    @Override
    public void onNext(T baseData) {
//        DD.dd("onNext", new Gson().toJson(baseData));
        doOnNext(baseData);
    }

    @Override
    public void onError(Throwable e) {
        String error = ApiException.handleException(e).getMessage();
        int code = ApiException.handleException(e).getCode();
        DD.dd("onError", code + " " + error);
        setError(code, error);
    }

    @Override
    public void onComplete() {
        doOnCompleted();
    }

    private void setError(int errorCode, String errorMsg) {
        doOnError(errorCode, errorMsg);
    }

}
