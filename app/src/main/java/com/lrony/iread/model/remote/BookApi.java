package com.lrony.iread.model.remote;

import com.lrony.iread.model.bean.BookDetailBean;
import com.lrony.iread.model.bean.packages.BookChapterPackage;
import com.lrony.iread.model.bean.packages.BookSortPackage;
import com.lrony.iread.model.bean.packages.BookSubSortPackage;
import com.lrony.iread.model.bean.packages.ChapterInfoPackage;
import com.lrony.iread.model.bean.packages.HotCommentPackage;
import com.lrony.iread.model.bean.packages.KeyWordPackage;
import com.lrony.iread.model.bean.packages.RecommendBookListPackage;
import com.lrony.iread.model.bean.packages.RecommendBookPackage;
import com.lrony.iread.model.bean.packages.SearchBookPackage;
import com.lrony.iread.model.bean.packages.SortBookPackage;
import com.lrony.iread.model.bean.packages.TagSearchPackage;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Lrony on 18-5-22.
 */
public interface BookApi {

    /**
     * 推荐书籍
     *
     * @param gender
     * @return
     */
    @GET("/book/recommend")
    Single<RecommendBookPackage> getRecommendBookPackage(@Query("gender") String gender);


    /**
     * 获取书籍的章节总列表
     *
     * @param bookId
     * @param view   默认参数为:chapters
     * @return
     */
    @GET("/mix-atoc/{bookId}")
    Single<BookChapterPackage> getBookChapterPackage(@Path("bookId") String bookId, @Query("view") String view);

    /**
     * 章节的内容
     * 这里采用的是同步请求。
     *
     * @param url
     * @return
     */
    @GET("http://chapter2.zhuishushenqi.com/chapter/{url}")
    Single<ChapterInfoPackage> getChapterInfoPackage(@Path("url") String url);

    /*******************************分类***************************************/
    /**
     * 获取分类
     *
     * @return
     */
    @GET("/cats/lv2/statistics")
    Single<BookSortPackage> getBookSortPackage();

    /**
     * 获取二级分类
     *
     * @return
     */
    @GET("/cats/lv2")
    Single<BookSubSortPackage> getBookSubSortPackage();

    /**
     * 按分类获取书籍列表
     *
     * @param gender male、female
     * @param type   hot(热门)、new(新书)、reputation(好评)、over(完结)
     * @param major  玄幻
     * @param minor  东方玄幻、异界大陆、异界争霸、远古神话
     * @param limit  50
     * @return
     */
    @GET("/book/by-categories")
    Single<SortBookPackage> getSortBookPackage(@Query("gender") String gender, @Query("type") String type, @Query("major") String major, @Query("minor") String minor, @Query("start") int start, @Query("limit") int limit);

    /*************************书籍详情**********************************/

    /**
     * 书籍热门评论
     *
     * @param book
     * @return
     */
    @GET("/post/review/best-by-book")
    Single<HotCommentPackage> getHotCommnentPackage(@Query("book") String book);

    /**
     * 书籍推荐书单
     *
     * @param bookId
     * @param limit
     * @return
     */
    @GET("/book-list/{bookId}/recommend")
    Single<RecommendBookListPackage> getRecommendBookListPackage(@Path("bookId") String bookId, @Query("limit") String limit);

    /**
     * 书籍详情
     *
     * @param bookId
     * @return
     */
    @GET("/book/{bookId}")
    Single<BookDetailBean> getBookDetail(@Path("bookId") String bookId);

    /**
     * 根据书籍的 Tag 进行检索
     *
     * @param tags
     * @param start
     * @param limit
     * @return
     */
    @GET("/book/by-tags")
    Single<TagSearchPackage> getTagSearchPackage(@Query("tags") String tags, @Query("start") String start, @Query("limit") String limit);

    /************************************搜索书籍******************************************************/

    /**
     * 关键字自动补全
     *
     * @param query
     * @return
     */
    @GET("/book/auto-complete")
    Single<KeyWordPackage> getKeyWordPacakge(@Query("query") String query);

    /**
     * 书籍查询
     *
     * @param query:作者名或者书名
     * @return
     */
    @GET("/book/fuzzy-search")
    Single<SearchBookPackage> getSearchBookPackage(@Query("query") String query);
}
