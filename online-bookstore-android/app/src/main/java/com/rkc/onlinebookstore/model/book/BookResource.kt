package com.rkc.onlinebookstore.model.book

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.util.*

/**
 * @author rkc
 * @date 2020/11/11 18:33
 * @version 1.0
 */
@Parcelize
data class BookResource(
    @SerializedName("id") val id: Int,
    @SerializedName("book_id") val bookId: Int,
    @SerializedName("resource_url") val resourceUrl: String,
    @SerializedName("symbol") val symbol: String,
    @SerializedName("supplement") val supplement: String,
    @SerializedName("create_time") val createTime: Date
) : Parcelable