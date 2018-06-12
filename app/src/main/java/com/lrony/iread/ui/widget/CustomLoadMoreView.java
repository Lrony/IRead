package com.lrony.iread.ui.widget;

import com.chad.library.adapter.base.loadmore.LoadMoreView;
import com.lrony.iread.R;

/**
 * Created by Lrony on 18-6-12.
 */
public class CustomLoadMoreView extends LoadMoreView {

    @Override
    public int getLayoutId() {
        return R.layout.view_load_more;
    }

    @Override
    protected int getLoadingViewId() {
        return R.id.loading_view;
    }

    @Override
    protected int getLoadFailViewId() {
        return R.id.error_view;
    }

    @Override
    protected int getLoadEndViewId() {
        return R.id.end_view;
    }
}
