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

    public static final String BOOK = "book-server:book";

    public static final String BOOK_TYPE = "book-server:bookType";

    public static final String BOOK_STORAGE = "book-server:bookStorage";

    public static final String BOOK_RESOURCE = "book-server:bookResource";

    public static final String SELECT_ALL_BOOK_ALONE = "book-server:selectAllBookAlone";

    public static final String SELECT_ALL_BOOK_INFO = "book-server:selectAllBookInfo";

    public static final String SELECT_ALL_BOOK_INFO_LIKE = "book-server:selectAllBookInfoLike";

    public static final String SELECT_ALL_BOOK_INFO_TYPE = "book-server:selectAllBookInfoType";

    public static final String SELECT_ALL_BOOK_ALONE_BY_ID = "book-server:selectAllBookAloneById";

    public static final String SELECT_ALL_BOOK_WITH_RESOURCE = "book-server:selectAllBookWithResource";

    public static final String SELECT_ALL_BOOK_WITH_RESOURCE_BY_TYPE = "book-server:selectAllBookWithResourceByType";

    public static final String SELECT_ALL_BOOK_WITH_RESOURCE_LIKE = "book-server:selectAllBookWithResourceLike";

    public static final String SELECT_ALL_BOOK_INFO_BY_BOOK_ID = "book-server:selectAllBookInfoByBookId";

    public static final String SELECT_ALL_BOOK_WITH_RESOURCE_BY_BOOK_ID = "book-server:selectAllBookWithResourceByBookId";

    public static final String SELECT_ALL_RESOURCE_ALONE = "book-server:selectAllResourceAlone";

    public static final String SELECT_ALL_BOOK_WITH_STORAGE_BY_BOOK_ID = "book-server:selectAllBookWithStorageByBookId";

    public static final String SELECT_ALL_RESOURCE_ALONE_BY_BOOK_ID = "book-server:selectAllResourceAloneByBookId";

    public static final String SELECT_RESOURCE_BY_ID = "book-server:selectResourceById";

    public static final String SELECT_ALL = "book-server:BookBannerServiceImpl.selectAll";

    public static final String SELECT_COUNT = "book-server:BookBannerServiceImpl.selectCount";

    public static final String SELECT_ALL_TYPE = "book-server:BookTypeServiceImpl.selectAllType";

    public static final String SELECT_ALL_TYPE_WITH_BOOK = "book-server:BookTypeServiceImpl.selectAllTypeWithBook";

    public static final String SELECT_TYPE_BY_ID = "book-server:BookTypeServiceImpl.selectTypeById";
}
