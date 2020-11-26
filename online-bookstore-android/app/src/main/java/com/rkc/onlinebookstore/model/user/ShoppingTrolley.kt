package com.rkc.onlinebookstore.model.user

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.rkc.onlinebookstore.model.book.Book
import kotlinx.android.parcel.Parcelize
import java.util.*

/**
 * @author rkc
 * @date 2020/11/26 9:18
 * @version 1.0
 */
@Parcelize
data class ShoppingTrolley(
    @SerializedName("id") var id: Int = -1,
    @SerializedName("book_id") var bookId: Int = -1,
    @SerializedName("account_username") var accountUsername: String = "",
    @SerializedName("collect_count") var collectCount: Int = -1,
    @SerializedName("create_time") var createTime: Date = Date()
) : Parcelable {
    lateinit var account: Account
    lateinit var book: Book
}
