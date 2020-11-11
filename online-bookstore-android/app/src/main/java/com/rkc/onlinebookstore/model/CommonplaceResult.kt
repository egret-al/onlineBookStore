package com.rkc.onlinebookstore.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * @author rkc
 * @date 2020/11/11 8:12
 * @version 1.0
 */
data class CommonplaceResult<T>(
    val code: Int = -999,
    val data: T,
    val message: String = ""
)