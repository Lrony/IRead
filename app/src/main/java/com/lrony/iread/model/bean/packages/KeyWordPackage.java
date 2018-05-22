package com.lrony.iread.model.bean.packages;

import com.lrony.iread.model.bean.BaseBean;

import java.util.List;

/**
 * Created by Lrony on 18-5-22.
 */
public class KeyWordPackage extends BaseBean {

    private List<String> keywords;

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }
}
