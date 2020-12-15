package com.rkc.onlinebookstore.datasource.home.home

import android.content.Context
import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.rkc.onlinebookstore.model.book.Book
import com.rkc.onlinebookstore.util.GsonUtils
import com.rkc.onlinebookstore.util.KotlinType
import com.rkc.onlinebookstore.util.OKHttpUtils
import com.rkc.onlinebookstore.viewmodel.home.home.TAG
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException

/**
 * @author rkc
 * @date 2020/12/11 10:01
 * @version 1.0
 */
class BookTypeDataSource(private val typeId: Int) : PageKeyedDataSource<Int, Book>() {
    private val firstPage = 1
    private val pageSize = 10

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Book>) {
        Log.d("page type", "loadAfter：${params.key}")
        val url = "/book-server/api/v1/book/pub/selectBookAndStorageByTypePage/$typeId/${params.key}/${pageSize}"
        OKHttpUtils.asyncHttpGet(url, object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("error", e.toString())
            }

            override fun onResponse(call: Call, response: Response) {
                val jsonObject = JSONObject(response.body?.string())
                if (jsonObject.getInt("code") == 1) {
                    val str = jsonObject.getJSONArray("data").toString()
                    val res = GsonUtils.getGson().fromJson<List<Book>>(str, KotlinType.getType(List::class.java, Book::class.java))
                    //得到数据后，放入到callback中
                    callback.onResult(res, params.key + 1)
                } else {
                    Log.e(TAG, jsonObject.getString("message"))
                }
            }
        })
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Book>) { }

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Book>) {
        val url = "/book-server/api/v1/book/pub/selectBookAndStorageByTypePage/$typeId/$firstPage/${pageSize}"
        OKHttpUtils.asyncHttpGet(url, object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("error", e.toString())
            }

            override fun onResponse(call: Call, response: Response) {
                val jsonObject = JSONObject(response.body?.string())
                if (jsonObject.getInt("code") == 1) {
                    val str = jsonObject.getJSONArray("data").toString()
                    val res = GsonUtils.getGson().fromJson<List<Book>>(str, KotlinType.getType(List::class.java, Book::class.java))
                    Log.d("page type", res.size.toString())
                    //得到数据后，放入到callback中
                    callback.onResult(res, null, 2)
                } else {
                    Log.e(TAG, jsonObject.getString("message"))
                }
            }
        })
    }
}