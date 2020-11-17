package com.example.gelderlandplein.models

import android.os.Parcelable
import com.example.gelderlandplein.R
import kotlinx.android.parcel.Parcelize

@Parcelize
class Event(

    var title: String? = "",
    var image: String? = "",
    var actieGeldig: String? = "",
    var beschrijving: String? = "",
    var link: String? = ""
) : Parcelable {

}