package com.lemin.commontools.widget.node;

import java.util.List;



public interface NodeProgressAdapter<T> {
    /**
     * 返回集合大小
     *
     * @return
     */
    int getCount();

    /**
     * 适配数据的集合
     *
     * @return
     */
    List<T> getData();
}
