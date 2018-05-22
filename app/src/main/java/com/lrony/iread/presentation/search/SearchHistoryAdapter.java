package com.lrony.iread.presentation.search;

import com.lrony.iread.R;
import com.lrony.iread.model.db.table.SearchHistory;
import com.lrony.iread.ui.help.CommonAdapter;
import com.lrony.iread.ui.help.CommonViewHolder;

import java.util.List;

/**
 * Created by Lrony on 18-5-22.
 */
public class SearchHistoryAdapter extends CommonAdapter<SearchHistory> {

    public SearchHistoryAdapter(List<SearchHistory> data) {
        super(R.layout.item_search_history, data);
    }

    @Override
    protected void convert(CommonViewHolder helper, SearchHistory item) {
        helper.setText(R.id.tv_keyword, item.getKeyword());
        helper.addOnClickListener(R.id.iv_keyword_clear);
    }
}
