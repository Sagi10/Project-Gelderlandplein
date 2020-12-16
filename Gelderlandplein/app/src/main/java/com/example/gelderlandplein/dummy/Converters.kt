
package com.example.gelderlandplein.dummy

import android.util.Log
import androidx.room.TypeConverter
import com.example.gelderlandplein.models.Shop
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class Converters {
    @TypeConverter
    fun fromArrayList(list: ArrayList<Shop>): String {
        val gson = Gson()
        Log.d("ITJSON", gson.toJson(list))
        return gson.toJson(list)
    }

    @TypeConverter
    fun fromString(value: String): ArrayList<Shop> {
        val listType: Type = object : TypeToken<ArrayList<Shop>>() {}.type
        Log.d("JSONAR", Gson().fromJson(value, listType))
        return Gson().fromJson(value, listType)
    }
}
