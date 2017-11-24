package com.axmor.kash.toolset.utils

import android.util.Log
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

/**
 * Created by akolodyazhnyy on 8/28/2017.
 */

/**
 * Json serialize, deserialize logic for object using Moshi.
 */

inline fun <reified T> serialize(obj: T): String {
    val json = Moshi.Builder().build().adapter<T>(T::class.java).toJson(obj)
    Log.i(obj.toString(), "JSON:" + json)
    return json
}

inline fun <reified T> deserialize(json: String): T {
    return Moshi.Builder().build().adapter<T>(T::class.java).fromJson(json)
}

inline fun <reified T> deserializeArray(jsonArray: String): List<T> {
    val moshi = Moshi.Builder().build()
    val type = Types.newParameterizedType(List::class.java, T::class.java)
    val adapter: JsonAdapter<List<T>> = moshi.adapter(type)
    return adapter.fromJson(jsonArray)
}