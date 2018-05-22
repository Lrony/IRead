package com.lrony.iread.presentation.search;

import com.lrony.iread.R;
import com.lrony.iread.ui.help.CommonAdapter;
import com.lrony.iread.ui.help.CommonViewHolder;

import java.util.List;

/**
 * Created by Lrony on 18-5-22.
 */
public class KeyWordAdapter extends CommonAdapter<String> {

    public KeyWordAdapter(List<String> data) {
        super(R.layout.item_search_key_word, data);
    }

    @Override
    protected void convert(CommonViewHolder helper, String item) {
        helper.setText(R.id.tv_title, item);
    }
}
