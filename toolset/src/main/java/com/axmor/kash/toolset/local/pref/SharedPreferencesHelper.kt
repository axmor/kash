package com.axmor.kash.toolset.local.pref

import android.content.SharedPreferences
import android.util.Log
import com.axmor.kash.toolset.utils.deserialize
import com.axmor.kash.toolset.utils.deserializeArray
import com.axmor.kash.toolset.utils.serialize

/**
 * Created by akolodyazhnyy on 8/28/2017.
 */

/**
 * Contains methods to put and get primitives, Strings and Object (using json by Moshi, see JSONUtils)
 * to SharedPreferences.
 */

fun SharedPreferences.removePref(key: String) {
    val editor = edit()
    editor.remove(key)
    editor.apply()
}

inline fun SharedPreferences.putPref(key: String, func: SharedPreferences.Editor.() -> Unit) {
    val editor = edit()
    editor.func()
    editor.apply()
}

//Boolean
fun SharedPreferences.putPrefBool(key: String, value: Boolean) = putPref(key, { putBoolean(key, value) })
fun SharedPreferences.getPrefBool(key: String) = getPrefBool(key, false)
fun SharedPreferences.getPrefBool(key: String, defaultValue: Boolean) = getBoolean(key, defaultValue)

//Long
fun SharedPreferences.putPrefLong(key: String, value: Long) = putPref(key, { putLong(key, value) })
fun SharedPreferences.getPrefLong(key: String) = getPrefLong(key, 0)
fun SharedPreferences.getPrefLong(key: String, defaultValue: Long) = getLong(key, defaultValue)

//String
fun SharedPreferences.putPrefString(key: String, value: String) = putPref(key, { putString(key, value) })
fun SharedPreferences.getPrefString(key: String) = getPrefString(key, "")
fun SharedPreferences.getPrefString(key: String, defaultValue: String) = getString(key, defaultValue)

//Int
fun SharedPreferences.putPrefInt(key: String, value: Int) = putPref(key, { putInt(key, value) })
fun SharedPreferences.getPrefInt(key: String) = getPrefInt(key, 0)
fun SharedPreferences.getPrefInt(key: String, defaultValue: Int) = getInt(key, defaultValue)

//Float
fun SharedPreferences.putPrefFloat(key: String, value: Float) = putPref(key, { putFloat(key, value) })
fun SharedPreferences.getPrefFloat(key: String) = getPrefFloat(key, 0f)
fun SharedPreferences.getPrefFloat(key: String, defaultValue: Float) = getFloat(key, defaultValue)

//ObjectArray
fun SharedPreferences.putObjectArray(key: String, obj: Any) = putPrefString(key, serialize(obj))
inline fun <reified T> SharedPreferences.getObjectArray(key: String) = deserializeArray<T>(getPrefString(key, "{}"))

//Object
inline fun <reified T> SharedPreferences.putObject(key: String, obj: T) = putPrefString(key, serialize<T>(obj))
inline fun <reified T> SharedPreferences.getObject(key: String) = deserialize<T>(getPrefString(key, "{}"))