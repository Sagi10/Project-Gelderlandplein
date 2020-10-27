package com.example.gelderlandplein.dummy

import android.os.Parcelable
import com.example.gelderlandplein.R
import kotlinx.android.parcel.Parcelize

@Parcelize
class Art(
    var title: String,
    var image: Int
) : Parcelable {
    companion object {
        val artTitles = arrayListOf<String>(
            "Title 1",
            "Title 2",
            "Title 3",
            "Title 4",
            "Title 5",
            "Title 6",
            "Title 7",
            "Title 8",
        )
        val artImages = arrayListOf<Int>(
            R.drawable.artimage1, R.drawable.artimage2,
            R.drawable.artimage1, R.drawable.artimage2,
            R.drawable.artimage1, R.drawable.artimage2,
            R.drawable.artimage1, R.drawable.artimage2
        )
    }
}