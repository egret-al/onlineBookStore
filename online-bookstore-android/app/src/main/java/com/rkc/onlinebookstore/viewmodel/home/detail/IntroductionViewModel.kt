package com.rkc.onlinebookstore.viewmodel.home.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rkc.onlinebookstore.model.book.Book
import com.rkc.onlinebookstore.util.GsonUtils
import com.rkc.onlinebookstore.util.OKHttpUtils
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException

/**
 * @author rkc
 * @date 2020/12/10 11:01
 * @version 1.0
 */
class IntroductionViewModel(application: Application) : AndroidViewModel(application) {
    private var _completeBook = MutableLiveData<Book>().apply { value = Book() }
    val completeBook: LiveData<Book> = _completeBook

    fun checkBookResource(book: Book) {
        if (book.bookResources != null) return
        //发起请求填充资源
        OKHttpUtils.asyncHttpGet("/book-server/api/v1/book/pub/selectBookAndResourceByBookId/${book.id}", object :
            Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("error", e.toString())
            }

            override fun onResponse(call: Call, response: Response) {
                val jsonObject = JSONObject(response.body?.string())
                if (jsonObject.getInt("code") == 1) {
                    _completeBook.postValue(GsonUtils.getGson().fromJson(jsonObject.getJSONObject("data").toString(), Book::class.java))
                }
            }
        })
    }
}