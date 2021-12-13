package com.lemin.commontools.helper;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.lemin.commontools.R;
import com.yanzhenjie.alertdialog.AlertDialog;

public class AndroidHelper {
    public static void call(Context context, String phoneNum) {
        if (context == null || TextUtils.isEmpty(phoneNum))
            return;
        try {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            Uri data = Uri.parse("tel:" + phoneNum);
            intent.setData(data);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void msg(Context context, String phoneNum) {
        if (context == null || TextUtils.isEmpty(phoneNum))
            return;
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            Uri data = Uri.parse("smsto:" + phoneNum);
            intent.setData(data);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void copy(Context context, String content) {
        if (context == null)
            return;
        try {
            ClipboardManager service = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData online = ClipData.newPlainText("BeenOnline", content);
            if (service == null)
                return;
            service.setPrimaryClip(online);
            Toast.makeText(context, "已复制", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface OnInputCall {
        public void onCall(String et, DialogInterface dialog);
    }

    public static void showInputDilog(Context context, String tit, final OnInputCall call) {

        View view = LayoutInflater.from(context).inflate(R.layout.dialog_alert_edit, null);
        final EditText text = view.findViewById(R.id.edit);
        text.setFocusable(true);
        AlertDialog.newBuilder(context).setTitle(tit)
                .setView(view)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (call != null) {
                            call.onCall(text.getText().toString(), dialog);
                        } else {
                            dialog.dismiss();
                        }
                    }
                })
                .setNegativeButton("取消", null)
                .show();
        text.requestFocusFromTouch();
    }
}
