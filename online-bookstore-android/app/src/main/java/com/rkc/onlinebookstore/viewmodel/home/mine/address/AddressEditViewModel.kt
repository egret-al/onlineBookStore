package com.rkc.onlinebookstore.viewmodel.home.mine.address

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rkc.onlinebookstore.model.user.Address
import com.rkc.onlinebookstore.util.OKHttpUtils
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException

/**
 * @author rkc
 * @date 2020/11/19 21:23
 * @version 1.0
 */
const val ERROR = -1
const val SUCCESS = 1

class AddressEditViewModel(application: Application) : AndroidViewModel(application) {
    private val _saveStatus = MutableLiveData<Int>()
    val saveStatus: LiveData<Int> = _saveStatus

    private val _addStatus = MutableLiveData<Int>()
    val addStatus: LiveData<Int> = _addStatus

    fun save(address: Address) {
        val jsonObject = JSONObject().apply {
            put("phone", address.phone)
            put("receiver_name", address.receiverName)
            put("address", address.address)
            put("id", address.id)
        }
        OKHttpUtils.asyncHttpPostJson("/user-server/api/v1/address/pri/updateAddress", jsonObject, object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("error", e.toString())
            }

            override fun onResponse(call: Call, response: Response) {
                val res = JSONObject(response.body?.string())
                if (res.getInt("code") == 1) {
                    _saveStatus.postValue(SUCCESS)
                } else {
                    _saveStatus.postValue(ERROR)
                }
            }
        })
    }

    fun add(address: Address) {
        val username = getApplication<Application>().getSharedPreferences("user", Context.MODE_PRIVATE).getString("username", "")
        if (username == "") {
            _addStatus.value = ERROR
        } else {
            val jsonObject = JSONObject().apply {
                put("phone", address.phone)
                put("receiver_name", address.receiverName)
                put("address", address.address)
                put("account_username", username)
            }
            OKHttpUtils.asyncHttpPostJson("/user-server/api/v1/address/pri/addAddress", jsonObject, object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        Log.e("error", e.toString())
                    }

                    override fun onResponse(call: Call, response: Response) {
                        val res = JSONObject(response.body?.string())
                        if (res.getInt("code") == 1) {
                            _addStatus.postValue(SUCCESS)
                        } else {
                            _addStatus.postValue(ERROR)
                        }
                    }
                })
        }
    }
}