package com.rkc.onlinebookstore.model

import com.google.gson.Gson
import org.json.JSONObject

/**
 * @author rkc
 * @date 2020/11/11 8:38
 * @version 1.0
 */
fun main() {
    val jsonString = "{\"code\":1,\"data\":{\"account\":{\"username\":\"1234567890\",\"password\":\"123456\",\"score\":4,\"create_time\":\"2020-09-11 00:00:00\",\"balance\":8582}},\"message\":\"登录成功！\"}"
//    val res = Gson().fromJson<CommonplaceResult<Account>>(jsonString, CommonplaceResult::class.java)
//    val res = Gson().fromJson<CommonplaceResult<Account>>(jsonString, CommonplaceResult::class.java)
//    println(res.toString())
//    println(res.data.username)

    val root = JSONObject(jsonString)
    val jsonObject = root.getJSONObject("account")
    println(jsonObject)
}