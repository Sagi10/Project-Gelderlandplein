package com.example.gelderlandplein.models

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Shop(
    var name: String? = "",
    var tag: String? = "",
    var image: String? = "",
    var openingstijden: ArrayList<String>?,
    var latitude: Float,
    var longitude: Float,
    var inventory: ArrayList<String>?

) {

//    @Exclude
//    fun toMap(): Map<String, Any?>{
//        return mapOf(
//            "title" to name,
//            "shop_tag" to tag,
//            "logo" to image
//        )
//    }
}