package com.rkc.onlinebookstore.viewmodel.home.order

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.rkc.onlinebookstore.model.order.Order
import com.rkc.onlinebookstore.util.GsonUtils
import com.rkc.onlinebookstore.util.KotlinType
import com.rkc.onlinebookstore.util.OKHttpUtils
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException

/**
 * @author rkc
 * @date 2020/11/15 21:14
 * @version 1.0
 */
class OrderListViewModel(application: Application) : AndroidViewModel(application) {
    private var _orderList = MutableLiveData<List<Order>>().apply { value = arrayListOf() }
    val orderList = _orderList

    fun fetchOrderList() {
        val username = getApplication<Application>().getSharedPreferences("user", Context.MODE_PRIVATE).getString("username", "")
        if (username == "") return
        OKHttpUtils.asyncHttpGet("/order-server/api/v1/order/pri/selectOrderByUsername/$username", object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("error", e.toString())
            }

            override fun onResponse(call: Call, response: Response) {
                val jsonObject = JSONObject(response.body?.string())
                if (jsonObject.getInt("code") == 1) {
                    _orderList.postValue(GsonUtils.getGson().fromJson(jsonObject.getJSONArray("data").toString(), KotlinType.getType(List::class.java, Order::class.java)))
                } else {
                    //TODO nothing
                }
            }
        })
    }
}