package ir.roohi.farshid.reminderpro

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences

import org.json.JSONObject


/**
 * Created by ImanX.
 * MyZarinPal-Android-V4 | Copyrights 2018 ZarinPal Crop.
 */

class Storage @SuppressLint("CommitPrefEdits")
constructor(context: Context, name: String) {

    private val sp: SharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor

    constructor(context: Context) : this(context, "dev") {}

    init {
        this.editor = sp.edit()
    }

    fun <T> put(clazz: Class<T>, key: String, value: T) {
        editor.putString(key, value.toString())
        editor.apply()
    }


    private fun remove(key: String) {
        editor.remove(key)
        editor.apply()
    }

    operator fun <T> get(clazz: Class<T>, key: String): T? {
        try {
            val resValue = sp.getString(key, null) ?: return null

            if (clazz.isAssignableFrom(String::class.java)) {
                return resValue as T
            }

            if (clazz.isAssignableFrom(Int::class.java) || clazz.isAssignableFrom(Int::class.javaPrimitiveType!!)) {
                return Integer.valueOf(resValue) as T
            }

            if (clazz.isAssignableFrom(Boolean::class.java) || clazz.isAssignableFrom(Boolean::class.javaPrimitiveType!!)) {
                return java.lang.Boolean.valueOf(resValue) as T
            }

            if (clazz.isAssignableFrom(Long::class.java) || clazz.isAssignableFrom(Long::class.javaPrimitiveType!!)) {
                return java.lang.Long.valueOf(resValue) as T
            }

            if (clazz.isAssignableFrom(Float::class.java) || clazz.isAssignableFrom(Float::class.javaPrimitiveType!!)) {
                return java.lang.Float.valueOf(resValue) as T
            }

            if (clazz.isAssignableFrom(JSONObject::class.java)) {
                return JSONObject(resValue) as T
            }


        } catch (ex: Exception) {
            ex.printStackTrace()
        }


        return null
    }


    @Synchronized
    fun removeAll() {
        for (key in sp.all.keys) {
            remove(key)
        }
    }

}
