package com.rkc.onlinebookstore.viewmodel.home.home

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rkc.onlinebookstore.model.book.Book
import com.rkc.onlinebookstore.model.book.BookBanner
import com.rkc.onlinebookstore.model.book.BookType
import com.rkc.onlinebookstore.util.GsonUtils
import com.rkc.onlinebookstore.util.KotlinType.Companion.getType
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

    private val _bookTypeLiveData = MutableLiveData<List<BookType>>()
    val bookTypeLiveData = _bookTypeLiveData

    fun fetchBookTypes() {
        OKHttpUtils.asyncHttpGet("/book-server/api/v1/book/pub/selectAllType", object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("error", e.toString())
            }

            override fun onResponse(call: Call, response: Response) {
                val js = JSONObject(response.body?.string())
                if (js.getInt("code") == 1) {
                    _bookTypeLiveData.postValue(GsonUtils.getGson().fromJson(js.getJSONArray("data").toString(), getType(List::class.java, BookType::class.java)))
                } else {
                    Log.e("error", js.getString("message"))
                }
            }
        })
    }

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
     * 模糊查询
     */
    fun fuzzyMatch(str: String) {
        OKHttpUtils.asyncHttpPostJson("/book-server/api/v1/book/pub/selectAllInfoLike", JSONObject().apply { put("book_name", str) },
            object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.e("error", e.toString())
                }

                override fun onResponse(call: Call, response: Response) {
                    val jsonObject = JSONObject(response.body?.string())
                    if (jsonObject.getInt("code") == 1) {
                        val res = GsonUtils.getGson().fromJson<List<Book>>(jsonObject.getJSONArray("data").toString(), getType(List::class.java, Book::class.java))
                        _bookListLiveData.postValue(res)
                    } else {
                        Log.e(TAG, jsonObject.getString("message"))
                    }
                }
            })
    }

    /**
     * 获取图书列表
     */
    fun fetchBookList() {
        val url = "/book-server/api/v1/book/pub/selectAllInfo"
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
                    Log.e(TAG, jsonObject.getString("message"))
                }
            }
        })
    }
}