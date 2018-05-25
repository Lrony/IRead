package com.lrony.iread.presentation.book.detail;

import android.content.Context;
import android.widget.ImageView;

import com.lrony.iread.R;
import com.lrony.iread.model.bean.BookDetailRecommendBookBean;
import com.lrony.iread.pref.Constant;
import com.lrony.iread.ui.help.CommonAdapter;
import com.lrony.iread.ui.help.CommonViewHolder;
import com.lrony.iread.util.ImageLoader;

import java.util.List;

/**
 * Created by liuxiaobin on 18-5-23.
 */

public class BookDetailRecommendAdapter extends CommonAdapter<BookDetailRecommendBookBean> {

    private Context context;

    public BookDetailRecommendAdapter(Context context, List<BookDetailRecommendBookBean> data) {
        super(R.layout.item_grid_book, data);
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
        ImageLoader.load(context, Constant.IMG_BASE_URL + item.getCover()
                , (ImageView) helper.getView(R.id.iv_cover));
    }
}
