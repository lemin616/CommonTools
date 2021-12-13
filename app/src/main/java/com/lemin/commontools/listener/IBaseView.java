package com.lemin.commontools.listener;

import android.os.Bundle;
import android.view.View;



public interface IBaseView {
    /**
     * 初始化数据
     * @param bundle 传递过来的 bundle
     */
    void initData(Bundle bundle);

    /**
     * 绑定布局

     * @return 布局 Id
     */
    int bindLayout();

    /**
     * 初始化 view
     */
    void initView(Bundle savedInstanceState, View view);

    /**
     * 初始化 Presenter
     */
    void initPresenter();

    /**
     * 业务操作
     */
    void doBusiness();

    /**
     * Toast
     * @param msg
     */
    void showToast(String msg);
}
