package com.rkc.onlinebookstore.viewmodel.home.home

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rkc.onlinebookstore.model.book.Book
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
 * @date 2020/11/28 9:06
 * @version 1.0
 */
class BookTypeHomeViewModel(application: Application) : AndroidViewModel(application) {
    private val _bookListLiveData = MutableLiveData<List<Book>>().apply { value = mutableListOf() }
    val bookListLiveData: LiveData<List<Book>> = _bookListLiveData

    private val _noData = MutableLiveData<Int>().apply { value = 0 }
    val noData = _noData

    fun fetchBooksByType(typeId: Int) {
        OKHttpUtils.asyncHttpGet("/book-server/api/v1/book/pub/selectAllInfoByType/${typeId}", object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("error", e.toString())
            }

            override fun onResponse(call: Call, response: Response) {
                val jsonObject = JSONObject(response.body?.string())
                if (jsonObject.getInt("code") == 1) {
                    val str = jsonObject.getJSONArray("data").toString()
                    val res = GsonUtils.getGson().fromJson<List<Book>>(str, KotlinType.getType(List::class.java, Book::class.java))
                    _bookListLiveData.postValue(res)
                } else {
                    Log.e(TAG, jsonObject.getString("message"))
                    _noData.postValue(_noData.value?.plus(1))
                }
            }
        })
    }
}