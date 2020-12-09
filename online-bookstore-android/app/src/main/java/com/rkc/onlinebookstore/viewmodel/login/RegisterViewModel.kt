package com.rkc.onlinebookstore.viewmodel.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.rkc.onlinebookstore.model.user.Account
import com.rkc.onlinebookstore.model.user.User
import com.rkc.onlinebookstore.util.AbstractOkHttpCallback
import com.rkc.onlinebookstore.util.OKHttpUtils
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException

/**
 * @author rkc
 * @date 2020/11/10 16:40
 * @version 1.0
 */
class RegisterViewModel(application: Application) : AndroidViewModel(application) {
    private val _registerSuccess = MutableLiveData<Int>().apply { value = 0 }
    val registerSuccess = _registerSuccess
    private val _registerFailure = MutableLiveData<Int>().apply { value = 0 }
    val registerFailure = _registerFailure

    val resultMessage = MutableLiveData<String>()

    fun register(account: Account, user: User, birthdayStr: String) {
        val paramJo = JSONObject().apply {
            put("account", JSONObject().apply {
                put("username", account.username)
                put("password", account.password)
            })
            put("user", JSONObject().apply {
                put("nickname", user.nickname)
                put("birthday", birthdayStr)
                put("sex", user.sex)
                put("phone", user.phone)
            })
        }
        OKHttpUtils.asyncHttpPostJson("/user-server/api/v1/account/pub/registry", paramJo, object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                resultMessage.postValue("请求超时！")
            }

            override fun onResponse(call: Call, response: Response) {
                val res = JSONObject(response.body?.string())
                if (res.getInt("code") == 1) {
                    resultMessage.postValue(res.getString("message"))
                    _registerSuccess.postValue(_registerSuccess.value?.plus(1))
                } else {
                    resultMessage.postValue(res.getString("message"))
                    _registerFailure.postValue(_registerFailure.value?.plus(1))
                }
            }
        })
    }
}