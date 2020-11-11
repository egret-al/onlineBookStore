package com.rkc.onlinebookstore.util

import android.annotation.SuppressLint
import com.google.gson.*
import java.lang.reflect.Type
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author rkc
 * @date 2020/11/11 9:31
 * @version 1.0
 */
class GsonUtils {

    companion object {
        private val gsonBuilder = GsonBuilder().apply {
            registerTypeAdapter(Date::class.java, object : JsonDeserializer<Date> {
                @SuppressLint("SimpleDateFormat")
                override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): Date? {
                    return SimpleDateFormat("yyyy-MM-dd HH:mm").parse(json?.asString)
                }
            })
        }

        fun getGson() : Gson {
            return gsonBuilder.create()
        }
    }
}
