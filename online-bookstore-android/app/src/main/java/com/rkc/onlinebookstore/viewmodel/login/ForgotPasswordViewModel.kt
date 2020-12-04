package com.rkc.onlinebookstore.viewmodel.login

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.rkc.onlinebookstore.model.user.Account
import com.rkc.onlinebookstore.util.AbstractOkHttpCallback
import com.rkc.onlinebookstore.util.GsonUtils
import com.rkc.onlinebookstore.util.OKHttpUtils
import org.apache.commons.lang3.StringEscapeUtils
import org.json.JSONObject

/**
 * @author rkc
 * @date 2020/12/4 9:25
 * @version 1.0
 */
class ForgotPasswordViewModel(application: Application) : AndroidViewModel(application) {
    private val _sendCodeSuccess = MutableLiveData<Int>().apply { value = 0 }
    val sendCodeSuccess = _sendCodeSuccess

    private val _sendCodeFailure = MutableLiveData<Int>().apply { value = 0 }
    val sendCodeFailure = _sendCodeFailure

    private val _resetSuccess = MutableLiveData<Int>().apply { value = 0 }
    val resetSuccess = _resetSuccess

    private val _resetFailure = MutableLiveData<Int>().apply { value = 0 }
    val resetFailure = _resetFailure

    fun resetPassword(account: Account, code: String) {
        val accountJson = JSONObject().apply {
            put("username", account.username)
            put("password", account.password)
        }
        val jsonObject = JSONObject().apply {
            put("account", accountJson)
            put("code", code)
        }
        OKHttpUtils.asyncHttpPostJson("/user-server/api/v1/account/pub/resetPassword", jsonObject, object : AbstractOkHttpCallback() {
            override fun doFailure() { _resetFailure.postValue(_resetFailure.value?.plus(1)) }

            override fun doSuccess() { _resetSuccess.postValue(_resetSuccess.value?.plus(1)) }
        })
    }

    fun sendCode(account: Account, phone: String) {
        val accountJson = JSONObject().apply { put("username", account.username) }
        val jsonObject = JSONObject().apply {
            put("account", accountJson)
            put("phone", phone)
        }
        OKHttpUtils.asyncHttpPostJson("/user-server/api/v1/account/pub/forgotPassword", jsonObject, object : AbstractOkHttpCallback() {
            override fun doFailure() { _sendCodeFailure.postValue(_sendCodeFailure.value?.plus(1)) }

            override fun doSuccess() { _sendCodeSuccess.postValue(_sendCodeSuccess.value?.plus(1)) }
        })
    }
}
