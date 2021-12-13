/*
 * Created by zhsheng26 on 16-10-18 上午8:49
 * Copyright (C) 2016, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.lemin.commontools.widget.popup;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.lemin.commontools.R;
import com.lemin.commontools.helper.DisplayHelper;
import com.lemin.commontools.listener.IActionItem;
import com.lemin.commontools.listener.OnCustomItemClickListener;

import java.util.ArrayList;
import java.util.List;



public class ArrowPopup extends PopupWindow {
    public final List<IActionItem> actionItems = new ArrayList<>();
    private Context context;
    private final int[] location = new int[2];
    private RecyclerView recyclerView;
    private ImageView ivArrowTop;
    private ImageView ivArrowBottom;
    private ActionsAdapter actionsAdapter;
    private LinearLayout rootView;
    private ContextThemeWrapper wrapper;
    private Window window;
    private int dpWith;
    private int screenWidth;
    private int screenHeight;

    public ArrowPopup(Context context, int style, boolean alpha, int maxHeight) {
        super(context);
        this.context = context;
        init();
        setupView(maxHeight);
    }


    public ArrowPopup(Activity activity, int style, boolean alpha) {
        this(activity, style, alpha, 0);
        if (alpha) window = activity.getWindow();
    }

    public ArrowPopup(Activity activity, boolean alpha, int maxHeight) {
        this(activity, 0, alpha, maxHeight);
        if (alpha) window = activity.getWindow();
    }

    private void init() {
        setFocusable(true);
        setTouchable(true);
        setOutsideTouchable(true);
        setBackgroundDrawable(new ColorDrawable());
        setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        DisplayMetrics displayMetrics = DisplayHelper.displayMetrics(context);
        screenWidth = displayMetrics.widthPixels;
        screenHeight = displayMetrics.heightPixels;
    }

    public void setDpWith(int dpWith) {
        this.dpWith = dpWith;
    }

    private void setupView(int maxHeight) {

        rootView = new LinearLayout(context);
        rootView.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        ivArrowTop = new ImageView(context);
        ivArrowTop.setImageResource(R.mipmap.ic_arrow_popup_top);
        rootView.addView(ivArrowTop, layoutParams);

        recyclerView = new RecyclerView(context);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        if (maxHeight != 0) {
            lp.height = DisplayHelper.dip2px(maxHeight);
        }
        recyclerView.setBackgroundResource(R.drawable.selector_popup_bg);
        int toPx = DisplayHelper.dip2px(5);
        recyclerView.setPadding(toPx, toPx, toPx, toPx);
        rootView.addView(recyclerView, lp);

        ivArrowBottom = new ImageView(context);
        ivArrowBottom.setImageResource(R.mipmap.ic_arrow_popup_top);
        rootView.addView(ivArrowBottom, layoutParams);

        actionsAdapter = new ActionsAdapter(actionItems);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(actionsAdapter);
        setContentView(rootView);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if (window != null) {
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.alpha = 1f;
            window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            window.setAttributes(lp);
        }
    }

    public void showPopupWindow(View anchor, List<IActionItem> items) {
        if (window != null) {
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.alpha = 0.7f;
            window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            window.setAttributes(lp);
        }
        actionItems.clear();
        actionItems.addAll(items);
        actionsAdapter.notifyDataSetChanged();
        calculationAtLocation(anchor);
    }

    public void showPopupWindow1(View anchor, List<PopupMenuItem> items) {
        if (window != null) {
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.alpha = 0.7f;
            window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            window.setAttributes(lp);
        }
        actionItems.clear();
        actionItems.addAll(items);
        actionsAdapter.notifyDataSetChanged();
        calculationAtLocation(anchor);
    }

    private void calculationAtLocation(View anchor) {
        anchor.getLocationOnScreen(location);


        Rect anchorRect = new Rect(location[0], location[1], location[0] + anchor.getWidth(), location[1] + anchor.getHeight());
        rootView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        rootView.measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        int popupHeight = screenHeight / 2;
        int popupWidth = rootView.getMeasuredWidth();


        int arrowWidth = ivArrowTop.getMeasuredWidth();
        int arrowHeight = ivArrowTop.getMeasuredHeight();
        int arrowMarginLeft = 0;
        int xPos = 0;
        int anchorCenterMarginLeft = anchorRect.centerX();
        int anchorCenterMarginRight = screenWidth - anchorRect.centerX();

        if (popupWidth / 2 <= anchorCenterMarginLeft && popupWidth / 2 <= anchorCenterMarginRight) {
            xPos = anchorRect.centerX() - (popupWidth / 2);
            arrowMarginLeft = popupWidth / 2 - arrowWidth / 2;
        }

        if (popupWidth / 2 > anchorCenterMarginLeft) {
            xPos = anchorRect.left;
            arrowMarginLeft = anchorRect.width() / 2 - arrowWidth / 2;
        }

        if (popupWidth / 2 > anchorCenterMarginRight) {
            xPos = anchorRect.right - popupWidth;
            arrowMarginLeft = popupWidth - anchorRect.width() / 2 - arrowWidth / 2;
        }
        int yPos = 0;
        boolean direction = screenHeight - anchorRect.bottom - popupHeight >= 0;
        if (direction) {
            yPos = anchorRect.bottom;
        } else {
            if (popupHeight > anchorRect.top) {
                ViewGroup.LayoutParams l = recyclerView.getLayoutParams();
                l.height = anchorRect.top - anchorRect.height();
            } else {
                yPos = anchorRect.top - popupHeight + arrowHeight;
            }
        }
        setAnimationStyle(direction ? R.style.popup_fade_top_to_bottom : R.style.popup_fade_bottom_to_top);
        setArrowDirection(direction ? ivArrowTop : ivArrowBottom, direction, arrowMarginLeft);
        showAtLocation(anchor, Gravity.NO_GRAVITY, xPos, yPos);
    }

    private void setArrowDirection(ImageView ivArrow, boolean direction, int marginLeft) {
        ivArrowTop.setVisibility(direction ? View.VISIBLE : View.GONE);
        ivArrowBottom.setVisibility(!direction ? View.VISIBLE : View.GONE);
        ViewGroup.MarginLayoutParams margin = new ViewGroup.MarginLayoutParams(ivArrow.getLayoutParams());
        margin.setMargins(margin.leftMargin + marginLeft, margin.topMargin, margin.rightMargin, margin.bottomMargin);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(margin);
        ivArrow.setLayoutParams(layoutParams);
    }

    class ActionsAdapter extends RecyclerView.Adapter<ActionsAdapter.ActionViewHolder> {
        private final List<IActionItem> actions;

        ActionsAdapter(List<IActionItem> actionItems) {
            actions = actionItems;
        }

        @Override
        public ActionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(context).inflate(R.layout.item_arrow_popup, null);
            return new ActionViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(ActionViewHolder holder, int position) {
            ViewGroup.LayoutParams layoutParams = new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT);
            layoutParams.height = DisplayHelper.dip2px(38);
            layoutParams.width = DisplayHelper.dip2px(100);
            holder.itemView.setLayoutParams(layoutParams);

            IActionItem actionItem = actions.get(position);
            holder.setData(actionItem);
            int adapterPosition = holder.getAdapterPosition();
            Drawable drawable;
            if (adapterPosition == 0) {
                drawable = context.getResources().getDrawable(R.drawable.selector_popup_item_top);
            } else if (adapterPosition == actions.size() - 1) {
                drawable = context.getResources().getDrawable(R.drawable.selector_popup_item_bottom);
            } else {
                drawable = context.getResources().getDrawable(R.drawable.selector_popup_item_center);
            }
            if (actions.size() == 1) {
                setCompatBackground(holder.itemView, context.getResources().getDrawable(R.drawable.selector_popup_item_bottom));
            } else {
                setCompatBackground(holder.itemView, drawable);
            }
        }

        @Override
        public int getItemCount() {
            return actions.size();
        }

        class ActionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            TextView tvTitle;
            ImageView ivIcon;
            private PopupMenuItem action;

            ActionViewHolder(View itemView) {
                super(itemView);
                tvTitle = (TextView) itemView.findViewById(R.id.tv_arrow_menu_title);
                ivIcon = (ImageView) itemView.findViewById(R.id.iv_arrow_menu_icon);
                itemView.setOnClickListener(this);
            }

            public void setData(IActionItem actionItem) {
                this.action = (PopupMenuItem) actionItem;
                if (actionItem.getItemIcon() != 0) {
                    ivIcon.setVisibility(View.VISIBLE);
                    ivIcon.setImageResource(actionItem.getItemIcon());
                } else {
                    ivIcon.setVisibility(View.GONE);
                }
                tvTitle.setText(actionItem.getItemTitle());
            }

            @Override
            public void onClick(View v) {
                if (clickListener != null) clickListener.onItemClick(v, getAdapterPosition());
            }
        }
    }

    private static void setCompatBackground(View view, Drawable drawable) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackground(drawable);
        } else {
            view.setBackgroundDrawable(drawable);
        }
    }

    private OnCustomItemClickListener clickListener;

    public void setClickListener(OnCustomItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

}
