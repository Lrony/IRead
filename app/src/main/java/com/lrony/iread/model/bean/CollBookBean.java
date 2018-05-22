package com.lrony.iread.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Lrony on 18-5-22.
 */
@Entity
public class CollBookBean implements Parcelable {

    public static final int STATUS_UNCACHE = 0; //未缓存
    public static final int STATUS_CACHING = 1; //正在缓存
    public static final int STATUS_CACHED = 2;  //已经缓存
    /**
     * _id : 53663ae356bdc93e49004474
     * title : 逍遥派
     * author : 白马出淤泥
     * shortIntro : 金庸武侠中有不少的神秘高手，书中或提起名字，或不曾提起，总之他们要么留下了绝世秘笈，要么就名震武林。 独孤九剑的创始者，独孤求败，他真的只创出九剑吗？ 残本葵花...
     * cover : /cover/149273897447137
     * hasCp : true
     * latelyFollower : 60213
     * retentionRatio : 22.87
     * updated : 2017-05-07T18:24:34.720Z
     * <p>
     * chaptersCount : 1660
     * lastChapter : 第1659章 朱长老
     */
    @Id
    private String _id; // 本地书籍中，path 的 md5 值作为本地书籍的 id
    private String title;
    private String author;
    private String shortIntro;
    private String cover; // 在本地书籍中，该字段作为本地文件的路径
    private boolean hasCp;
    private int latelyFollower;
    private double retentionRatio;
    //最新更新日期
    private String updated;
    //最新阅读日期
    private String lastRead;
    private int chaptersCount;
    private String lastChapter;
    //是否更新或未阅读
    private boolean isUpdate = true;
    //是否是本地文件
    private boolean isLocal = false;

    @Generated(hash = 757968961)
    public CollBookBean(String _id, String title, String author, String shortIntro, String cover,
                        boolean hasCp, int latelyFollower, double retentionRatio, String updated, String lastRead,
                        int chaptersCount, String lastChapter, boolean isUpdate, boolean isLocal) {
        this._id = _id;
        this.title = title;
        this.author = author;
        this.shortIntro = shortIntro;
        this.cover = cover;
        this.hasCp = hasCp;
        this.latelyFollower = latelyFollower;
        this.retentionRatio = retentionRatio;
        this.updated = updated;
        this.lastRead = lastRead;
        this.chaptersCount = chaptersCount;
        this.lastChapter = lastChapter;
        this.isUpdate = isUpdate;
        this.isLocal = isLocal;
    }

    @Generated(hash = 149378998)
    public CollBookBean() {
    }

    protected CollBookBean(Parcel in) {
        _id = in.readString();
        title = in.readString();
        author = in.readString();
        shortIntro = in.readString();
        cover = in.readString();
        hasCp = in.readByte() != 0;
        latelyFollower = in.readInt();
        retentionRatio = in.readDouble();
        updated = in.readString();
        lastRead = in.readString();
        chaptersCount = in.readInt();
        lastChapter = in.readString();
        isUpdate = in.readByte() != 0;
        isLocal = in.readByte() != 0;
    }

    public static final Creator<CollBookBean> CREATOR = new Creator<CollBookBean>() {
        @Override
        public CollBookBean createFromParcel(Parcel in) {
            return new CollBookBean(in);
        }

        @Override
        public CollBookBean[] newArray(int size) {
            return new CollBookBean[size];
        }
    };

    public String get_id() {
        return this._id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return this.author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getShortIntro() {
        return this.shortIntro;
    }

    public void setShortIntro(String shortIntro) {
        this.shortIntro = shortIntro;
    }

    public String getCover() {
        return this.cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public boolean getHasCp() {
        return this.hasCp;
    }

    public void setHasCp(boolean hasCp) {
        this.hasCp = hasCp;
    }

    public int getLatelyFollower() {
        return this.latelyFollower;
    }

    public void setLatelyFollower(int latelyFollower) {
        this.latelyFollower = latelyFollower;
    }

    public double getRetentionRatio() {
        return this.retentionRatio;
    }

    public void setRetentionRatio(double retentionRatio) {
        this.retentionRatio = retentionRatio;
    }

    public String getUpdated() {
        return this.updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getLastRead() {
        return this.lastRead;
    }

    public void setLastRead(String lastRead) {
        this.lastRead = lastRead;
    }

    public int getChaptersCount() {
        return this.chaptersCount;
    }

    public void setChaptersCount(int chaptersCount) {
        this.chaptersCount = chaptersCount;
    }

    public String getLastChapter() {
        return this.lastChapter;
    }

    public void setLastChapter(String lastChapter) {
        this.lastChapter = lastChapter;
    }

    public boolean getIsUpdate() {
        return this.isUpdate;
    }

    public void setIsUpdate(boolean isUpdate) {
        this.isUpdate = isUpdate;
    }

    public boolean getIsLocal() {
        return this.isLocal;
    }

    public void setIsLocal(boolean isLocal) {
        this.isLocal = isLocal;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_id);
        dest.writeString(title);
        dest.writeString(author);
        dest.writeString(shortIntro);
        dest.writeString(cover);
        dest.writeByte((byte) (hasCp ? 1 : 0));
        dest.writeInt(latelyFollower);
        dest.writeDouble(retentionRatio);
        dest.writeString(updated);
        dest.writeString(lastRead);
        dest.writeInt(chaptersCount);
        dest.writeString(lastChapter);
        dest.writeByte((byte) (isUpdate ? 1 : 0));
        dest.writeByte((byte) (isLocal ? 1 : 0));
    }
}
