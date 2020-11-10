package com.example.gelderlandplein.dummy

import android.os.Parcelable
import com.example.gelderlandplein.R
import kotlinx.android.parcel.Parcelize

@Parcelize
class Art(
        var name: String? = "",
        var image: String? = "",
        var beschrijving: String? = "",
) : Parcelable {
}