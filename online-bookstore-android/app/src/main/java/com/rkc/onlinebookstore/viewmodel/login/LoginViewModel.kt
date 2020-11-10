package com.rkc.onlinebookstore.viewmodel.login

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import com.rkc.onlinebookstore.model.Account
import com.rkc.onlinebookstore.util.OKHttpUtils
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException

/**
 * @author rkc
 * @date 2020/11/9 22:41
 * @version 1.0
 */
class LoginViewModel(application: Application) : AndroidViewModel(application) {
    private lateinit var context: Context

    companion object {
        private const val LOGIN_SUCCESS = 1
        private const val LOGIN_FAILURE = 0
        private const val NET_ERROR = -1
    }

    @SuppressLint("HandlerLeak") private val handler: Handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                LOGIN_SUCCESS -> {
                    val account = msg.obj as Account
                    Toast.makeText(context, "登录成功！", Toast.LENGTH_SHORT).show()
                    val edit = context.getSharedPreferences("user", Context.MODE_PRIVATE).edit()
                    edit.putString("username", account.username)
                    edit.putString("password", account.password)
                    edit.apply()
//                    context.startActivity()
                }
                LOGIN_FAILURE -> Toast.makeText(context, "登录失败！", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun login(context: Context, username: String, password: String) {
        this.context = context
        val jsonObject = JSONObject().apply {
            put("username", username)
            put("password", password)
        }
        OKHttpUtils.asyncHttpPostJson("/user-server/api/v1/account/pub/login", jsonObject, object :Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("error", e.toString())
                val message = Message.obtain().apply {
                    what = NET_ERROR
                    obj = e.toString()
                }
                handler.sendMessage(message)
            }
            override fun onResponse(call: Call, response: Response) {
                val message = Message.obtain().apply {
                    what = LOGIN_SUCCESS
                    obj = Account(username=username, password=password)
                }
                Log.d("login", response.body?.string())
                //判断返回code是否成功
//                handler.sendMessage(message)
            }
        })
    }

    fun destroyHandler() {
        handler.removeCallbacksAndMessages(null)
    }
}