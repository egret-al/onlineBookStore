package com.rkc.onlinebookstore.model.book

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * @author rkc
 * @date 2020/11/14 16:39
 * @version 1.0
 */
@Parcelize
data class BookType(
    @SerializedName("id") val id: Int = -1,
    @SerializedName("type") val type: String = "",
    @SerializedName("supplement") val supplement: String = "",
    @SerializedName("img") val imgIcon: String = "",
    @SerializedName("books") val books: ArrayList<Book> = arrayListOf()
) : Parcelable