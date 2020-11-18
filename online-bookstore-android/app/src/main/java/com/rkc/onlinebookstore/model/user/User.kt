package com.rkc.onlinebookstore.model.user

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize
import java.util.*

/**
 * @author rkc
 * @date 2020/11/17 17:24
 * @version 1.0
 */
@Parcelize
data class User(
    @SerializedName("id") val id: Int = -1,
    @SerializedName("account_username") val accountUsername: String = "",
    @SerializedName("nickname") val nickname: String = "",
    @SerializedName("birthday") val birthday: Date = Date(),
    @SerializedName("sex") val sex: String = "",
    @SerializedName("phone") val phone: String = "",
    @SerializedName("last_login_time") val lastLoginTime: Date = Date(),
) : Parcelable {
    @IgnoredOnParcel
    @SerializedName("account")
    lateinit var account: Account
}