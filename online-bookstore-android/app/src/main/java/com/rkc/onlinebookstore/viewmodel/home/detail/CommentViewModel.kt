package com.rkc.onlinebookstore.viewmodel.home.detail

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.rkc.onlinebookstore.model.book.Book
import com.rkc.onlinebookstore.model.user.Comment
import com.rkc.onlinebookstore.util.GsonUtils
import com.rkc.onlinebookstore.util.KotlinType
import com.rkc.onlinebookstore.util.OKHttpUtils
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.ArrayList

/**
 * @author rkc
 * @date 2020/11/17 16:55
 * @version 1.0
 */
const val SEND_SUCCESS = 1;
const val SEND_FAILURE = -1

class CommentViewModel(application: Application) : AndroidViewModel(application) {
    private val _commentsLiveData = MutableLiveData<ArrayList<Comment>>()
    val commentLiveData = _commentsLiveData

    private val _sendStatus = MutableLiveData<Int>().apply { value = 0 }
    val sendStatus = _sendStatus

    var book = Book()

    @SuppressLint("SimpleDateFormat")
    fun sendComment(content: String) {
        val accountUsername = getApplication<Application>().getSharedPreferences("user", Context.MODE_PRIVATE).getString("username", "")
        if (accountUsername == "") return
        val jsonObject = JSONObject().apply {
            put("account_username", accountUsername)
            put("book_id", book.id)
            put("content", content)
        }
        OKHttpUtils.asyncHttpPostJson("/user-server/api/v1/comment/insertComment", jsonObject, object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("error", e.toString())
            }

            override fun onResponse(call: Call, response: Response) {
                val rs = JSONObject(response.body?.string())
                if (rs.getInt("code") == 1) {
                    //通知刷新视图
                    _sendStatus.postValue(_sendStatus.value?.plus(1))
                }
            }
        })
    }

    fun fetchComments() {
        val url = "/user-server/api/v1/comment/selectCommentsByBookId/${book.id}"
        OKHttpUtils.asyncHttpGet(url, object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("error", e.toString())
            }
            override fun onResponse(call: Call, response: Response) {
                val jsonObject = JSONObject(response.body?.string())
                if (jsonObject.getInt("code") == 1) {
                    Log.d("tag", jsonObject.getJSONArray("data").toString())
                    _commentsLiveData.postValue(GsonUtils.getGson().fromJson(jsonObject.getJSONArray("data").toString(), KotlinType.getType(List::class.java, Comment::class.java)))
                }
            }
        })
    }
}