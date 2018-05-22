package com.lrony.iread.presentation.search;

import android.content.Context;
import android.widget.ImageView;

import com.lrony.iread.R;
import com.lrony.iread.model.bean.packages.SearchBookPackage;
import com.lrony.iread.pref.Constant;
import com.lrony.iread.ui.help.CommonAdapter;
import com.lrony.iread.ui.help.CommonViewHolder;
import com.lrony.iread.util.ImageLoader;

import java.util.List;

/**
 * Created by Lrony on 18-5-22.
 */
public class SearchBookAdapter extends CommonAdapter<SearchBookPackage.BooksBean> {

    private Context context;

    public SearchBookAdapter(Context context, List<SearchBookPackage.BooksBean> data) {
        super(R.layout.item_list_book, data);
        this.context = context;
    }

    @Override
    protected void convert(CommonViewHolder helper, SearchBookPackage.BooksBean item) {
        helper.setText(R.id.tv_title, item.getTitle());
        helper.setText(R.id.tv_describe, item.getShortIntro());
        helper.setText(R.id.tv_author, item.getAuthor());
        helper.setText(R.id.tv_type, item.getCat());
        helper.setText(R.id.tv_retention_ratio, item.getRetentionRatio() + "%留存率");

        ImageLoader.load(context, Constant.IMG_BASE_URL + item.getCover()
                , (ImageView) helper.getView(R.id.iv_cover));
    }
}
