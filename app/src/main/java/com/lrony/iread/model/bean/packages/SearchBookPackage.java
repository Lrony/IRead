package com.lrony.iread.model.bean.packages;

import android.os.Parcel;
import android.os.Parcelable;

import com.lrony.iread.model.bean.BaseBean;

import java.util.List;

/**
 * Created by Lrony on 18-5-22.
 */
public class SearchBookPackage extends BaseBean {

    private List<BooksBean> books;

    public List<BooksBean> getBooks() {
        return books;
    }

    public void setBooks(List<BooksBean> books) {
        this.books = books;
    }

    public static class BooksBean implements Parcelable {
        /**
         * _id : 51d11e782de6405c45000068
         * hasCp : true
         * title : 大主宰
         * cat : 玄幻
         * author : 天蚕土豆
         * site : zhuishuvip
         * cover : /agent/http://image.cmfu.com/books/2750457/2750457.jpg
         * shortIntro : 大千世界，位面交汇，万族林立，群雄荟萃，一位位来自下位面的天之至尊，在这无尽世界，演绎着令人向往的传奇，追求着那主宰之路。 无尽火域，炎帝执掌，万火焚苍穹。 武...
         * lastChapter : 第1565章 人法合一
         * retentionRatio : 57.92
         * banned : 0
         * latelyFollower : 415890
         * wordCount : 4942027
         */

        private String _id;
        private boolean hasCp;
        private String title;
        private String cat;
        private String author;
        private String site;
        private String cover;
        private String shortIntro;
        private String lastChapter;
        private double retentionRatio;
        private int banned;
        private int latelyFollower;
        private int wordCount;

        protected BooksBean(Parcel in) {
            _id = in.readString();
            hasCp = in.readByte() != 0;
            title = in.readString();
            cat = in.readString();
            author = in.readString();
            site = in.readString();
            cover = in.readString();
            shortIntro = in.readString();
            lastChapter = in.readString();
            retentionRatio = in.readDouble();
            banned = in.readInt();
            latelyFollower = in.readInt();
            wordCount = in.readInt();
        }

        public static final Creator<BooksBean> CREATOR = new Creator<BooksBean>() {
            @Override
            public BooksBean createFromParcel(Parcel in) {
                return new BooksBean(in);
            }

            @Override
            public BooksBean[] newArray(int size) {
                return new BooksBean[size];
            }
        };

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public boolean isHasCp() {
            return hasCp;
        }

        public void setHasCp(boolean hasCp) {
            this.hasCp = hasCp;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCat() {
            return cat;
        }

        public void setCat(String cat) {
            this.cat = cat;
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

        public int getBanned() {
            return banned;
        }

        public void setBanned(int banned) {
            this.banned = banned;
        }

        public int getLatelyFollower() {
            return latelyFollower;
        }

        public void setLatelyFollower(int latelyFollower) {
            this.latelyFollower = latelyFollower;
        }

        public int getWordCount() {
            return wordCount;
        }

        public void setWordCount(int wordCount) {
            this.wordCount = wordCount;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(_id);
            dest.writeByte((byte) (hasCp ? 1 : 0));
            dest.writeString(title);
            dest.writeString(cat);
            dest.writeString(author);
            dest.writeString(site);
            dest.writeString(cover);
            dest.writeString(shortIntro);
            dest.writeString(lastChapter);
            dest.writeDouble(retentionRatio);
            dest.writeInt(banned);
            dest.writeInt(latelyFollower);
            dest.writeInt(wordCount);
        }
    }
}
