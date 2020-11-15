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
    @SerializedName("id") val id: Int = -1,
    @SerializedName("book_id") val bookId: Int = -1,
    @SerializedName("last_add_time") val lastAddTime: Date = Date(),
    @SerializedName("residue_count") val residueCount: Int = -1
) : Parcelable