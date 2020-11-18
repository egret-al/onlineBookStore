package com.rkc.onlinebookstore.viewmodel.home.order

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.rkc.onlinebookstore.model.book.Book
import com.rkc.onlinebookstore.model.order.Order
import com.rkc.onlinebookstore.util.GsonUtils
import com.rkc.onlinebookstore.util.OKHttpUtils
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException

/**
 * @author rkc
 * @date 2020/11/16 8:44
 * @version 1.0
 */
class OrderDetailViewModel(application: Application) : AndroidViewModel(application) {
    private var _orderLiveData = MutableLiveData<Order>()
    var orderLiveData = _orderLiveData

    private var _bookLiveData = MutableLiveData<Book>()
    var bookLiveData = _bookLiveData

    fun setOrderLiveData(order: Order) {
        _orderLiveData.value = order
    }

    fun fetchBook() {
        //非法值容错
        if (_orderLiveData.value == null || _orderLiveData.value!!.serialNumber == "" || _orderLiveData.value!!.bookId == -1) return
        OKHttpUtils.asyncHttpGet("/book-server/api/v1/book/pub/selectBookContainAllInfoById/${_orderLiveData.value!!.bookId}",
            object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.e("error", e.toString())
                }
                override fun onResponse(call: Call, response: Response) {
                    val jsonObject = JSONObject(response.body?.string())
                    if (jsonObject.getInt("code") == 1) {
                        _bookLiveData.postValue(GsonUtils.getGson().fromJson(jsonObject.getJSONObject("data").toString(), Book::class.java))
                    } else {
                        //TODO
                    }
                }
            })
    }
}