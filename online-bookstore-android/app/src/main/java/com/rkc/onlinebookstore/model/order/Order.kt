package com.rkc.onlinebookstore.model.order

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.util.*

/**
 * @author rkc
 * @date 2020/11/14 10:52
 * @version 1.0
 */
const val ORDER_EXPIRE = -1
const val ORDER_UN_PAYMENT = 0
const val ORDER_FINISHED = 1

@Parcelize
data class Order(
    @SerializedName("serial_number") val serialNumber: String = "",
    @SerializedName("book_id") val bookId: Int = -1,
    @SerializedName("username_id") val usernameId: String = "",
    @SerializedName("order_content") val orderContent: String = "",
    @SerializedName("product_count") val productCount: Int = -1,
    @SerializedName("whole_price") val wholePrice: Int = -1,
    @SerializedName("obtain_score") val obtainScore: Int = -1,
    @SerializedName("create_time") val createTime: Date = Date(),
    @SerializedName("payment_time") val paymentTime: Date = Date(),
    @SerializedName("delivery_time") val deliveryTime: Date = Date(),
    @SerializedName("end_time") val endTime: Date = Date(),
    @SerializedName("order_payment_status") val orderPaymentStatus: Int = -2,
    @SerializedName("use_score") val useScore: Int = -1,
    @SerializedName("book_name") val bookName: String = "",
    @SerializedName("phone") val phone: String = "",
    @SerializedName("receiver_name") val receiverName: String = "",
    @SerializedName("address") val address: String = ""
) : Parcelable