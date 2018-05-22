package com.lrony.iread.model.bean.packages;

import com.lrony.iread.model.bean.BaseBean;
import com.lrony.iread.model.bean.HotCommentBean;

import java.util.List;

/**
 * Created by Lrony on 18-5-22.
 */
public class HotCommentPackage extends BaseBean {

    private List<HotCommentBean> reviews;

    public List<HotCommentBean> getReviews() {
        return reviews;
    }

    public void setReviews(List<HotCommentBean> reviews) {
        this.reviews = reviews;
    }
}
