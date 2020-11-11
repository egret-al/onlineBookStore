package com.rkc.onlinebookstore.viewmodel.home.home

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rkc.onlinebookstore.model.book.Book
import com.rkc.onlinebookstore.model.book.BookBanner
import com.rkc.onlinebookstore.util.GsonUtils
import com.rkc.onlinebookstore.util.OKHttpUtils
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

const val TAG = "TAG"

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private val _bookListLiveData = MutableLiveData<List<Book>>().apply { value = mutableListOf() }
    val bookListLiveData: LiveData<List<Book>> = _bookListLiveData

    private val _bookBannerLiveData = MutableLiveData<List<BookBanner>>().apply { value = mutableListOf() }
    val bookBannerLiveData: LiveData<List<BookBanner>> = _bookBannerLiveData

    /**
     * 获取轮播图
     */
    fun fetchBookBanner() {
        val url = "/book-server/api/v1/book/pub/selectAllBanner"
        OKHttpUtils.asyncHttpGet(url, object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d(TAG, "onFailure: $e")
            }

            override fun onResponse(call: Call, response: Response) {
                val jsonObject = JSONObject(response.body?.string())
                if (jsonObject.getInt("code") == 1) {
                    _bookBannerLiveData.postValue(GsonUtils.getGson().fromJson(jsonObject.getJSONArray("data").toString(), getType(List::class.java, BookBanner::class.java)))
                } else {
                    //TODO 请求失败
                }
            }
        })
    }

    /**
     * 获取图书列表
     */
    fun fetchBookList() {
        val url = "/book-server/api/v1/book/pub/selectBookAndResource"
        OKHttpUtils.asyncHttpGet(url, object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d(TAG, "onFailure: $e")
            }

            override fun onResponse(call: Call, response: Response) {
                val jsonObject = JSONObject(response.body?.string())
                if (jsonObject.getInt("code") == 1) {
                    val str = jsonObject.getJSONArray("data").toString()
                    val res = GsonUtils.getGson().fromJson<List<Book>>(str, getType(List::class.java, Book::class.java))
                    _bookListLiveData.postValue(res)
                } else {
                    //TODO 请求失败
                    Log.e(TAG, "onResponse: 网络错误", )
                }
            }
        })
    }

    fun getType(raw: Class<*>, vararg args: Type) = object : ParameterizedType {
        override fun getRawType(): Type = raw
        override fun getActualTypeArguments(): Array<out Type> = args
        override fun getOwnerType(): Type? = null
    }
}