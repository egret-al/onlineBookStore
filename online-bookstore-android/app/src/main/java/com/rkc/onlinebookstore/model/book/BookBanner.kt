package com.rkc.onlinebookstore.model.book

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.util.*

/**
 * @author rkc
 * @date 2020/11/11 21:41
 * @version 1.0
 */
@Parcelize
data class BookBanner(
    @SerializedName("id") val id: Int,
    @SerializedName("resource_url") val resourceUrl: String,
    @SerializedName("modify_time") val modifyTime: Date,
    @SerializedName("b_id") val bookId: Int
) : Parcelable