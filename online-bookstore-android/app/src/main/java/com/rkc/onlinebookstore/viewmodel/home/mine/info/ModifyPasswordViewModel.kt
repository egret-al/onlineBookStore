package com.rkc.onlinebookstore.viewmodel.home.mine.info

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.rkc.onlinebookstore.model.user.Account
import com.rkc.onlinebookstore.util.GsonUtils
import com.rkc.onlinebookstore.util.OKHttpUtils
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException

/**
 * @author rkc
 * @date 2020/11/22 10:35
 * @version 1.0
 */
const val PASSWORD_SUCCESS = 1
const val PASSWORD_FAILURE = -1
const val REQUEST_ERROR = -2

class ModifyPasswordViewModel(application: Application) : AndroidViewModel(application) {
    private val _passwordStatus = MutableLiveData<Int>().apply { value = 0 }
    val passwordStatus = _passwordStatus

    fun modifyPassword(username: String, oldPassword: String, newPassword: String) {
        val jo = JSONObject().apply {
            put("username", username)
            put("oldPassword", oldPassword)
            put("newPassword", newPassword)
        }

        OKHttpUtils.asyncHttpPostJson("/user-server/api/v1/account/pri/modifyPassword", jo, object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("error", e.toString())
                _passwordStatus.postValue(REQUEST_ERROR)
            }

            override fun onResponse(call: Call, response: Response) {
                when (JSONObject(response.body?.string()).getInt("code")) {
                    1 -> _passwordStatus.postValue(PASSWORD_SUCCESS)
                    0 -> _passwordStatus.postValue(PASSWORD_FAILURE)
                }
            }
        })
    }

    fun resetPasswordStatus() {
        _passwordStatus.value = 0
    }
}