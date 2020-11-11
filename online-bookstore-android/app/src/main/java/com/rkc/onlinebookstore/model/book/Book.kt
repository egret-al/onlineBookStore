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
    @SerializedName("id") val id: Int,
    @SerializedName("book_name") val bookName: String,
    @SerializedName("isbn") val isbn: String,
    @SerializedName("author") val author: String,
    @SerializedName("publisher") val publisher: String,
    @SerializedName("price") val price: Int,
    @SerializedName("create_time") val createTime: Date,
    @SerializedName("t_id") val typeId: Int,
    @SerializedName("main_cover") val mainCover: String,
    @SerializedName("description") val description: String,
    @SerializedName("bookResources") val bookResources: ArrayList<BookResource>,
    @SerializedName("bookStorage") val bookStorage: BookStorage
) : Parcelable