package com.lemin.commontools.helper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.widget.ImageView;

import com.lemin.commontools.R;
import com.lemin.commontools.base.BaseApplication;
import com.lemin.commontools.helper.glide.GlideCircleTransform;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.previewlibrary.loader.IZoomMediaLoader;
import com.previewlibrary.loader.MySimpleTarget;



public class GlideHelper implements IZoomMediaLoader {

    /**
     * Glide加载z资源图片
     */
    public static void loadNetworkResource(String url, ImageView view) {
        Glide.with(BaseApplication.instance().getApplicationContext())
                .load(url)
//                .error(R.mipmap.icon_default)
//                .placeholder(R.mipmap.icon_default)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)//是将图片原尺寸缓存到本地。
                .centerCrop()
                .dontAnimate()
                .priority(Priority.HIGH)
                .into(view);
    }

    public static void loadImage(String url, ImageView view) {
        Glide.with(BaseApplication.instance().getApplicationContext())
                .load(url)
                .priority(Priority.HIGH)
                .into(view);
    }

    public static void loadImageCircle(String url, ImageView view) {
        Glide.with(BaseApplication.instance().getApplicationContext())
                .load(url)
                .priority(Priority.HIGH)
                .transform(new GlideCircleTransform(view.getContext()))
                .into(view);
    }

    /**
     * Glide加载z资源图片
     */
    public static void loadLocalResource(int resourceId, ImageView view) {
        Glide.with(BaseApplication.instance().getApplicationContext())
                .load(resourceId)
                .centerCrop()
                .dontAnimate()
                .priority(Priority.HIGH)
                .into(view);
    }

    /**
     * Glide加载z资源图片
     */
    public static void loadLocalResource(String path, ImageView view) {
        Glide.with(BaseApplication.instance().getApplicationContext())
                .load(path)
                .centerCrop()
                .dontAnimate()
                .priority(Priority.HIGH)
                .into(view);
    }


    public static void trimMemory(Context context) {
        // trim memory
        Glide.get(context).clearMemory();
    }

    public static void lowMemory(Context context) {
        // low memory clear Glide cache
        Glide.get(context).clearMemory();
    }

    @Override
    public void displayImage(@NonNull Fragment context, @NonNull String path, @NonNull final MySimpleTarget<Bitmap> simpleTarget) {
        Glide.with(context)
                .load(path)
                .asBitmap()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
//                .error(R.mipmap.icon_default)
//                .placeholder(R.mipmap.icon_default)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        simpleTarget.onResourceReady(resource);
                    }

                    @Override
                    public void onLoadStarted(Drawable placeholder) {
                        super.onLoadStarted(placeholder);
                        simpleTarget.onLoadStarted();
                    }

                    @Override
                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
                        super.onLoadFailed(e, errorDrawable);
                        simpleTarget.onLoadFailed(errorDrawable);
                    }
                });
    }

    @Override
    public void onStop(@NonNull Fragment context) {
        Glide.with(context).onStop();
    }

    @Override
    public void clearMemory(@NonNull Context c) {
        Glide.get(c).clearMemory();
    }
}
