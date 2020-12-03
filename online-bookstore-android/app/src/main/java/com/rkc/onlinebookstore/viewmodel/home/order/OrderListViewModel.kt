package com.rkc.onlinebookstore.viewmodel.home.order

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.rkc.onlinebookstore.model.order.ACKNOWLEDGED
import com.rkc.onlinebookstore.model.order.Order
import com.rkc.onlinebookstore.util.AbstractOkHttpCallback
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
    private var _orderList = MutableLiveData<ArrayList<Order>>().apply { value = arrayListOf() }
    val orderList = _orderList

    private val _orderDelete = MutableLiveData<Int>().apply { value = 0 }
    val orderDelete = _orderDelete

    private val _ackFailure = MutableLiveData<Int>().apply { value = 0 }
    val ackFailure = _ackFailure

    private val _ackSuccessPosition = MutableLiveData<Int>().apply { value = -1 }
    val ackSuccessPosition = _ackSuccessPosition

    private fun setItemSendStatus(serialNumber: String, status: Int) {
        _orderList.value?.forEachIndexed { index, order ->
            run {
                if (order.serialNumber == serialNumber) {
                    order.sendStatus = status
                    _ackSuccessPosition.postValue(index)
                }
            }
        }
    }

    fun tryAcknowledge(serialNumber: String) {
        val js = JSONObject().apply { put("serial_number", serialNumber) }
        OKHttpUtils.asyncHttpPostJson("/order-server/api/v1/order/pri/acknowledge", js, object : AbstractOkHttpCallback() {
            override fun doSuccess() {
                //签收成功，修改本地数据状态
                setItemSendStatus(serialNumber, ACKNOWLEDGED)
            }

            override fun doFailure() {
                _ackFailure.postValue(_ackFailure.value?.plus(1))
            }
        })
    }

    /**
     * 删除订单
     */
    fun deleteOrder(serial: String, index: Int) {
        val username = getApplication<Application>().getSharedPreferences("user", Context.MODE_PRIVATE).getString("username", "")
        OKHttpUtils.asyncHttpGet("/order-server/api/v1/order/pri/deleteOrder/$serial/$username", object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("error", e.toString())
            }
            override fun onResponse(call: Call, response: Response) {
                val jsonObject = JSONObject(response.body?.string())
                //保证数据的一致性
                if (jsonObject.getInt("code") == 1) {
                    //删除本地
                    _orderList.value?.removeAt(index)
                    //因为_orderList.value?.removeAt(index)的操作不能够被view层所观察到，因此我们需要定义一个操作成功的变量来提示view层进行recyclerView的刷新
                    _orderDelete.postValue(_orderDelete.value?.plus(1))
                }
            }
        })
    }

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