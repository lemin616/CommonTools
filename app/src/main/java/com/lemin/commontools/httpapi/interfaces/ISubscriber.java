package com.lemin.commontools.httpapi.interfaces;

import io.reactivex.disposables.Disposable;

/**
 *         定义请求结果处理接口
 */

public interface ISubscriber<T> {

    /**
     * doOnSubscribe 回调
     *
     * @param d Disposable
     */
    void doOnSubscribe(Disposable d);

    /**
     * 错误回调
     * @param errorCode 错误码
     * @param errorMsg 错误信息
     */
    void doOnError(int errorCode ,String errorMsg);

    /**
     * 成功回调
     *
     * @param t 泛型
     */
    void doOnNext(T t);

    /**
     * 请求完成回调
     */
    void doOnCompleted();
}
