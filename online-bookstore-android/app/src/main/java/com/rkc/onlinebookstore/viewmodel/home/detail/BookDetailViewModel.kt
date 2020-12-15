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
import com.rkc.onlinebookstore.model.user.Address
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
 * @date 2020/11/12 10:32
 * @version 1.0
 */
const val REQUEST_ERROR = -1
const val REQUEST_SUCCESS = 1
const val REQUESTING = 0
const val ORDER_CREATED_KEY = "order_created"

class BookDetailViewModel(application: Application) : AndroidViewModel(application) {
    val selectedAddress = MutableLiveData<Address>()
    val bindAddresses = MutableLiveData<ArrayList<Address>>()

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

    val hasDefaultAddress = MutableLiveData<Boolean>().apply { value = true }

    private val _addSuccess = MutableLiveData<Int>().apply { value = 0 }
    private val _addFailure = MutableLiveData<Int>().apply { value = 0 }
    val addSuccess = _addSuccess
    val addFailure = _addFailure

    fun resetNetRequestStatus() {
        _purchaseNetRequestSuccessful.postValue(-2)
    }

    fun addToShoppingTrolley() {
        val username = getApplication<Application>().getSharedPreferences("user", Context.MODE_PRIVATE).getString("username", "")
        if ("" == username) return
        val jo = JSONObject().apply {
            put("book_id", book.value!!.id)
            put("account_username", username)
            put("collect_count", _sellingCountLiveData.value)
        }
        OKHttpUtils.asyncHttpPostJson("/user-server/api/v1/shopping/pri/insertShoppingTrolley", jo, object : AbstractOkHttpCallback() {
            override fun doFailure() {
                _addFailure.postValue(_addFailure.value?.plus(1))
            }

            override fun doSuccess() {
                _addSuccess.postValue(_addSuccess.value?.plus(1))
            }
        })
    }

    fun setBook(book: Book?) {
        //因为book中的bookStorage是该页面必须的属性，因此如果没有该属性。需要访问后端查询数据并填充
        if (book?.bookStorage != null) {
            _book.postValue(book)
            return
        }
        getBookStorage(book)
    }

    /**
     * 查询bookStorage和bookResource填充到book
     */
    private fun getBookStorage(book: Book?) {
        if (book == null) return
        OKHttpUtils.asyncHttpGet("/book-server/api/v1/book/pub/selectStorageByBookId/${book.id}", object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("error", e.toString())
            }

            override fun onResponse(call: Call, response: Response) {
                val jsonObject = JSONObject(response.body?.string())
                if (jsonObject.getInt("code") == 1) {
                    book.bookStorage = GsonUtils.getGson().fromJson(jsonObject.getJSONObject("data").toString(), BookStorage::class.java)
                   _book.postValue(book)
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

    fun selectAddresses() {
        val username = getApplication<Application>().getSharedPreferences("user", Context.MODE_PRIVATE).getString("username", "")
        OKHttpUtils.asyncHttpPostJson("/user-server/api/v1/address/pri/selectByAccount", JSONObject().apply { put("username", username) },
        object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("error", e.toString())
            }

            override fun onResponse(call: Call, response: Response) {
                val res = JSONObject(response.body?.string())
                if (res.getInt("code") == 1) {
                    bindAddresses.postValue(GsonUtils.getGson().fromJson(res.getJSONArray("data").toString(), KotlinType.getType(List::class.java, Address::class.java)))
                }
            }
        })
    }

    fun getDefaultAddress() {
        val username = getApplication<Application>().getSharedPreferences("user", Context.MODE_PRIVATE).getString("username", "")
        if ("" == username) return
        val jsonObject = JSONObject().apply {
            put("account_username", username)
        }
        //请求服务器获取默认地址
        OKHttpUtils.asyncHttpPostJson("/user-server/api/v1/address/pri/selectDefaultAddress", jsonObject,
            object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.e("error", e.toString())
                }

                override fun onResponse(call: Call, response: Response) {
                    val addressJs = JSONObject(response.body?.string())
                    if (addressJs.getInt("code") == 1) {
                        //得到默认address
                        val address = GsonUtils.getGson().fromJson(addressJs.getJSONObject("data").toString(), Address::class.java)
                        hasDefaultAddress.postValue(true)
                        //选择地址设置为默认地址
                        selectedAddress.postValue(address)
                        Log.d("默认地址", address.toString())
                    } else {
                        hasDefaultAddress.postValue(false)
                    }
                }
            })
    }

    /**
     * 立即购买
     */
    fun immediatelyPurchase(book: Book?) {
        val username = getApplication<Application>().getSharedPreferences("user", Context.MODE_PRIVATE).getString("username", "")
        if ("" == username || book == null) return
        if (selectedAddress.value == null) {
            hasDefaultAddress.value = false
            return
        }
        val jsonObject = JSONObject().apply {
            put("book_id", book.id)
            put("username_id", username)
            put("order_content", "下单了${_sellingCountLiveData.value}本《${book.bookName}》")
            put("product_count", _sellingCountLiveData.value)
            put("use_score", _useScore.value)
            put("book_name", book.bookName)
            put("phone", selectedAddress.value!!.phone)
            put("receiver_name", selectedAddress.value!!.receiverName)
            put("address", selectedAddress.value!!.address)
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