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
    private val _phoneStatus = MutableLiveData<Int>().apply { value = 0 }
    val phoneStatus = _phoneStatus

    fun savePhone(user: User) {
        val userJo = JSONObject().apply {
            put("account_username", user.accountUsername)
            put("phone", user.phone)
        }
        OKHttpUtils.asyncHttpPostJson("/user-server/api/v1/user/pri/modifyPhone", userJo, object :
            Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("error", e.toString())
            }

            override fun onResponse(call: Call, response: Response) {
                if (JSONObject(response.body?.string()).getInt("code") == 1) {
                    _phoneStatus.postValue(_phoneStatus.value?.plus(1))
                }
            }
        })
    }
}