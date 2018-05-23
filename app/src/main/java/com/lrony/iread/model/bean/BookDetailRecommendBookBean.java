package com.lrony.iread.model.bean;

/**
 * Created by Lrony on 18-5-23.
 */
public class BookDetailRecommendBookBean {

    /**
     * _id : 59e2c2b08bde16e66f9e3b85
     * title : 大道朝天
     * author : 猫腻
     * site : zhuishuvip
     * cover : /agent/http://img.1391.com/api/v1/bookcenter/cover/1/2158396/2158396_afcd9c8e1c974e99a0a139a353b00f3b.jpg/
     * shortIntro : 千里杀一人，十步不愿行。
     * lastChapter : 第210章 谁来回答这个问题？
     * retentionRatio : 64.69
     * latelyFollower : 17735
     * majorCate : 玄幻
     * minorCate : 东方玄幻
     * allowMonthly : false
     * isSerial : true
     */

    private String _id;
    private String title;
    private String author;
    private String site;
    private String cover;
    private String shortIntro;
    private String lastChapter;
    private double retentionRatio;
    private int latelyFollower;
    private String majorCate;
    private String minorCate;
    private boolean allowMonthly;
    private boolean isSerial;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getShortIntro() {
        return shortIntro;
    }

    public void setShortIntro(String shortIntro) {
        this.shortIntro = shortIntro;
    }

    public String getLastChapter() {
        return lastChapter;
    }

    public void setLastChapter(String lastChapter) {
        this.lastChapter = lastChapter;
    }

    public double getRetentionRatio() {
        return retentionRatio;
    }

    public void setRetentionRatio(double retentionRatio) {
        this.retentionRatio = retentionRatio;
    }

    public int getLatelyFollower() {
        return latelyFollower;
    }

    public void setLatelyFollower(int latelyFollower) {
        this.latelyFollower = latelyFollower;
    }

    public String getMajorCate() {
        return majorCate;
    }

    public void setMajorCate(String majorCate) {
        this.majorCate = majorCate;
    }

    public String getMinorCate() {
        return minorCate;
    }

    public void setMinorCate(String minorCate) {
        this.minorCate = minorCate;
    }

    public boolean isAllowMonthly() {
        return allowMonthly;
    }

    public void setAllowMonthly(boolean allowMonthly) {
        this.allowMonthly = allowMonthly;
    }

    public boolean isIsSerial() {
        return isSerial;
    }

    public void setIsSerial(boolean isSerial) {
        this.isSerial = isSerial;
    }
}
