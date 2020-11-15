package com.rkc.onlinebookstore.model.book

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.util.*
import kotlin.collections.ArrayList

/**
 * @author rkc
 * @date 2020/11/11 17:42
 * @version 1.0
 */
@Parcelize
data class Book (
    @SerializedName("id") val id: Int = -1,
    @SerializedName("book_name") val bookName: String = "",
    @SerializedName("isbn") val isbn: String = "",
    @SerializedName("author") val author: String = "",
    @SerializedName("publisher") val publisher: String = "",
    @SerializedName("price") val price: Int = -1,
    @SerializedName("create_time") val createTime: Date = Date(),
    @SerializedName("t_id") val typeId: Int = -1,
    @SerializedName("main_cover") val mainCover: String = "",
    @SerializedName("description") val description: String = "",
    @SerializedName("bookResources") var bookResources: ArrayList<BookResource> = arrayListOf(),
    @SerializedName("bookStorage") var bookStorage: BookStorage = BookStorage()
) : Parcelable