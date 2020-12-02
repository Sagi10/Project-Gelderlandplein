package com.example.gelderlandplein.models

import android.os.Parcelable
import com.google.firebase.database.IgnoreExtraProperties
import kotlinx.android.parcel.Parcelize

@IgnoreExtraProperties
@Parcelize
data class Shop(
    var name: String? = "",
    var tag: String? = "",
    var image: String? = "",
    var openingstijden: ArrayList<String>?,
    var latitude: Double,
    var longitude: Double,
    var inventory: ArrayList<String>?,
    var website: String? = ""

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