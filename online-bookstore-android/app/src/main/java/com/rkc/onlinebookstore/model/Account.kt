package com.rkc.onlinebookstore.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

/**
 * @author rkc
 * @date 2020/11/10 22:12
 * @version 1.0
 */
@Parcelize
data class Account(
    val username: String = "",
    val password: String = "",
    val score: Int = 0,
    val createTime: Date = Date(),
    val balance: Int = 0
) : Parcelable