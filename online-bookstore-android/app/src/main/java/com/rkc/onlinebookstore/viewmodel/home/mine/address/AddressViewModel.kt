package com.rkc.onlinebookstore.viewmodel.home.mine.address

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rkc.onlinebookstore.model.user.Address
import com.rkc.onlinebookstore.util.GsonUtils
import com.rkc.onlinebookstore.util.KotlinType
import com.rkc.onlinebookstore.util.OKHttpUtils
import com.rkc.onlinebookstore.viewmodel.login.DEFAULT_ADDRESS
import com.rkc.onlinebookstore.viewmodel.login.USER
import com.rkc.onlinebookstore.viewmodel.login.USERNAME
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException

/**
 * @author rkc
 * @date 2020/11/19 17:19
 * @version 1.0
 */
class AddressViewModel(application: Application) : AndroidViewModel(application) {
    private val _setDefaultStatus = MutableLiveData<Int>().apply { value = 0 }
    val setDefaultStatus = _setDefaultStatus

    private val _addressesLiveData = MutableLiveData<ArrayList<Address>>()
    val addressesLiveData: LiveData<ArrayList<Address>> = _addressesLiveData

    fun setDefaultAddress(addressId: Int) {
        val username = getApplication<Application>().getSharedPreferences(USER, Context.MODE_PRIVATE).getString(USERNAME, "")
        if (username == "") return
        OKHttpUtils.asyncHttpGet("/user-server/api/v1/address/pri/setDefaultAddress/${addressId}/$username", object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("error", e.toString())
            }

            override fun onResponse(call: Call, response: Response) {
                val res = JSONObject(response.body?.string())
                if (res.getInt("code") == 1) {
                    //默认地址设置成功，通知界面进行刷新
                    val edit = getApplication<Application>().getSharedPreferences(USER, Context.MODE_PRIVATE).edit()
                    edit.putInt(DEFAULT_ADDRESS, addressId)
                    edit.apply()
                    _setDefaultStatus.postValue(_setDefaultStatus.value?.plus(1))
                }
            }
        })
    }

    fun fetchAddresses() {
        val username = getApplication<Application>().getSharedPreferences(USER, Context.MODE_PRIVATE).getString(USERNAME, "")
        if (username == "") return
        OKHttpUtils.asyncHttpGet("/user-server/api/v1/address/pri/selectByAccount/$username", object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("error", e.toString())
            }

            override fun onResponse(call: Call, response: Response) {
                val jsonObject = JSONObject(response.body?.string())
                if (jsonObject.getInt("code") == 1) {
                    _addressesLiveData.postValue(GsonUtils.getGson().fromJson(jsonObject.getJSONArray("data").toString(), KotlinType.getType(List::class.java, Address::class.java)))
                }
            }
        })
    }
}