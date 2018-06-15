package com.lrony.iread.pref;

import com.lrony.iread.util.FileUtils;

import java.io.File;

/**
 * Created by Lrony on 18-5-22.
 */
public class Constant {

    /*URL_BASE*/
    public static final String API_BASE_URL = "http://api.zhuishushenqi.com";
    public static final String IMG_BASE_URL = "http://statics.zhuishushenqi.com";

    public static final String API_OTHER_URL = "https://www.sojson.com/open/api/";
    public static final String API_WY_URL = "https://api.4gml.com/";

    //BookCachePath (因为getCachePath引用了Context，所以必须是静态变量，不能够是静态常量)
    public static String BOOK_CACHE_PATH = FileUtils.getCachePath()+ File.separator
            + "book_cache"+ File.separator ;
}
