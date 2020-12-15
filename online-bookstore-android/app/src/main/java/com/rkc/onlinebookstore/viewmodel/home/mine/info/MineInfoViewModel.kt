package com.rkc.onlinebookstore.viewmodel.home.mine.info

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.rkc.onlinebookstore.model.user.Account
import com.rkc.onlinebookstore.util.GsonUtils
import com.rkc.onlinebookstore.util.OKHttpUtils
import com.rkc.onlinebookstore.viewmodel.login.USER
import com.rkc.onlinebookstore.viewmodel.login.USERNAME
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException

/**
 * @author rkc
 * @date 2020/11/21 16:57
 * @version 1.0
 */
const val MALE = 0
const val FEMALE = 1

class MineInfoViewModel(application: Application) : AndroidViewModel(application) {
    private val _accountLiveData = MutableLiveData<Account>()
    val accountLiveData = _accountLiveData

    fun modifySex(sexCode: Int) {
        val username = getApplication<Application>().getSharedPreferences(USER, Context.MODE_PRIVATE).getString(USERNAME, "")
        if (username == "") return
        val userJo = JSONObject().apply {
            put("account_username", username)
            put("sex", "男")
        }
        if (sexCode == FEMALE) userJo.put("sex", "女")
        OKHttpUtils.asyncHttpPostJson("/user-server/api/v1/user/pri/modifySex", userJo, object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("error", e.toString())
            }

            override fun onResponse(call: Call, response: Response) {
                if (JSONObject(response.body?.string()).getInt("code") == 1) {
                    Log.d("tag", "性别修改成功")
                }
            }
        })
    }

    fun fetchUserAndAccount() {
        val username = getApplication<Application>().getSharedPreferences(USER, Context.MODE_PRIVATE).getString(USERNAME, "")
        if (username == "") return
        OKHttpUtils.asyncHttpPostJson("/user-server/api/v1/account/pub/getAccountWithUser", JSONObject().apply { put("username", username) }, object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("error", e.toString())
            }

            override fun onResponse(call: Call, response: Response) {
                val res = JSONObject(response.body?.string())
                if (res.getInt("code") == 1) {
                    _accountLiveData.postValue(GsonUtils.getGson().fromJson(res.getJSONObject("data").toString(), Account::class.java))
                }
            }
        })
    }
}