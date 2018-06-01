package com.lrony.iread.presentation.book.catalog;

import com.lrony.iread.R;
import com.lrony.iread.model.bean.BookChapterBean;
import com.lrony.iread.model.bean.packages.BookChapterPackage;
import com.lrony.iread.ui.help.CommonAdapter;
import com.lrony.iread.ui.help.CommonViewHolder;

import java.util.List;

/**
 * Created by liuxiaobin on 18-6-1.
 */

public class BookCatalogAdapter extends CommonAdapter<BookChapterBean> {


    public BookCatalogAdapter(List<BookChapterBean> book) {
        super(R.layout.item_category, book);
    }


    /**
     * Implement this method and use the helper to adapt the view to the given item.
     *
     * @param helper A fully initialized helper.
     * @param item   The item that needs to be displayed.
     */
    @Override
    protected void convert(CommonViewHolder helper, BookChapterBean item) {
        helper.setText(R.id.category_tv_chapter,item.getTitle());

    }
}
