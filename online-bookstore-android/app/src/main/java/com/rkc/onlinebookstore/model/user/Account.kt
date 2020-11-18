package com.rkc.onlinebookstore.model.user

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize
import java.util.*

/**
 * @author rkc
 * @date 2020/11/10 22:12
 * @version 1.0
 */
@Parcelize
data class Account(
    @SerializedName("username") var username: String = "",
    @SerializedName("password") var password: String = "",
    @SerializedName("score") var score: Int = 0,
    @SerializedName("create_time") var createTime: Date = Date(),
    @SerializedName("balance") var balance: Int = 0,
) : Parcelable {
    @IgnoredOnParcel
    @SerializedName("user")
    lateinit var user: User
}