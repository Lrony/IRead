package com.lrony.iread.model.db.table;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

/**
 * Created by Lrony on 18-5-22.
 * 搜索历史表
 */
@Entity(
        active = true
)
public class SearchHistory implements Parcelable {

    @Id
    private String keyword;

    private long timestamp;

    /**
     * Used to resolve relations
     */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /**
     * Used for active entity operations.
     */
    @Generated(hash = 328013218)
    private transient SearchHistoryDao myDao;

    @Generated(hash = 1339179721)
    public SearchHistory(String keyword, long timestamp) {
        this.keyword = keyword;
        this.timestamp = timestamp;
    }

    @Generated(hash = 1905904755)
    public SearchHistory() {
    }

    protected SearchHistory(Parcel in) {
        keyword = in.readString();
        timestamp = in.readLong();
    }

    public static final Creator<SearchHistory> CREATOR = new Creator<SearchHistory>() {
        @Override
        public SearchHistory createFromParcel(Parcel in) {
            return new SearchHistory(in);
        }

        @Override
        public SearchHistory[] newArray(int size) {
            return new SearchHistory[size];
        }
    };

    public String getKeyword() {
        return this.keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(keyword);
        dest.writeLong(timestamp);
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1862102362)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getSearchHistoryDao() : null;
    }
}
