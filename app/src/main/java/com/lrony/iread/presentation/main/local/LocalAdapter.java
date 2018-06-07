package com.lrony.iread.presentation.main.local;

import android.content.Context;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lrony.iread.R;
import com.lrony.iread.model.bean.CollBookBean;
import com.lrony.iread.pref.Constant;
import com.lrony.iread.ui.help.CommonAdapter;
import com.lrony.iread.ui.help.CommonViewHolder;
import com.lrony.iread.util.ImageLoader;

import java.util.List;


/**
 * @author wangdan
 */
public class LocalAdapter extends CommonAdapter<CollBookBean> {

    private Context mContext;

    public LocalAdapter(List<CollBookBean> data, Context context) {
        super(R.layout.item_grid_book, data);
        this.mContext = context;
    }

    @Override
    protected void convert(CommonViewHolder helper, CollBookBean item) {
        helper.setText(R.id.tv_title, item.getTitle());
        helper.setVisible(R.id.tv_updated, item.getIsUpdate());
        ImageLoader.load(mContext, Constant.IMG_BASE_URL + item.getCover(), helper.getView(R.id.iv_cover));
    }
}
