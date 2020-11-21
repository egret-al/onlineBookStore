package com.rkc.onlinebookstore.model.user

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize
import java.util.*

/**
 * @author rkc
 * @date 2020/11/17 17:24
 * @version 1.0
 */
@Entity
@Parcelize
data class User(
    @SerializedName("id")
    @PrimaryKey(autoGenerate = true)
    val id: Int = -1,

    @SerializedName("account_username")
    @ColumnInfo(name = "account_username")
    var accountUsername: String = "",

    @SerializedName("nickname")
    @ColumnInfo(name = "nickname")
    var nickname: String = "",

    @SerializedName("birthday")
    @ColumnInfo(name = "birthday")
    var birthday: Date = Date(),

    @SerializedName("sex")
    @ColumnInfo(name = "sex")
    var sex: String = "",

    @SerializedName("phone")
    @ColumnInfo(name = "phone")
    var phone: String = "",

    @SerializedName("last_login_time")
    @ColumnInfo(name = "last_login_time")
    var lastLoginTime: Date = Date(),

    @SerializedName("default_address_id")
    var defaultAddressId: Int = -1

) : Parcelable {

    @Ignore
    @IgnoredOnParcel
    @SerializedName("account")
    lateinit var account: Account
}