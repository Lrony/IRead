package com.lrony.iread.presentation.book.recommend;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.lrony.iread.R;
import com.lrony.iread.model.bean.BookDetailRecommendBookBean;
import com.lrony.iread.pref.Constant;
import com.lrony.iread.ui.help.CommonAdapter;
import com.lrony.iread.ui.help.CommonViewHolder;
import com.lrony.iread.util.ImageLoader;
import com.lrony.iread.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuxiaobin on 18-5-31.
 */

public class RecommendAdapter extends CommonAdapter<BookDetailRecommendBookBean> {

    private Context context;

    public RecommendAdapter(Context context, List<BookDetailRecommendBookBean> data) {
        super(R.layout.item_list_book, data);
        this.context = context;
    }

    /**
     * Implement this method and use the helper to adapt the view to the given item.
     *
     * @param helper A fully initialized helper.
     * @param item   The item that needs to be displayed.
     */
    @Override
    protected void convert(CommonViewHolder helper, BookDetailRecommendBookBean item) {
        helper.setText(R.id.tv_title, item.getTitle());
        helper.setText(R.id.tv_describe, item.getShortIntro());
        helper.setText(R.id.tv_author, item.getAuthor());
        helper.setText(R.id.tv_type, item.getMinorCate());
        helper.setText(R.id.tv_retention_ratio, item.getRetentionRatio()
                + StringUtils.getStringByID(R.string.book_retention_ratio));
        ImageLoader.load(context, Constant.IMG_BASE_URL + item.getCover()
                , (ImageView) helper.getView(R.id.iv_cover));
    }
}
