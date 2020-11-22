package com.rkc.onlinebookstore.viewmodel.home.mine.info

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.rkc.onlinebookstore.util.AbstractOkHttpCallback
import com.rkc.onlinebookstore.util.OKHttpUtils
import okhttp3.Call
import okhttp3.Response
import org.json.JSONObject

/**
 * @author rkc
 * @date 2020/11/22 16:21
 * @version 1.0
 */
const val ADD_SUCCESS = 1
const val ADD_FAILURE = -1
const val REQUEST_STATUS = -2

class TopUpViewModel(application: Application) : AndroidViewModel(application) {
    private val _balanceLiveData = MutableLiveData<Int>()
    val balanceLiveData = _balanceLiveData

    private val _addStatus = MutableLiveData<Int>().apply { value = 0 }
    val addStatus = _addStatus

    fun doTopUpResidue(username: String, count: Int) {
        val jo = JSONObject().apply {
            put("username", username)
            put("count", count)
        }
        OKHttpUtils.asyncHttpPostJson("/user-server/api/v1/account/pri/topUpResidue", jo, object : AbstractOkHttpCallback() {
            override fun doSuccess() {
                _addStatus.postValue(ADD_SUCCESS)
                _balanceLiveData.postValue(_balanceLiveData.value?.plus(count))
            }

            override fun doFailure() { _addStatus.postValue(ADD_FAILURE) }
        })
    }

    fun initBalance(balance: Int) {
        _balanceLiveData.value = balance
    }

    fun resetStatus() {
        _balanceLiveData.value = 0
    }
}