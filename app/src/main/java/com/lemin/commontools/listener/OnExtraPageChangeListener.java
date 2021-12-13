package com.lemin.commontools.listener;

/**
 * page切换额外功能接口
 */

public abstract class OnExtraPageChangeListener {

    public abstract void onExtraPageScrolled(int position, float positionOffset, int positionOffsetPixels);

    public abstract void onExtraPageSelected(int position);

    public abstract void onExtraPageScrollStateChanged(int state);
}
