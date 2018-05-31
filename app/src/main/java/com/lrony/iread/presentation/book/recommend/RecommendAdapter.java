package com.lrony.iread.presentation.book.recommend;

import android.content.Context;

import com.lrony.iread.R;
import com.lrony.iread.model.bean.BookDetailRecommendBookBean;
import com.lrony.iread.ui.help.CommonAdapter;
import com.lrony.iread.ui.help.CommonViewHolder;

import java.util.List;

/**
 * Created by liuxiaobin on 18-5-31.
 */

public class RecommendAdapter extends CommonAdapter<BookDetailRecommendBookBean>{
    public RecommendAdapter(Context context, List<BookDetailRecommendBookBean> data) {
        super(R.layout.item_list_book, data);
    }

    /**
     * Implement this method and use the helper to adapt the view to the given item.
     *
     * @param helper A fully initialized helper.
     * @param item   The item that needs to be displayed.

     */
    @Override
    protected void convert(CommonViewHolder helper, BookDetailRecommendBookBean item) {

    }
}
