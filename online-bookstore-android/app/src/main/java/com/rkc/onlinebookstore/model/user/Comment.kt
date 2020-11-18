package com.rkc.onlinebookstore.model.user

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize
import java.util.*

/**
 * @author rkc
 * @date 2020/11/17 16:49
 * @version 1.0
 */
@Parcelize
data class Comment(
    @SerializedName("id") var id: Int = -1,
    @SerializedName("account_username") var accountUsername: String = "",
    @SerializedName("book_id") var bookId: Int = -1,
    @SerializedName("content") var content: String = "",
    @SerializedName("create_time") var createTime: Date = Date()
) : Parcelable {
    @IgnoredOnParcel
    @SerializedName("account")
    lateinit var account: Account
}