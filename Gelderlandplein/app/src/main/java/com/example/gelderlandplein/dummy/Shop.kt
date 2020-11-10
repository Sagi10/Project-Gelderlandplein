package com.example.gelderlandplein.dummy

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Shop(
    var name: String? = "",
    var tag: String? = "",
    var image: String? = "",
    var openingstijden: Array<String>?,
    var latitude: Float,
    var longitude: Float,
    var inventory: Array<String>?

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