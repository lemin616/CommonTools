package com.lemin.commontools.helper;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import com.lemin.commontools.base.BaseApplication;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 文件管理工具
 * 创建功能：创建应用目录
 * 清除功能：主要功能有清除内/外缓存，清除数据库，清除sharedPreference，清除files和清除自定义目录
 */

public class FileHelper {
    /**
     * 判断是否有SDCard
     *
     * @return 有为true，没有为false
     */
    public static boolean hasSDCard() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            return true;
        }
        return false;
    }

    /**
     * 生成图片文件路径
     *
     * @return
     */
    public static String createPointImageFileDir() {
        String path;
        if (hasSDCard()) {
            path = Environment.getExternalStorageDirectory() + "/beenmanage/CACHE/.image" + File.separator;
        } else {
            path = BaseApplication.instance().getCacheDir().getPath() + "/beenmanage/CACHE/.image";
        }
        mkdirs(path);
        return path;
    }

    /**
     * 生成图片文件路径
     *
     * @return
     */
    public static String createImageFileDir() {
        String path;
        if (hasSDCard()) {
            path = Environment.getExternalStorageDirectory() + "/beenmanage/CACHE/image" + File.separator;
        } else {
            path = BaseApplication.instance().getCacheDir().getPath() + "/beenmanage/CACHE/image";
        }
        mkdirs(path);
        return path;
    }

    /**
     * 生成图片文件路径
     *
     * @return
     */
    public static String createPictureFileDir() {
        String path;
        if (hasSDCard()) {
            path = Environment.getExternalStorageDirectory() + "/beenmanage/CACHE/picture" + File.separator;
        } else {
            path = BaseApplication.instance().getCacheDir().getPath() + "/beenmanage/CACHE/picture";
        }
        mkdirs(path);
        return path;
    }

    /**
     * 生成拍照图片文件路径
     *
     * @return
     */
    public static File createImageFileDir(Integer type) {
        File image = null;
        try {
            String path = getPicturesPath();
            switch (type) {
                case 0:
                    path += "/photo";
                    break;
                case 1:
                    path += "/image";
                    break;
                case 2:
                    path += "/screenshot";
                    break;
            }
            File pictureDir = new File(path);
            mkdirs(path);

            SimpleDateFormat timeFormatter = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA);
            long time = System.currentTimeMillis();
            String imageName = timeFormatter.format(new Date(time));

            image = File.createTempFile(
                    imageName,       /* prefix */
                    ".jpg",          /* suffix */
                    pictureDir       /* directory */
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    /**
     * 获取图片存放路径
     *
     * @return
     */
    public static String getPicturesPath() {
        String path;
        if (hasSDCard()) {
            path = Environment.getExternalStorageDirectory() + "/beenmanage/CACHE/Pictures" + File.separator;
        } else {
            path = BaseApplication.instance().getCacheDir().getPath() + "/beenmanage/CACHE/Pictures";
        }

        return path;
    }

    /**
     * 生成apk路径
     *
     * @return
     */
    public static String createAPKFileDir() {
        String path;
        if (hasSDCard()) {
            path = Environment.getExternalStorageDirectory() + "/beenmanage/CACHE/apk" + File.separator;
        } else {
            path = BaseApplication.instance().getCacheDir().getPath() + "/beenmanage/CACHE/apk";
        }
        mkdirs(path);
        return path;
    }

    /**
     * 生成Log路径
     *
     * @return
     */
    public static String createLogFileDir() {
        String path;
        if (hasSDCard()) {
            path = Environment.getExternalStorageDirectory() + "/beenmanage/cache/log" + File.separator;
        } else {
            path = BaseApplication.instance().getCacheDir().getPath() + "/beenmanage/cache/log";
        }
        mkdirs(path);
        return path;
    }

    /**
     * 删除SD卡或者手机的缓存图片和目录
     */
    public static void deleteFile(Context context, String path) {
        if (!path.contains(createImageFileDir())) {//只删除应用内拍摄的照片
            return;
        }
        LogHelper.error("===========", "删除应用内拍摄的图片");

        File dirFile = new File(path);
        if (!dirFile.exists()) {
            return;
        }
//            LogHelper.error("===========","删除图片："+path);
        Intent scanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        scanIntent.setData(Uri.fromFile(dirFile));
        context.sendBroadcast(scanIntent);

        dirFile.delete();
    }

    /**
     * 生成文件夹
     *
     * @param DirPath
     */
    public static void mkdirs(String DirPath) {
        File file = new File(DirPath);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    /**
     * 路径是否存在
     *
     * @param path
     * @return
     */
    public static boolean pathIsExist(String path) {
        File dirFile = new File(path);
        if (dirFile.exists()) {
            return true;
        }
        return false;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        String column = MediaStore.Images.Media.DATA;
        String[] projection = {column};
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * 需要查下缓存大小
     *
     * @param context
     * @return
     * @throws Exception
     */
    public static String getTotalCacheSize(Context context) throws Exception {
        long cacheSize = getFolderSize(context.getCacheDir());
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            cacheSize += getFolderSize(context.getExternalCacheDir());
        }
        return getFormatSize(cacheSize);
    }

    /**
     * 清空缓存
     *
     * @param context
     */
    public static void clearAllCache(Context context) {
        deleteDir(context.getCacheDir());
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            deleteDir(context.getExternalCacheDir());
        }
    }

    private static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }

    /**
     * * 清除本应用内部缓存(/data/data/com.xxx.xxx/cache) * *
     *
     * @param context
     */
    public static void cleanInternalCache(Context context) {
        deleteFilesByDirectory(context.getCacheDir());
    }

    /**
     * * 清除本应用所有数据库(/data/data/com.xxx.xxx/databases) * *
     *
     * @param context
     */
    public static void cleanDatabases(Context context) {
        deleteFilesByDirectory(new File("/data/data/"
                + context.getPackageName() + "/databases"));
    }

    /**
     * * 清除本应用SharedPreference(/data/data/com.xxx.xxx/shared_prefs) *
     *
     * @param context
     */
    public static void cleanSharedPreference(Context context) {
        deleteFilesByDirectory(new File("/data/data/"
                + context.getPackageName() + "/shared_prefs"));
    }

    /**
     * * 按名字清除本应用数据库 * *
     *
     * @param context
     * @param dbName
     */
    public static void cleanDatabaseByName(Context context, String dbName) {
        context.deleteDatabase(dbName);
    }

    /**
     * * 清除/data/data/com.xxx.xxx/files下的内容 * *
     *
     * @param context
     */
    public static void cleanFiles(Context context) {
        deleteFilesByDirectory(context.getFilesDir());
    }

    /**
     * * 清除外部cache下的内容(/mnt/sdcard/android/data/com.xxx.xxx/cache)
     *
     * @param context
     */
    public static void cleanExternalCache(Context context) {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            deleteFilesByDirectory(context.getExternalCacheDir());
        }
    }

    /**
     * * 清除自定义路径下的文件，使用需小心，请不要误删。而且只支持目录下的文件删除 * *
     *
     * @param filePath
     */
    public static void cleanCustomCache(String filePath) {
        deleteFilesByDirectory(new File(filePath));
    }

    /**
     * * 清除本应用所有的数据 * *
     *
     * @param context
     * @param filepath
     */
    public static void cleanApplicationData(Context context, String... filepath) {
        cleanInternalCache(context);
        cleanExternalCache(context);
        cleanDatabases(context);
        cleanSharedPreference(context);
        cleanFiles(context);
        if (filepath == null) {
            return;
        }
        for (String filePath : filepath) {
            cleanCustomCache(filePath);
        }
    }

    /**
     * * 删除方法 这里只会删除某个文件夹下的文件，如果传入的directory是个文件，将不做处理 * *
     *
     * @param directory
     */
    private static void deleteFilesByDirectory(File directory) {
        if (directory != null && directory.exists() && directory.isDirectory()) {
            for (File item : directory.listFiles()) {
                item.delete();
            }
        }
    }

    // 获取文件
    //Context.getExternalFilesDir() --> SDCard/Android/data/你的应用的包名/files/ 目录，一般放一些长时间保存的数据
    //Context.getExternalCacheDir() --> SDCard/Android/data/你的应用包名/cache/目录，一般存放临时缓存数据
    public static long getFolderSize(File file) throws Exception {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (int i = 0; i < fileList.length; i++) {
                // 如果下面还有文件
                if (fileList[i].isDirectory()) {
                    size = size + getFolderSize(fileList[i]);
                } else {
                    size = size + fileList[i].length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    /**
     * 删除指定目录下文件及目录
     *
     * @param deleteThisPath
     * @param filePath
     * @return
     */
    public static void deleteFolderFile(String filePath, boolean deleteThisPath) {
        if (!StringHelper.isEmpty(filePath)) {
            try {
                File file = new File(filePath);
                if (file.isDirectory()) {// 如果下面还有文件
                    File files[] = file.listFiles();
                    for (int i = 0; i < files.length; i++) {
                        deleteFolderFile(files[i].getAbsolutePath(), true);
                    }
                }
                if (deleteThisPath) {
                    if (!file.isDirectory()) {// 如果是文件，删除
                        file.delete();
                    } else {// 目录
                        if (file.listFiles().length == 0) {// 目录下没有文件或者目录，删除
                            file.delete();
                        }
                    }
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    /**
     * 格式化单位
     *
     * @param size
     * @return
     */
    public static String getFormatSize(double size) {
        double kiloByte = size / 1024;
//        if (kiloByte < 1) {
//            return size + "Byte";
//        }

        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "KB";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "MB";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()
                + "TB";
    }

    public static String getCacheSize(File file) throws Exception {
        return getFormatSize(getFolderSize(file));
    }
}
