package com.lemin.commontools.httpapi.base;


import com.lemin.commontools.httpapi.exception.ApiException;
import com.lemin.commontools.httpapi.interfaces.ISubscriber;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * 基类BaseObserver
 */

public abstract class BaseObserver<T> implements Observer<T>, ISubscriber<T> {

    @Override
    public void onSubscribe(@NonNull Disposable d) {
        doOnSubscribe(d);
    }

    @Override
    public void onNext(@NonNull T t) {
        doOnNext(t);
    }

    @Override
    public void onError(@NonNull Throwable e) {
        String error = ApiException.handleException(e).getMessage();
        setError(-1, error);
    }

    @Override
    public void onComplete() {
        doOnCompleted();
    }


    private void setError(int errorCode, String errorMsg) {
        doOnError(errorCode, errorMsg);
    }

}
