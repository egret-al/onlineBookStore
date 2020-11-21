package com.rkc.onlinebookstore.model.user

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * @author rkc
 * @date 2020/11/19 17:35
 * @version 1.0
 */
@Parcelize
data class Address(
    @SerializedName("id") var id: Int = -1,
    @SerializedName("account_username") var accountUsername: String = "",
    @SerializedName("phone") var phone: String = "",
    @SerializedName("receiver_name") var receiverName: String = "",
    @SerializedName("address") var address: String = ""
) : Parcelable