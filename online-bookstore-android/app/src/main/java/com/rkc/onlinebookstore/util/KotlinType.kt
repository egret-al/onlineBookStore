package com.rkc.onlinebookstore.util

import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * @author rkc
 * @date 2020/11/14 22:23
 * @version 1.0
 */
class KotlinType {

    companion object {
        fun getType(raw: Class<*>, vararg args: Type) = object : ParameterizedType {
            override fun getRawType(): Type = raw
            override fun getActualTypeArguments(): Array<out Type> = args
            override fun getOwnerType(): Type? = null
        }
    }
}