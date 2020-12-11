package com.rkc.onlinebookstore.datasource.home.home

import androidx.paging.DataSource
import com.rkc.onlinebookstore.model.book.Book

/**
 * @author rkc
 * @date 2020/12/11 10:18
 * @version 1.0
 */
class BookTypeDataSourceFactory(private val typeId: Int) : DataSource.Factory<Int, Book>() {
    override fun create(): DataSource<Int, Book> {
        return BookTypeDataSource(typeId)
    }
}