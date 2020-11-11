package com.rkc.onlinebookstore.model.user

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.util.*

/**
 * @author rkc
 * @date 2020/11/10 22:12
 * @version 1.0
 */
@Parcelize
data class Account(
    @SerializedName("username") val username: String = "",
    @SerializedName("password") val password: String = "",
    @SerializedName("score") val score: Int = 0,
    @SerializedName("create_time") val createTime: Date = Date(),
    @SerializedName("balance") val balance: Int = 0
) : Parcelable