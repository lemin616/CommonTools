package com.lemin.commontools.helper;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.media.Image;
import android.media.ImageReader;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;

import com.lemin.commontools.base.BaseApplication;
import com.lemin.commontools.utils.DD;

import java.io.File;
import java.lang.ref.SoftReference;
import java.nio.ByteBuffer;



public class ShotterHelper {

    private final SoftReference<Context> mRefContext;
    private ImageReader mImageReader;

    private MediaProjection mMediaProjection;
    private VirtualDisplay mVirtualDisplay;
    private OnShotListener mOnShotListener;

    public ShotterHelper(Context context, Intent data) {
        this.mRefContext = new SoftReference<>(context);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mMediaProjection = getMediaProjectionManager().getMediaProjection(Activity.RESULT_OK,
                    data);
            mImageReader = ImageReader.newInstance(
                    getScreenWidth(),
                    getScreenHeight(),
                    PixelFormat.RGBA_8888,//此处必须和下面 buffer处理一致的格式 ，RGB_565在一些机器上出现兼容问题。
                    1);
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void virtualDisplay() {
        mVirtualDisplay = mMediaProjection.createVirtualDisplay("screen-mirror",
                getScreenWidth(),
                getScreenHeight(),
                DisplayHelper.displayMetrics(BaseApplication.INSTANCE).densityDpi,
                DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR,
                mImageReader.getSurface(), null, null);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void startScreenShot(OnShotListener onShotListener) {
        mOnShotListener = onShotListener;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            virtualDisplay();
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Image image = mImageReader.acquireLatestImage();
                                        AsyncTaskCompat.executeParallel(new SaveTask(), image);
                                    }
                                },
                    300);
        }
    }

    class SaveTask extends AsyncTask<Image, Void, Bitmap> {

        @TargetApi(Build.VERSION_CODES.KITKAT)
        @Override
        protected Bitmap doInBackground(Image... params) {
            if (params == null || params.length < 1 || params[0] == null) {
                return null;
            }

            Image image = params[0];

            int width = image.getWidth();
            int height = image.getHeight();
            final Image.Plane[] planes = image.getPlanes();
            final ByteBuffer buffer = planes[0].getBuffer();
            //每个像素的间距
            int pixelStride = planes[0].getPixelStride();
            //总的间距
            int rowStride = planes[0].getRowStride();
            int rowPadding = rowStride - pixelStride * width;
            Bitmap bitmap = Bitmap.createBitmap(width + rowPadding / pixelStride, height,
                    Bitmap.Config.ARGB_8888);//虽然这个色彩比较费内存但是 兼容性更好
            bitmap.copyPixelsFromBuffer(buffer);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height);
            image.close();
            File fileImage = null;
            if (bitmap != null) {
                fileImage = FileHelper.createImageFileDir(2);
                BitmapHelper.saveBitmap(fileImage, bitmap);
            }
            DD.dd("ScreenPath", fileImage.getAbsolutePath());
            if (fileImage != null) {
                return bitmap;
            }
            return null;
        }

        @TargetApi(Build.VERSION_CODES.KITKAT)
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);

            if (bitmap != null && !bitmap.isRecycled()) {
                bitmap.recycle();
            }

            if (mVirtualDisplay != null) {
                mVirtualDisplay.release();
            }

            if (mOnShotListener != null) {
                mOnShotListener.onFinish();
            }

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private MediaProjectionManager getMediaProjectionManager() {
        return (MediaProjectionManager) getContext().getSystemService(
                Context.MEDIA_PROJECTION_SERVICE);
    }

    private Context getContext() {
        return mRefContext.get();
    }

    private int getScreenWidth() {
        return DisplayHelper.screenW();
    }

    private int getScreenHeight() {
        return DisplayHelper.screenH();
    }

    // a  call back listener
    public interface OnShotListener {
        void onFinish();
    }

    static class AsyncTaskCompat {

        /**
         * Executes the task with the specified parameters, allowing multiple tasks to run in parallel
         * on a pool of threads managed by {@link AsyncTask}.
         *
         * @param task   The {@link AsyncTask} to execute.
         * @param params The parameters of the task.
         * @return the instance of AsyncTask.
         */
        static <Params, Progress, Result> AsyncTask<Params, Progress, Result> executeParallel(
                AsyncTask<Params, Progress, Result> task, Params... params) {
            if (task == null) {
                throw new IllegalArgumentException("task can not be null");
            }

            // From API 11 onwards, we need to manually select the THREAD_POOL_EXECUTOR
            AsyncTaskCompatHoneycomb.executeParallel(task, params);

            return task;
        }
    }

    static class AsyncTaskCompatHoneycomb {

        static <Params, Progress, Result> void executeParallel(
                AsyncTask<Params, Progress, Result> task, Params... params) {
            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, params);
        }

    }
}
