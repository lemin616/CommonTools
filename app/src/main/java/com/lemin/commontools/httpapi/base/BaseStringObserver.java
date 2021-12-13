package com.lemin.commontools.httpapi.base;

import com.lemin.commontools.httpapi.exception.ApiException;
import com.lemin.commontools.httpapi.interfaces.IStringSubscriber;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 *         结果不做处理直接返回string
 */

public abstract class BaseStringObserver implements Observer<String>, IStringSubscriber {

    @Override
    public void onSubscribe(Disposable d) {
        doOnSubscribe(d);
    }

    @Override
    public void onNext(String string) {
        doOnNext(string);
    }

    @Override
    public void onError(Throwable e) {
        String error = ApiException.handleException(e).getMessage();
        doOnError(error);
    }

    @Override
    public void onComplete() {
        doOnCompleted();
    }

}
