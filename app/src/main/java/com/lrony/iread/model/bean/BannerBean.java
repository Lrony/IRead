package com.lrony.iread.model.bean;

/**
 * Created by Lrony on 18-5-23.
 */
public class BannerBean extends BaseBean {

    private String path;
    private String title;

    public BannerBean(String path, String title) {
        this.path = path;
        this.title = title;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
