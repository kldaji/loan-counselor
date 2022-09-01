package com.kldaji.data

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import org.json.JSONArray

@ProvidedTypeConverter
class StringListTypeConverter {
    @TypeConverter
    fun stringListToJson(list: List<String>): String {
        val array = JSONArray()
        list.forEach {
            array.put(it)
        }
        return array.toString()
    }

    @TypeConverter
    fun jsonToStringList(json: String): List<String> {
        val list = mutableListOf<String>()
        val array = JSONArray(json)
        val len = array.length()
        for (i in 0 until len) {
            list.add(array.getString(i))
        }
        return list
    }
}
