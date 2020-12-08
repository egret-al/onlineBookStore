package com.rkc.onlinebookstore.viewmodel.home.mine.info

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.rkc.onlinebookstore.model.user.User
import com.rkc.onlinebookstore.util.OKHttpUtils
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException

/**
 * @author rkc
 * @date 2020/11/21 22:09
 * @version 1.0
 */
class ModifyPhoneViewModel(application: Application) : AndroidViewModel(application) {
    private val _phoneSuccess = MutableLiveData<Int>().apply { value = 0 }
    private val _phoneFailure = MutableLiveData<Int>().apply { value = 0 }
    val phoneFailure = _phoneFailure
    val phoneSuccess = _phoneSuccess
    val phoneResultMessage = MutableLiveData<String>()

    private val _sendSuccess = MutableLiveData<Int>().apply { value = 0 }
    private val _sendFailure = MutableLiveData<Int>().apply { value = 0 }
    val sendSuccess = _sendSuccess
    val sendFailure = _sendFailure
    val sendResultMessage = MutableLiveData<String>()

    fun sendCode(user: User) {
        val userJo = JSONObject().apply {
            put("account_username", user.accountUsername)
            put("phone", user.phone)
        }
        OKHttpUtils.asyncHttpPostJson("/user-server/api/v1/user/pri/modifyPhoneSendCode", userJo, object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("error", e.toString())
            }

            override fun onResponse(call: Call, response: Response) {
                val res = JSONObject(response.body?.string())
                if (res.getInt("code") == 1) {
                    _sendSuccess.postValue(_sendSuccess.value?.plus(1))
                } else {
                    _sendFailure.postValue(_sendFailure.value?.plus(1))
                }
                sendResultMessage.postValue(res.getJSONObject("data").getString("message"))
            }
        })
    }

    fun savePhone(user: User, code: String) {
        val userJo = JSONObject().apply {
            put("account_username", user.accountUsername)
            put("phone", user.phone)
        }
        val paramJo = JSONObject().apply {
            put("user", userJo)
            put("code", code)
        }
        OKHttpUtils.asyncHttpPostJson("/user-server/api/v1/user/pri/modifyPhone", paramJo, object :
            Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("error", e.toString())
            }

            override fun onResponse(call: Call, response: Response) {
                val res = JSONObject(response.body?.string())
                Log.d("tag", res.toString())
                if (res.getInt("code") == 1) {
                    _phoneSuccess.postValue(_phoneSuccess.value?.plus(1))
                } else {
                    _phoneFailure.postValue(_phoneFailure.value?.plus(1))
                }
                phoneResultMessage.postValue(res.getString("message"))
            }
        })
    }
}