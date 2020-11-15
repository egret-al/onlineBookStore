package com.rkc.onlinebookstore.viewmodel.home.classify

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rkc.onlinebookstore.model.book.BookType
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
 * @date 2020/11/13 21:57
 * @version 1.0
 */
const val REQUEST_SUCCESSFULLY = 1
const val REQUEST_FAILURE = -1

class ClassifyViewModel(application: Application) : AndroidViewModel(application) {
    private var _listBookType = MutableLiveData<List<BookType>>().apply { value = ArrayList() }
    val listBookType: LiveData<List<BookType>> = _listBookType

    private var _requestStatus = MutableLiveData<Int>().apply { value = 0 }
    val requestStatus: LiveData<Int> = _requestStatus

    fun getAllBookType() {
        val url = "/book-server/api/v1/book/pub/selectAllTypeWithBook"
        OKHttpUtils.asyncHttpGet(url, object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                _requestStatus.postValue(REQUEST_FAILURE)
            }

            override fun onResponse(call: Call, response: Response) {
                val jsonObject = JSONObject(response.body?.string())
                if (jsonObject.getInt("code") == 1) {
                    _listBookType.postValue(GsonUtils.getGson().fromJson(jsonObject.getJSONArray("data").toString(), KotlinType.getType(List::class.java, BookType::class.java)))
                    _requestStatus.postValue(REQUEST_SUCCESSFULLY)
                } else {
                    //TODO 请求失败
                    _requestStatus.postValue(REQUEST_FAILURE)
                }
            }
        })
    }
}