package com.rkc.onlinebookstore.viewmodel.login

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rkc.onlinebookstore.model.user.Account
import com.rkc.onlinebookstore.model.user.User
import com.rkc.onlinebookstore.util.GsonUtils
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
const val REQUESTING = 0
const val LOGIN_SUCCESS = 1
const val LOGIN_FAILURE = -1
const val NET_ERROR = -2

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    private var _loginStatus = MutableLiveData<Int>().apply { value = 2 }
    val loginStatus: LiveData<Int> = _loginStatus

    fun login(username: String, password: String) {
        val jsonObject = JSONObject().apply {
            put("username", username)
            put("password", password)
        }
        OKHttpUtils.asyncHttpPostJson(
            "/user-server/api/v1/account/pub/login",
            jsonObject,
            object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    _loginStatus.postValue(NET_ERROR)
                }

                override fun onResponse(call: Call, response: Response) {
                    val jsonObject = JSONObject(response.body?.string())
                    if (jsonObject.getInt("code") == 1) {
                        val account = GsonUtils.getGson().fromJson(jsonObject.getJSONObject("data").getJSONObject("account").toString(), Account::class.java)
                        val user = GsonUtils.getGson().fromJson(jsonObject.getJSONObject("data").getJSONObject("account").getJSONObject("user").toString(), User::class.java)
                        //TODO 将用户信息存入sqlite

                        val edit = getApplication<Application>().getSharedPreferences("user", Context.MODE_PRIVATE).edit()
                        edit.putString("username", account.username)
                        edit.putString("password", account.password)
                        edit.putString("nickname", user.nickname)
                        edit.apply()
                        //setValue(T) 必须在主线程中调用 , 而 postValue(T) 既可以在主线程中调用, 也可以在子线程中调用
                        _loginStatus.postValue(LOGIN_SUCCESS)
                    } else {
                        _loginStatus.postValue(LOGIN_FAILURE)
                    }
                }
            })
    }
}