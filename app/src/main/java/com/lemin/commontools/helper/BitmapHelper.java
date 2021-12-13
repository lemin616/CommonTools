package com.lemin.commontools.helper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;



public class BitmapHelper {
    /**
     * Bitmap对象保存图片文件
     *
     * @param path
     * @param bitmap
     */
    public static void saveBitmap(String path, Bitmap bitmap) {
        try {
            File file = new File(path);// 将要保存图片的路径
            FileOutputStream fos = new FileOutputStream(file);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Bitmap对象保存图片文件
     *
     * @param file
     * @param bitmap
     */
    public static void saveBitmap(File file, Bitmap bitmap) {
        try {
            FileOutputStream fos = new FileOutputStream(file);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 以最省内存的方式读取本地资源的图片
     * 当使用像 imageView.setBackgroundResource，imageView.setImageResource,
     * 或者 BitmapFactory.decodeResource 这样的方法来设置一张大图片的时候，
     * 这些函数在完成decode后，最终都是通过java层的createBitmap来完成的，需要消耗更多内存。
     * 因此，改用先通过BitmapFactory.decodeStream方法，创建出一个bitmap，
     * 再将其设为ImageView的 source，decodeStream最大的秘密在于其直接调用JNI>>nativeDecodeAsset()来完成decode，无需再使用java层的createBitmap，
     * 从而节省了java层的空间。如果在读取时加上图片的Config参数，可以跟有效减少加载的内存，从而跟有效阻止抛out of Memory异常。
     */
    public static Bitmap readBitmap(Context context, int resId) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        //获取资源图片
        InputStream is = context.getResources().openRawResource(resId);
        return BitmapFactory.decodeStream(is, null, opt);
    }

    /**
     * 加载本地图片
     *
     * @param url
     * @return
     */
    public static Bitmap readBitmap(String url) {
        try {
            FileInputStream fis = new FileInputStream(url);
            return BitmapFactory.decodeStream(fis);  ///把流转化为Bitmap图片
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 通过一个uri获取一个Bitmap对象
     *
     * @param uri
     * @param mContext
     * @return
     */
    public static Bitmap readBitmapFromUri(Uri uri, Context mContext) {
        try {
            // 读取uri所在的图片
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(mContext.getContentResolver(), uri);
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 读取图片属性：旋转的角度
     *
     * @param path 图片绝对路径
     * @return degree旋转的角度
     */
    public static int readBitmapDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * 旋转图片
     *
     * @param angle
     * @param bitmap
     * @return Bitmap
     */
//    public static Bitmap rotateBitmap(int angle, Bitmap bitmap) {
//        // 旋转图片 动作
//        Matrix matrix = new Matrix();
//        matrix.postRotate(angle);
//        // 创建新的图片
//        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
//                bitmap.getWidth(), bitmap.getHeight(), matrix, true);
//        return resizedBitmap;
//    }

    /**
     * 旋转图片
     *
     * @param angle  被旋转角度
     * @param bitmap 图片对象
     * @return 旋转后的图片
     */
    public static Bitmap rotateBitmap(int angle, Bitmap bitmap) {
        Bitmap returnBm = null;
        // 根据旋转角度，生成旋转矩阵
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        LogHelper.error("angle2==" + angle);
        try {
            // 将原始图片按照旋转矩阵进行旋转，并得到新的图片
            returnBm = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        } catch (OutOfMemoryError e) {
        }
        if (returnBm == null) {
            returnBm = bitmap;
        }
        if (bitmap != returnBm) {
            bitmap.recycle();
        }
        return returnBm;
    }

    public static void amendRotateBitmap(String path) {
        Bitmap bitmap = readBitmap(path);
        if (null == bitmap) return;
        int degree = readBitmapDegree(path);
        LogHelper.error("angle2=" + degree);
        Bitmap bt = rotateBitmap(degree, bitmap);
        saveBitmap(path, bt);
    }

    /**
     * 二次压缩bitmap
     *
     * @param srcPath
     * @return
     */
    public static Bitmap secondCompressBitmap(String srcPath) {
        BitmapFactory.Options options = getBitmapOption(srcPath);
        int w = options.outWidth;
        int h = options.outHeight;

        // 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float hh = 800f;// 这里设置高度为800f
        float ww = 480f;// 这里设置宽度为480f
        Bitmap bitmap = decodeBitmapFromPath(srcPath, hh, ww);

        LogHelper.error("===========", "压缩前图片尺寸 宽：" + bitmap.getWidth() + "\t高：" + bitmap.getHeight() + "\n图片所占用的内存空间：" + getBitmapSize(bitmap));

        int size = 0;
        if (bitmap.getWidth() >= bitmap.getHeight()) {
            size = (int) (bitmap.getWidth() / 400);
        } else if (w < h) {
            size = (int) (bitmap.getHeight() / 400);
        }

        if (size <= 0) {
            size = 1;
        }

        LogHelper.error("===========", "压缩比例：" + size + "\t压缩后的宽：" + bitmap.getWidth() / size + "\t压缩后的高:" + bitmap.getHeight() / size);
        Bitmap bm = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth() / size, bitmap.getHeight() / size, true);
        bm = compressBitmap(bm);
        LogHelper.error("===========", "压缩后图片尺寸 宽：" + bm.getWidth() + "高：" + bm.getHeight() + "\n图片所占用的内存空间：" + getBitmapSize(bm));
        return bm;// 压缩好比例大小后再进行质量压缩
    }

    /**
     * 先比例大小压缩(根据路径获取图片并压缩)，之后质量压缩
     */
    public static Bitmap decodeBitmapFromPath(String srcPath, float hh, float ww) {
        BitmapFactory.Options newOpts = getBitmapOption(srcPath);
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;

        if (hh == 0 && ww == 0) {
            //现在主流手机比较多是720*1280分辨率，所以高和宽我们设置为
            hh = 1280f;//这里设置高度为1280f
            ww = 720f;//这里设置宽度为720f
        }

        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;//设置缩放比例
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        return compressBitmap(bitmap);//压缩好比例大小后再进行质量压缩
    }

    /**
     * 质量压缩方式
     *
     * @return
     */
    public static ByteArrayOutputStream qualityCompress(Bitmap bitmap) {
        ByteArrayOutputStream baos = null;
        try {
            baos = new ByteArrayOutputStream();
            int options = 100;
            // 第一个参数 ：图片格式
            // 第二个参数： 图片质量，100为最高，0为最差
            // 第三个参数：保存压缩后的数据的流
            //质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            //循环判断如果压缩后图片是否大于100kb,大于继续压缩
            while (baos.toByteArray().length / 1024 > 100) {
                //重置baos即清空baos
                baos.reset();
                //每次都减少10
                options -= 10;
                if (options < 11) {//为了防止图片大小一直达不到100kb，options一直在递减，当options<0时，下面的方法会报错
                    // 也就是说即使达不到200kb，也就压缩到10了
                    bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);
                    break;
                }
                //这里压缩options%，把压缩后的数据存放到baos中
                bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return baos;
    }

    /**
     * 质量压缩Bitmap
     */
    public static Bitmap compressBitmap(Bitmap image) {
        Bitmap bitmap = null;
        try {
            ByteArrayOutputStream baos = qualityCompress(image);
            //把压缩后的数据baos存放到ByteArrayInputStream中
            ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
            //把ByteArrayInputStream数据生成图片
            bitmap = BitmapFactory.decodeStream(isBm, null, null);
            isBm.close();
            baos.flush();
            baos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    /**
     * 获取压缩图片的参数
     *
     * @param path
     * @return
     */
    public static BitmapFactory.Options getBitmapOption(String path) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);//此时返回bm为空
        options.inJustDecodeBounds = false;
        return options;
    }

    //获取图片质量大小
    public static int getBitmapSize(Bitmap bitmap) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {    //API 19
            return bitmap.getAllocationByteCount();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {//API 12
            return bitmap.getByteCount();
        }
        return bitmap.getRowBytes() * bitmap.getHeight();                //earlier version
    }

    /**
     * 根据图片要显示的宽和高，对图片进行压缩，避免OOM
     *
     * @param path
     * @param width  要显示的imageview的宽度
     * @param height 要显示的imageview的高度
     * @return
     */
    private static Bitmap decodeSampledBitmapFromPath(String path, int width, int height) {
        // 获取图片的宽和高，并不把他加载到内存当中
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        options.inSampleSize = caculateInSampleSize(options, width, height);
        // 使用获取到的inSampleSize再次解析图片(此时options里已经含有压缩比 options.inSampleSize，再次解析会得到压缩后的图片，不会oom了 )
        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeFile(path, options);
        return bitmap;
    }

    /**
     * 根据需求的宽和高以及图片实际的宽和高计算SampleSize
     *
     * @param options
     * @param reqWidth  要显示的imageview的宽度
     * @param reqHeight 要显示的imageview的高度
     * @return
     * @compressExpand 这个值是为了像预览图片这样的需求，他要比所要显示的imageview高宽要大一点，放大才能清晰
     */
    private static int caculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        int width = options.outWidth;
        int height = options.outHeight;
        int inSampleSize = 1;
        if (width >= reqWidth || height >= reqHeight) {
            int widthRadio = Math.round(width * 1.0f / reqWidth);
            int heightRadio = Math.round(width * 1.0f / reqHeight);
            inSampleSize = Math.max(widthRadio, heightRadio);

        }
        return inSampleSize;
    }
}
