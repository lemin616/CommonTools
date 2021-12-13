package com.lemin.commontools.helper;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;



public class SpaceItemDecoration extends RecyclerView.ItemDecoration {
    private int space;

    public SpaceItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

//        if (parent.getChildPosition(view) != 0)
//            outRect.top = space;

        if (parent.getPaddingLeft() != space) {
            parent.setPadding(space, space, space, space);
            parent.setClipToPadding(false);
        }

        outRect.top = space;
        outRect.left = space;
        outRect.right = space;

    }
}
