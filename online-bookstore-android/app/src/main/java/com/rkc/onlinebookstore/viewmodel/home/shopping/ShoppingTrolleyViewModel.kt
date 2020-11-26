package com.rkc.onlinebookstore.viewmodel.home.shopping

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.rkc.onlinebookstore.model.user.ShoppingTrolley
import com.rkc.onlinebookstore.util.AbstractOkHttpCallback
import com.rkc.onlinebookstore.util.GsonUtils
import com.rkc.onlinebookstore.util.KotlinType
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
 * @date 2020/11/26 9:47
 * @version 1.0
 */
class ShoppingTrolleyViewModel(application: Application) : AndroidViewModel(application) {
    private val _shoppingTrolleysLiveData = MutableLiveData<ArrayList<ShoppingTrolley>>()
    val shoppingTrolleyLiveData = _shoppingTrolleysLiveData

    private val _deleteStatus = MutableLiveData<Int>().apply { value = 0 }
    val deleteStatus = _deleteStatus

    fun fetchShoppingTrolleys() {
        val username = getApplication<Application>().getSharedPreferences(USER, Context.MODE_PRIVATE).getString(USERNAME, "")
        if (username != "") {
            val jo = JSONObject().apply {
                put("username", username)
            }
            OKHttpUtils.asyncHttpPostJson("/user-server/api/v1/shopping/pri/selectCompleteProductByAccount", jo, object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.e("error", e.toString())
                }

                override fun onResponse(call: Call, response: Response) {
                    val res = JSONObject(response.body?.string())
                    if (res.getInt("code") == 1) {
                        _shoppingTrolleysLiveData.postValue(GsonUtils.getGson().fromJson(res.getJSONArray("data").toString(), KotlinType.getType(List::class.java, ShoppingTrolley::class.java)))
                    }
                }
            })
        }
    }

    fun deleteShoppingTrolleyById(id: Int) {
        OKHttpUtils.asyncHttpPostJson("/user-server/api/v1/shopping/pri/deleteTrolleyById", JSONObject().apply { put("id", id) }, object :
            AbstractOkHttpCallback() {
            override fun doSuccess() {
                _deleteStatus.postValue(_deleteStatus.value?.plus(1))
            }
        })
    }
}