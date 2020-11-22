package com.rkc.onlinebookstore.util

import android.util.Log
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException

/**
 * OKHttp回调抽象类，与服务器端约定好，返回状态码为1则为成功，0为失败
 * @author rkc
 * @date 2020/11/22 16:19
 * @version 1.0 只能实现没有data返回数据的回调
 */
abstract class AbstractOkHttpCallback : Callback {
    private var message = ""
    override fun onFailure(call: Call, e: IOException) {
        Log.d("error", e.toString())
    }

    override fun onResponse(call: Call, response: Response) {
        val res = JSONObject(response.body?.string())
        if (res.getInt("code") == 1) {
            doSuccess()
        } else if (res.getInt("code") == 0) {
            doFailure()
        }
        message = res.getString("message")
    }

    /**
     * 请求成功的回调
     */
    open fun doSuccess() { }

    /**
     * 请求失败的回调
     */
    open fun doFailure() { }

    fun getMessage(): String {
        return message
    }
}