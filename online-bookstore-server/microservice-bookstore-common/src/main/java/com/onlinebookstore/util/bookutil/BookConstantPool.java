package com.onlinebookstore.util.bookutil;

/**
 * @author rkc
 * @date 2020/9/17 15:54
 * @version 1.0
 */
public class BookConstantPool {

    /**
     * 缓存时间：30秒 1分钟 5分钟 10分钟 30分钟 1小时
     * 分别对应下标0   1    2     3     4      5
     */
    public static final long[] CACHE_TIME = { 30, 60, 300, 600, 1800, 3600 };

    public static final String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static final String TIMEZONE = "GMT+8";

    public static final String SELECT_ALL_BOOK_ALONE = "selectAllBookAlone";

    public static final String SELECT_ALL_BOOK_INFO = "selectAllBookInfo";

    public static final String SELECT_ALL_BOOK_ALONE_BY_ID = "selectAllBookAloneById";

    public static final String SELECT_ALL_BOOK_WITH_RESOURCE = "selectAllBookWithResource";

    public static final String SELECT_ALL_BOOK_INFO_BY_BOOK_ID = "selectAllBookInfoByBookId";

    public static final String SELECT_ALL_BOOK_WITH_RESOURCE_BY_BOOK_ID = "selectAllBookWithResourceByBookId";

    public static final String SELECT_ALL_RESOURCE_ALONE = "selectAllResourceAlone";

    public static final String SELECT_ALL_BOOK_WITH_STORAGE_BY_BOOK_ID = "selectAllBookWithStorageByBookId";

    public static final String SELECT_ALL_RESOURCE_ALONE_BY_BOOK_ID = "selectAllResourceAloneByBookId";

    public static final String SELECT_RESOURCE_BY_ID = "selectResourceById";

    public static final String SELECT_ALL = "BookBannerServiceImpl.selectAll";

    public static final String SELECT_COUNT = "BookBannerServiceImpl.selectCount";

    public static final String SELECT_ALL_TYPE = "BookTypeServiceImpl.selectAllType";

    public static final String SELECT_ALL_TYPE_WITH_BOOK = "BookTypeServiceImpl.selectAllTypeWithBook";

    public static final String SELECT_TYPE_BY_ID = "BookTypeServiceImpl.selectTypeById";
}
