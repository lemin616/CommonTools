package com.lemin.commontools.base;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lemin.commontools.R;
import com.lemin.commontools.helper.StringHelper;

/**
 * Created by 59395 on 2018/3/3.
 */

public class BaseDialog extends Dialog {
    private Context mContext;
    private String title;
    private String message;
    private View mContentView;
    private String positiveButtonText;
    private String negativeButtonText;
    private int resId;
    private OnClickListener positiveButtonClickListener;
    private OnClickListener negativeButtonClickListener;

    public BaseDialog(Context context) {
        super(context);
        this.mContext = context;
    }

    public BaseDialog(Context context, int theme) {
        super(context, theme);
        this.mContext = context;
    }


    /**
     * Set the Dialog title from resource
     *
     * @param title
     * @return
     */
    BaseDialog setmTitle(int title) {
        this.title = (String) mContext.getText(title);
        return this;
    }

    /**
     * Set the Dialog title from String
     *
     * @param title
     * @return
     */
    public BaseDialog setmTitle(String title) {
        this.title = title;
        return this;
    }

    /**
     * Set the Dialog message from resource
     *
     * @param message
     * @return
     */
    public BaseDialog setmMessage(int message) {
        this.message = (String) mContext.getText(message);
        return this;
    }

    /**
     * Set the Dialog message from String
     *
     * @param message
     * @return
     */
    public BaseDialog setmMessage(String message) {
        this.message = message;
        return this;
    }

    /**
     * Set the Dialog ContentView v
     *
     * @param v
     * @return
     */
    public BaseDialog setmContentView(View v) {
        this.mContentView = v;
        return this;
    }

    /**
     * Set the Dialog Background resId
     *
     * @param resId
     * @return
     */
    public BaseDialog setmBackground(int resId) {
        this.resId = resId;
        return this;
    }

    /**
     * Set the Dialog positiveButtonText from Int
     *
     * @param positiveButtonText
     * @return
     */
    public BaseDialog setmPositiveButtonText(int positiveButtonText) {
        this.positiveButtonText = (String) mContext.getText(positiveButtonText);
        return this;
    }

    /**
     * Set the Dialog positiveButtonText from String
     *
     * @param positiveButtonText
     * @return
     */
    public BaseDialog setmPositiveButtonText(String positiveButtonText) {
        this.positiveButtonText = positiveButtonText;
        return this;
    }

    /**
     * Set the Dialog negativeButtonText from Int
     *
     * @param negativeButtonText
     * @return
     */
    public BaseDialog setmNegativeButtonText(int negativeButtonText) {
        this.negativeButtonText = (String) mContext.getText(negativeButtonText);
        return this;
    }

    /**
     * Set the Dialog negativeButtonText from String
     *
     * @param negativeButtonText
     * @return
     */
    public BaseDialog setmNegativeButtonText(String negativeButtonText) {
        this.negativeButtonText = negativeButtonText;
        return this;
    }

    public BaseDialog setmPositiveButtonClickListener(OnClickListener listener) {
        this.positiveButtonClickListener = listener;
        return this;
    }

    public BaseDialog setmNegativeButtonClickListener(OnClickListener listener) {
        this.negativeButtonClickListener = listener;
        return this;
    }

    public BaseDialog mCreate() {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // instantiate the dialog with the custom Theme
        final BaseDialog dialog = new BaseDialog(mContext, R.style.Theme_DialogNoTitle);
        View layout = inflater.inflate(R.layout.view_base_dialog, null);
        if (!StringHelper.isEmpty(positiveButtonText))
            ((TextView) layout.findViewById(R.id.tv_confirm)).setText(positiveButtonText);
        dialog.addContentView(layout, new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        if (positiveButtonClickListener != null) {
            layout.findViewById(R.id.tv_confirm)
                    .setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            //确定按钮
                            dialog.dismiss();
                            positiveButtonClickListener.onClick(dialog,
                                    DialogInterface.BUTTON_POSITIVE);
                        }
                    });
        }

        if (!StringHelper.isEmpty(negativeButtonText)) {
            ((TextView) layout.findViewById(R.id.tv_cancel)).setText(negativeButtonText);
        }

        if (negativeButtonClickListener != null) {
            layout.findViewById(R.id.tv_cancel)
                    .setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            //取消按钮
                            dialog.dismiss();
                            negativeButtonClickListener.onClick(dialog,
                                    DialogInterface.BUTTON_NEGATIVE);
                        }
                    });
        }

        // set the content message
        if (title != null) {
            ((TextView) layout.findViewById(R.id.tv_title)).setText(title);
        }
        if (message != null) {
            ((TextView) layout.findViewById(R.id.tv_message)).setText(message);
        }
        if (mContentView != null) {
            // if no message set
            // add the contentView to the dialog body
            ((LinearLayout) layout.findViewById(R.id.layout_content)).removeAllViews();
            ((LinearLayout) layout.findViewById(R.id.layout_content)).addView(
                    mContentView, new ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT));
        }
        dialog.setContentView(layout);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }
}
