package com.lemin.commontools.widget.popup;

import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;

import com.lemin.commontools.listener.IActionItem;




public class PopupMenuItem implements IActionItem {
    public int id;
    public CharSequence title;
    @DrawableRes
    public int iconRes;

    public PopupMenuItem(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public PopupMenuItem(int id, String title, @DrawableRes int iconRes) {
        this.id = id;
        this.title = title;
        this.iconRes = iconRes;
    }

    @Override
    public void setItemTitle(@NonNull CharSequence itemTitle) {
        this.title = itemTitle;
    }

    @Override
    public void setItemIcon(@DrawableRes int drawableRes) {
        this.iconRes = drawableRes;
    }

    @NonNull
    @Override
    public CharSequence getItemTitle() {
        return title;
    }

    @Override
    public int getItemIcon() {
        return iconRes;
    }

    public int getId() {
        return id;
    }
}
