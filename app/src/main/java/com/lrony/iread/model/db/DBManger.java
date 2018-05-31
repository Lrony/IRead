package com.lrony.iread.model.db;

import android.support.annotation.Nullable;
import android.util.Log;

import com.lrony.iread.App;
import com.lrony.iread.model.bean.BookChapterBean;
import com.lrony.iread.model.bean.BookChapterBeanDao;
import com.lrony.iread.model.bean.BookRecordBean;
import com.lrony.iread.model.bean.BookRecordBeanDao;
import com.lrony.iread.model.bean.CollBookBean;
import com.lrony.iread.model.bean.CollBookBeanDao;
import com.lrony.iread.model.db.table.DaoSession;
import com.lrony.iread.model.db.table.SearchHistory;
import com.lrony.iread.model.db.table.SearchHistoryDao;
import com.lrony.iread.util.BookManager;
import com.lrony.iread.util.IOUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;

/**
 * Created by Lrony on 18-5-22.
 */
public final class DBManger {

    private static final String TAG = "DBManger";

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

    //********************************************************************

    /**
     * 保存图书到书架
     *
     * @param book
     */
    public void saveBookTb(CollBookBean book) {
        if (loadBookTbById(book.get_id()) == null) {
            mDaoSession.getCollBookBeanDao().insert(book);
        } else {
            mDaoSession.getCollBookBeanDao().update(book);
        }
    }

    /**
     * 通过ID取书籍
     *
     * @param bookid
     * @return
     */
    public CollBookBean loadBookTbById(String bookid) {
        return mDaoSession.getCollBookBeanDao().load(bookid);
    }

    /**
     * 判断书籍是否存在
     *
     * @param bookId
     * @return
     */
    public boolean hasBookTb(String bookId) {
        return loadBookTbById(bookId) != null;
    }

    /**
     * 判断书籍是否存在
     *
     * @param book
     * @return
     */
    public boolean hasBookTb(CollBookBean book) {
        return hasBookTb(book.get_id());
    }

    /**
     * 通过表删除书籍
     *
     * @param bookTb
     */
    public void deleteBookTb(CollBookBean bookTb) {
        mDaoSession.getCollBookBeanDao().delete(bookTb);
    }

    /**
     * 通过书籍ID删除书籍
     *
     * @param bookid
     */
    public void deleteBookTb(String bookid) {
        mDaoSession.getCollBookBeanDao().deleteByKey(bookid);
    }

    /**
     * 删除多本书籍
     *
     * @param entities
     */
    public void deleteBookTbs(Iterable<CollBookBean> entities) {
        mDaoSession.getCollBookBeanDao().deleteInTx(entities);
    }

    /**
     * 更新书籍信息
     *
     * @param bookTb
     * @return
     */
    public boolean updateBookTb(CollBookBean bookTb) {
        if (loadBookTbById(bookTb.get_id()) != null) {
            mDaoSession.getCollBookBeanDao().update(bookTb);
            return true;
        }
        return false;
    }

    /**
     * 获取书架图书数量
     *
     * @return
     */
    public long getBookTbCount() {
        return mDaoSession.getCollBookBeanDao().count();
    }

    /**
     * 获取所有书架书籍
     *
     * @return
     */
    public List<CollBookBean> loadAllBookTb() {
        return mDaoSession.getCollBookBeanDao()
                .queryBuilder()
                .orderDesc(CollBookBeanDao.Properties.LastRead)
                .list();
    }

//    /**
//     * 获取所有书架书籍
//     *
//     * @return
//     */
//    public List<CollBookBean> loadAllBookTb() {
//        return mDaoSession.getCollBookBeanDao()
//                .queryBuilder()
//                .orderAsc(CollBookBeanDao.Properties.Sort)
//                .list();
//    }
//
//    /**
//     * 获取的书籍，根据阅读时间排序
//     *
//     * @return
//     */
//    public List<CollBookBean> loadBookTbOrderLatestRead() {
//        return mDaoSession.getCollBookBeanDao()
//                .queryBuilder()
//                .orderDesc(CollBookBeanDao.Properties.LatestReadTimestamp, CollBookBeanDao.Properties.Sort)
//                .list();
//    }
//
//    /**
//     * 获取的书籍，根据阅读次数排序
//     *
//     * @return
//     */
//    public List<CollBookBean> loadBookTbOrderMostRead() {
//        return mDaoSession.getCollBookBeanDao()
//                .queryBuilder()
//                .orderDesc(CollBookBeanDao.Properties.ReadNumber, CollBookBeanDao.Properties.Sort)
//                .list();
//    }
//
//    /**
//     * 获取的书籍，根据书籍名称排序
//     *
//     * @return
//     */
//    public List<CollBookBean> loadBookTbOrderName() {
//        return mDaoSession.getCollBookBeanDao()
//                .queryBuilder()
//                .orderAsc(CollBookBeanDao.Properties.Title, CollBookBeanDao.Properties.Sort)
//                .list();
//    }

    /**
     * 保存阅读数据
     *
     * @param bean
     */
    public void saveBookRecord(BookRecordBean bean) {
        mDaoSession.getBookRecordBeanDao()
                .insertOrReplace(bean);
    }

    /**
     * 获取阅读记录
     *
     * @param bookId
     * @return
     */
    public BookRecordBean getBookRecord(String bookId) {
        return mDaoSession.getBookRecordBeanDao()
                .queryBuilder()
                .where(BookRecordBeanDao.Properties.BookId.eq(bookId))
                .unique();
    }

    //存储已收藏书籍
    public void saveCollBookWithAsync(CollBookBean bean) {
        //启动异步存储
        mDaoSession.startAsyncSession()
                .runInTx(
                        () -> {
                            if (bean.getBookChapters() != null) {
                                // 存储BookChapterBean
                                mDaoSession.getBookChapterBeanDao()
                                        .insertOrReplaceInTx(bean.getBookChapters());
                            }
                            //存储CollBook (确保先后顺序，否则出错)
                            mDaoSession.insertOrReplace(bean);
                        }
                );
    }

    /**
     * 异步存储BookChapter
     *
     * @param beans
     */
    public void saveBookChaptersWithAsync(List<BookChapterBean> beans) {
        mDaoSession.startAsyncSession()
                .runInTx(
                        () -> {
                            //存储BookChapterBean
                            mDaoSession.getBookChapterBeanDao()
                                    .insertOrReplaceInTx(beans);
                            Log.d(TAG, "saveBookChaptersWithAsync: " + "进行存储");
                        }
                );
    }

    /**
     * 存储章节
     *
     * @param folderName
     * @param fileName
     * @param content
     */
    public void saveChapterInfo(String folderName, String fileName, String content) {
        File file = BookManager.getBookFile(folderName, fileName);
        //获取流并存储
        Writer writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(file));
            writer.write(content);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
            IOUtils.close(writer);
        }
    }

    //获取书籍列表
    public Single<List<BookChapterBean>> getBookChaptersInRx(String bookId) {
        return Single.create(new SingleOnSubscribe<List<BookChapterBean>>() {
            @Override
            public void subscribe(SingleEmitter<List<BookChapterBean>> e) throws Exception {
                List<BookChapterBean> beans = mDaoSession
                        .getBookChapterBeanDao()
                        .queryBuilder()
                        .where(BookChapterBeanDao.Properties.BookId.eq(bookId))
                        .list();
                e.onSuccess(beans);
            }
        });
    }
}
