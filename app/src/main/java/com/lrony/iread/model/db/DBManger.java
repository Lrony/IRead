package com.lrony.iread.model.db;

import android.support.annotation.Nullable;

import com.lrony.iread.App;
import com.lrony.iread.model.db.table.DaoSession;
import com.lrony.iread.model.db.table.SearchHistory;
import com.lrony.iread.model.db.table.SearchHistoryDao;

import java.util.List;

/**
 * Created by Lrony on 18-5-22.
 */
public final class DBManger {

    private DaoSession mDaoSession;

    private static final DBManger sDBManger = new DBManger();

    private DBManger() {
        DBRepository.initDatabase(App.context());
        mDaoSession = DBRepository.getDaoSession();
    }

    public static DBManger getInstance() {
        return sDBManger;
    }

    // 保存搜索关键字
    public void saveSearchKeyword(@Nullable String keyword) {
        if (keyword != null) {
            SearchHistoryDao searchHistoryDao = mDaoSession.getSearchHistoryDao();
            SearchHistory searchHistory = searchHistoryDao.load(keyword);
            if (searchHistory != null) {
                searchHistory.setTimestamp(System.currentTimeMillis());
                searchHistoryDao.update(searchHistory);
            } else {
                searchHistory = new SearchHistory();
                searchHistory.setKeyword(keyword);
                searchHistory.setTimestamp(System.currentTimeMillis());
                searchHistoryDao.insert(searchHistory);
            }
        }
    }

    // 删除搜索关键字
    public void deleteSearchKeyword(@Nullable String keyword) {
        if (keyword != null) {
            SearchHistoryDao searchHistoryDao = mDaoSession.getSearchHistoryDao();
            searchHistoryDao.deleteByKey(keyword);
        }
    }

    // 删除所有搜索历史
    public void clearSearchKeyword() {
        SearchHistoryDao searchHistoryDao = mDaoSession.getSearchHistoryDao();
        searchHistoryDao.deleteAll();
    }

    // 读取搜索历史
    public List<SearchHistory> loadSearchKeyword() {
        SearchHistoryDao searchHistoryDao = mDaoSession.getSearchHistoryDao();
        return searchHistoryDao
                .queryBuilder()
                .orderDesc(SearchHistoryDao.Properties.Timestamp)
                .list();
    }
}
