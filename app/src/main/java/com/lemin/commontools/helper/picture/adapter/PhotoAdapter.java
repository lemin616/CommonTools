package com.lemin.commontools.helper.picture.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.lemin.commontools.R;
import com.lemin.commontools.helper.GlideHelper;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * To Change The World
 * 2018/7/7 19:44:39
 * Created by Mr.Wang
 */

public class PhotoAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public static final int TYPE_ADD = 1;
    public static final int TYPE_PHOTO = 2;
    private int selectMax = 2;
    public boolean needAdd = true;


    Context mContext;
    public PhotoAdapter(@Nullable List<String> data, int selectMax, Context context) {
        super(R.layout.view_photo, data);
        this.selectMax = selectMax;
        mContext=context;
    }

    @Override
    public int getItemCount() {
            return mData.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (getData().get(position).equals("TAG_PHOPO_RJ_RJ")) {
            return TYPE_ADD;
        } else {
            return TYPE_PHOTO;
        }
    }

    private boolean isShowAddItem(int position) {
        int size = mData.size();
        if (needAdd)
            return size < selectMax;
        else
            return false;
    }

    @Override
    protected void convert(final BaseViewHolder helper, String item) {
        helper.addOnClickListener(R.id.iv_photo);
        if (getItemViewType(helper.getLayoutPosition()) == TYPE_ADD) {
            helper.setImageResource(R.id.iv_photo, R.mipmap.icon_photo_add);
        } else {
            helper.setVisible(R.id.iv_delete, showDelete);
            helper.addOnClickListener(R.id.iv_delete);
            GlideHelper.loadLocalResource(item, (ImageView) helper.getView(R.id.iv_photo));
        }
    }

    private boolean showDelete = false;

    public void setShowDelete(boolean showDelete) {
        this.showDelete = showDelete;
    }

    @NonNull
    @Override
    public List<String> getData() {
        return super.getData();
    }

    @Override
    public void setNewData(@Nullable List<String> data) {
        if (data.contains("TAG_PHOPO_RJ_RJ")) {
            data.remove("TAG_PHOPO_RJ_RJ");
        }
        if (data.size() < 2) {
            data.add("TAG_PHOPO_RJ_RJ");//默认添加图标志位
        }
        super.setNewData(data);
    }
}
