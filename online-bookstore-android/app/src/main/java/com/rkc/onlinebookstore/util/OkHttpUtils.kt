package com.rkc.onlinebookstore.util

import android.util.Log
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import kotlin.math.sign

/**
 * @author rkc
 * @date 2020/11/9 22:30
 * @version 1.0
 */
class OKHttpUtils {

    companion object {
        private const val serverAddress = "http://100.66.30.183:9527"
//        private const val serverAddress = "http://10.127.105.16:9527"
//        private const val serverAddress = "http://10.127.106.16:9527"
        private val okHttpClientBuilder = OkHttpClient().newBuilder().addInterceptor(SignInterceptor())
        private val jsonType = "application/json;charset=utf-8".toMediaTypeOrNull()

        /**
         * 异步get请求
         */
        fun asyncHttpGet(url: String, callback: Callback) {
            val request = Request.Builder().delete().get().url(serverAddress + url).build()
            okHttpClientBuilder.build().newCall(request).also { it.enqueue(callback) }
        }

        /**
         * post表单请求
         */
        fun asyncHttpPostForm(url: String, formBody: FormBody, callback: Callback) {
            okHttpClientBuilder.build().newCall(Request.Builder().url(serverAddress + url).post(formBody).build()).also { it.enqueue(callback) }
        }

        /**
         * post请求，json数据格式
         */
        fun asyncHttpPostJson(url: String, jsonObject: JSONObject, callback: Callback) {
            Log.d("asyncHttpPostJson", jsonObject.toString())
            val requestBody = jsonObject.toString().toRequestBody(jsonType)
            okHttpClientBuilder.build().newCall(Request.Builder().url(serverAddress + url).post(requestBody).build()).also { it.enqueue(callback) }
        }

        /**
         * 异步delete请求
         */
        fun asyncHttpDelete(url: String, callback: Callback) {

        }
    }
}

class SignInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        //获取请求实例
        return chain.proceed(chain.request().newBuilder().addHeader("clientType", "android").build())
    }
}