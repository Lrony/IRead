package com.lrony.iread.model.bean.packages;

import com.lrony.iread.model.bean.BaseBean;
import com.lrony.iread.model.bean.BookSortBean;

import java.util.List;

/**
 * Created by Lrony on 18-5-22.
 */
public class BookSortPackage extends BaseBean {

    private List<BookSortBean> male;
    private List<BookSortBean> female;

    public List<BookSortBean> getMale() {
        return male;
    }

    public void setMale(List<BookSortBean> male) {
        this.male = male;
    }

    public List<BookSortBean> getFemale() {
        return female;
    }

    public void setFemale(List<BookSortBean> female) {
        this.female = female;
    }
}
