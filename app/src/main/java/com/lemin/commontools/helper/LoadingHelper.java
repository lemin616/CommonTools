package com.lemin.commontools.helper;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.lemin.commontools.R;

public class LoadingHelper {
    public static AlertDialog show(Context context) {
        Activity aty = null;
        if (context instanceof Activity) {
            aty = ((Activity) context);
        } else if (context instanceof ContextWrapper) {
            Context baseContext = ((ContextWrapper) context).getBaseContext();
            if (baseContext instanceof Activity)
                aty = ((Activity) baseContext);
        }
        if (aty == null)
            return null;
        MDialog mDialog = new MDialog(aty);
        mDialog.show();
        return mDialog;
    }

    public static void dismiss(AlertDialog dialog) {
        if (dialog != null && dialog.isShowing())
            dialog.dismiss();
    }

    public static class MDialog extends AlertDialog {

        protected MDialog(Context context) {
            super(context);
        }

        protected MDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
            super(context, cancelable, cancelListener);
        }


        protected MDialog(Context context, int themeResId) {
            super(context, themeResId);
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            Window mWindow = getWindow();
            WindowManager.LayoutParams mParams = mWindow.getAttributes();
            mParams.gravity = Gravity.CENTER;
            mParams.width = WindowManager.LayoutParams.MATCH_PARENT;
            mParams.height = WindowManager.LayoutParams.MATCH_PARENT;
            mParams.dimAmount = 0.1f;
            mWindow.setAttributes(mParams);
            View decorView = mWindow.getDecorView();
            decorView.setPadding(0, 0, 0, 0);
            decorView.setBackgroundColor(Color.TRANSPARENT);
            setContentView(R.layout.view_loading);
            setCancelable(false);
            setCanceledOnTouchOutside(false);
        }
    }
}
