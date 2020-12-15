package com.rkc.onlinebookstore.viewmodel.home.order

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rkc.onlinebookstore.util.OKHttpUtils
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException

/**
 * @author rkc
 * @date 2020/11/14 11:31
 * @version 1.0
 */
const val ORDER_CANCEL_SUCCESSFULLY = 1
const val ORDER_CANCEL_FAILURE = -1
const val ORDER_CANCEL_STATUS = 0

const val ORDER_FINISH_SUCCESSFULLY = 1
const val ORDER_FINISH_FAILURE = -1
const val ORDER_FINISH_STATUS = 0

class UnpaidOrderViewModel(application: Application) : AndroidViewModel(application) {
    private val _orderCancelStatus = MutableLiveData<Int>().apply { value = ORDER_CANCEL_STATUS }
    val orderCancelStatus: LiveData<Int> = _orderCancelStatus

    private val _orderFinishStatus = MutableLiveData<Int>().apply { value = ORDER_FINISH_STATUS }
    val orderFinishStatus: LiveData<Int> = _orderFinishStatus
    val orderFinishMessage = MutableLiveData<String>()

    /**
     * 取消订单
     */
    fun cancelOrder(serialNumber: String) {
        val username = getApplication<Application>().getSharedPreferences("user", Context.MODE_PRIVATE).getString("username", "")
        if (username == "") return
        val js = JSONObject().apply {
            put("serialNumber", serialNumber)
            put("username", username)
        }
        OKHttpUtils.asyncHttpPostJson("/order-server/api/v1/order/pri/cancelOrder", js, object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                _orderCancelStatus.postValue(ORDER_CANCEL_FAILURE)
                Log.e("error", e.toString())
            }
            override fun onResponse(call: Call, response: Response) {
                val jsonObject = JSONObject(response.body?.string())
                if (jsonObject.getInt("code") == 1) {
                    //订单取消成功
                    _orderCancelStatus.postValue(ORDER_CANCEL_SUCCESSFULLY)
                } else {
                    _orderCancelStatus.postValue(ORDER_CANCEL_FAILURE)
                    Log.e("error", jsonObject.toString())
                }
            }
        })
    }

    /**
     * 下单
     */
    fun purchase(serialNumber: String) {
        OKHttpUtils.asyncHttpPostJson("/user-server/api/v1/account/pri/purchaseBook", JSONObject().apply { put("serialNumber", serialNumber) } ,object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                _orderFinishStatus.postValue(ORDER_FINISH_FAILURE)
                Log.e("error", e.toString())
            }
            override fun onResponse(call: Call, response: Response) {
                val jsonObject = JSONObject(response.body?.string())
                if (jsonObject.getInt("code") == 1) {
                    //购买成功
                    orderFinishMessage.postValue(jsonObject.getString("message"))
                    _orderFinishStatus.postValue(ORDER_FINISH_SUCCESSFULLY)
                } else {
                    //购买失败
                    orderFinishMessage.postValue(jsonObject.getString("message"))
                    _orderFinishStatus.postValue(ORDER_FINISH_FAILURE)
                    Log.e("error", jsonObject.toString())
                }
            }
        })
    }
}