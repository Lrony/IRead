package com.lrony.iread.model.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Lrony on 18-5-22.
 */
@Entity
public class BookHelpfulBean {

    /**
     * total : 1
     * no : 5
     * yes : 6
     */
    @Id
    private String _id;

    private int total;
    private int no;
    private int yes;

    @Generated(hash = 1641622044)
    public BookHelpfulBean(String _id, int total, int no, int yes) {
        this._id = _id;
        this.total = total;
        this.no = no;
        this.yes = yes;
    }

    @Generated(hash = 534892841)
    public BookHelpfulBean() {
    }

    public String get_id() {
        return this._id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public int getTotal() {
        return this.total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getNo() {
        return this.no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public int getYes() {
        return this.yes;
    }

    public void setYes(int yes) {
        this.yes = yes;
    }
}
