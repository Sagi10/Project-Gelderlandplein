
package com.example.gelderlandplein.dummy

import androidx.room.TypeConverter
import com.example.gelderlandplein.models.Shop
import com.example.gelderlandplein.models.ShopList
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class Converters {
    @TypeConverter
    fun fromArrayList(list: ArrayList<Shop?>?): String? {
        val gson = Gson()
        return gson.toJson(list)
    }

    @TypeConverter
    fun fromString(value: String?): ArrayList<Shop?>? {
        val listType: Type = object : TypeToken<ArrayList<String?>?>() {}.type
        return Gson().fromJson(value, listType)
    }
}
