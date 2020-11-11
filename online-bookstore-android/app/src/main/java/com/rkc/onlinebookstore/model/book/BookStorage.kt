package com.rkc.onlinebookstore.model.book

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.util.*

/**
 * @author rkc
 * @date 2020/11/11 18:35
 * @version 1.0
 */
@Parcelize
data class BookStorage(
    @SerializedName("id") val id: Int,
    @SerializedName("book_id") val bookId: Int,
    @SerializedName("last_add_time") val lastAddTime: Date,
    @SerializedName("residue_count") val residueCount: Int
) : Parcelable