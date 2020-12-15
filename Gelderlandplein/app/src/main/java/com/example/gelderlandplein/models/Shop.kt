package com.example.gelderlandplein.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.database.IgnoreExtraProperties
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "shopTable")
@IgnoreExtraProperties
@Parcelize
data class Shop(
    @ColumnInfo(name = "shop")
    var name: String? = "",
    var tag: String? = "",
    var image: String? = "",
    var openingstijden: ArrayList<String>?,
    var latitude: Double,
    var longitude: Double,
    var inventory: ArrayList<String>?,
    var website: String?,

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long? = null

) : Parcelable {

//    @Exclude
//    fun toMap(): Map<String, Any?>{
//        return mapOf(
//            "title" to name,
//            "shop_tag" to tag,
//            "logo" to image
//        )
//    }
}