package com.lemin.commontools.helper.picture;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.lemin.commontools.R;
import com.lemin.commontools.helper.FileHelper;
import com.previewlibrary.view.BasePhotoFragment;
import com.previewlibrary.wight.SmoothImageView;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * To Change The World
 * 2018-08-09 11:06
 * Created by Mr.Wang
 */
public class PreviewPicFragment extends BasePhotoFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_preview_pic, container, false);
    }

    @CallSuper
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final SmoothImageView imageView = view.findViewById(R.id.photoView);
        view.findViewById(R.id.bt_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageView.buildDrawingCache(true);
                imageView.buildDrawingCache();
                Bitmap bitmap = imageView.getDrawingCache();
                saveBitmapFile(bitmap);
            }
        });
    }

    public void saveBitmapFile(Bitmap bitmap) {
        if (bitmap == null) {
            return;
        }
        File file = new File(FileHelper.createImageFileDir() + "/" + System.currentTimeMillis() + ".jpg");//将要保存图片的路径和图片名称
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
            Toast.makeText(getContext(), "保存成功", Toast.LENGTH_LONG).show();
            getContext().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(new File(file.getPath()))));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
