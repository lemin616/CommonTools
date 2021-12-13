package com.lemin.commontools.helper.picture;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;

import com.lemin.commontools.R;
import com.lemin.commontools.helper.FileHelper;
import com.previewlibrary.GPreviewBuilder;
import com.previewlibrary.enitity.IThumbViewInfo;
import com.tangxiaolv.telegramgallery.GalleryActivity;
import com.tangxiaolv.telegramgallery.GalleryConfig;

import java.io.File;
import java.util.List;

import static com.lemin.commontools.helper.FileHelper.getDataColumn;
import static com.lemin.commontools.helper.FileHelper.isDownloadsDocument;
import static com.lemin.commontools.helper.FileHelper.isExternalStorageDocument;
import static com.lemin.commontools.helper.FileHelper.isGooglePhotosUri;
import static com.lemin.commontools.helper.FileHelper.isMediaDocument;

/**
 * Created by Jacky on 2017/8/7.
 * 照片封装工具类
 */

public class PhotoHelper {

    public static final int GET_IMAGE_BY_CAMERA = 5001;
    public static final int GET_IMAGE_FROM_PHONE = 5002;
    public static final int CROP_IMAGE = 5003;
    public static Uri imageUriFromCamera;
    public static Uri cropImageUri;

    public static void openCameraImage(final Activity activity) {
        imageUriFromCamera = createImagePathUri(activity);

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // MediaStore.EXTRA_OUTPUT参数不设置时,系统会自动生成一个uri,但是只会返回一个缩略图
        // 返回图片在onActivityResult中通过以下代码获取
        // Bitmap bitmap = (Bitmap) data.getExtras().get("data");
        //这段代码判断，在安卓7.0以前版本是不需要的。特此注意。不然这里也会抛出异常
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        }
        intent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUriFromCamera);
        activity.startActivityForResult(intent, GET_IMAGE_BY_CAMERA);
    }

    public static void openCameraImage(final Activity activity,int code) {
        imageUriFromCamera = createImagePathUri(activity);

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // MediaStore.EXTRA_OUTPUT参数不设置时,系统会自动生成一个uri,但是只会返回一个缩略图
        // 返回图片在onActivityResult中通过以下代码获取
        // Bitmap bitmap = (Bitmap) data.getExtras().get("data");
        //这段代码判断，在安卓7.0以前版本是不需要的。特此注意。不然这里也会抛出异常
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        }
        intent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUriFromCamera);
        activity.startActivityForResult(intent, code);
    }

    public static void openCameraImage(final Fragment fragment) {
        imageUriFromCamera = createImagePathUri(fragment.getContext());

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // MediaStore.EXTRA_OUTPUT参数不设置时,系统会自动生成一个uri,但是只会返回一个缩略图
        // 返回图片在onActivityResult中通过以下代码获取
        // Bitmap bitmap = (Bitmap) data.getExtras().get("data");
        //这段代码判断，在安卓7.0以前版本是不需要的。特此注意。不然这里也会抛出异常
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        }
        intent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUriFromCamera);
        fragment.startActivityForResult(intent, GET_IMAGE_BY_CAMERA);
    }

    public static void openLocalImage(final Activity activity) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
        activity.startActivityForResult(intent, GET_IMAGE_FROM_PHONE);
    }

    public static void openLocalImage(final Fragment fragment) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
        fragment.startActivityForResult(intent, GET_IMAGE_FROM_PHONE);
    }

    /**
     * 相册多选模式
     *
     * @param activity
     * @param count
     */
    public static void multiPhotoPicker(Activity activity, int count) {
        GalleryConfig config = new GalleryConfig.Build()
                .limitPickPhoto(count)//最大限制图片数量
                .singlePhoto(false)//是否多选
                .hintOfPick(String.format(activity.getString(R.string.str_limit_photo), String.valueOf(count)))//超过数量提示
                .filterMimeTypes(new String[]{"image/gif"})//过滤媒体类型
                .build();
        GalleryActivity.openActivity(activity, GET_IMAGE_FROM_PHONE, config);
    }

    public static void multiPhotoPicker(Activity activity, int count,int code) {
        GalleryConfig config = new GalleryConfig.Build()
                .limitPickPhoto(count)//最大限制图片数量
                .singlePhoto(false)//是否多选
                .hintOfPick(String.format(activity.getString(R.string.str_limit_photo), String.valueOf(count)))//超过数量提示
                .filterMimeTypes(new String[]{"image/gif"})//过滤媒体类型
                .build();
        GalleryActivity.openActivity(activity, code, config);
    }

    /**
     * 相册单选模式
     *
     * @param activity
     */
    public static void singlePhotoPicker(Activity activity) {
        GalleryConfig config = new GalleryConfig.Build()
                .singlePhoto(true).build();
        GalleryActivity.openActivity(activity, GET_IMAGE_FROM_PHONE, config);
    }

    public static void cropImage(Activity activity, Uri srcUri) {
        cropImageUri = createImagePathUri(activity);

        Intent intent = new Intent("com.android.camera.action.CROP");
        //这段代码判断，在安卓7.0以前版本是不需要的。特此注意。不然这里也会抛出异常
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        }
        intent.setDataAndType(srcUri, "image/*");
        intent.putExtra("crop", "true");

        // aspectX aspectY 是裁剪框宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪后生成图片的宽高
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, cropImageUri);
        intent.putExtra("return-data", true);

        activity.startActivityForResult(intent, CROP_IMAGE);
    }

    public static void cropImage(Fragment fragment, Uri srcUri) {
        cropImageUri = createImagePathUri(fragment.getContext());

        Intent intent = new Intent("com.android.camera.action.CROP");
        //这段代码判断，在安卓7.0以前版本是不需要的。特此注意。不然这里也会抛出异常
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        }
        intent.setDataAndType(srcUri, "image/*");
        intent.putExtra("crop", "true");

        // aspectX aspectY 是裁剪框宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪后生成图片的宽高
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, cropImageUri);
        intent.putExtra("return-data", true);

        fragment.startActivityForResult(intent, CROP_IMAGE);
    }

    /**
     * 创建一条图片地址uri,用于保存拍照后的照片
     *
     * @param context
     * @return 图片的uri
     */
    public static Uri createImageUriPath(final Context context) {
        final Uri[] imageFilePath = {null};

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            imageFilePath[0] = Uri.parse("");
//            RxToast.error("请先获取写入SDCard权限");
        } else {
//            String status = FileHelper.createPhotoImageFileDir();
//            SimpleDateFormat timeFormatter = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA);
//            long time = System.currentTimeMillis();
//            String imageName = timeFormatter.format(new Date(time)) + ".jpg";
//            imageFilePath[0] = Uri.fromFile(new File(status + imageName));

            File oriPhotoFile = FileHelper.createImageFileDir(1);

            if (oriPhotoFile != null) {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                    imageFilePath[0] = Uri.fromFile(oriPhotoFile);
                } else {
                    imageFilePath[0] = FileProvider.getUriForFile(context, context.getPackageName() + ".provider", oriPhotoFile);
                }
            }
        }

        return imageFilePath[0];
    }

    /**
     * 创建一条图片地址uri,用于保存拍照后的照片
     *
     * @param context
     * @return 图片的uri
     */
    public static Uri createImagePathUri(final Context context) {
        final Uri[] imageFilePath = {null};

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            imageFilePath[0] = Uri.parse("");
//            RxToast.error("请先获取写入SDCard权限");
        } else {
//            String status = Environment.getExternalStorageState();
//            SimpleDateFormat timeFormatter = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA);
//            long time = System.currentTimeMillis();
//            String imageName = timeFormatter.format(new Date(time));

            File oriPhotoFile = FileHelper.createImageFileDir(1);
            // ContentValues是我们希望这条记录被创建时包含的数据信息
            ContentValues values = new ContentValues(2);
//            values.put(MediaStore.Images.Media.DISPLAY_NAME, imageName);
            values.put(MediaStore.Images.Media.DATA, oriPhotoFile.getAbsolutePath());
//            values.put(MediaStore.Images.Media.DATE_TAKEN, time);
            values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");

            if (FileHelper.hasSDCard()) {// 判断是否有SD卡,优先使用SD卡存储,当没有SD卡时使用手机存储
                imageFilePath[0] = context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                imageFilePath[0] = context.getContentResolver().insert(MediaStore.Images.Media.INTERNAL_CONTENT_URI, values);
            }
        }

        return imageFilePath[0];
    }

    /**
     * @param context
     * @param imageUri
     * @return
     */
    public static String getFilePath(Context context, Uri imageUri) {
        String path;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            path = getImageAbsolutePath(context, imageUri);
        } else {
            path = getRealFilePath(context, imageUri);
        }
        return path;
    }

    //此方法 只能用于4.4以下的版本
    public static String getRealFilePath(final Context context, final Uri uri) {
        if (context == null || uri == null)
            return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            String[] projection = {MediaStore.Images.ImageColumns.DATA};
            Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);

            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    /**
     * 根据Uri获取图片绝对路径，解决Android4.4以上版本Uri转换
     */
    @TargetApi(19)
    public static String getImageAbsolutePath(Context context, Uri imageUri) {
        if (context == null || imageUri == null)
            return null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && DocumentsContract.isDocumentUri(context, imageUri)) {
            if (isExternalStorageDocument(imageUri)) {
                String docId = DocumentsContract.getDocumentId(imageUri);
                String[] split = docId.split(":");
                String type = split[0];
                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            } else if (isDownloadsDocument(imageUri)) {
                String id = DocumentsContract.getDocumentId(imageUri);
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                return getDataColumn(context, contentUri, null, null);
            } else if (isMediaDocument(imageUri)) {
                String docId = DocumentsContract.getDocumentId(imageUri);
                String[] split = docId.split(":");
                String type = split[0];
                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                String selection = MediaStore.Images.Media._ID + "=?";
                String[] selectionArgs = new String[]{split[1]};
                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        } // MediaStore (and general)
        else if ("content".equalsIgnoreCase(imageUri.getScheme())) {
            // Return the remote address
            if (isGooglePhotosUri(imageUri))
                return imageUri.getLastPathSegment();
            return getDataColumn(context, imageUri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(imageUri.getScheme())) {
            return imageUri.getPath();
        }
        return null;
    }

    /*
     * 图片预览
     * */
    public static <T extends IThumbViewInfo> void previewPhoto(Activity aty, List<T> data, int currentPos) {
        GPreviewBuilder.from(aty)//activity实例必须
//                .to(CustomActivity.class)//自定义Activity 使用默认的预览不需要
                .setData(data)//集合
                .setUserFragment(PreviewPicFragment.class)//自定义Fragment 使用默认的预览不需要
                .setCurrentIndex(currentPos)
                .setSingleFling(false)//是否在黑屏区域点击返回
                .setDrag(false)//是否禁用图片拖拽返回
                .setSingleShowType(false)
                .setType(GPreviewBuilder.IndicatorType.Dot)//指示器类型
                .start();//启动
    }


    /*
     * 图片预览
     * */
    public static <T extends IThumbViewInfo> void previewPhoto(Activity aty, T data) {
        GPreviewBuilder.from(aty)//activity实例必须
//                .to(CustomActivity.class)//自定义Activity 使用默认的预览不需要
                .setSingleData(data)//集合
                .setCurrentIndex(0)
                .setUserFragment(PreviewPicFragment.class)//自定义Fragment 使用默认的预览不需要
                .setSingleFling(false)//是否在黑屏区域点击返回
                .setDrag(false)//是否禁用图片拖拽返回
                .setSingleShowType(false)
                .setType(GPreviewBuilder.IndicatorType.Dot)//指示器类型
                .start();//启动
    }
}