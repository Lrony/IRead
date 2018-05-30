package com.lrony.iread.presentation.main.local;

import android.content.Context;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lrony.iread.R;
import com.lrony.iread.model.bean.CollBookBean;
import com.lrony.iread.pref.Constant;
import com.lrony.iread.util.ImageLoader;

import java.util.List;


/**
 * @author wangdan
 */
public class LocalAdapter extends BaseQuickAdapter<CollBookBean,BaseViewHolder>{

    private Context mContext;

    public LocalAdapter(@Nullable List<CollBookBean> data, Context context) {
        super(R.layout.item_grid_book, data);
        this.mContext = context;

    }

    @Override
    protected void convert(BaseViewHolder helper, CollBookBean item) {
        helper.setText(R.id.tv_title,item.getTitle());
        Context context ;
        ImageLoader.load( mContext , Constant.IMG_BASE_URL + item.getCover(),helper.getView(R.id.iv_cover));
    }
}
