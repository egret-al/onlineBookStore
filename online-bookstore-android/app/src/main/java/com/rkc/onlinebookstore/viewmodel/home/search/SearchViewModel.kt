package com.rkc.onlinebookstore.viewmodel.home.search

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.rkc.onlinebookstore.model.book.Book
import com.rkc.onlinebookstore.util.GsonUtils
import com.rkc.onlinebookstore.util.KotlinType.Companion.getType
import com.rkc.onlinebookstore.util.OKHttpUtils
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException

/**
 * @author rkc
 * @date 2020/12/11 16:18
 * @version 1.0
 */
class SearchViewModel(application: Application) : AndroidViewModel(application) {
    private val _bookListLiveData = MutableLiveData<List<Book>>()
    val bookListLiveData = _bookListLiveData

    fun clearAll() {
        _bookListLiveData.value = arrayListOf()
    }

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
                        Log.e("tag", jsonObject.getString("message"))
                    }
                }
            })
    }
}