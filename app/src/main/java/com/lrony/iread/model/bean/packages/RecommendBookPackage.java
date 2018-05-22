package com.lrony.iread.model.bean.packages;

import com.lrony.iread.model.bean.BaseBean;
import com.lrony.iread.model.bean.CollBookBean;

import java.util.List;

/**
 * Created by Lrony on 18-5-22.
 */
public class RecommendBookPackage extends BaseBean {

    private List<CollBookBean> books;

    public List<CollBookBean> getBooks() {
        return books;
    }

    public void setBooks(List<CollBookBean> books) {
        this.books = books;
    }
}
