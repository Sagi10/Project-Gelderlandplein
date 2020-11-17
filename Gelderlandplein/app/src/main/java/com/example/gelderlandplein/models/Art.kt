package com.example.gelderlandplein.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Art(
        var name: String? = "",
        var image: String? = "",
        var beschrijving: String? = "",
        var artist: String? = ""
) : Parcelable {
}