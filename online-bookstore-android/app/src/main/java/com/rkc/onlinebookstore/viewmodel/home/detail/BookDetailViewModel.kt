package com.rkc.onlinebookstore.viewmodel.home.detail

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rkc.onlinebookstore.model.book.Book
import com.rkc.onlinebookstore.model.book.BookStorage
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
 * @date 2020/11/12 10:32
 * @version 1.0
 */
const val REQUEST_ERROR = -1
const val REQUEST_SUCCESS = 1
const val REQUESTING = 0
const val ORDER_CREATED_KEY = "order_created"

class BookDetailViewModel(application: Application) : AndroidViewModel(application) {
    private var _book = MutableLiveData<Book>().apply { value = Book() }
    val book: LiveData<Book> = _book

    //购买的数量
    private val _sellingCountLiveData = MutableLiveData<Int>().apply { value = 1 }
    val sellingCountLiveData: LiveData<Int> = _sellingCountLiveData

    //是否使用积分
    private val _useScore = MutableLiveData<Int>().apply { value = 0 }
    val useScore: LiveData<Int> = _useScore

    //网络请求
    private val _purchaseNetRequestSuccessful = MutableLiveData<Int>().apply { value =  REQUESTING}
    val purchaseNetRequestSuccessful: LiveData<Int> = _purchaseNetRequestSuccessful

    //回调订单
    private val _orderCreated = MutableLiveData<Order>().apply { value = Order() }
    val orderCreated: LiveData<Order> = _orderCreated

    fun setBook(book: Book?) {
        //因为book中的bookStorage是该页面必须的属性，因此如果没有该属性。需要访问后端查询数据并填充
        if (book?.bookStorage != null) {
            _book.postValue(book)
            return
        }
        fillInBook(book)
    }

    /**
     * 查询bookStorage和bookResource填充到book
     */
    private fun fillInBook(book: Book?) {
        if (book == null) return
        OKHttpUtils.asyncHttpGet("/book-server/api/v1/book/pub/selectBookContainAllInfoById/${book.id}", object : Callback {
            override fun onFailure(call: Call, e: IOException) { }

            override fun onResponse(call: Call, response: Response) {
                val jsonObject = JSONObject(response.body?.string())
                if (jsonObject.getInt("code") == 1) {
                   _book.postValue(GsonUtils.getGson().fromJson(jsonObject.getJSONObject("data").toString(), Book::class.java))
                }
            }
        })
    }

    fun modifyUseScoreStatus(status: Int) {
        _useScore.postValue(status)
    }

    fun addCount(limit: Int) {
        //边界值判断
        if (_sellingCountLiveData.value!! >= limit) return
        _sellingCountLiveData.postValue(_sellingCountLiveData.value?.plus(1))
    }

    fun subCount() {
        //非法值判断
        if (_sellingCountLiveData.value!! <= 1) return
        _sellingCountLiveData.postValue(_sellingCountLiveData.value?.minus(1))
    }

    /**
     * 立即购买
     */
    fun immediatelyPurchase(book: Book?) {
        val username = getApplication<Application>().getSharedPreferences("user", Context.MODE_PRIVATE).getString("username", "")
        if ("" == username || book == null) return
        val jsonObject = JSONObject().apply {
            put("book_id", book.id)
            put("username_id", username)
            put("order_content", "下单了${_sellingCountLiveData.value}本《${book.bookName}》")
            put("product_count", _sellingCountLiveData.value)
            put("use_score", _useScore.value)
            put("book_name", book.bookName)
        }
        OKHttpUtils.asyncHttpPostJson("/user-server/api/v1/account/pri/createOrder", jsonObject, object :
            Callback{
            override fun onFailure(call: Call, e: IOException) {
                _purchaseNetRequestSuccessful.postValue(REQUEST_ERROR)
            }

            override fun onResponse(call: Call, response: Response) {
                val jsonObject = JSONObject(response.body?.string())
                if (jsonObject.getInt("code") == 1) {
                    _purchaseNetRequestSuccessful.postValue(REQUEST_SUCCESS)
                    //添加回调的订单，用于给
                    _orderCreated.postValue(GsonUtils.getGson().fromJson(jsonObject.getJSONObject("data").toString(), Order::class.java))
                } else {
                    _purchaseNetRequestSuccessful.postValue(REQUEST_ERROR)
                }
            }
        })
    }
}